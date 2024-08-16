package fr.tmsconsult.p3_backend_chatop.repositories;

import fr.tmsconsult.p3_backend_chatop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByEmail(String email);

}
