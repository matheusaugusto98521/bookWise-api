package br.com.mtech.bookWise.repositories;

import br.com.mtech.bookWise.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAuthorRepository extends JpaRepository<Author, UUID> {
}
