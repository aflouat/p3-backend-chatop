package fr.tmsconsult.p3_backend_chatop.models;

import java.time.LocalDate;

public class DeterministicDateProvider implements DateProvider {

    public LocalDate currentDate;

    @Override
    public LocalDate now() {
        return currentDate;
    }
    public DeterministicDateProvider() {
        this.currentDate = LocalDate.now();
    }

}
