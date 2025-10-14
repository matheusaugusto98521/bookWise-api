package br.com.mtech.bookWise.services;

import br.com.mtech.bookWise.DTO.CategoryRequestDTO;
import br.com.mtech.bookWise.exceptions.ModelExistsException;
import br.com.mtech.bookWise.exceptions.ModelNotFoundException;
import br.com.mtech.bookWise.exceptions.NullInformationException;
import br.com.mtech.bookWise.model.Category;
import br.com.mtech.bookWise.repositories.ICategoryRepository;
import br.com.mtech.bookWise.utils.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CategoryServicesTest {

    @Mock
    private ICategoryRepository repository;

    @Mock
    private Validation validation;

    @Autowired
    @InjectMocks
    private CategoryServices categoryServices;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should Create new Category successfully when everything is ok")
    void createCategoryCase1Success() throws NullInformationException, ModelExistsException {
        CategoryRequestDTO data = new CategoryRequestDTO("Comédia", "Livros sobre humor");
        Category category = new Category();
        BeanUtils.copyProperties(data, category);

        when(repository.save(any(Category.class))).thenReturn(category);

        Category result = categoryServices.createCategory(data);

        assertNotNull(result);
        assertEquals("Comédia", result.getNameCategory());
        verify(validation).validateCategoryData(null, data);
        verify(repository).save(any(Category.class));
    }

    @Test
    @DisplayName("Should not create a new Category when something is wrong")
    void createCategoryCase2Error() throws NullInformationException, ModelExistsException {
        CategoryRequestDTO data = new CategoryRequestDTO(null, "Livros sobre humor");
        doThrow(new NullInformationException("Nenhum dado pode ser nulo")).when(validation).validateCategoryData(null, data);

        assertThrows(NullInformationException.class, () -> categoryServices.createCategory(data));
        verify(validation).validateCategoryData(null, data);
        verify(repository, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("Should Update Exists Category successfully when everything is ok")
    void updateCategoryCase1Success() throws NullInformationException, ModelExistsException, ModelNotFoundException {
        UUID idCategory = UUID.randomUUID();
        CategoryRequestDTO data = new CategoryRequestDTO("Comédia", "Livros sobre humor");
        Category existingCategory = new Category();
        existingCategory.setIdCategory(idCategory);
        BeanUtils.copyProperties(data, existingCategory);

        CategoryRequestDTO updatedData = new CategoryRequestDTO("Comédia Romântica", "Livros sobre humor e amor");

        Category updatedCategory = new Category();
        updatedCategory.setIdCategory(idCategory);
        BeanUtils.copyProperties(updatedData, updatedCategory);

        when(repository.findById(idCategory)).thenReturn(Optional.of(existingCategory));
        doNothing().when(validation).validateCategoryData(idCategory, updatedData);
        when(repository.save(any(Category.class))).thenReturn(updatedCategory);

        Category result = categoryServices.updateCategory(idCategory, updatedData);

        assertNotNull(result);
        assertEquals("Comédia Romântica", result.getNameCategory());
        assertEquals("Livros sobre humor e amor", result.getDescriptionCategory());
        assertEquals(idCategory, result.getIdCategory());
        verify(validation).validateCategoryData(idCategory, updatedData);
        verify(repository).save(any(Category.class));
    }

    @Test
    @DisplayName("Should not update Category when data is null")
    void updateCategoryCase2Error() throws NullInformationException, ModelExistsException {
        UUID idCategory = UUID.randomUUID();
        CategoryRequestDTO data = new CategoryRequestDTO("Comédia", "Livros sobre humor");
        Category existingCategory = new Category();
        existingCategory.setIdCategory(idCategory);
        BeanUtils.copyProperties(data, existingCategory);

        CategoryRequestDTO updatedData = new CategoryRequestDTO(null, "Livros sobre humor e amor");
        doThrow(new NullInformationException("Nenhum dado pode ser nulo")).when(validation).validateCategoryData(idCategory, updatedData);

        assertThrows(NullInformationException.class, () -> categoryServices.updateCategory(idCategory, updatedData));
        verify(validation).validateCategoryData(idCategory, updatedData);
        verify(repository, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("Should delete Category successfully when it exists")
    void deleteCategoryCase1Success() throws ModelNotFoundException {
        UUID idCategory = UUID.randomUUID();
        CategoryRequestDTO data = new CategoryRequestDTO("Comédia", "Livros sobre humor");
        Category existingCategory = new Category();
        existingCategory.setIdCategory(idCategory);
        BeanUtils.copyProperties(data, existingCategory);

        when(repository.findById(idCategory)).thenReturn(Optional.of(existingCategory));
        doNothing().when(repository).delete(existingCategory);

        categoryServices.deleteCategory(idCategory);

        verify(repository).findById(idCategory);
        verify(repository).delete(existingCategory);
    }

    @Test
    @DisplayName("Should throw exception when deleting a non-existing Category")
    void deleteCategoryCase2Error(){
        UUID idCategory = UUID.randomUUID();

        when(repository.findById(idCategory)).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> categoryServices.deleteCategory(idCategory));

        verify(repository, never()).delete(any(Category.class));
    }

    @Test
    void getAllCategoriesCase1Success() {
        CategoryRequestDTO data1 = new CategoryRequestDTO("Comédia", "Livros sobre humor");
        Category category1 = new Category();
        category1.setIdCategory(UUID.randomUUID());
        BeanUtils.copyProperties(data1, category1);

        CategoryRequestDTO data2 = new CategoryRequestDTO("Comédia Romântica", "Livros sobre humor e amor");
        Category category2 = new Category();
        category2.setIdCategory(UUID.randomUUID());
        BeanUtils.copyProperties(data2, category2);

        List<Category> categories = Arrays.asList(category1, category2);

        when(repository.findAll()).thenReturn(categories);

        List<Category> result = categoryServices.getAllCategories();

        assertNotNull(result);
        assertEquals(2, categories.size());
        assertTrue(result.contains(category1));
        assertTrue(categories.contains(category1));
        assertTrue(categories.contains(category2));

        verify(repository).findAll();
    }

    @Test
    void findCategoryById() {
    }
}