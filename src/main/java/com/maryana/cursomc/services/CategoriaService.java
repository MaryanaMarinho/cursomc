package com.maryana.cursomc.services;

import com.maryana.cursomc.domain.Categoria;
import com.maryana.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria buscar(Integer id) {

        Optional<Categoria> obj = repo.findById(id); //busca no banco pelo o id que eu passei e retorna o objeto pronto
        return obj.orElse(null);

    }
}
