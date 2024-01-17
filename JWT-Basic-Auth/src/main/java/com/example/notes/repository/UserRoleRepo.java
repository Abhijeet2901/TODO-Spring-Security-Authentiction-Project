package com.example.notes.repository;


import com.example.notes.model.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends MongoRepository<UserRole,String> {
    @Query(value = "{'userId':?0}")
    UserRole findByUserId(Long userId);
}
