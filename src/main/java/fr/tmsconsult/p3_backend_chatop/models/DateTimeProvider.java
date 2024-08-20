package fr.tmsconsult.p3_backend_chatop.models;

import java.time.LocalDateTime;

public interface DateTimeProvider {
    LocalDateTime now();
}