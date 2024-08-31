package fr.tmsconsult.p3_backend_chatop.repositories;

import fr.tmsconsult.p3_backend_chatop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
