package com.uce.edu.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uce.edu.repository.modelo.Vehiculo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Transactional
@Repository
public class VehiculoRepositoryImpl implements IVehiculoRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void ingresar(Vehiculo vehiculo) {
		this.entityManager.persist(vehiculo);

	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void actualizar(Vehiculo vehiculo) {

		this.entityManager.merge(vehiculo);
	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void eliminarPorPlaca(String placa) {
		Vehiculo vehiculoAux = this.seleccionarPorPlaca(placa);
		this.entityManager.remove(vehiculoAux);

	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public Vehiculo seleccionarPorPlaca(String placa) {
		TypedQuery<Vehiculo> query = this.entityManager
				.createQuery("SELECT v FROM Vehiculo v WHERE v.placa =: datoPlaca", Vehiculo.class);
		query.setParameter("datoPlaca", placa);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Vehiculo> seleccionarTodos() {
		TypedQuery<Vehiculo> myQuery = this.entityManager.createQuery("SELECT v FROM Vehiculo v", Vehiculo.class);

		return myQuery.getResultList();
	}

	@Override
	public List<Vehiculo> seleccionarPorMarcaModelo(String marca, String modelo) {
		TypedQuery<Vehiculo> query = this.entityManager.createQuery(
				"SELECT v FROM Vehiculo v WHERE v.marca =:datoMarca AND v.modelo =:datoModelo", Vehiculo.class);
		query.setParameter("datoModelo", modelo);
		query.setParameter("datoMarca", marca);
		return query.getResultList();
	}

}
