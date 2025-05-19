package co.edu.unbosque.gestiontarjetas.service;

import co.edu.unbosque.gestiontarjetas.model.DTO.ClienteDTO;
import co.edu.unbosque.gestiontarjetas.model.entity.Cliente;
import co.edu.unbosque.gestiontarjetas.repository.IClienteRepository;
import co.edu.unbosque.gestiontarjetas.service.implementation.ClienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private IClienteRepository clienteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Debe retornar lista de clientes correctamente mapeados")
    void listarClientes() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        ClienteDTO dto = new ClienteDTO();
        dto.setId(1L);

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(modelMapper.map(cliente, ClienteDTO.class)).thenReturn(dto);

        // When
        List<ClienteDTO> resultado = clienteService.listarClientes();

        // Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        verify(clienteRepository, times(1)).findAll();
    }
}
