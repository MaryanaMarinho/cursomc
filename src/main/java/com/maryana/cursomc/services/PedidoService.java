package com.maryana.cursomc.services;

import com.maryana.cursomc.domain.Pedido;
import com.maryana.cursomc.domain.Pedido;
import com.maryana.cursomc.repositories.PedidoRepository;
import com.maryana.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public Pedido buscar(Integer id) {

        Optional<Pedido> obj = repo.findById(id); //busca no banco pelo o id que eu passei e retorna o objeto pronto
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto nao encontrado! id " + id + ", Tipo " + Pedido.class.getName()));

    }
}
