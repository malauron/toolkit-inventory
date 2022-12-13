package com.toolkit.inventory.Security.Service;

import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Dto.ResponseDto;
import com.toolkit.inventory.Security.Dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    UserDto save(UserDto userDto);
    ResponseDto login(User user);
}
