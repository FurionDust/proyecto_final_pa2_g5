package com.uce.edu.repository;

import java.util.List;

import com.uce.edu.repository.modelo.Cliente;

public interface IClienteRepository {

	public void ingresar(Cliente cliente);

	public void actualizar(Cliente cliente);

	public void eliminarPorCedula(String cedula);

	public Cliente seleccionarPorCedula(String cedula);

	public List<Cliente> seleccionarTodos();

}
