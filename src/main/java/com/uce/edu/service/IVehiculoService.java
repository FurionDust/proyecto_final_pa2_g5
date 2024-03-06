package com.uce.edu.service;

import java.util.List;

import com.uce.edu.repository.modelo.Vehiculo;
import com.uce.edu.repository.modelo.dto.ReservaDTO;

public interface IVehiculoService {

	public void agregar(Vehiculo vehiculo);

	public void actualizar(Vehiculo vehiculo);

	public void eliminarPorPlaca(String placa);

	public Vehiculo buscarPorPlaca(String placa);

	public List<Vehiculo> buscarPorMarcaModelo(String marca, String modelo);

	public void actualizarEstado(String placa, String nuevoEstado);

	public List<Vehiculo> buscarTodos();

	public ReservaDTO retirarReserva(Integer numReserva);

}
