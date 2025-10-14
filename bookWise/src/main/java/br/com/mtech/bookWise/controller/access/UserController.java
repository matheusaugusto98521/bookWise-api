package br.com.mtech.bookWise.controller.access;

import br.com.mtech.bookWise.DTO.access.UserRequestDTO;
import br.com.mtech.bookWise.DTO.response.ResponseDTO;
import br.com.mtech.bookWise.exceptions.ModelExistsException;
import br.com.mtech.bookWise.exceptions.ModelNotFoundException;
import br.com.mtech.bookWise.exceptions.NullInformationException;
import br.com.mtech.bookWise.model.Book;
import br.com.mtech.bookWise.model.security.User;
import br.com.mtech.bookWise.services.access.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/access")
public class UserController {

    @Autowired
    private UserServices services;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<User>> createUser(@RequestBody @Valid UserRequestDTO data) throws NullInformationException, ModelExistsException, ModelNotFoundException {
        User user = this.services.createUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<User>("Usuário criado com sucesso!", user));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<User>> updateUser(@RequestParam("idUser")UUID idUser, @RequestBody @Valid UserRequestDTO data) throws NullInformationException, ModelExistsException, ModelNotFoundException {
        User user = this.services.updateUser(idUser, data);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<User>("Usuário atualizado com sucesso!", user));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("idUser") UUID idUser) throws ModelNotFoundException {
        this.services.deleteUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseDTO<User>> getUserById(@RequestParam("idUser") UUID idUser) throws ModelNotFoundException {
        User user = this.services.getUserById(idUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<User>("Usuário encontrado para o ID: " + idUser, user));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<User>>> getAllUsers(){
        List<User> users = this.services.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<List<User>>("Usuários: ", users));
    }

    @GetMapping("/books")
    public ResponseEntity<ResponseDTO<List<Book>>> getBooksByUser(@RequestParam("idUser") UUID idUser) throws ModelNotFoundException {
        List<Book> booksByUser = this.services.booksByUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<List<Book>>("Livros emprestados: ", booksByUser));
    }
}
