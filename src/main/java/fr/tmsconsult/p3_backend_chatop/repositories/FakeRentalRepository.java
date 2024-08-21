package fr.tmsconsult.p3_backend_chatop.repositories;

import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FakeRentalRepository implements RentalRepository {
    private static List<Rental> rentals = new ArrayList<>();
    // Ensure the repository is cleared
    public void clear() {
        rentals.clear();
    }
    @Override
    public void flush() {
        // No operation needed for in-memory storage
    }

    @Override
    public <S extends Rental> S saveAndFlush(S entity) {
        rentals.add(entity);
        return entity;
    }

    @Override
    public <S extends Rental> List<S> saveAllAndFlush(Iterable<S> entities) {
        entities.forEach(this::saveAndFlush);
        return (List<S>) rentals;
    }

    @Override
    public void deleteAllInBatch(Iterable<Rental> entities) {
        entities.forEach(rentals::remove);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteAllInBatch() {
        rentals.clear();
    }

    @Override
    public Rental getOne(Integer id) {
        return rentals.stream().filter(r -> r.getId()==id).findFirst().orElse(null);
    }

    @Override
    public Rental getById(Integer id) {
        return getOne(id);
    }

    @Override
    public Rental getReferenceById(Integer id) {
        return getOne(id);
    }

    @Override
    public <S extends Rental> List<S> findAll(Example<S> example) {
        return (List<S>) rentals;
    }

    @Override
    public <S extends Rental> List<S> findAll(Example<S> example, Sort sort) {
        return findAll(example); // No sorting applied in this fake implementation
    }

    @Override
    public <S extends Rental> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return (List<S>) rentals;
    }

    @Override
    public List<Rental> findAll() {
        return rentals;
    }

    @Override
    public List<Rental> findAllById(Iterable<Integer> ids) {
        List<Rental> result = new ArrayList<>();
        ids.forEach(id -> findById(id).ifPresent(result::add));
        return result;
    }

    @Override
    public <S extends Rental> S save(S entity) {
        rentals.add(entity);
        return entity;
    }

    @Override
    public Optional<Rental> findById(Integer id) {
        return rentals.stream().filter(r -> r.getId()==id).findFirst();
    }

    @Override
    public boolean existsById(Integer id) {
        return rentals.stream().anyMatch(r -> r.getId()==id);
    }

    @Override
    public long count() {
        return rentals.size();
    }

    @Override
    public void deleteById(Integer id) {
        rentals.removeIf(r -> r.getId()==id);
    }

    @Override
    public void delete(Rental entity) {
        rentals.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends Rental> entities) {
        entities.forEach(rentals::remove);
    }

    @Override
    public void deleteAll() {
        rentals.clear();
    }

    @Override
    public List<Rental> findAll(Sort sort) {
        return rentals; // No sorting applied in this fake implementation
    }

    @Override
    public Page<Rental> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Paging not supported in FakeRentalRepository");
    }

    @Override
    public <S extends Rental> Optional<S> findOne(Example<S> example) {
        return Optional.empty(); // Simplified implementation
    }

    @Override
    public <S extends Rental> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Paging not supported in FakeRentalRepository");
    }

    @Override
    public <S extends Rental> long count(Example<S> example) {
        return rentals.size();
    }

    @Override
    public <S extends Rental> boolean exists(Example<S> example) {
        return !rentals.isEmpty();
    }

    @Override
    public <S extends Rental, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }
}
