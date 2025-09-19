package br.com.mtech.bookWise.services;

import br.com.mtech.bookWise.DTO.BookRequestDTO;
import br.com.mtech.bookWise.DTO.BookUpdateRequestDTO;
import br.com.mtech.bookWise.enums.BookStatus;
import br.com.mtech.bookWise.exceptions.IllegalModificationException;
import br.com.mtech.bookWise.exceptions.ModelExistsException;
import br.com.mtech.bookWise.exceptions.ModelNotFoundException;
import br.com.mtech.bookWise.exceptions.NullInformationException;
import br.com.mtech.bookWise.model.Author;
import br.com.mtech.bookWise.model.Book;
import br.com.mtech.bookWise.model.Category;
import br.com.mtech.bookWise.model.security.User;
import br.com.mtech.bookWise.repositories.IBookRepository;
import br.com.mtech.bookWise.services.access.UserServices;
import br.com.mtech.bookWise.utils.Validation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookServices {

    private IBookRepository repository;
    private CategoryServices categoryServices;
    private AuthorServices authorServices;
    private Validation validation;
    private UserServices userServices;

    @Transactional
    public Book registerBook(UUID idCategory, UUID idAuthor, BookRequestDTO data) throws NullInformationException, ModelExistsException, ModelNotFoundException {
        this.validation.validateDataBook(data, null);
        Category category = this.categoryServices.findCategoryById(idCategory);
        Author author = this.authorServices.getAuthorById(idAuthor);

        Book book = new Book();
        book.setTitle(data.title());
        book.setIsbn(data.isbn());
        book.setYearPublication(data.yearPublication());
        book.setNumberPages(data.numberPages());
        book.setStatus(BookStatus.AVAILABLE);

        author.addBook(book);
        category.addBook(book);

        this.saveBook(book);
        return book;
    }

    public Book updateBook(UUID idBook, BookUpdateRequestDTO data) throws NullInformationException, ModelExistsException, ModelNotFoundException {
        this.validation.validateDataUpdateBook(data, idBook);

        Book bookFounded = this.getBookById(idBook);
        bookFounded.setTitle(data.title());
        bookFounded.setNumberPages(data.numberPages());
        bookFounded.setYearPublication(data.yearPublication());

        this.saveBook(bookFounded);
        return bookFounded;
    }

    public void deleteBook(UUID idBook) throws ModelNotFoundException {
        Book bookFounded = this.getBookById(idBook);
        this.repository.delete(bookFounded);
    }

    public List<Book> getAllBooks(){
        return this.repository.findAll();
    }

    @Transactional
    public void lendBook(String isbn, UUID idUser) throws ModelNotFoundException, NullInformationException, IllegalModificationException {
        Book bookFounded = this.getByIsbn(isbn);
        User userFounded = this.userServices.getUserById(idUser);

        if(bookFounded.getStatus() == BookStatus.AVAILABLE){
            bookFounded.setStatus(BookStatus.OCCUPED);
            bookFounded.setLoanDate(LocalDate.now());
            userFounded.addBook(bookFounded);
            this.saveBook(bookFounded);
        }else{
            throw new IllegalModificationException("Livro não disponível no momento!");
        }

    }

    @Transactional
    public void returnBook(UUID idUser, String isbn) throws ModelNotFoundException, NullInformationException, IllegalModificationException {
        Book bookFounded = this.getByIsbn(isbn);
        User userFounded = this.userServices.getUserById(idUser);

        if(bookFounded.getStatus() == BookStatus.AVAILABLE){
            throw new IllegalModificationException("Este livro já foi devolvido!");
        }

        userFounded.removeBook(bookFounded);
        bookFounded.setStatus(BookStatus.AVAILABLE);
        bookFounded.setLoanDate(null);

        this.saveBook(bookFounded);
    }

    public Book getByIsbn(String isbn) throws ModelNotFoundException {
        return this.repository.findByIsbn(isbn).orElseThrow(
                () -> new ModelNotFoundException("Livro não encontrado para o ISBN: " + isbn));
    }

    public Book getBookById(UUID idBook) throws ModelNotFoundException {
        return this.repository.findById(idBook).orElseThrow(
                () -> new ModelNotFoundException("Livro não encontrado para o ID: " + idBook));
    }

    public void saveBook(Book book) throws NullInformationException {
        if(book != null){
            this.repository.save(book);
        }else{
            throw new NullInformationException("Livro a ser salvo não pode ser nulo");
        }
    }
}
