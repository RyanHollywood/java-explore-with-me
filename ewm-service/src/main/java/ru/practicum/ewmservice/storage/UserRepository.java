package ru.practicum.ewmservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}