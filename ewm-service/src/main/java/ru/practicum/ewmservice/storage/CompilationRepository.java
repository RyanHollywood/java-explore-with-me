package ru.practicum.ewmservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.model.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}
