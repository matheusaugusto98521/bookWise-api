package br.com.mtech.bookWise.model;

import br.com.mtech.bookWise.enums.BookStatus;
import br.com.mtech.bookWise.model.security.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_books")
@NoArgsConstructor
@Data
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idBook;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private int numberPages;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private int yearPublication;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate loanDate;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
