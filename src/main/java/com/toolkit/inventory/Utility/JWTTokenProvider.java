package com.toolkit.inventory.Utility;

import org.springframework.beans.factory.annotation.Value;

public class JWTTokenProvider {

  @Value("jwt.secret")
  private String secret;


}
