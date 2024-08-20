package fr.tmsconsult.p3_backend_chatop.models;

import java.time.LocalDateTime;

public class DeterministicDateProvider implements DateTimeProvider {

    public LocalDateTime currentDate;

    @Override
    public LocalDateTime now() {
        return currentDate;
    }
    public DeterministicDateProvider() {
        this.currentDate = LocalDateTime.now();
    }

}
