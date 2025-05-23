package ru.hogwarts.artefact.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.artefact.school.model.Faculty;
import ru.hogwarts.artefact.school.services.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (null == faculty) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        faculty.setId(null);
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty renamefaculty =facultyService.editFaculty(faculty);
        if (renamefaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(renamefaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

}
