package com.maryana.cursomc.resources;

import com.maryana.cursomc.domain.Categoria;
import com.maryana.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Categoria obj) { //requestBody é pra transformar o json em objeto java
        obj = service.insert(obj);

        //depois de salval o objeto, redirecionamos para a uri corrente com o id do novo objeto que acabou de criar
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
