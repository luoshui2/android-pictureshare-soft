package com.example.demo2.database_user_information;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User_informationRepository extends JpaRepository<User_information,String> {

}
