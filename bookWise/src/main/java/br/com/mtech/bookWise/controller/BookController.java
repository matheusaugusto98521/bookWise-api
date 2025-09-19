package br.com.mtech.bookWise.controller;

import br.com.mtech.bookWise.DTO.BookRequestDTO;
import br.com.mtech.bookWise.DTO.BookUpdateRequestDTO;
import br.com.mtech.bookWise.DTO.response.ResponseDTO;
import br.com.mtech.bookWise.exceptions.IllegalModificationException;
import br.com.mtech.bookWise.exceptions.ModelExistsException;
import br.com.mtech.bookWise.exceptions.ModelNotFoundException;
import br.com.mtech.bookWise.exceptions.NullInformationException;
import br.com.mtech.bookWise.model.Book;
import br.com.mtech.bookWise.services.BookServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookServices services;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Book>> registerBook(@RequestParam("idAuthor")UUID idAuthor,
                                                          @RequestParam("idCategory") UUID idCategory,
                                                          @RequestBody @Valid BookRequestDTO data) throws NullInformationException, ModelExistsException, ModelNotFoundException {
        Book book = this.services.registerBook(idCategory, idAuthor,data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<Book>("Livro criado com sucesso!", book));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Book>> updateBook(@RequestParam("idBook") UUID idBook,
                                                        @RequestBody @Valid BookUpdateRequestDTO data) throws NullInformationException, ModelExistsException, ModelNotFoundException {
        Book bookUpdated = this.services.updateBook(idBook, data);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Book>("Livro Alterado com sucesso!", bookUpdated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBook(@RequestParam("idBook") UUID idBook) throws ModelNotFoundException {
        this.services.deleteBook(idBook);
        return ResponseEntity.status(HttpStatus.OK).body("Excluído com sucesso");
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseDTO<Book>> getBookById(@RequestParam("idBook") UUID idBook) throws ModelNotFoundException {
        Book bookFounded = this.services.getBookById(idBook);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Book>("Livro encontrado: ", bookFounded));
    }

    @GetMapping("/get-by-isbn")
    public ResponseEntity<ResponseDTO<Book>> getBookByIsbn(@RequestParam("isbn") String isbn) throws ModelNotFoundException {
        Book bookFounded = this.services.getByIsbn(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Book>("Livro encontrado: ", bookFounded));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<Book>>> getAllBooks(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<List<Book>>("Livros: ", this.services.getAllBooks()));
    }

    @PostMapping("/lend-book")
    public ResponseEntity<String> lendBook(@RequestParam("isbn") String isbn,
                                           @RequestParam("idUser") UUID idUser) throws NullInformationException, IllegalModificationException, ModelNotFoundException {
        this.services.lendBook(isbn, idUser);
        return ResponseEntity.status(HttpStatus.OK).body("Livro emprestado com sucesso!");
    }

    @PutMapping("/return-book")
    public ResponseEntity<String> returnBook(@RequestParam("idUser") UUID idUser,
                                             @RequestParam("isbn") String isbn) throws NullInformationException, IllegalModificationException, ModelNotFoundException {
        this.services.returnBook(idUser, isbn);
        return ResponseEntity.status(HttpStatus.OK).body("Livro devolvido com sucesso!");
    }


}
