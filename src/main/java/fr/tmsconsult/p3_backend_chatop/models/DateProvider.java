package fr.tmsconsult.p3_backend_chatop.models;

import java.time.LocalDate;

public interface DateProvider {
    LocalDate now();
}