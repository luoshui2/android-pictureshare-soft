package com.example.demo2.database_user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
//    @Query("select s from User s where s.usename = ?1")
//    User findUserByUsername(String name);

}
