package com.motorbike_service.MotorbikeService;



import com.motorbike_service.Repository.MotorbikeRepository;
import com.motorbike_service.entities.Motorbike;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorbikeService {

    private final MotorbikeRepository motorbikeRepository;

    public MotorbikeService(MotorbikeRepository motorbikeRepository) {
        this.motorbikeRepository = motorbikeRepository;
    }

    public Motorbike saveMotorbike(Motorbike motorbike) {
        return motorbikeRepository.save(motorbike);
    }

    public List<Motorbike> getAllMotorbikes() {
        return motorbikeRepository.findAll();
    }

    public Optional<Motorbike> getMotorbikeById(Long id) {
        return motorbikeRepository.findById(id);
    }

    public List<Motorbike> getMotorbikesByUser(Long userId) {
        return motorbikeRepository.findByUserId(userId);
    }

    public void deleteMotorbike(Long id) {
        motorbikeRepository.deleteById(id);
    }
}
