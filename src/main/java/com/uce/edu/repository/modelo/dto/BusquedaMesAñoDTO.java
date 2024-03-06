package com.uce.edu.repository.modelo.dto;

public class BusquedaMesAñoDTO {

	private String mes;
	private String año;

	public BusquedaMesAñoDTO() {
		super();
	}

	public BusquedaMesAñoDTO(String mes, String año) {
		super();
		this.mes = mes;
		this.año = año;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAño() {
		return año;
	}

	public void setAño(String año) {
		this.año = año;
	}

}
