package com.imagedb.artoolkit.repositories;

import com.imagedb.artoolkit.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;



public interface ImageRepository extends MongoRepository<Image,String>, QueryDslPredicateExecutor<Image>{
     Image findImageById(String id);
}
