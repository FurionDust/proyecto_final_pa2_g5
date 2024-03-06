package com.uce.edu.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.edu.repository.IReservaRepository;
import com.uce.edu.repository.IVehiculoRepository;
import com.uce.edu.repository.modelo.Cliente;
import com.uce.edu.repository.modelo.Cobro;
import com.uce.edu.repository.modelo.Reserva;
import com.uce.edu.repository.modelo.Vehiculo;
import com.uce.edu.repository.modelo.dto.ReservaClienteRedirectDTO;
import com.uce.edu.repository.modelo.dto.ReservaClienteVehiculoDTO;
import com.uce.edu.repository.modelo.dto.ReservaDTO;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
public class ReservaServiceImpl implements IReservaService {

	@Autowired
	private IReservaRepository iReservaRepository;
	@Autowired
	private IVehiculoRepository iVehiculoRepository;
	@Autowired
	private IClienteService clienteService;

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void agregar(Reserva reserva) {
		this.iReservaRepository.insertar(reserva);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void generar(String placa, String cedula, LocalDate fechaIn, LocalDate fechaFn) {
		if (fechaIn.isBefore(LocalDate.now())) {
		} else {
			List<Reserva> listReser = this.iReservaRepository.seleccionarFechasPorPlaca(placa);
			List<LocalDate> listFechaInicio = new ArrayList<>();
			List<LocalDate> listFechaFin = new ArrayList<>();
			for (Reserva reserva : listReser) {
				listFechaInicio.add(reserva.getFechaInicio());
				listFechaFin.add(reserva.getFechaFin());
			}
			Collections.sort(listFechaInicio, Collections.reverseOrder());
			Collections.sort(listFechaFin, Collections.reverseOrder());
			if (listReser.isEmpty()) {
				// Crear
				this.agregar(crearObjectoReserva(placa, cedula, fechaIn, fechaFn));
			} else {
				if (listReser.size() > 1) {
					boolean auxFecha = false;
					for (int i = listFechaInicio.size() - 1; i > 0; i--) {
						if (fechaIn.isAfter(listFechaFin.get(i)) && fechaFn.isBefore(listFechaInicio.get(i - 1))) {
							auxFecha = true;
							break;
						}
					}
					if (auxFecha) {
						// Crear
						this.agregar(crearObjectoReserva(placa, cedula, fechaIn, fechaFn));
					} else {

						if (fechaIn.isAfter(listFechaFin.get(0))) {
							// Crear
							this.agregar(crearObjectoReserva(placa, cedula, fechaIn, fechaFn));
						}
					}
				} else {
					if (fechaIn.isAfter(listFechaFin.get(listFechaFin.size() - 1))) {
						// Crear
						this.agregar(crearObjectoReserva(placa, cedula, fechaIn, fechaFn));
					}
				}
			}
		}
	}

	@Transactional(value = TxType.REQUIRED)
	public Reserva crearObjectoReserva(String placa, String cedula, LocalDate fechaIn, LocalDate fechaFn) {
		Vehiculo vehiculo = this.iVehiculoRepository.seleccionarPorPlaca(placa);
		Cliente cliente = this.clienteService.buscarPorCedula(cedula);

		long tiempo = Math.max(1, fechaIn.until(fechaFn, ChronoUnit.DAYS));
		BigDecimal valorSubTotal = vehiculo.getValorPorDia().multiply(new BigDecimal(tiempo));
		BigDecimal valorIce = (valorSubTotal.multiply(new BigDecimal(15)).divide(new BigDecimal(100)));
		BigDecimal valorTotalPagar = valorSubTotal.add(valorIce);

		Reserva reserva = new Reserva();
		reserva.setPlacaVehiculo(placa);
		reserva.setCedulaCliente(cedula);
		reserva.setFechaInicio(fechaIn);
		reserva.setFechaFin(fechaFn);
		reserva.setValorSubTotal(valorSubTotal);
		reserva.setValorIce(valorIce);
		reserva.setValorTotalAPagar(valorTotalPagar);
		reserva.setEstado(cedula);
		reserva.setCliente(cliente);
		reserva.setVehiculo(vehiculo);

		Cobro cobro = new Cobro();
		cobro.setNumeroTarjeta("123456765432");
		cobro.setFechaDeCobro(LocalDate.now());
		cobro.setReserva(reserva);

		reserva.setCobro(cobro);
		return reserva;
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public ReservaDTO buscarPorNumReserva(Integer numReserva) {
		try {
			return this.iReservaRepository.seleccionarPorIdDTO(numReserva);
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Reserva> reportesReserva(LocalDate fechaInicio, LocalDate fechaFin) {

		return this.iReservaRepository.seleccionarReservaPorFecha(fechaInicio, fechaFin);
	}

	@Override
	@Transactional(TxType.NOT_SUPPORTED)
	public boolean estaDisponible(String placa, LocalDate fechaInicio, LocalDate fechaFin) {

		boolean estaDisponible = true;
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			reservas = iReservaRepository.seleccionarPorPlacaYFechas(placa, fechaInicio, fechaFin);
		} catch (Exception e) {
			estaDisponible = true;
		}

		estaDisponible = reservas.size() == 0;

		return estaDisponible;
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public ReservaClienteRedirectDTO reservarVehiculo(ReservaClienteVehiculoDTO dto) {

		String redirect = "redirect:/clientes/no-disponible";
		Reserva reserva = new Reserva();

		if (this.estaDisponible(dto.getPlacaVehiculo(), dto.getFechaInicio(), dto.getFechaFin())) {

			redirect = "redirect:/clientes/cobro";

			Vehiculo vehiculo = this.iVehiculoRepository.seleccionarPorPlaca(dto.getPlacaVehiculo());
			Cliente cliente = this.clienteService.buscarPorCedula(dto.getCedulaCliente());

			long tiempo = Math.max(1, dto.getFechaInicio().until(dto.getFechaFin(), ChronoUnit.DAYS));
			BigDecimal valorSubTotal = vehiculo.getValorPorDia().multiply(new BigDecimal(tiempo));
			BigDecimal valorIce = (valorSubTotal.multiply(new BigDecimal(15)).divide(new BigDecimal(100)));
			BigDecimal valorTotalPagar = valorSubTotal.add(valorIce);

			reserva = new Reserva();
			reserva.setCedulaCliente(dto.getCedulaCliente());
			reserva.setCliente(cliente);
			reserva.setEstado("Generada (G)");
			reserva.setFechaInicio(dto.getFechaInicio());
			reserva.setFechaFin(dto.getFechaFin());
			reserva.setVehiculo(vehiculo);
			reserva.setPlacaVehiculo(dto.getPlacaVehiculo());
			reserva.setValorSubTotal(valorSubTotal);
			reserva.setValorIce(valorIce);
			reserva.setValorTotalAPagar(valorTotalPagar);

		}

		ReservaClienteRedirectDTO dto2 = new ReservaClienteRedirectDTO(reserva, redirect);
		return dto2;
	}

	@Override
	public Reserva buscarPorId(Integer Id) {
		return iReservaRepository.seleccionarPorId(Id);
	}

	@Override
	public List<Reserva> buscarTodo() {
		return this.iReservaRepository.seleccionarTodo();
	}

	@Override
	public LocalDate buscarPorPlacaUltimaFecha(String placa) {
		return this.iReservaRepository.seleccionarPorPlacaUltimaFecha(placa);
	}

}
