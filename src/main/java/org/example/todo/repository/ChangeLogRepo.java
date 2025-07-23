package org.example.todo.repository;

import org.example.todo.model.ChangeLogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChangeLogRepo extends MongoRepository<ChangeLogEntry, String> {
    Optional<ChangeLogEntry> findTopByOrderByTimestampDesc();
}
