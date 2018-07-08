package com.maryana.cursomc.resources;

import com.maryana.cursomc.domain.Categoria;
import com.maryana.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)//isso eh um verbo do http, os verbos sao (get, post, delete, update)
    public ResponseEntity<?> find(@PathVariable Integer id) {
        //responseEntity ja te da as respostas do http de sucesso ou nao

        Categoria obj = service.buscar(id);

        return ResponseEntity.ok().body(obj); //retornando a resposta de sucesso com o objeto no corpo
    }
}
