package fr.tmsconsult.p3_backend_chatop.services.interfaces;

import fr.tmsconsult.p3_backend_chatop.entities.Message;

public interface IMessageService {
    /**
     * Use this to send a message to the owner of a property for rent
     * @param message
     */
    void add(Message message);

}
