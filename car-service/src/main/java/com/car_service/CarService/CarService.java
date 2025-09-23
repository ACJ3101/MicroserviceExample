package com.car_service.CarService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.car_service.CarRepository.CarRepository;
import com.car_service.entities.Car;

@Service
public class CarService {
	
	private CarRepository carRepository;
	
	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	
	 public Car saveCar(Car car) {
	        return carRepository.save(car);
	    }

	    public List<Car> getAllCars() {
	        return carRepository.findAll();
	    }

	    public Optional<Car> getCarById(Long id) {
	        return carRepository.findById(id);
	    }

	    public List<Car> getCarsByUser(Long userId) {
	        return carRepository.findByUserId(userId);
	    }

	    public void deleteCar(Long id) {
	        carRepository.deleteById(id);
	    }
}
