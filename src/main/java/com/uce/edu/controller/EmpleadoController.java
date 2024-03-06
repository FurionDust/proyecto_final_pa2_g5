package com.uce.edu.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uce.edu.repository.modelo.Cliente;
import com.uce.edu.repository.modelo.Cobro;
import com.uce.edu.repository.modelo.Reserva;
import com.uce.edu.repository.modelo.Vehiculo;
import com.uce.edu.repository.modelo.dto.BusquedaVehiculoDTO;
import com.uce.edu.repository.modelo.dto.ReservaClienteRedirectDTO;
import com.uce.edu.repository.modelo.dto.ReservaClienteVehiculoDTO;
import com.uce.edu.repository.modelo.dto.ReservaDTO;
import com.uce.edu.service.IClienteService;
import com.uce.edu.service.IReservaService;
import com.uce.edu.service.IVehiculoService;

import jakarta.persistence.NoResultException;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

	private Cliente holderClienteBusqueda = new Cliente();

	private Vehiculo holderVehiculo = new Vehiculo();

	private ReservaDTO holderReservaDTO = new ReservaDTO();

	List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();

	Reserva reservaHolder;

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IVehiculoService vehiculoService;

	@Autowired
	private IReservaService reservaService;

	@GetMapping("/home")
	public String cargarVistaInicio() {
		vehiculos = vehiculoService.buscarTodos();
		return "vistaEmpleadosHome";
	}
	@GetMapping("/registrar-cliente")
	public String cargarVistaRegistrarCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "vistaEmpleadosRegistrarCliente";
	}
	
	@PostMapping("/realizar-registro")
	public String registrarCliente(Cliente cliente, Model model) {
		Cliente existeCliente = clienteService.buscarPorCedula(cliente.getCedula());

		if (existeCliente != null) {
			model.addAttribute("error", "Ya existe un cliente registrado con este número de cédula.");
			return "vistaErrorCedulaExisteEmpleado";
		}

		cliente.setRegistro("Empleado (E)");
		clienteService.agregar(cliente);

		return "redirect:/empleados/registro-exitoso";
	}

	@GetMapping("/registro-exitoso")
	public String cargarVistaRegistroExitosoEmpleado() {
		return "vistaEmpleadoRegistroExitoso";
	}

	@GetMapping("/buscar-cliente")
	public String cargarVistaBuscarCliente(Model model) {
		model.addAttribute("cliente", holderClienteBusqueda != null ? holderClienteBusqueda : new Cliente());
		return "vistaEmpleadosBuscarCliente";
	}

	@GetMapping("/error-cedula-inexistente")
	public String manejarErrorCedulaInexistente(Model model) {
		return "vistaClientesErrorCedulaInexistente";
	}

	@PutMapping("/realizar-busqueda-cliente")
	public String buscarCliente(Cliente cliente) {
		try {
			holderClienteBusqueda = clienteService.buscarPorCedula(cliente.getCedula());

			if (holderClienteBusqueda == null) {
				return "redirect:/empleados/error-cedula-inexistente";
			}

			return "redirect:/empleados/buscar-cliente";
		} catch (Exception e) {
			return "redirect:/empleados/error-cedula-inexistente";
		}
	}

	@GetMapping("/ingresar-vehiculo")
	public String cargarIngresarVehiculo(Model model) {
		model.addAttribute("vehiculo", new Vehiculo());
		return "vistaEmpleadosIngresarVehiculo";
	}

	@PostMapping("/realizar-ingreso-vehiculo")
    public String ingresarVehiculo(Vehiculo vehiculo, Model model) {
        Vehiculo existePlaca = vehiculoService.buscarPorPlaca(vehiculo.getPlaca());

        if (existePlaca != null) {
            model.addAttribute("error", "Ya existe una placa registrada con este número");
            return "vistaErrorPlacaExiste";
        }

        vehiculo.setEstado("Disponible (D)");
        vehiculoService.agregar(vehiculo);

        return "redirect:/empleados/registro-exitoso-vehiculo";
    }

    @GetMapping("/registro-exitoso-vehiculo")
    public String cargarVistaRegistroExitosoVehiculo() {
        return "vistaEmpleadoRegistroExitoso";
    }

	@GetMapping("/buscar-vehiculo")
	public String cargarVistaBuscarVehiculo(Model model) {
		model.addAttribute("vehiculo", holderVehiculo != null ? holderVehiculo : new Vehiculo());
		return "vistaEmpleadosBuscarVehiculo";
	}

	@GetMapping("/error-placa-inexistente")
	public String manejarErrorPlacaInexistente(Model model) {
		return "vistaVehiculoPlacaInexistente";
	}

	@PutMapping("/realizar-busqueda-vehiculo")
	public String bucarVehiculo(Vehiculo vehiculo) {
		try {
			holderVehiculo = vehiculoService.buscarPorPlaca(vehiculo.getPlaca());

			if (holderVehiculo == null) {
				return "redirect:/empleados/error-placa-inexistente";
			}

			return "redirect:/empleados/buscar-vehiculo";
		} catch (Exception e) {
			return "redirect:/empleados/error-placa-inexistente";
		}
	}

	@GetMapping("/retirar-vehiculo-reservado")
	public String cargarVistaRetirarVehiculoReservado(Model model) {

		model.addAttribute("reservaDTO", holderReservaDTO);
		return "vistaEmpleadosRetiroReserva";
	}

	@PutMapping("/cargar-informacion-reserva")

	public String cargarInformacionReserva(ReservaDTO reservaDTO, Model model) {

		try {
			ReservaDTO reservaExiste = reservaService.buscarPorNumReserva(reservaDTO.getNumReserva());
			if (reservaExiste == null) {
				model.addAttribute("error", "La reserva con el número proporcionado no existe.");
				return "vistaErrorReserva";
			}
			holderReservaDTO = reservaExiste;
			holderReservaDTO.setNumReserva(reservaDTO.getNumReserva());

			return "redirect:/empleados/retirar-vehiculo-reservado";
		} catch (NoResultException e) {
			model.addAttribute("error", "La reserva con el número proporcionado no existe.");
			return "vistaErrorReserva";
		} catch (Exception e) {

			return "vistaErrorReserva";
		}
	}

	@PutMapping("/realizar-retiro-reserva/{numReserva}")
	public String retirarVehiculoReservado(@PathVariable("numReserva") Integer numReserva, ReservaDTO reservaDTO) {

		vehiculoService.retirarReserva(reservaDTO.getNumReserva());
		return "redirect:/empleados/home";
	}

	@GetMapping("/retirar-vehiculo-sin-reserva")
	public String cargarVistaVehiculosDisponibles(Model model) {
		model.addAttribute("vehiculos", vehiculos);
		model.addAttribute("dto", new BusquedaVehiculoDTO());
		return "vistaEmpleadosRetiroNoReserva";
	}

	@PostMapping("/realizar-busqueda-vehiculos")
	public String buscarVehiculos(@ModelAttribute BusquedaVehiculoDTO dto) {
		vehiculos = vehiculoService.buscarPorMarcaModelo(dto.getMarca(), dto.getModelo());
		return "redirect:/empleados/retirar-vehiculo-sin-reserva";
	}

	@GetMapping("/reservar-vehiculo")
	public String cargarVistaReservarVehiculo(Model model) {
		model.addAttribute("dto", new ReservaClienteVehiculoDTO());
		return "vistaEmpleadosReservarVehiculo";
	}

	@PostMapping("/reservar")
	public String realizarReserva(ReservaClienteVehiculoDTO dto, Model model) {
		try {
			Vehiculo vehiculo = vehiculoService.buscarPorPlaca(dto.getPlacaVehiculo());

			if (vehiculo == null) {
				model.addAttribute("error", "La placa del vehículo no existe.");
				return "vistaClientesErrorPlacaInexistente";
			}

			Cliente cliente = clienteService.buscarPorCedula(dto.getCedulaCliente());

			if (cliente == null) {
				model.addAttribute("error", "El cliente con la cédula proporcionada no existe.");
				return "vistaClientesErrorCedulaInexistente";
			}

			ReservaClienteRedirectDTO redirectReserva = reservaService.reservarVehiculo(dto);
			reservaHolder = redirectReserva.getReserva();
			String[] redirect = redirectReserva.getRedirect().split("/");
			return "redirect:/empleados/" + redirect[2];
		} catch (Exception e) {
			model.addAttribute("error", "Se produjo un error al procesar la reserva.");
			return "vistaEmpleadoPlacaCedulaInexistente";
		}
	}

	@GetMapping("/cobro")
	public String paginaCobro(Model model) {

		model.addAttribute("reserva", reservaHolder);
		Cobro cobro = new Cobro();
		cobro.setFechaDeCobro(LocalDate.now());

		model.addAttribute("cobro", cobro);
		return "vistaEmpleadosCobro";
	}

	@PostMapping("/cobrar")
	public String generarCobro(Cobro cobro) {

		cobro.setReserva(reservaHolder);
		reservaHolder.setCobro(cobro);

		reservaService.agregar(reservaHolder);

		reservaHolder = null;

		return "redirect:/empleados/retirar-vehiculo-reservado";
	}

	@GetMapping("/volver")
	public String volver() {
		return "redirect:/home/";
	}
}
