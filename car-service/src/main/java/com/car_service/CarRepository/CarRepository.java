package com.car_service.CarRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car_service.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

	//Los metodos que buscan por otras tablas como el usuario id deben ser indicados.
	 List<Car> findByUserId(Long userId);
}
