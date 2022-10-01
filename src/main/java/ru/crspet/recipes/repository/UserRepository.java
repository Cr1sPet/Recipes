package ru.crspet.recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.crspet.recipes.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByEmail(String email);

}
