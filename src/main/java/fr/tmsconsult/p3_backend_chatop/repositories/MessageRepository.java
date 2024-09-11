package fr.tmsconsult.p3_backend_chatop.repositories;

import fr.tmsconsult.p3_backend_chatop.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
