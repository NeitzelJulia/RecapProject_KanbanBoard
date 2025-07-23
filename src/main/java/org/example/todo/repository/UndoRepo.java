package org.example.todo.repository;

import org.example.todo.model.UndoEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UndoRepo extends MongoRepository<UndoEntry, String> {
    Optional<UndoEntry> findTopByOrderByTimestampDesc();
}
