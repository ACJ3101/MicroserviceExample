package com.usuario.service.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entities.User;
import com.usuario.service.feignClient.CarFeignClient;
import com.usuario.service.feignClient.MotorbikeFeignClient;
import com.usuario.service.models.Car;
import com.usuario.service.models.Motorbike;
import com.usuario.service.repository.UserRepository;

@Service
public class UserService {
	
	private RestTemplate restTemplate;
	
	private UserRepository userRepository ;
	
	private CarFeignClient carFeignClient;
	
	private MotorbikeFeignClient motorbikeFeignClient;
	
	public UserService(UserRepository userRepository,RestTemplate restTemplate,CarFeignClient carFeignClient,MotorbikeFeignClient motorbikeFeignClient) {
			this.userRepository =  userRepository; 
			this.restTemplate = restTemplate;
			this.carFeignClient = carFeignClient;
			this.motorbikeFeignClient = motorbikeFeignClient;
	}
	
//	//Con restTemplate implementamos el get de car y motorbike, al final lo hacemos con feign
//	
//	public List<Car> getCars(Long userId){
//		List<Car> cars = restTemplate.getForObject("http://localhost:8083/cars/user/" + userId,	List.class);
//		return cars;
//		
//	}
//	public List<Car> getMotorbike(Long userId){
//		List<Car> cars = restTemplate.getForObject("http://localhost:8082/motorbikes/user/" + userId,	List.class);
//		return cars;
//		
//	}
	
	//Con Feign implementamos los save de car y motorbike y los gets
	public Car saveCar(Long userId,Car car) {
		car.setUserId(userId);
		Car newCar = carFeignClient.save(car);
		return newCar ;
	}
	
	public List<Car> getCars(Long userId) {
		List<Car> carsList = carFeignClient.getCars(userId);
		return carsList;
	}
	
	public Motorbike saveMotorbike(Long userId,Motorbike motorbike) {
		motorbike.setUserId(userId);
		Motorbike newMotorbike = motorbikeFeignClient.save(motorbike);
		return newMotorbike;
	}
	
	public List<Motorbike> getMotorbikes(Long userId) {
		List<Motorbike> motorbikesList = motorbikeFeignClient.getMotorbikes(userId);
		return motorbikesList;
	}
	
	public Map<String, Object> getUserAndVehicles(Long userId){
		Map<String,Object> result = new HashMap<>();
		User user = userRepository.findById(userId).orElse(null);
		
		if(user == null) {
			result.put("Menssage", "El usuario no existe");
			return result;
		}
		
		result.put("User",user);
		List<Car> cars = carFeignClient.getCars(userId);
		if(cars.isEmpty()) {
			result.put("Cars", "El usuario no tiene coches");
		}
		else {
			result.put("Cars", cars);
		}
		
		List<Motorbike> motorbike = motorbikeFeignClient.getMotorbikes(userId);
		if(motorbike.isEmpty()) {
			result.put("Motorbikes", "El usuario no tiene motos");
		}		
		else {
			result.put("Motos", motorbike);
		}
		return result;
	}

	//Resto de implementaciones
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User saveUser(User user) {
		 User newUser = userRepository.save(user);
		 return newUser;
	}
	
	public void deleteUser(Long id) {
		 userRepository.deleteById(id);
	}
	

}
