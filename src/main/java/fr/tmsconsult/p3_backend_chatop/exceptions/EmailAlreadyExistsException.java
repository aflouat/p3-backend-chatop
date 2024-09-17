package fr.tmsconsult.p3_backend_chatop.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailAlreadyExistsException extends Throwable {
    private final String message;
}
