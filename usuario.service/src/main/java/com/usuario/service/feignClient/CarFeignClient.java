package com.usuario.service.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.service.models.Car;

@FeignClient(name = "car-service",url = "http://localhost:8083")
public interface CarFeignClient{
	
	@PostMapping("/cars")
	public Car save(@RequestBody Car car);
	
	@GetMapping("/cars/user/{userId}")
	public List<Car> getCars(@PathVariable("userId") Long userId);

}
