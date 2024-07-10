package org.zerock.b01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.text.Document;

@Configuration
public class SwaggerConfig {

  @Bean
  public Doket api(){
    return new Doket(DocumentationType.0AS_30)
  }
}
