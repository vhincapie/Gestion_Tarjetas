package co.edu.unbosque.gestiontarjetas.controller;

import co.edu.unbosque.gestiontarjetas.model.DTO.TarjetaCreditoDTO;
import co.edu.unbosque.gestiontarjetas.service.interfaces.IClienteService;
import co.edu.unbosque.gestiontarjetas.service.interfaces.ITarjetaCreditoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/tarjetas")
public class TarjetaCreditoController {

    private final ITarjetaCreditoService tarjetaService;
    private final IClienteService clienteService;

    public TarjetaCreditoController(ITarjetaCreditoService tarjetaService, IClienteService clienteService) {
        this.tarjetaService = tarjetaService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String mostrarTarjetas(Model model) {
        model.addAttribute("tarjetas", tarjetaService.listarTarjetas());
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("nuevaTarjeta", new TarjetaCreditoDTO());
        return "tarjetas";
    }

    @PostMapping("/crear")
    public String crearTarjeta(@ModelAttribute("nuevaTarjeta") TarjetaCreditoDTO dto) {
        tarjetaService.crearTarjeta(dto);
        return "redirect:/tarjetas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarTarjeta(@PathVariable Long id) {
        tarjetaService.eliminarLogicamente(id);
        return "redirect:/tarjetas";
    }

    @PostMapping("/{id}/modificar")
    public String modificarCupo(@PathVariable Long id, @RequestParam BigDecimal nuevoCupoTotal) {
        tarjetaService.modificarCupoTotal(id, nuevoCupoTotal);
        return "redirect:/tarjetas";
    }
}
