package com.space_yellow_duck.miniproject.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.space_yellow_duck.miniproject.Entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);

}