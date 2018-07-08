package com.maryana.cursomc.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @RequestMapping(method = RequestMethod.GET) //isso eh um verbo do http, os verbos sao (get, post, delete, update)
    public String listar() {
        return "REST está funcionando";
    }
}
