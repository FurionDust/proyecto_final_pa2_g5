package com.uce.edu.service;

import com.uce.edu.repository.modelo.Cliente;

public interface IClienteService {

	public void agregar(Cliente cliente);

	public void actualizar(Cliente cliente);

	public void borrarPorCedula(String cedula);

	public Cliente buscarPorCedula(String cedula);

}
