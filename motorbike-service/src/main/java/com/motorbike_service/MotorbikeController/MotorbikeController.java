package com.motorbike_service.MotorbikeController;

import com.motorbike_service.MotorbikeService.MotorbikeService;
import com.motorbike_service.entities.Motorbike;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motorbikes")
public class MotorbikeController {

    private final MotorbikeService motorbikeService;

    public MotorbikeController(MotorbikeService motorbikeService) {
        this.motorbikeService = motorbikeService;
    }

    // Crear una moto
    @PostMapping
    public ResponseEntity<Motorbike> createMotorbike(@RequestBody Motorbike motorbike) {
        Motorbike savedMotorbike = motorbikeService.saveMotorbike(motorbike);
        return ResponseEntity.status(201).body(savedMotorbike);
    }

    // Listar todas las motos
    @GetMapping
    public ResponseEntity<List<Motorbike>> getAllMotorbikes() {
        List<Motorbike> bikes = motorbikeService.getAllMotorbikes();
        
        return ResponseEntity.ok(bikes);
    }

    // Obtener moto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Motorbike> getMotorbikeById(@PathVariable Long id) {
        return motorbikeService.getMotorbikeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener motos por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Motorbike>> getMotorbikesByUser(@PathVariable Long userId) {
        List<Motorbike> bikes = motorbikeService.getMotorbikesByUser(userId);
       
        return ResponseEntity.ok(bikes);
    }

   

    // Eliminar moto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotorbike(@PathVariable Long id) {
        motorbikeService.deleteMotorbike(id);
        return ResponseEntity.noContent().build();
    }
}
