package com.uce.edu.repository.modelo.dto;

import java.math.BigDecimal;

public class ReservaDTO3 {

	private String placa;
	private BigDecimal subtotal;
	private BigDecimal total;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
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

	public ReservaDTO3(String placa, BigDecimal subtotal, BigDecimal total) {
		super();
		this.placa = placa;
		this.subtotal = subtotal;
		this.total = total;
	}

	public ReservaDTO3() {
		super();
	}

}
