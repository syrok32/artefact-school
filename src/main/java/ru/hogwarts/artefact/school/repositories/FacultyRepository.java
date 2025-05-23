package ru.hogwarts.artefact.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.artefact.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByName(String name);
}
