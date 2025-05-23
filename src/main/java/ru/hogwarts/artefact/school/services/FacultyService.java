package ru.hogwarts.artefact.school.services;

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
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    // Service
    public List<Faculty> findByName(String name) {
        return facultyRepository.findByName(name);
    }

    public Collection<Student> getStudentsByFaculty(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        return faculty != null ? faculty.getStudents() : Collections.emptyList();
    }

}

