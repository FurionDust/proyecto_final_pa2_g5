package com.uce.edu.repository.modelo.dto;

import java.time.LocalDate;

public class ReservaClienteVehiculoDTO {
	private String cedulaCliente;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String placaVehiculo;

	private String redirect;

	public ReservaClienteVehiculoDTO() {
		super();
	}

	public ReservaClienteVehiculoDTO(String cedulaCliente, LocalDate fechaInicio, LocalDate fechaFin,
			String placaVehiculo, String redirect) {
		super();
		this.cedulaCliente = cedulaCliente;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.placaVehiculo = placaVehiculo;
		this.redirect = redirect;
	}

	public String getCedulaCliente() {
		return cedulaCliente;
	}

	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
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

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

}
