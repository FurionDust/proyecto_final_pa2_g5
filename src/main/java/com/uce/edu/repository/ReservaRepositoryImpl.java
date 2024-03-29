package com.uce.edu.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.uce.edu.repository.modelo.Reserva;
import com.uce.edu.repository.modelo.dto.ReservaDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Transactional
@Repository
public class ReservaRepositoryImpl implements IReservaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void insertar(Reserva reserva) {
		this.entityManager.persist(reserva);
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public List<Reserva> seleccionarFechasPorPlaca(String placa) {
		TypedQuery<Reserva> query = this.entityManager
				.createQuery("SELECT r FROM Reserva r WHERE r.placaVehiculo =: datoPlaca", Reserva.class);
		query.setParameter("datoPlaca", placa);
		return query.getResultList();
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public Reserva seleccionarPorId(Integer id) {
		return this.entityManager.find(Reserva.class, id);
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public ReservaDTO seleccionarPorIdDTO(Integer numReserva) {
		TypedQuery<ReservaDTO> query = this.entityManager.createQuery(
				"SELECT NEW com.uce.edu.repository.modelo.dto.ReservaDTO(r.vehiculo.placa,r.vehiculo.modelo,r.vehiculo.estado,r.fechaInicio,r.fechaFin,r.cedulaCliente)"
						+ " FROM Reserva r WHERE r.id =: numReserva",
				ReservaDTO.class);
		query.setParameter("numReserva", numReserva);
		return query.getSingleResult();
	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void actualizar(Reserva reserva) {
		this.entityManager.merge(reserva);
	}

	// 3a
	@Override
	public List<Reserva> seleccionarReservaPorFecha(LocalDate fehaInicio, LocalDate fechaFin) {

		TypedQuery<Reserva> myQuery = this.entityManager.createQuery(
				"SELECT r FROM Reserva r WHERE r.fechaInicio >= :datoInicio AND r.fechaFin <= :datoFin ", Reserva.class);
		myQuery.setParameter("datoInicio", fehaInicio);
		myQuery.setParameter("datoFin", fechaFin);
		return myQuery.getResultList();
	}

	@Override
	public List<String> seleccionPlacasVehiculos() {

		TypedQuery<String> myQuery = this.entityManager.createQuery(
				"SELECT r.placaVehiculo FROM Reserva r GROUP BY r.placaVehiculo ORDER BY r.placaVehiculo",
				String.class);
		return myQuery.getResultList();
	}

	@Override
	public List<String> buscarCedulas() {

		TypedQuery<String> myQuery = this.entityManager.createQuery(
				"SELECT r.cedulaCliente FROM Reserva r GROUP BY r.cedulaCliente ORDER BY r.cedulaCliente",
				String.class);
		return myQuery.getResultList();
	}

	@Override

	public List<Reserva> seleccionarPorPlacaYFechas(String pl, LocalDate fehaInicio, LocalDate fechaFin) {

		String jpql = "SELECT r FROM Reserva r " + "WHERE r.placaVehiculo = :datoPl AND("
				+ "(r.fechaInicio >= :datoInicio AND r.fechaInicio <= :datoFin) "
				+ "OR (r.fechaFin >= :datoInicio AND r.fechaFin <= :datoFin) "
				+ "OR (r.fechaInicio <= :datoInicio AND r.fechaFin >= :datoInicio) "
				+ "OR (r.fechaInicio <= :datoFin AND r.fechaFin >= :datoFin)) ";

		TypedQuery<Reserva> myQuery = this.entityManager.createQuery(jpql, Reserva.class);
		myQuery.setParameter("datoInicio", fehaInicio);
		myQuery.setParameter("datoFin", fechaFin);
		myQuery.setParameter("datoPl", pl);

		List<Reserva> reservas = myQuery.getResultList();

		return reservas;
	}

	@Override
	public List<Reserva> seleccionarTodo() {
		TypedQuery<Reserva> myQuery = this.entityManager.createQuery("SELECT r FROM Reserva r ", Reserva.class);

		return myQuery.getResultList();
	}

	@Override
	public LocalDate seleccionarPorPlacaUltimaFecha(String placa) {
		TypedQuery<Reserva> mTypedQuery = this.entityManager
				.createQuery("SELECT r FROM Reserva r WHERE r.placaVehiculo =:datoPlacaVehiculo", Reserva.class);
		mTypedQuery.setParameter("datoPlacaVehiculo", placa);
		List<LocalDate> listaFecha = new ArrayList<>();

		for (Reserva reserIt : mTypedQuery.getResultList()) {
			listaFecha.add(reserIt.getFechaFin());
		}
		Collections.sort(listaFecha, Collections.reverseOrder());

		return listaFecha.get(0);
	}

}
