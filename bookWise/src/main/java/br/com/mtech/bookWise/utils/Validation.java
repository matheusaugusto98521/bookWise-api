package br.com.mtech.bookWise.utils;

import br.com.mtech.bookWise.DTO.AuthorRequestDTO;
import br.com.mtech.bookWise.DTO.BookRequestDTO;
import br.com.mtech.bookWise.DTO.BookUpdateRequestDTO;
import br.com.mtech.bookWise.DTO.CategoryRequestDTO;
import br.com.mtech.bookWise.DTO.access.UserRequestDTO;
import br.com.mtech.bookWise.exceptions.ModelExistsException;
import br.com.mtech.bookWise.exceptions.ModelNotFoundException;
import br.com.mtech.bookWise.exceptions.NullInformationException;
import br.com.mtech.bookWise.model.Book;
import br.com.mtech.bookWise.model.Category;
import br.com.mtech.bookWise.model.security.User;
import br.com.mtech.bookWise.repositories.IBookRepository;
import br.com.mtech.bookWise.repositories.ICategoryRepository;
import br.com.mtech.bookWise.repositories.access.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class Validation {

    private IUserRepository userRepository;
    private IBookRepository bookRepository;
    private ICategoryRepository categoryRepository;

    public void validateUserDataCreate(UserRequestDTO data, UUID idFindUser) throws NullInformationException, ModelExistsException {
        if(data == null ||
        data.name().isBlank() ||
        data.username().isBlank() ||
        data.emailUser().isBlank() ||
        data.password().isBlank() ||
        data.role().isBlank()){
            throw new NullInformationException("Informações a serem criadas não devem ser nulas ou estarem em branco");

        }

        User byUsername = (User) this.userRepository.findByUsername(data.username());
        if(byUsername != null && !byUsername.getIdUser().equals(idFindUser)){
            throw new ModelExistsException("Nome de Usuário já está em uso!");
        }

        User byEmailUser = this.userRepository.findByEmailUser(data.emailUser());
        if(byEmailUser != null && !byEmailUser.getIdUser().equals(idFindUser)){
            throw new ModelExistsException("Email de Usuário já está em uso!");

        }
    }

    public void validateDataBook(BookRequestDTO data, UUID idBookReference) throws NullInformationException, ModelExistsException, ModelNotFoundException {
        if(data == null ||
        data.title().isBlank() ||
        data.numberPages() == 0 ||
        data.isbn().isBlank()){
            throw new NullInformationException("Informações a serem criadas não devem ser nulas ou estarem em branco");
        }

        Optional<Book> byIsbn = this.bookRepository.findByIsbn(data.isbn());

        if (byIsbn.isPresent()) {
            throw new ModelExistsException("ISBN já está em uso!");
        }
    }

    public void validateDataUpdateBook(BookUpdateRequestDTO data, UUID idBookReference) throws NullInformationException, ModelExistsException, ModelNotFoundException {
        if(data == null ||
                data.title().isBlank() ||
                data.numberPages() == 0 ||
                data.yearPublication() == 0){
            throw new NullInformationException("Informações a serem atualizadas não devem ser nulas ou estarem em branco");
        }

    }

    public void validateDataAuthor(AuthorRequestDTO data) throws NullInformationException {
        if(data == null ||
        data.nameAuthor().isBlank() ||
        data.biography().isBlank() ||
        data.birthDate().isBlank()){
            throw new NullInformationException("Informações a serem criadas não devem ser nulas ou estarem em branco");
        }
    }

    public void validateCategoryData(UUID idCategory, CategoryRequestDTO data) throws NullInformationException, ModelExistsException {
        if(data == null ||
        data.nameCategory().isBlank()){
            throw new NullInformationException("Informações a serem criadas não devem ser nulas ou estarem em branco");
        }

        Category byName = this.categoryRepository.findByNameCategory(data.nameCategory());
        if(byName != null && !byName.getIdCategory().equals(idCategory)){
            throw new ModelExistsException("Nome de Categoria já está em uso!");

        }

    }
}
