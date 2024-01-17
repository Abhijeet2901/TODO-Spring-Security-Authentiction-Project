package com.example.notes.service;

import com.example.notes.model.Role;
import com.example.notes.model.User;
import com.example.notes.model.UserRole;
import com.example.notes.repository.RoleRepository;
import com.example.notes.repository.UserRepository;
import com.example.notes.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailsDAO {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepo userRoleRepo;

    @Autowired
    RoleRepository roleRepository;

    public org.springframework.security.core.userdetails.User loadUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found, for given email: " + email);
        }

        UserRole userRole = userRoleRepo.findByUserId(user.getId());
        Set<Long> roleIds = userRole.getRoleIds();

        Set<Role> roles = new HashSet<>();
        roleIds.stream().forEach(roleId -> {
            Role role = roleRepository.findById(roleId).get();
            roles.add(role);
        });
        user.setRoles(roles);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
    }
}
