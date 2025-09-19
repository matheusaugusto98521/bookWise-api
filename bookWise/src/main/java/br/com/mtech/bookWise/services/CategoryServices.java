package br.com.mtech.bookWise.services;

import br.com.mtech.bookWise.DTO.CategoryRequestDTO;
import br.com.mtech.bookWise.exceptions.ModelExistsException;
import br.com.mtech.bookWise.exceptions.ModelNotFoundException;
import br.com.mtech.bookWise.exceptions.NullInformationException;
import br.com.mtech.bookWise.model.Category;
import br.com.mtech.bookWise.repositories.ICategoryRepository;
import br.com.mtech.bookWise.utils.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServices {

    private ICategoryRepository repository;
    private Validation validation;

    public Category createCategory(CategoryRequestDTO data) throws NullInformationException, ModelExistsException {
        this.validation.validateCategoryData(null, data);
        Category category = new Category();


        category.setNameCategory(data.nameCategory());
        category.setDescriptionCategory(data.descriptionCategory());

        saveCategory(category);
        return category;
    }

    public Category updateCategory(UUID idCategory, CategoryRequestDTO data) throws ModelNotFoundException, NullInformationException, ModelExistsException {
        Category categoryFounded = findCategoryById(idCategory);
        this.validation.validateCategoryData(idCategory, data);


        categoryFounded.setNameCategory(data.nameCategory());
        categoryFounded.setDescriptionCategory(data.descriptionCategory());

        saveCategory(categoryFounded);
        return categoryFounded;
    }

    public void deleteCategory(UUID idCategory) throws ModelNotFoundException {
        Category categoryFounded = findCategoryById(idCategory);
        this.repository.delete(categoryFounded);
    }

    public List<Category> getAllCategories(){
        return this.repository.findAll();
    }

    public Category findCategoryById(UUID idCategory) throws ModelNotFoundException {
        return this.repository.findById(idCategory).orElseThrow(
                () -> new ModelNotFoundException("Categoria não encontrada para o ID: " + idCategory)
        );
    }

    public void saveCategory(Category category) throws NullInformationException {
        if(category != null){
            this.repository.save(category);
        }else{
            throw new NullInformationException("Categoria a ser salva não pode ser nula");
        }
    }
}
