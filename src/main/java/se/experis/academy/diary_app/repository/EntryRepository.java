package se.experis.academy.diary_app.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.experis.academy.diary_app.model.Entry;

import java.util.List;

@Repository
public interface EntryRepository extends CrudRepository<Entry, Long> {

    List<Entry> findByActiveTrueOrderByDateDesc();

    Entry findById(long id);

    @Override
    void delete(Entry entry);

    @Override
    <S extends Entry> S save(S s);

}

