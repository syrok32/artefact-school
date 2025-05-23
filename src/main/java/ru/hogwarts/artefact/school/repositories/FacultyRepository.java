package ru.hogwarts.artefact.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.artefact.school.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
