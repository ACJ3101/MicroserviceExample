package com.usuario.service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entities.User;
import com.usuario.service.models.Car;
import com.usuario.service.models.Motorbike;
import com.usuario.service.repository.UserRepository;
import com.usuario.service.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuarios")  
public class UserController {

    private final UserRepository userRepository;
	
	private final UserService userService;
	
	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> listUsers(){
		List<User> userList = userService.getAllUsers();
		if (userList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(userList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		User user = userService.getUserById(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}
	
	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User user){
	    User newUser = userService.saveUser(user);
	    return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
	    userService.deleteUser(id);
	    return ResponseEntity.noContent().build(); // 204 No Content
	}
	
//	@GetMapping("cars/{userId}")
//	public ResponseEntity<List<Car>> listCars(@PathVariable Long userId){
//		User user = userService.getUserById(userId);
//		if (user == null) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		List<Car> cars = userService.getCars(userId);
//		if (cars.isEmpty() || cars == null) {
//			return ResponseEntity.noContent().build();
//		}
//		
//		return ResponseEntity.ok(cars);
//	}
//	
//	@GetMapping("motorbikes/{userId}")
//	public ResponseEntity<List<Car>> listMotorbike(@PathVariable Long userId){
//		User user = userService.getUserById(userId);
//		if (user == null) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		List<Car> cars = userService.getMotorbike(userId);
//		if (cars.isEmpty() || cars == null) {
//			return ResponseEntity.noContent().build();
//		}
//		
//		return ResponseEntity.ok(cars);
//	}
	
	@CircuitBreaker(name = "carsCB",fallbackMethod = "fallBackSaveCar")
	@PostMapping("/cars/{userId}")
	public ResponseEntity<Car> saveCar(@PathVariable("userId") Long userId, @RequestBody Car car) {
	    Car newCar = userService.saveCar(userId, car);
	    return ResponseEntity.ok(newCar);
	}
	@CircuitBreaker(name = "carsCB",fallbackMethod = "fallBackGetCar")
	@GetMapping("/cars/{userId}")
	public ResponseEntity<List<Car>> getCarsById(@PathVariable("userId") Long userId){
		List<Car> listCars =  userService.getCars(userId);
		return ResponseEntity.ok(listCars);
	}
	
	@CircuitBreaker(name = "motorbikesCB",fallbackMethod = "fallBackSaveMotorbike")
	@PostMapping("/motorbikes/{userId}")
	public ResponseEntity<Motorbike> saveMotorbike(@PathVariable("userId") Long userId, @RequestBody Motorbike motorbike) {
		Motorbike newMotorbike = userService.saveMotorbike(userId, motorbike);
		return ResponseEntity.ok(newMotorbike);
	}
	@CircuitBreaker(name = "motorbikesCB",fallbackMethod = "fallBackGetMotorbike")
	@GetMapping("/motorbikes/{userId}")
	public ResponseEntity<List<Motorbike>> getMotorbikeById(@PathVariable("userId") Long userId){
		List<Motorbike> listMotorbike=  userService.getMotorbikes(userId);
		return ResponseEntity.ok(listMotorbike);
	}
	@CircuitBreaker(name = "allCB",fallbackMethod = "fallBackGetAll")
	@GetMapping("/all/{userId}")
	public ResponseEntity<Map<String, Object>> getVehiclesByUserId(@PathVariable("userId") Long userId){
		Map<String, Object> result =  userService.getUserAndVehicles(userId);
		return ResponseEntity.ok(result);
	}

	  private ResponseEntity<List<Car>> fallBackGetCar(Long userId, Throwable exception) {
	        return new ResponseEntity(
	                "El usuario " + userId + " no tiene operativo ese coche",
	                HttpStatus.OK
	        );
	    }
	
	  private ResponseEntity<Object> fallBackSaveCar(Long userId, Car car, Throwable exception) {
		    // Aquí puedes devolver un mensaje o incluso datos dummy
		    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
		                         .body("No es posible guardar el coche del usuario con id: " + userId + 
		                               ". Servicio no disponible, se usó fallback.");
		}
	  private ResponseEntity<List<Motorbike>> fallBackGetMotorbike(Long userId, Throwable exception) {
		  return new ResponseEntity(
				  "El usuario " + userId + " no tiene operativo esa moto",
				  HttpStatus.OK
				  );
	  }
	  
	  private ResponseEntity<Object> fallBackSaveMotorbike(Long userId, Motorbike motorbike, Throwable exception) {
		    // Aquí puedes devolver un mensaje o incluso datos dummy
		    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
		                         .body("No es posible guardar la moto del usuario con id: " + userId + 
		                               ". Servicio no disponible, se usó fallback.");
		}
	  private ResponseEntity<Map<String, Object>> fallBackGetAll(Long userId, Throwable exception) {
		  return new ResponseEntity(
				  "No es posible usar ese servicio para el usuario con id : "+ userId,
				  HttpStatus.OK
				  );
	  }
	  
	
	
				

}
