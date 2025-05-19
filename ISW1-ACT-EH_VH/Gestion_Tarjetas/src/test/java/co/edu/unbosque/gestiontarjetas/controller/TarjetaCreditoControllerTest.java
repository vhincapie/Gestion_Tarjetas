package co.edu.unbosque.gestiontarjetas.controller;

import co.edu.unbosque.gestiontarjetas.model.DTO.TarjetaCreditoDTO;
import co.edu.unbosque.gestiontarjetas.service.interfaces.IClienteService;
import co.edu.unbosque.gestiontarjetas.service.interfaces.ITarjetaCreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TarjetaCreditoController.class)
@Import(TarjetaCreditoControllerTest.MockConfig.class)
class TarjetaCreditoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ITarjetaCreditoService tarjetaService;

    @Autowired
    private IClienteService clienteService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ITarjetaCreditoService tarjetaCreditoService() {
            return Mockito.mock(ITarjetaCreditoService.class);
        }

        @Bean
        public IClienteService clienteService() {
            return Mockito.mock(IClienteService.class);
        }
    }

    @BeforeEach
    void setup() {
        when(tarjetaService.listarTarjetas()).thenReturn(Collections.emptyList());
        when(clienteService.listarClientes()).thenReturn(Collections.emptyList());
    }

    @Test
    @DisplayName("GET /tarjetas debe retornar vista con modelo lleno")
    void mostrarTarjetas() throws Exception {
        // Given -> datos mockeados en @BeforeEach

        // When
        mockMvc.perform(get("/tarjetas"))

                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("tarjetas"))
                .andExpect(model().attributeExists("tarjetas"))
                .andExpect(model().attributeExists("clientes"))
                .andExpect(model().attributeExists("nuevaTarjeta"));
    }

    @Test
    @DisplayName("POST /tarjetas/crear debe registrar tarjeta y redirigir")
    void crearTarjeta() throws Exception {
        // Given -> parámetros del formulario

        // When
        mockMvc.perform(post("/tarjetas/crear")
                        .param("cupoTotal", "1000")
                        .param("cupoDisponible", "600")
                        .param("cliente.id", "1"))

                // Then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tarjetas"));

        verify(tarjetaService).crearTarjeta(any(TarjetaCreditoDTO.class));
    }

    @Test
    @DisplayName("POST /tarjetas/{id}/eliminar debe marcar tarjeta como inactiva y redirigir")
    void eliminarTarjeta() throws Exception {
        // Given -> ID 1L

        // When
        mockMvc.perform(post("/tarjetas/1/eliminar"))

                // Then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tarjetas"));

        verify(tarjetaService).eliminarLogicamente(1L);
    }

    @Test
    @DisplayName("POST /tarjetas/{id}/modificar debe actualizar el cupo y redirigir")
    void modificarCupo() throws Exception {
        // Given -> ID y nuevo cupo

        // When
        mockMvc.perform(post("/tarjetas/1/modificar")
                        .param("nuevoCupoTotal", "1500"))

                // Then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tarjetas"));

        verify(tarjetaService).modificarCupoTotal(eq(1L), eq(new BigDecimal("1500")));
    }
}
