package com.usuario.service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

	private String modelo;
	private String marca;
	private Long userId;
	
}
