package ru.hogwarts.artefact.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.artefact.school.model.Faculty;
import ru.hogwarts.artefact.school.model.Student;
import ru.hogwarts.artefact.school.repositories.FacultyRepository;
import ru.hogwarts.artefact.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("create");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("find");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("edit");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("delete");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        logger.info("all");
        return facultyRepository.findAll();
    }


    public List<Faculty> findByName(String name) {
        logger.info("find by name");
        return facultyRepository.findByName(name);
    }

    public Collection<Student> getStudentsByFaculty(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        return faculty != null ? faculty.getStudents() : Collections.emptyList();
    }

}

