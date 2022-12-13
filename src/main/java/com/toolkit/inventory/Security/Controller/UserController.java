package com.toolkit.inventory.Security.Controller;

import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Dto.ResponseDto;
import com.toolkit.inventory.Security.Dto.UserDto;
import com.toolkit.inventory.Security.Service.UserService;
import com.toolkit.inventory.Security.Utility.JWTTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;

    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {

       ResponseDto responseDto = userService.login(user);

       return new ResponseEntity<>(responseDto.getUser(), responseDto.getHttpHeaders(), OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE')")
    @PutMapping("/users")
    public UserDto save(@RequestBody UserDto userDto) {

        return userService.save(userDto);

    }

}
