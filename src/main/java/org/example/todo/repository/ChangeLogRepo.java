package org.example.todo.repository;

import org.example.todo.model.ChangeLogEntry;
import org.example.todo.model.StackType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChangeLogRepo extends MongoRepository<ChangeLogEntry, String> {
    Optional<ChangeLogEntry> findTopByStackTypeOrderByTimestampDesc(StackType stackType);
}
