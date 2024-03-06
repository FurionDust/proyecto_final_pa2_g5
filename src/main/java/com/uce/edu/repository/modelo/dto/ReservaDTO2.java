package com.uce.edu.repository.modelo.dto;

import java.math.BigDecimal;

public class ReservaDTO2 {

	private String cedula;
	private String nombre;
	private BigDecimal subtotal;
	private BigDecimal total;

	public ReservaDTO2() {
		super();
	}

	public ReservaDTO2(String cedula, String nombre, BigDecimal subtotal, BigDecimal total) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.subtotal = subtotal;
		this.total = total;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
