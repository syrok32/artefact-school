package ru.hogwarts.artefact.school.repositories;

import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.artefact.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {


    List<Student> findByAgeBetween(Integer startAge, Integer endAge);
}

