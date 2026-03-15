package com.space_yellow_duck.miniproject.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.space_yellow_duck.miniproject.Entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

}