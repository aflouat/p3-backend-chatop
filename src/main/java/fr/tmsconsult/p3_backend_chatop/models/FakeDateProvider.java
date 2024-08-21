package fr.tmsconsult.p3_backend_chatop.models;

import java.time.LocalDate;

public class FakeDateProvider implements DateProvider {
    private LocalDate localDate;

    public FakeDateProvider(String dateTime) {
        this.localDate = LocalDate.parse(dateTime);
    }

    @Override
    public LocalDate now() {
        return localDate;
    }

    public FakeDateProvider(LocalDate localDate) {
        this.localDate = localDate;
    }
}
