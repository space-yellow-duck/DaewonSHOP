package com.space_yellow_duck.miniproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.Entity.Address;
import com.space_yellow_duck.miniproject.Entity.User;
import com.space_yellow_duck.miniproject.Repository.AddressRepository;

@Service
public class AddressService {
	private final AddressRepository addressRepository;
	public AddressService(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}
	public List<Address> getAddresses(User user){
		
		return addressRepository.findAllByUser(user);
	}
}
