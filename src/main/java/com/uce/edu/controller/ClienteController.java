package com.uce.edu.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uce.edu.repository.modelo.Cliente;
import com.uce.edu.repository.modelo.Cobro;
import com.uce.edu.repository.modelo.Reserva;
import com.uce.edu.repository.modelo.Vehiculo;
import com.uce.edu.repository.modelo.dto.BusquedaVehiculoDTO;
import com.uce.edu.repository.modelo.dto.ReservaClienteRedirectDTO;
import com.uce.edu.repository.modelo.dto.ReservaClienteVehiculoDTO;
import com.uce.edu.service.IClienteService;
import com.uce.edu.service.IReservaService;
import com.uce.edu.service.IVehiculoService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();

	Reserva reservaHolder;

	BusquedaVehiculoDTO holderBusquedaVehiculoDTO = new BusquedaVehiculoDTO();

	ReservaClienteVehiculoDTO reservaClienteVehiculoDTOHolder = new ReservaClienteVehiculoDTO();

	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IReservaService reservaService;

	@GetMapping("/home")
	public String inicio(Model model) {

		vehiculos = vehiculoService.buscarTodos();
		model.addAttribute("vehiculos", vehiculos);
		return "vistaClientesHome";
	}

	@GetMapping("/vehiculos-disponibles")
	public String cargarVistaVehiculosDisponibles(Model model) {

		model.addAttribute("vehiculos", vehiculos);
		model.addAttribute("dto", holderBusquedaVehiculoDTO);
		return "vistaClientesVehiculosDisponibles";
	}

	@PostMapping("/realizar-busqueda-vehiculos")
	public String buscarVehiculos(@ModelAttribute BusquedaVehiculoDTO dto) {
		vehiculos = vehiculoService.buscarPorMarcaModelo(dto.getMarca(), dto.getModelo());
		return "redirect:/clientes/vehiculos-disponibles";
	}

	@GetMapping("/reservar-vehiculo")
	public String cargarVistaReservarVehiculo(Model model) {

		model.addAttribute("dto", reservaClienteVehiculoDTOHolder);
		return "vistaClientesReservarVehiculo";
	}

	@GetMapping("/registrarse")
	public String cargarVistaClienteRegistrarse(Model model) {

		model.addAttribute("cliente", new Cliente());
		return "vistaClientesRegistrarse";
	}

	@PostMapping("/registrar-cliente")
	public String registrarCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {

		Cliente existeCliente = clienteService.buscarPorCedula(cliente.getCedula());

		if (existeCliente != null) {
			model.addAttribute("error", "Ya existe un cliente registrado con este número de cédula.");
			return "vistaErrorCedulaExiste";
		}

		cliente.setRegistro("Cliente (C)");
		clienteService.agregar(cliente);

		return "redirect:/clientes/registro-exitoso";
	}

	@GetMapping("/registro-exitoso")
	public String cargarVistaRegistroExitoso() {
		return "vistaClienteRegistroExitoso";
	}

	@GetMapping("/error-cedula-placa-inexistente")
	public String handleCedulaPlacaInexistenteError() {
		return "vistaClientesErrorPlacaInexistente";
	}

	@GetMapping("/error-placa-inexistente")
	public String manejarErrorPlacaInexistente(Model model) {
		return "vistaClientesErrorPlacaInexistente";
	}

	@PostMapping("/reservar")
	public String realizarReserva(ReservaClienteVehiculoDTO dto) {

		try {
			Vehiculo vehiculo = vehiculoService.buscarPorPlaca(dto.getPlacaVehiculo());

			if (vehiculo == null) {

				return "redirect:/clientes/error-placa-inexistente";
			}

			Cliente cliente = clienteService.buscarPorCedula(dto.getCedulaCliente());

			if (cliente == null) {

				return "redirect:/clientes/error-cedula-placa-inexistente";
			}
			ReservaClienteRedirectDTO redirectReserva = reservaService.reservarVehiculo(dto);
			reservaClienteVehiculoDTOHolder = dto;
			reservaHolder = redirectReserva.getReserva();

			return redirectReserva.getRedirect();
		} catch (Exception e) {

			return "redirect:/clientes/error-placa-inexistente";
		}
	}

	@GetMapping("/cobro")
	public String paginaCobro(Model model) {

		model.addAttribute("reserva", reservaHolder);

		Cobro cobro = new Cobro();
		cobro.setFechaDeCobro(LocalDate.now());

		model.addAttribute("cobro", cobro);
		return "vistaClientesCobro";
	}

	@PostMapping("/cobrar")
	public String generarCobro(Cobro cobro) {

		cobro.setReserva(reservaHolder);
		reservaHolder.setCobro(cobro);
		reservaService.agregar(reservaHolder);

		reservaHolder = null;
		return "redirect:/clientes/exito";
	}

	@GetMapping("/exito")
	public String cargarVistaExito() {

		reservaHolder = null;
		return "vistaClientesExito";

	}

	@GetMapping("/no-disponible")

	public String cargarVistaFracaso(Model model) {

		model.addAttribute("fecha",
				reservaService.buscarPorPlacaUltimaFecha(reservaClienteVehiculoDTOHolder.getPlacaVehiculo()));
		return "vistaClientesFracaso";
	}
}
