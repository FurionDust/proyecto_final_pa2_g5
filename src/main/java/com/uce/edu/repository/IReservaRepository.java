package com.uce.edu.repository;

import java.time.LocalDate;
import java.util.List;

import com.uce.edu.repository.modelo.Reserva;
import com.uce.edu.repository.modelo.dto.ReservaDTO;

public interface IReservaRepository {

	public void insertar(Reserva reserva);

	public void actualizar(Reserva reserva);

	public List<Reserva> seleccionarFechasPorPlaca(String placa);

	public Reserva seleccionarPorId(Integer id);

	public ReservaDTO seleccionarPorIdDTO(Integer id);

	public List<Reserva> seleccionarReservaPorFecha(LocalDate fehaInicio, LocalDate fechaFin);

	public List<String> seleccionPlacasVehiculos();

	public List<String> buscarCedulas();

	public List<Reserva> seleccionarPorPlacaYFechas(String placa, LocalDate fehaInicio, LocalDate fechaFin);

	public List<Reserva> seleccionarTodo();

	public LocalDate seleccionarPorPlacaUltimaFecha(String placa);

}
