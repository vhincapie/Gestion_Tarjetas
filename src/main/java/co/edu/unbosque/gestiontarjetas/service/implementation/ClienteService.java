package co.edu.unbosque.gestiontarjetas.service.implementation;


import co.edu.unbosque.gestiontarjetas.model.DTO.ClienteDTO;
import co.edu.unbosque.gestiontarjetas.repository.IClienteRepository;
import co.edu.unbosque.gestiontarjetas.service.interfaces.IClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService implements IClienteService {

    private final IClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    public ClienteService(IClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }
}