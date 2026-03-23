package com.space_yellow_duck.miniproject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.space_yellow_duck.miniproject.Entity.Address;
import com.space_yellow_duck.miniproject.Entity.User;

public interface AddressRepository extends JpaRepository<Address, Long>{
	List<Address> findAllByUser(User user);
}
