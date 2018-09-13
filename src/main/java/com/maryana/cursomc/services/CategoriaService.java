package com.maryana.cursomc.services;

import com.maryana.cursomc.domain.Categoria;
import com.maryana.cursomc.dto.CategoriaDTO;
import com.maryana.cursomc.repositories.CategoriaRepository;
import com.maryana.cursomc.services.exceptions.DataIntegrityException;
import com.maryana.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) {

        Optional<Categoria> obj = repo.findById(id); //busca no banco pelo o id que eu passei e retorna o objeto pronto
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto nao encontrado! id " + id + ", Tipo " + Categoria.class.getName()));

    }

    public Categoria insert(Categoria obj) {

        obj.setId(null);

        return repo.save(obj);
    }

    public Categoria update(Categoria obj) {

        find(obj.getId());

        return repo.save(obj);
    }


    public void delete(Integer id) {

        find(id);

        try {
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {

            throw new DataIntegrityException("Nao e possivel excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll() {

        return repo.findAll();
    }

    //retornando paginas de categoria
    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return repo.findAll(pageRequest);
    }

    //instacia uma categoria apartir de um dto
    public Categoria fromDTO(CategoriaDTO objDto) {
        return new Categoria(objDto.getId(), objDto.getNome());
    }
}
