package com.uce.edu.repository.modelo.dto;

import java.time.LocalDate;


public class ReservaFechasDTO {

    LocalDate fechaInicio;
    LocalDate fechaFin;
	public ReservaFechasDTO(LocalDate fechaInicio, LocalDate fechaFin) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	public ReservaFechasDTO() {
		super();
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
}
