package com.uce.edu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uce.edu.repository.modelo.Reserva;
import com.uce.edu.repository.modelo.dto.ReservaFechasDTO;
import com.uce.edu.service.IReservaService;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

	@Autowired
	private IReservaService reservaService;

	List<Reserva> reservasHolder;

	@GetMapping("/")
	public String inicializar(Model model) {

		reservasHolder = reservaService.buscarTodo();
		return "redirect:/reportes/reservas";
	}

	@GetMapping("/reservas")
	public String cargarVistaReportesReservas(Model model) {

		model.addAttribute("dto", new ReservaFechasDTO());
		model.addAttribute("reservas", reservasHolder);
		return "vistaReportesReservas";
	}

	@PutMapping("/filtrar-reporte-reservas")
	public String filtrarReporteReservas(ReservaFechasDTO dto) {
		reservasHolder = reservaService.reportesReserva(dto.getFechaInicio(), dto.getFechaFin());

		return "redirect:/reportes/reservas";
	}
}
