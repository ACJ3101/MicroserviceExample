package com.motorbike_service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motorbike_service.entities.Motorbike;

public interface MotorbikeRepository extends JpaRepository<Motorbike, Long> {
	
	 List<Motorbike> findByUserId(Long userId);

}
