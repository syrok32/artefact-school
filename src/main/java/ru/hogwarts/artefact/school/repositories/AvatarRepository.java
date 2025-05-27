package ru.hogwarts.artefact.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.artefact.school.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);




}
