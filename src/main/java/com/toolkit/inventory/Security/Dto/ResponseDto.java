package com.toolkit.inventory.Security.Dto;

import com.toolkit.inventory.Security.Domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class ResponseDto {

    User user;
    HttpHeaders httpHeaders;

}
