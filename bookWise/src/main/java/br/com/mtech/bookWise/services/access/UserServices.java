package br.com.mtech.bookWise.services.access;

import br.com.mtech.bookWise.DTO.access.UserRequestDTO;
import br.com.mtech.bookWise.enums.Roles;
import br.com.mtech.bookWise.exceptions.ModelExistsException;
import br.com.mtech.bookWise.exceptions.ModelNotFoundException;
import br.com.mtech.bookWise.exceptions.NullInformationException;
import br.com.mtech.bookWise.infra.security.AuthServices;
import br.com.mtech.bookWise.model.Book;
import br.com.mtech.bookWise.model.security.User;
import br.com.mtech.bookWise.repositories.access.IUserRepository;
import br.com.mtech.bookWise.utils.Validation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServices implements UserDetailsService {

    private IUserRepository repository;
    private AuthServices authServices;
    private Validation validation;

    public User createUser(UserRequestDTO data) throws NullInformationException, ModelExistsException {
        User user = new User();

        this.validation.validateUserDataCreate(data, null);

        user.setName(data.name());
        user.setUsername(data.username());
        user.setEmailUser(data.emailUser());
        user.setPassword(this.authServices.encodePassword(data.password()));

        if(data.role().equals("Admin")){
            user.setRole(Roles.ADMIN);
        }
        if(data.role().equals("User")){
            user.setRole(Roles.USER);
        }

        saveUser(user);
        return user;
    }



    public User updateUser(UUID idUser, UserRequestDTO data) throws ModelNotFoundException, NullInformationException, ModelExistsException {
        User userFounded = this.getUserById(idUser);

        this.validation.validateUserDataCreate(data, idUser);

        userFounded.setName(data.name());
        userFounded.setUsername(data.username());
        userFounded.setEmailUser(data.emailUser());

        saveUser(userFounded);
        return userFounded;
    }


    public void deleteUser(UUID idUser) throws ModelNotFoundException {
        User user = getUserById(idUser);
        this.repository.delete(user);
    }

    public List<User> getAllUsers(){
        return this.repository.findAll();
    }

    public List<Book> booksByUser(UUID idUser) throws ModelNotFoundException {
        User userFounded = this.getUserById(idUser);
        return userFounded.getBooks();
    }


    public User getUserById(UUID idUser) throws ModelNotFoundException {
        return this.repository.findById(idUser).orElseThrow(
                () -> new ModelNotFoundException("Usuário não encontrado para o ID: " + idUser)
        );
    }

    public void saveUser(User user) throws NullInformationException {
        if(user != null){
            this.repository.save(user);
        }else{
            throw new NullInformationException("Usuário não pode ser nulo ao salvar");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username);
    }
}
