package ru.hogwarts.artefact.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.artefact.school.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

