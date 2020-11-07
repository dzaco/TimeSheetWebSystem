package com.epstein.service;

import com.epstein.entity.Role;
import com.epstein.entity.User;
import com.epstein.dto.UserDTO;
import com.epstein.model.Roles;
import com.epstein.repository.RoleRepository;
import com.epstein.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService  implements UserDetailsService {

    private User logged;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ContractService contractService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getLogged() {
        return logged;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public List<User> getActiveUsers() {
        return userRepository.findAllActive();
    }
    public List<User> getInactiveUsers() { return userRepository.findAllInactive();}
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(new User());
    }
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public String deleteUser(int id) {
        userRepository.deleteById(id);
        return "Użytkownik " + id + " został usunięty";
    }
    
    public String deactivateUser(int id) {
        User user = this.getUserById(id);
        user.setActive(false);
        this.updateUser(user);
        return "Użytkownik "
                + id + ": "
                + user.getFirstName() + " " + user.getLastName()
                + " został dezaktywowany";
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(new User());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setDepartment(user.getDepartment());
        existingUser.setPosition(user.getPosition());
        existingUser.setContract(user.getContract());
        existingUser.setRoles(user.getRolesClass());
        existingUser.setEmploymentTime(user.getEmploymentTime());
        existingUser.setActive(user.isActive());
        return userRepository.save(existingUser);
    }

    public User updateUser(UserDTO userDTO) {
        User user = this.getUserFromForm(userDTO);
        return this.updateUser(user);
    }

    public User getUserFromForm(UserDTO form) {
        User user = userRepository.findById(form.getId()).orElse(new User());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setDepartment( departmentService.getDepartmentById(form.getDepartmentId()) );
        user.setPosition(form.getPosition());
//        Arrays.stream(
//                form.getRoles())
//                .forEach( role -> {
//                    Role entity = roleRepository.findByRole(role);
//                    user.setRole(entity);
//                });
        Roles roles = new Roles(new HashSet<>());
        for (String stringRole : form.getRoles()) {
            Role entityRole = roleRepository.findByRole(stringRole);
            roles.add(entityRole);
        }
        user.setRoles(roles);
        user.setContract( contractService.getContractById(form.getContractId()) );
        user.setEmploymentTime(form.getEmploymentTime());
        user.setActive(true);
        return user;
    }

    public User getUserFromForm(UserDTO form, boolean newUser ) {
        User user = new User();
        user.setId(form.getId());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPassword( this.passwordEncoder.encode(form.getPassword()) );
        user.setDepartment( departmentService.getDepartmentById(form.getDepartmentId()) );
        user.setPosition(form.getPosition());
        Arrays.stream(
                form.getRoles())
                .forEach( role -> {
                    Role entity = roleRepository.findByRole(role);
                    user.setRole(entity);
                });


        user.setContract( contractService.getContractById(form.getContractId()) );
        user.setEmploymentTime(form.getEmploymentTime());
        user.setActive(true);
        return user;
    }

    public  User sampleUser() {
        User user = new User();
        user.setId( this.userRepository.lastId() );
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setPassword("password");
        user.setDepartment(this.departmentService.getDepartmentById(1));
        user.setPosition("position");
        user.setContract(this.contractService.getContractById(1));
        user.setRole("USER");
        user.setEmploymentTime(160);
        user.setActive(true);
        return user;
    }
    public User getNewInstanceOfUser() {
        return userRepository.save(this.sampleUser());
    }

    public List<User> getUsersInDepartment(int departmentId, int active) {
        return this.userRepository.getUsersInDepartment(departmentId,active);
    }

    public List<User> getUsersInProject(int projectId) {
        return this.userRepository.getUsersInProject(projectId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);
        if(user == null) throw new UsernameNotFoundException("Nieprawidłowy email lub hasło");
        Roles roles = new Roles( roleRepository.findByUserId( user.getId() ) );

        user.setRoles(roles);       // todo rozwiazanie dziala ale jest brzydkie - User nie zczytuje poprawnie roles

        this.logged = user;
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                this.mapRolesToAuthorities(roles) );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Roles roles) {
        List<GrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            GrantedAuthority auth = new SimpleGrantedAuthority(role.getRole());
            list.add(auth);
        }
        return list;
    }

    public void passwordReset( int id) {
        String password = this.passwordEncoder.encode("password");
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            user.get().setPassword(password);
            this.updateUser(user.get());
        }
    }
}
