package br.com.machadowelton.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
    basePackages = {
    		"br.com.machadowelton.services.repositories.products",
    		"br.com.machadowelton.services.repositories.common"
    		},
    mongoTemplateRef = "secondaryMongoTemplate"
)
@ConfigurationProperties(prefix = "secondary.mongodb")
public class SecondaryMongoConnection extends AbstractMongoConfig {


  @Override  
  @Bean(name = "secondaryMongoTemplate")
  public MongoTemplate getMongoTemplate() {
    return new MongoTemplate(mongoDbFactory());
  }
}
