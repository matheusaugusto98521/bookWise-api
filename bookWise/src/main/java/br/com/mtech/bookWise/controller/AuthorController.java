package br.com.mtech.bookWise.controller;

import br.com.mtech.bookWise.DTO.AuthorRequestDTO;
import br.com.mtech.bookWise.DTO.response.ResponseDTO;
import br.com.mtech.bookWise.exceptions.ModelNotFoundException;
import br.com.mtech.bookWise.exceptions.NullInformationException;
import br.com.mtech.bookWise.model.Author;
import br.com.mtech.bookWise.services.AuthorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorServices authorServices;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Author>> createAuthor(@RequestBody AuthorRequestDTO data) throws NullInformationException {
        Author author = this.authorServices.createAuthor(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<Author>("Autor criado com sucesso!", author));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Author>> updateAuthor(@RequestParam("idAuthor")UUID idAuthor, @RequestBody AuthorRequestDTO data) throws NullInformationException, ModelNotFoundException {
        Author authorUpdated = this.authorServices.updateAuthor(idAuthor, data);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Author>("Autor Atualizado com sucesso", authorUpdated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAuthor(@RequestParam("idAuthor") UUID idAuthor) throws ModelNotFoundException {
        this.authorServices.deleteAuthor(idAuthor);
        return ResponseEntity.status(HttpStatus.OK).body("Autor Deletado com Sucesso");
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseDTO<Author>> getAuthorById(@RequestParam("idAuthor") UUID idAuthor) throws ModelNotFoundException {
        Author authorFounded = this.authorServices.getAuthorById(idAuthor);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Author>("Autor Encontrado: ", authorFounded));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<Author>>> getAllAuthors(){
        List<Author> authors = this.authorServices.getAllAuthors();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<List<Author>>("Autores: ", authors));
    }
}
