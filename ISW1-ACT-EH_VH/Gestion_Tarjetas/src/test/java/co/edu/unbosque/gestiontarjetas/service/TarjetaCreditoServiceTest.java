package co.edu.unbosque.gestiontarjetas.service;

import co.edu.unbosque.gestiontarjetas.exceptions.exceptions.TarjetaCreditoException;
import co.edu.unbosque.gestiontarjetas.model.DTO.ClienteDTO;
import co.edu.unbosque.gestiontarjetas.model.DTO.TarjetaCreditoDTO;
import co.edu.unbosque.gestiontarjetas.model.entity.Cliente;
import co.edu.unbosque.gestiontarjetas.model.entity.TarjetaCredito;
import co.edu.unbosque.gestiontarjetas.repository.IClienteRepository;
import co.edu.unbosque.gestiontarjetas.repository.ITarjetaCreditoRepository;
import co.edu.unbosque.gestiontarjetas.service.implementation.TarjetaCreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarjetaCreditoServiceTest {

    private TarjetaCreditoService service;
    private ITarjetaCreditoRepository tarjetaRepo;
    private IClienteRepository clienteRepo;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        tarjetaRepo = mock(ITarjetaCreditoRepository.class);
        clienteRepo = mock(IClienteRepository.class);
        modelMapper = new ModelMapper();
        service = new TarjetaCreditoService(tarjetaRepo, clienteRepo, modelMapper);
    }

    @Test
    @DisplayName("Debe crear tarjeta exitosamente con datos válidos")
    void crearTarjeta() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);

        TarjetaCreditoDTO dto = new TarjetaCreditoDTO();
        dto.setFechaVencimiento("2025-12");
        dto.setCupoTotal(new BigDecimal("1000"));
        dto.setCupoDisponible(new BigDecimal("700"));
        dto.setCliente(clienteDTO);

        when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));
        when(tarjetaRepo.existsByNumeroTarjeta(anyString())).thenReturn(false);
        when(tarjetaRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // When
        TarjetaCreditoDTO resultado = service.crearTarjeta(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("ACTIVO", resultado.getEstado());
    }

    @Test
    @DisplayName("Debe retornar lista vacía si no hay tarjetas")
    void listarTarjetas() {
        // Given
        when(tarjetaRepo.findAll()).thenReturn(java.util.Collections.emptyList());

        // When
        var lista = service.listarTarjetas();

        // Then
        assertTrue(lista.isEmpty());
    }

    @Test
    @DisplayName("Debe modificar correctamente el cupo total")
    void modificarCupoTotal() {
        // Given
        TarjetaCredito tarjeta = new TarjetaCredito();
        tarjeta.setId(1L);
        tarjeta.setCupoUtilizado(new BigDecimal("300"));
        tarjeta.setCupoTotal(new BigDecimal("1000"));

        when(tarjetaRepo.findById(1L)).thenReturn(Optional.of(tarjeta));
        when(tarjetaRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // When
        TarjetaCreditoDTO result = service.modificarCupoTotal(1L, new BigDecimal("1200"));

        // Then
        assertEquals(new BigDecimal("900"), result.getCupoDisponible());
    }

    @Test
    @DisplayName("Debe lanzar excepción si nuevo cupo es menor al utilizado")
    void modificarCupoTotalExcept() {
        // Given
        TarjetaCredito tarjeta = new TarjetaCredito();
        tarjeta.setId(1L);
        tarjeta.setCupoUtilizado(new BigDecimal("500"));

        when(tarjetaRepo.findById(1L)).thenReturn(Optional.of(tarjeta));

        // When - Then
        assertThrows(TarjetaCreditoException.class, () -> service.modificarCupoTotal(1L, new BigDecimal("400")));
    }

    @Test
    @DisplayName("Debe cambiar estado a INACTIVO al eliminar tarjeta")
    void eliminarLogicamente() {
        // Given
        TarjetaCredito tarjeta = new TarjetaCredito();
        tarjeta.setId(1L);
        tarjeta.setEstado("ACTIVO");

        when(tarjetaRepo.findById(1L)).thenReturn(Optional.of(tarjeta));

        // When
        service.eliminarLogicamente(1L);

        // Then
        assertEquals("INACTIVO", tarjeta.getEstado());
        verify(tarjetaRepo).save(tarjeta);
    }

    @Test
    @DisplayName("Debe transformar fecha de formato yyyy-MM a MM/yyyy")
    void transformarFecha() {
        // Given
        TarjetaCreditoDTO dto = new TarjetaCreditoDTO();
        dto.setFechaVencimiento("2025-09");

        // When
        service.transformarFecha(dto);

        // Then
        assertEquals("09/2025", dto.getFechaVencimiento());
    }

    @Test
    @DisplayName("Debe validar correctamente formato MM/yyyy")
    void validarFormatoFecha() {
        // When
        boolean valido = service.validarFormatoFecha("12/2025");

        // Then
        assertTrue(valido);
    }

    @Test
    @DisplayName("Debe fallar validación si el formato de fecha es incorrecto")
    void validarFormatoFechaFalse() {
        // When
        boolean valido = service.validarFormatoFecha("2025-12");

        // Then
        assertFalse(valido);
    }

    @Test
    @DisplayName("Debe detectar franquicia VISA")
    void detectarFranquiciaVISA() {
        // When
        String resultado = service.detectarFranquicia("4123456789012345");

        // Then
        assertEquals("VISA", resultado);
    }

    @Test
    @DisplayName("Debe detectar franquicia MASTERCARD")
    void detectarFranquiciaMastercard() {
        // When
        String resultado = service.detectarFranquicia("5212345678901234");

        // Then
        assertEquals("MASTERCARD", resultado);
    }

    @Test
    @DisplayName("Debe detectar franquicia AMEX")
    void detectarFranquiciaAMEX() {
        // When
        String resultado = service.detectarFranquicia("341234567890123");

        // Then
        assertEquals("AMEX", resultado);
    }

    @Test
    @DisplayName("Debe generar número de tarjeta aleatorio no nulo")
    void generarNumeroAleatorio() {
        // When
        String numero = service.generarNumeroAleatorio();

        // Then
        assertNotNull(numero);
    }
}
