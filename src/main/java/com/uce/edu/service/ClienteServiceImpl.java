package com.uce.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.edu.repository.IClienteRepository;
import com.uce.edu.repository.modelo.Cliente;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteRepository clienteRepository;

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void agregar(Cliente cliente) {

		this.clienteRepository.ingresar(cliente);

	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void actualizar(Cliente cliente) {

		this.clienteRepository.actualizar(cliente);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void borrarPorCedula(String cedula) {

		this.clienteRepository.eliminarPorCedula(cedula);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public Cliente buscarPorCedula(String cedula) {
		try {
			Cliente clienteExistente = this.clienteRepository.seleccionarPorCedula(cedula);
			return clienteExistente;
		} catch (NoResultException e) {

			return null;
		}
	}
}
