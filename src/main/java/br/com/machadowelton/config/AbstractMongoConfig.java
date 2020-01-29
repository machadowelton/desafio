package br.com.machadowelton.config;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

import lombok.Data;

@Data
public abstract class AbstractMongoConfig {

  private String host, database;
  private int port;

  public MongoDbFactory mongoDbFactory() {
    return new SimpleMongoDbFactory(getMongoClient(), database);
  }

  /*
   * Method that creates MongoClient
   */
  private MongoClient getMongoClient() {
    return new MongoClient(host, port);
  }

  abstract public MongoTemplate getMongoTemplate();
}