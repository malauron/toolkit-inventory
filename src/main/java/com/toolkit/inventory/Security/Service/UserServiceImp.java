package com.toolkit.inventory.Security.Service;

import com.toolkit.inventory.Security.Domain.Role;
import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Dto.ResponseDto;
import com.toolkit.inventory.Security.Dto.UserDto;
import com.toolkit.inventory.Security.Projection.UserView;
import com.toolkit.inventory.Security.Repository.RoleRepository;
import com.toolkit.inventory.Security.Repository.UserRepository;
import com.toolkit.inventory.Security.Utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.toolkit.inventory.Security.Utility.SecurityParams.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@Service
public class UserServiceImp implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImp(RoleRepository roleRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JpaUserDetailsService jpaUserDetailsService,
                          AuthenticationManager authenticationManager,
                          JWTTokenProvider jwtTokenProvider) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;

    }

    @Override
    public Page<UserView> findUsers(int page, int size, String searchDesc){
        Pageable pageable = PageRequest.of(page, size);
        return this.userRepository.findUsers(searchDesc, pageable);
    }
    @Override
    public ResponseDto login(User user) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        User loginUser = userRepository.findByUsername(user.getUsername()).get();

        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(user.getUsername());

        HttpHeaders jwtHeader = new HttpHeaders();
        jwtHeader.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userDetails));

        ResponseDto responseDto = new ResponseDto();
        responseDto.setUser(loginUser);
        responseDto.setHttpHeaders(jwtHeader);

        return responseDto;
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
