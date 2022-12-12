package com.toolkit.inventory.Security.Service;

import com.toolkit.inventory.Security.Domain.Role;
import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Dto.UserDto;
import com.toolkit.inventory.Security.Repository.RoleRepository;
import com.toolkit.inventory.Security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(RoleRepository roleRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {

        Set<Role> roles = roleRepository.findAll().stream().collect(Collectors.toSet());

        Optional<User> usr = userRepository.findByUsername(userDto.getUsername());
        User user;
        if (usr.isPresent()) {
            user = usr.get();
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRoles(roles);
        } else {
            user = User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .roles(roles)
                    .build();
        }
        userRepository.save(user);

        userDto.setPassword(user.getPassword());

        return userDto;
    }

}
