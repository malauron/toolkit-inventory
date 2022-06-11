package com.toolkit.inventory.Controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ShutdownController implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @PostMapping("/shutdownToolkit")
  public void shutdownToolkit() {
    ((ConfigurableApplicationContext) applicationContext).close();
  }

  @Override
  public void setApplicationContext(ApplicationContext ctx) throws BeansException {
    this.applicationContext = ctx;
  }
}
