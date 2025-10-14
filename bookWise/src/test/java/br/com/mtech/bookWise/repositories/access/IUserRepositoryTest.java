package br.com.mtech.bookWise.repositories.access;

import br.com.mtech.bookWise.DTO.access.UserRequestDTO;
import br.com.mtech.bookWise.model.security.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class IUserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    IUserRepository userRepository;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findByEmailUserSuccessCase() {
        String email = "fernandaA1234@gmail.com";
        UserRequestDTO data = new UserRequestDTO(
                "Fernanda Anacleto", "fernandaAnacleto123",
                email, "12345678@A", "Admin"
        );

        this.createUser(data);
        Optional<User> result = this.userRepository.findByEmailUser(email);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get User from DB when user not exists")
    void findByEmailUserCase2() {
        String email = "fernandaA1234@gmail.com";
        Optional<User> result = this.userRepository.findByEmailUser(email);

        assertThat(result.isEmpty()).isTrue();
    }

    private void createUser(UserRequestDTO data){
        User newUser = new User();
        BeanUtils.copyProperties(data, newUser);

        this.entityManager.persist(newUser);
    }
}