package com.maryana.cursomc.resources;

import com.maryana.cursomc.domain.Categoria;
import com.maryana.cursomc.dto.CategoriaDTO;
import com.maryana.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    //isso eh um verbo do http, os verbos sao (get, post, delete, update)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
        //responseEntity ja te da as respostas do http de sucesso ou nao

        Categoria obj = service.find(id);

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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {

        obj.setId(id);
        obj = service.update(obj);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> findAll() {

        List<Categoria> list = service.findAll();

        //percorrendo o list, e pra cada elemento da lista eu vou instanciar o dto correspondente
        //obj é o apelido que eu dou pra cada elemento da lista
        //resumidamente eu convertir uma lista em outra lista, de List<Categoria> para List<CategoriaDTO>
        List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction) {

        Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);

        Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }
}
