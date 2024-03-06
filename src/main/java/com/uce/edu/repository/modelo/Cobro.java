package com.uce.edu.repository.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "cobro")
public class Cobro {

	@Id
	@GeneratedValue(generator = "seq_cobro", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_cobro", sequenceName = "seq_cobro", allocationSize = 1)

	@Column(name = "cobr_id")
	private Integer id;

	@Column(name = "cobr_numero_tarjeta")
	private String numeroTarjeta;

	@Column(name = "cobr_fecha_de_cobro")
	private LocalDate fechaDeCobro;

	@OneToOne
	@JoinColumn(name = "cobr_id_reserva")
	private Reserva reserva;

	public Cobro() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public LocalDate getFechaDeCobro() {
		return fechaDeCobro;
	}

	public void setFechaDeCobro(LocalDate fechaDeCobro) {
		this.fechaDeCobro = fechaDeCobro;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

}