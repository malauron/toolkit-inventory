package com.toolkit.inventory.Security.Service;

import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Dto.ResponseDto;
import com.toolkit.inventory.Security.Dto.UserDto;
import com.toolkit.inventory.Security.Projection.UserView;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface UserService {

    Page<UserView> findUsers(int page, int size, String searchDesc);
    UserDto save(UserDto userDto);
    ResponseDto login(User user);
}
