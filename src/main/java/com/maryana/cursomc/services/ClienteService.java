package com.maryana.cursomc.services;

import com.maryana.cursomc.domain.Cidade;
import com.maryana.cursomc.domain.Cliente;
import com.maryana.cursomc.domain.Endereco;
import com.maryana.cursomc.domain.enums.TipoCliente;
import com.maryana.cursomc.dto.ClienteDTO;
import com.maryana.cursomc.dto.ClienteNewDTO;
import com.maryana.cursomc.repositories.ClienteRepository;
import com.maryana.cursomc.repositories.EnderecoRepository;
import com.maryana.cursomc.services.exceptions.DataIntegrityException;
import com.maryana.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Integer id) {

        Optional<Cliente> obj = repo.findById(id); //busca no banco pelo o id que eu passei e retorna o objeto pronto
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto nao encontrado! id " + id + ", Tipo " + Cliente.class.getName()));

    }

    @Transactional
    public Cliente insert(Cliente obj) {

        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj) {

        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);

        return repo.save(newObj);
    }


    public void delete(Integer id) {

        find(id);

        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {

            throw new DataIntegrityException("Nao e possivel excluir porque há entidades relacionadas");
        }
    }

    public List<Cliente> findAll() {

        return repo.findAll();
    }

    //retornando paginas de cliente

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return repo.findAll(pageRequest);
    }
    //instacia uma cliente apartir de um dto

    public Cliente fromDTO(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto) {

        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);

        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());

        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }

        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

}
