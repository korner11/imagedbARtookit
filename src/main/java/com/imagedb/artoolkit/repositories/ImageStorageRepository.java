package com.imagedb.artoolkit.repositories;

import com.imagedb.artoolkit.entity.Image;
import com.imagedb.artoolkit.entity.ImageStorage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;


public interface ImageStorageRepository extends MongoRepository<ImageStorage,String>{
     ImageStorage findImageById(String id);
}
