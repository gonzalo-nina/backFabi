package org.example.crudpruebafabi.service;

import org.example.crudpruebafabi.model.Cliente;
import org.example.crudpruebafabi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Transactional
    public Cliente actualizarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
