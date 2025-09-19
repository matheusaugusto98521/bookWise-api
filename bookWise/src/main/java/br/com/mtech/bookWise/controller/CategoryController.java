package br.com.mtech.bookWise.controller;

import br.com.mtech.bookWise.DTO.CategoryRequestDTO;
import br.com.mtech.bookWise.DTO.response.ResponseDTO;
import br.com.mtech.bookWise.exceptions.ModelExistsException;
import br.com.mtech.bookWise.exceptions.ModelNotFoundException;
import br.com.mtech.bookWise.exceptions.NullInformationException;
import br.com.mtech.bookWise.model.Category;
import br.com.mtech.bookWise.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServices services;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Category>> createCategory(@RequestBody CategoryRequestDTO data) throws NullInformationException, ModelExistsException {
        Category category = this.services.createCategory(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<Category>("Categoria salva com sucesso:", category));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Category>> updateCategory(@RequestParam("idCategory")UUID idCategory,
                                                                @RequestBody CategoryRequestDTO data) throws NullInformationException, ModelNotFoundException, ModelExistsException {
        Category updatedCategory = this.services.updateCategory(idCategory, data);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Category>("Categoria alterada com sucesso", updatedCategory));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam("idCategory") UUID idCategory) throws ModelNotFoundException {
        this.services.deleteCategory(idCategory);
        return ResponseEntity.status(HttpStatus.OK).body("Categoria Deletada com sucesso");
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseDTO<Category>> getCategoryById(@RequestParam("idCategory") UUID idCategory) throws ModelNotFoundException {
        Category categoryFounded = this.services.findCategoryById(idCategory);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Category>("Categoria encontrada: ", categoryFounded));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<Category>>> getAllCategories(){
        List<Category> categories = this.services.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<List<Category>>("Categorias: ", categories));
    }
}
