package com.car_service.controller;

import com.car_service.CarService.CarService;
import com.car_service.entities.Car;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // Crear un coche
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car savedCar = carService.saveCar(car);
        return ResponseEntity.status(201).body(savedCar);
    }

    // Listar todos los coches
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        
        return ResponseEntity.ok(cars);
    }

    // Obtener coche por ID
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener coches por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Car>> getCarsByUser(@PathVariable Long userId) {
        List<Car> cars = carService.getCarsByUser(userId);
       
        return ResponseEntity.ok(cars);
    }

    // Eliminar coche
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

   
}
