package com.usuario.service.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.service.models.Motorbike;

@FeignClient(name = "motorbike-service")
public interface MotorbikeFeignClient {
	
	@PostMapping("/motorbikes")
	public Motorbike save(@RequestBody Motorbike motorbike);
	
	@GetMapping("/motorbikes/user/{userId}")
	public List<Motorbike> getMotorbikes(@PathVariable("userId") Long userId);

}
