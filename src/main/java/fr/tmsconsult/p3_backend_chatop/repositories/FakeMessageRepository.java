package fr.tmsconsult.p3_backend_chatop.repositories;


import fr.tmsconsult.p3_backend_chatop.entities.Message;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FakeMessageRepository implements MessageRepository {

    private List<Message> messages = new ArrayList<>();

    @Override
    public <S extends Message> S save(S entity) {
        entity.setId(messages.size() + 1);  // Simple ID assignment
        messages.add(entity);
        return entity;
    }

    @Override
    public Optional<Message> findById(Integer id) {
        return messages.stream().filter(m -> m.getId() == id).findFirst();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public long count() {
        return messages.size();
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Message entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Message> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Message> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Message> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Message> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Message getOne(Integer integer) {
        return null;
    }

    @Override
    public Message getById(Integer integer) {
        return null;
    }

    @Override
    public Message getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Message> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Message> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Message> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Message> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Message> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Message> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Message, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Message> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Message> findAll() {
        return List.of();
    }

    @Override
    public List<Message> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public List<Message> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return null;
    }

    // Implement other required methods as needed
}
