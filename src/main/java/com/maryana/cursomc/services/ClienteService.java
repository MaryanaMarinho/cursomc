package com.maryana.cursomc.services;

import com.maryana.cursomc.domain.Cliente;
import com.maryana.cursomc.domain.Cliente;
import com.maryana.cursomc.dto.ClienteDTO;
import com.maryana.cursomc.repositories.ClienteRepository;
import com.maryana.cursomc.services.exceptions.DataIntegrityException;
import com.maryana.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Cliente find(Integer id) {

        Optional<Cliente> obj = repo.findById(id); //busca no banco pelo o id que eu passei e retorna o objeto pronto
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto nao encontrado! id " + id + ", Tipo " + Cliente.class.getName()));

    }

    public Cliente update(Cliente obj) {

        Cliente newObj =  find(obj.getId());
        updateData(newObj, obj);
        
        return repo.save(newObj);
    }


    public void delete(Integer id) {

        find(id);

        try {
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {

            throw new DataIntegrityException("Nao e possivel excluir porque há entidades relacionadas");
        }
    }

    public List<Cliente> findAll() {

        return repo.findAll();
    }

    //retornando paginas de cliente

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return repo.findAll(pageRequest);
    }
    //instacia uma cliente apartir de um dto

    public Cliente fromDTO(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

}
