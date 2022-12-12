package com.toolkit.inventory.Security.Controller;

import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Dto.UserDto;
import com.toolkit.inventory.Security.Service.UserService;
import com.toolkit.inventory.Security.Utility.JWTTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;

    public UserController(UserService userService,
                          JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        User loginUser = userService.(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PutMapping("/users")
    public UserDto save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

}
