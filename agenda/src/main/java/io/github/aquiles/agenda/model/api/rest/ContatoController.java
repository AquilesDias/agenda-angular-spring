package io.github.aquiles.agenda.model.api.rest;

import io.github.aquiles.agenda.model.entity.Contato;
import io.github.aquiles.agenda.model.repository.ContatoRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
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

    @GetMapping
    public Page<Contato> list(
            @RequestParam(value = "page", defaultValue = "0") Integer pagina,
            @RequestParam(value = "size", defaultValue ="10") Integer tamanhoPagina
    ){
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(pagina, tamanhoPagina, sort);
        return repository.findAll(pageRequest);
    }

    @PatchMapping("/{id}/favorito")
    public void favorite(@PathVariable Integer id){

        Optional<Contato> contato = repository.findById(id);

        contato.ifPresent( c -> {
            boolean favorito = c.getFavorito() == Boolean.TRUE;
            c.setFavorito(favorito);
            repository.save(c);
                });
    }

    @PutMapping("/{id}/foto")
    public byte[] addPhoto(@PathVariable Integer id, @RequestParam("foto")Part arquivo){

        Optional<Contato> contato = repository.findById(id);

        return contato.map( c -> {
           try {
               InputStream is = arquivo.getInputStream();
               byte[] bytes = new byte[(int)arquivo.getSize()];
               IOUtils.readFully(is, bytes);
               c.setFoto(bytes);
               repository.save(c);
               is.close();
               return bytes;

           } catch (IOException e){return null;}
        }).orElse(null);
    }

}
