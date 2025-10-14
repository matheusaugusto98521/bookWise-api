package br.com.mtech.bookWise.services;

import br.com.mtech.bookWise.DTO.AuthorRequestDTO;
import br.com.mtech.bookWise.exceptions.ModelNotFoundException;
import br.com.mtech.bookWise.exceptions.NullInformationException;
import br.com.mtech.bookWise.model.Author;
import br.com.mtech.bookWise.repositories.IAuthorRepository;
import br.com.mtech.bookWise.utils.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthorServicesTest {

    @Mock
    private IAuthorRepository repository;

    @Mock
    private Validation validation;

    @Autowired
    @InjectMocks
    private AuthorServices authorServices;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should Create new Author successfully when everything is ok")
    void createAuthorCase1Success() throws NullInformationException {
        AuthorRequestDTO data =  new AuthorRequestDTO("RepolhoZikaSkills", "Rei do Free Fire", "21/10/2003");
        Author author = new Author();
        BeanUtils.copyProperties(data, author);

        when(repository.save(any(Author.class))).thenReturn(author);

        Author authorSaved = authorServices.createAuthor(data);

        assertNotNull(authorSaved);
        assertEquals("RepolhoZikaSkills", authorSaved.getNameAuthor());
        verify(validation).validateDataAuthor(data);
        verify(repository).save(any(Author.class));
    }

    @Test
    @DisplayName("Should not create a new Author when something is wrong")
    void createAuthorCase2Error() throws NullInformationException {
        AuthorRequestDTO data =  new AuthorRequestDTO(null, "Rei do Free Fire", "21/10/2003");
        doThrow(new NullInformationException("Nenhum dado pode ser nulo")).when(validation).validateDataAuthor(data);

        assertThrows(NullInformationException.class, () -> authorServices.createAuthor(data));
        verify(validation).validateDataAuthor(data);
        verify(repository, never()).save(any(Author.class));
    }

    @Test
    @DisplayName("Should Update Exists Author successfully when everything is ok")
    void updateAuthorCase1Success() throws NullInformationException, ModelNotFoundException {
        AuthorRequestDTO data =  new AuthorRequestDTO("RepolhoZikaSkills", "Rei do Free Fire", "21/10/2003");

        UUID idAuthor = UUID.randomUUID();
        Author existingAuthor = new Author();
        existingAuthor.setIdAuthor(idAuthor);
        BeanUtils.copyProperties(data, existingAuthor);

        AuthorRequestDTO updatedData =  new AuthorRequestDTO("RepolhoZikaSkill", "Rei do Fre Fire", "21/10/2005");

        Author updatedAuthor = new Author();
        updatedAuthor.setIdAuthor(idAuthor);
        BeanUtils.copyProperties(updatedData, updatedAuthor);

        when(repository.findById(idAuthor)).thenReturn(Optional.of(existingAuthor));
        doNothing().when(validation).validateDataAuthor(updatedData);
        when(repository.save(any(Author.class))).thenReturn(updatedAuthor);

        Author result = authorServices.updateAuthor(idAuthor, updatedData);


        assertNotNull(result);
        assertEquals("RepolhoZikaSkill", result.getNameAuthor());
        assertEquals("Rei do Fre Fire", result.getBiography());
        assertEquals(idAuthor, result.getIdAuthor());
        verify(validation).validateDataAuthor(updatedData);
        verify(repository).save(any(Author.class));
    }

    @Test
    @DisplayName("Should not update Author when data is null")
    void updateAuthorCase2Error() throws NullInformationException {
        AuthorRequestDTO data =  new AuthorRequestDTO("RepolhoZikaSkills", "Rei do Free Fire", "21/10/2003");

        UUID idAuthor = UUID.randomUUID();
        Author existingAuthor = new Author();
        existingAuthor.setIdAuthor(idAuthor);
        BeanUtils.copyProperties(data, existingAuthor);

        AuthorRequestDTO updatedData =  new AuthorRequestDTO(null, "Rei do Fre Fires", "21/10/2008");
        doThrow(new NullInformationException("Nenhum dado pode ser nulo")).when(validation).validateDataAuthor(updatedData);

        assertThrows(NullInformationException.class, () -> authorServices.updateAuthor(idAuthor ,updatedData));
        verify(validation).validateDataAuthor(updatedData);
        verify(repository, never()).save(any(Author.class));
    }

    @Test
    @DisplayName("Should delete Author successfully when it exists")
    void deleteAuthorCase1Success() throws ModelNotFoundException {
        AuthorRequestDTO data =  new AuthorRequestDTO("RepolhoZikaSkills", "Rei do Free Fire", "21/10/2003");

        UUID idAuthor = UUID.randomUUID();
        Author existingAuthor = new Author();
        existingAuthor.setIdAuthor(idAuthor);
        BeanUtils.copyProperties(data, existingAuthor);

        when(repository.findById(idAuthor)).thenReturn(Optional.of(existingAuthor));
        doNothing().when(repository).delete(existingAuthor);

        authorServices.deleteAuthor(idAuthor);

        verify(repository).findById(idAuthor);
        verify(repository).delete(existingAuthor);
    }

    @Test
    @DisplayName("Should throw exception when deleting a non-existing Author")
    void deleteAuthorCase2Error() {
        UUID idAuthor = UUID.randomUUID();

        when(repository.findById(idAuthor)).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> authorServices.deleteAuthor(idAuthor));

        verify(repository, never()).delete(any(Author.class));
    }

    @Test
    @DisplayName("Should return all Authors successfully")
    void getAllAuthorsCase1Success() {

        Author author1 = new Author();
        author1.setIdAuthor(UUID.randomUUID());
        author1.setNameAuthor("Author 1");
        author1.setBiography("Biography 1");
        author1.setBirthDate("21/10/2009");

        Author author2 = new Author();
        author2.setIdAuthor(UUID.randomUUID());
        author2.setNameAuthor("Author 2");
        author2.setBiography("Biography 2");
        author2.setBirthDate("21/08/2009");

        List<Author> authors = Arrays.asList(author1, author2);

        when(repository.findAll()).thenReturn(authors);

        List<Author> result = authorServices.getAllAuthors();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(authors.contains(author1));
        assertTrue(authors.contains(author2));

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no Authors exist")
    void getAllAuthorsCase2() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Author> result = authorServices.getAllAuthors();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should return Author successfully when it exists")
    void getAuthorByIdCase1Success() throws ModelNotFoundException {
        UUID idAuthor = UUID.randomUUID();
        AuthorRequestDTO data =  new AuthorRequestDTO("RepolhoZikaSkills", "Rei do Free Fire", "21/10/2003");

        Author existingAuthor = new Author();
        existingAuthor.setIdAuthor(idAuthor);
        BeanUtils.copyProperties(data, existingAuthor);

        when(repository.findById(idAuthor)).thenReturn(Optional.of(existingAuthor));

        Author result = authorServices.getAuthorById(idAuthor);

        assertNotNull(result);
        assertEquals(idAuthor, result.getIdAuthor());
        assertEquals("RepolhoZikaSkills", result.getNameAuthor());
        assertEquals("Rei do Free Fire", result.getBiography());

        verify(repository).findById(idAuthor);
    }

    @Test
    @DisplayName("Should throw exception when Author does not exist")
    void getAuthorByIdCase2Error() {
        UUID idAuthor = UUID.randomUUID();

        when(repository.findById(idAuthor)).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> authorServices.getAuthorById(idAuthor));

        verify(repository).findById(idAuthor);
    }
}