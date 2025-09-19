package br.com.mtech.bookWise.services;

import br.com.mtech.bookWise.DTO.AuthorRequestDTO;
import br.com.mtech.bookWise.exceptions.ModelNotFoundException;
import br.com.mtech.bookWise.exceptions.NullInformationException;
import br.com.mtech.bookWise.model.Author;
import br.com.mtech.bookWise.repositories.IAuthorRepository;
import br.com.mtech.bookWise.utils.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorServices {

    private IAuthorRepository repository;
    private Validation validation;

    public Author createAuthor(AuthorRequestDTO data) throws NullInformationException {
        this.validation.validateDataAuthor(data);

        Author author = new Author();


        author.setNameAuthor(data.nameAuthor());
        author.setBirthDate(data.birthDate());
        author.setBiography(data.biography());

        saveAuthor(author);
        return author;
    }

    public Author updateAuthor(UUID idAuthor, AuthorRequestDTO data) throws ModelNotFoundException, NullInformationException {
        this.validation.validateDataAuthor(data);
        Author authorFounded = getAuthorById(idAuthor);

        authorFounded.setNameAuthor(data.nameAuthor());
        authorFounded.setBirthDate(data.birthDate());
        authorFounded.setBiography(data.biography());


        saveAuthor(authorFounded);
        return authorFounded;
    }

    public void deleteAuthor(UUID idAuthor) throws ModelNotFoundException {
        Author authorFounded = getAuthorById(idAuthor);
        this.repository.delete(authorFounded);
    }

    public List<Author> getAllAuthors(){
        return this.repository.findAll();
    }

    public Author getAuthorById(UUID idAuthor) throws ModelNotFoundException {
        return this.repository.findById(idAuthor).orElseThrow(
                () -> new ModelNotFoundException("Autor não encontrado para o ID: " + idAuthor)
        );
    }

    public void saveAuthor(Author author) throws NullInformationException {
        if(author != null){
            this.repository.save(author);
        }else{
            throw new NullInformationException("Autor não pode ser nulo ao salvar");
        }
    }
}
