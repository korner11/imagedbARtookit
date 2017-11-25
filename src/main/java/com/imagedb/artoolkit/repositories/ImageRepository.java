package com.imagedb.artoolkit.repositories;

import com.imagedb.artoolkit.entity.Image;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import java.util.List;


public interface ImageRepository extends MongoRepository<Image,String>, QueryDslPredicateExecutor<Image>{
     Image findImageById(String id);
}
