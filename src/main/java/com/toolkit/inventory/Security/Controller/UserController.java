package com.toolkit.inventory.Security.Controller;

import com.toolkit.inventory.Security.Dto.UserDto;
import com.toolkit.inventory.Security.Service.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/users")
    public UserDto save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

}
