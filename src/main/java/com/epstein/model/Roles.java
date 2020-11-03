package com.epstein.model;

import com.epstein.entity.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Roles extends HashSet<Role> {

    public Roles(Collection<Role> roles) {
       // this.roles = new HashSet<>(roles);
        this.addAll( roles );
    }

    public boolean hasRole( Role role ) {
//        return this.roles.stream()
//                .anyMatch(r -> r.getRole().equals(role.getRole()));
        return this.stream()
                .anyMatch(r -> r.getRole().equals(role.getRole()));
    }
    public boolean hasRole( String role ) {
        Role r = new Role(role);
        return this.hasRole(r);
    }

    public boolean hasOnlyRole( Role role ) {
        return this.size() == 1 && this.hasRole(role);
    }
    public boolean hasOnlyRole( String role ) {
        return this.hasOnlyRole( new Role(role) );
    }

    public boolean hasAnyRole(Roles roles ) {
        for (Role role : roles) {
            if(this.hasRole(role) )
                return true;
        }
        return false;
    }

//    public Set<Role> getSet() {
//        return this.roles;
//    }

    public Roles remove(Role role) {
        super.remove(role);
        return this;
    }
    public Roles remove(String role) {
        return this.remove( new Role(role) );
    }

    public String[] toArray() {
        String[] names = new String[this.size()];
        int i = 0;

        for (Role role : this) {
            names[i++] = role.getRole();
        }

        return names;
    }

    @Override
    public String toString() {
        String str = "";
        for (Role role : this) {
            str += role.getRole() + ", ";
        }
        str = str.substring(0, str.lastIndexOf(','));
        return str;
    }
}
