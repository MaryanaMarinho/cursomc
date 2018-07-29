package com.maryana.cursomc.services;

import com.maryana.cursomc.domain.Cliente;
import com.maryana.cursomc.repositories.ClienteRepository;
import com.maryana.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Cliente buscar(Integer id) {

        Optional<Cliente> obj = repo.findById(id); //busca no banco pelo o id que eu passei e retorna o objeto pronto
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto nao encontrado! id " + id + ", Tipo " + Cliente.class.getName()));

    }
}
