package pl.edu.pja.tpo02.flashcardsapp;

import org.springframework.data.repository.CrudRepository;

public interface SpringRepository extends CrudRepository<Entry,Integer> {
    Entry findById(int i);

}
