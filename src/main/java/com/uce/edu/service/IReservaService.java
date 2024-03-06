package com.uce.edu.service;

import java.time.LocalDate;
import java.util.List;

import com.uce.edu.repository.modelo.Reserva;
import com.uce.edu.repository.modelo.dto.ReservaClienteRedirectDTO;
import com.uce.edu.repository.modelo.dto.ReservaClienteVehiculoDTO;
import com.uce.edu.repository.modelo.dto.ReservaDTO;

public interface IReservaService {

	public void agregar(Reserva reserva);

	public void generar(String placa, String cedula, LocalDate fechaIn, LocalDate fechaFn);

	public ReservaDTO buscarPorNumReserva(Integer numReserva);

	public List<Reserva> reportesReserva(LocalDate fechaInicio, LocalDate fechaFin);

	public boolean estaDisponible(String placa, LocalDate fechaInicio, LocalDate fechaFin);

	public ReservaClienteRedirectDTO reservarVehiculo(ReservaClienteVehiculoDTO dto);

	public Reserva buscarPorId(Integer Id);

	public List<Reserva> buscarTodo();

	public LocalDate buscarPorPlacaUltimaFecha(String placa);
}
