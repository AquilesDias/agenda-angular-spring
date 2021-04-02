package io.github.aquiles.agenda.model.api.rest;

import io.github.aquiles.agenda.model.entity.Contato;
import io.github.aquiles.agenda.model.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/contatos")
public class ContatoController {

    @Autowired
    private ContatoRepository repository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public Contato save( @RequestBody Contato contato){
       return repository.save(contato);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
         repository.deleteById(id);
    }

    @GetMapping("/")
    public List<Contato> list(){
        return repository.findAll();
    }

    @PatchMapping("/{id}/favorito")
    public void favorite(@PathVariable Integer id, @RequestBody Boolean favorito){

        Optional<Contato> contato = repository.findById(id);

        contato.ifPresent( c -> {
            c.setFavorito(favorito);
            repository.save(c);
                });
    }




}
