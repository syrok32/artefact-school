package ru.hogwarts.artefact.school.repositories;

import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.artefact.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {


    List<Student> findByAgeBetween(Integer startAge, Integer endAge);

    @Query(value = "SELECT COUNT(name) FROM student", nativeQuery = true)
    Long countAllStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Double getAverageStudentAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> findLastFiveStudents();
}

