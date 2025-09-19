package br.com.mtech.bookWise.repositories;

import br.com.mtech.bookWise.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, UUID> {
    Category findByNameCategory(String nameCategory);
}
