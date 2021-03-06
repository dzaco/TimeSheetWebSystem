package com.epstein.service;

import com.epstein.entity.Department;
import com.epstein.entity.User;
import com.epstein.entity.UserForm;
import com.epstein.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ContractService contractService;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public List<User> getActiveUsers() {
        return userRepository.findAllActive();
    }
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(new User());
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
        existingUser.setRole(user.getRole());
        existingUser.setEmploymentTime(user.getEmploymentTime());
        existingUser.setActive(user.isActive());
        return userRepository.save(existingUser);
    }

    public User getUserFromForm(UserForm form) {
        User user = userRepository.findById(form.getId()).orElse(new User());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setDepartment( departmentService.getDepartmentById(form.getDepartmentId()) );
        user.setPosition(form.getPosition());
        user.setRole(form.getRole());
        user.setContract( contractService.getContractById(form.getContractId()) );
        user.setEmploymentTime(form.getEmploymentTime());
        return user;
    }

    public User getUserFromForm(UserForm form, boolean newUser ) {
        User user = new User();
        user.setId(form.getId());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setDepartment( departmentService.getDepartmentById(form.getDepartmentId()) );
        user.setPosition(form.getPosition());
        user.setRole(form.getRole());
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
        User user = userRepository.save(this.sampleUser());
        return user;
    }

    public List<User> getUsersInDepartment(int departmentId, int active) {
        return this.userRepository.getUsersInDepartment(departmentId,active);
    }

    public List<User> getUserInProject(int projectId) {
        return this.userRepository.getUsersInProject(projectId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);
        return user;
    }
}
