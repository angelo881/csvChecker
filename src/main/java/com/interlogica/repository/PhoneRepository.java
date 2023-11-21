package com.interlogica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interlogica.data.PhoneNumber;

public interface PhoneRepository extends JpaRepository<PhoneNumber, String> {

	

}
