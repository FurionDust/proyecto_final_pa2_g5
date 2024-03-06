package com.uce.edu.repository.modelo.dto;

import com.uce.edu.repository.modelo.Reserva;


public class ReservaClienteRedirectDTO {

    Reserva reserva;

    String redirect;

	public ReservaClienteRedirectDTO(Reserva reserva, String redirect) {
		super();
		this.reserva = reserva;
		this.redirect = redirect;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
    
    
    
}
