package com.cluz.stocktracker.repository;


import com.cluz.stocktracker.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	UserDetails findByEmail(String email);
}
