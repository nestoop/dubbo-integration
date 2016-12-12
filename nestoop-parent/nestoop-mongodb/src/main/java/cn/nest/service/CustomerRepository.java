package cn.nest.service;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by botter
 *
 * @Date 13/11/16.
 * @description
 */
public interface CustomerRepository extends MongoRepository<String, String> {

    String findPassword(String userName);
}
