package com.uce.edu.repository.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva")
public class Reserva {

	@Id

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reserva")
	@SequenceGenerator(name = "seq_reserva", sequenceName = "seq_reserva", allocationSize = 1)

	@Column(name = "reser_id")
	private Integer id;

	@Column(name = "reser_fecha_inico")
	private LocalDate fechaInicio;

	@Column(name = "reser_fecha_fin")
	private LocalDate fechaFin;

	@Column(name = "reser_valor_sub_total")
	private BigDecimal valorSubTotal;

	@Column(name = "reser_valor_ice")
	private BigDecimal valorIce;

	@Column(name = "reser_valor_total_a_pagar")
	private BigDecimal valorTotalAPagar;

	@Column(name = "reser_estado")
	private String estado;

	@Column(name = "reser_placa_vehiculo")
	private String placaVehiculo;

	@Column(name = "reser_cedula_cliente")
	private String cedulaCliente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reser_id_cliente")

	private Cliente cliente;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reser_id_vehiculo")
	private Vehiculo vehiculo;

	@OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
	private Cobro cobro;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public BigDecimal getValorSubTotal() {
		return valorSubTotal;
	}

	public void setValorSubTotal(BigDecimal valorSubTotal) {
		this.valorSubTotal = valorSubTotal;
	}

	public BigDecimal getValorIce() {
		return valorIce;
	}

	public void setValorIce(BigDecimal valorIce) {
		this.valorIce = valorIce;
	}

	public BigDecimal getValorTotalAPagar() {
		return valorTotalAPagar;
	}

	public void setValorTotalAPagar(BigDecimal valorTotalAPagar) {
		this.valorTotalAPagar = valorTotalAPagar;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public String getCedulaCliente() {
		return cedulaCliente;
	}

	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}

	public Cobro getCobro() {
		return cobro;
	}

	public void setCobro(Cobro cobro) {
		this.cobro = cobro;
	}

}
