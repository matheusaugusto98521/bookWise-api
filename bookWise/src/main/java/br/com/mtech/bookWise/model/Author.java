package br.com.mtech.bookWise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_authors")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "idAuthor")
@ToString(exclude = "books")
public class Author implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAuthor;

    @Column(nullable = false)
    private String nameAuthor;

    private String biography;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date birthDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setBirthDate(String birthDate){
        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        try{
            sfd.setLenient(false);
            this.birthDate = sfd.parse(birthDate);
        } catch (ParseException e) {
            System.out.println("Data em formato errado!");
        }
    }

    public void addBook(Book book){
        book.setAuthor(this);
        this.books.add(book);
    }
}
