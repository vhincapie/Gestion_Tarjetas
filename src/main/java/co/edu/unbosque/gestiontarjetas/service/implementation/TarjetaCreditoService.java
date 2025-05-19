package co.edu.unbosque.gestiontarjetas.service.implementation;

import co.edu.unbosque.gestiontarjetas.exceptions.exceptions.TarjetaCreditoException;
import co.edu.unbosque.gestiontarjetas.model.DTO.TarjetaCreditoDTO;
import co.edu.unbosque.gestiontarjetas.model.entity.Cliente;
import co.edu.unbosque.gestiontarjetas.model.entity.TarjetaCredito;
import co.edu.unbosque.gestiontarjetas.repository.IClienteRepository;
import co.edu.unbosque.gestiontarjetas.repository.ITarjetaCreditoRepository;
import co.edu.unbosque.gestiontarjetas.service.interfaces.ITarjetaCreditoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarjetaCreditoService implements ITarjetaCreditoService {

    private final ITarjetaCreditoRepository tarjetaRepo;
    private final IClienteRepository clienteRepo;
    private final ModelMapper modelMapper;

    public TarjetaCreditoService(ITarjetaCreditoRepository tarjetaRepo, IClienteRepository clienteRepo, ModelMapper modelMapper) {
        this.tarjetaRepo = tarjetaRepo;
        this.clienteRepo = clienteRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public TarjetaCreditoDTO crearTarjeta(TarjetaCreditoDTO dto) {
        validarDatos(dto);

        Cliente cliente = clienteRepo.findById(dto.getCliente().getId())
                .orElseThrow(() -> new TarjetaCreditoException("Cliente no existe"));

        String numeroGenerado = generarNumeroTarjetaUnico();

        String franquicia = detectarFranquicia(numeroGenerado);

        BigDecimal cupoUtilizado = dto.getCupoTotal().subtract(dto.getCupoDisponible());

        TarjetaCredito entidad = modelMapper.map(dto, TarjetaCredito.class);
        entidad.setCliente(cliente);
        entidad.setNumeroTarjeta(numeroGenerado);
        entidad.setFranquicia(franquicia);
        entidad.setEstado("ACTIVO");
        entidad.setCupoUtilizado(cupoUtilizado);

        TarjetaCredito guardada = tarjetaRepo.save(entidad);

        return modelMapper.map(guardada, TarjetaCreditoDTO.class);
    }

    @Override
    public List<TarjetaCreditoDTO> listarTarjetas() {
        return tarjetaRepo.findAll().stream()
                .map(entidad -> modelMapper.map(entidad, TarjetaCreditoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TarjetaCreditoDTO modificarCupoTotal(Long id, BigDecimal nuevoCupoTotal) {
        if (nuevoCupoTotal == null || nuevoCupoTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TarjetaCreditoException("Cupo total debe ser mayor a cero");
        }

        TarjetaCredito tarjeta = tarjetaRepo.findById(id)
                .orElseThrow(() -> new TarjetaCreditoException("Tarjeta no existe"));

        BigDecimal cupoUtilizado = tarjeta.getCupoUtilizado();

        if (nuevoCupoTotal.compareTo(cupoUtilizado) < 0) {
            throw new TarjetaCreditoException("Cupo total no puede ser menor al utilizado");
        }

        tarjeta.setCupoTotal(nuevoCupoTotal);
        tarjeta.setCupoDisponible(nuevoCupoTotal.subtract(cupoUtilizado));

        TarjetaCredito actualizada = tarjetaRepo.save(tarjeta);
        return modelMapper.map(actualizada, TarjetaCreditoDTO.class);
    }

    @Override
    public void eliminarLogicamente(Long id) {
        TarjetaCredito tarjeta = tarjetaRepo.findById(id)
                .orElseThrow(() -> new TarjetaCreditoException("Tarjeta no existe"));
        tarjeta.setEstado("INACTIVO");
        tarjetaRepo.save(tarjeta);
    }

    private void validarDatos(TarjetaCreditoDTO dto) {
        if (dto.getFechaVencimiento() == null || !validarFormatoFecha(dto.getFechaVencimiento())) {
            throw new TarjetaCreditoException("Fecha vencimiento invalida");
        }

        if (dto.getCupoTotal() == null || dto.getCupoDisponible() == null) {
            throw new TarjetaCreditoException("Cupos no pueden ser nulos");
        }

        if (dto.getCupoTotal().compareTo(BigDecimal.ZERO) <= 0 || dto.getCupoDisponible().compareTo(BigDecimal.ZERO) < 0) {
            throw new TarjetaCreditoException("Valores de cupo invalidos");
        }

        if (dto.getCupoDisponible().compareTo(dto.getCupoTotal()) > 0) {
            throw new TarjetaCreditoException("Cupo disponible no puede ser mayor que total");
        }

        if (dto.getCliente() == null || dto.getCliente().getId() == null) {
            throw new TarjetaCreditoException("Cliente obligatorio");
        }
    }

    private boolean validarFormatoFecha(String fecha) {
        try {
            YearMonth.parse(fecha, DateTimeFormatter.ofPattern("MM/yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private String detectarFranquicia(String numero) {
        if (numero.length() == 16 && numero.startsWith("4")) return "VISA";
        if (numero.length() == 16 && numero.matches("^5[1-5].*")) return "MASTERCARD";
        if (numero.length() == 15 && (numero.startsWith("34") || numero.startsWith("37"))) return "AMEX";
        return "DESCONOCIDA";
    }

    private String generarNumeroTarjetaUnico() {
        String numero;
        do {
            numero = generarNumeroAleatorio();
        } while (tarjetaRepo.existsByNumeroTarjeta(numero));
        return numero;
    }

    private String generarNumeroAleatorio() {
        int tipo = (int) (Math.random() * 3);
        switch (tipo) {
            case 0:
                return "4" + String.format("%015d", (long) (Math.random() * 1_000_000_000_000_000L));
            case 1:
                int start = 51 + (int) (Math.random() * 5);
                return start + String.format("%014d", (long) (Math.random() * 1_000_000_000_000_00L));
            case 2:
                String prefix = Math.random() < 0.5 ? "34" : "37";
                return prefix + String.format("%013d", (long) (Math.random() * 1_000_000_000_000L));
            default:
                return "4" + String.format("%015d", (long) (Math.random() * 1_000_000_000_000_000L));
        }
    }
}
