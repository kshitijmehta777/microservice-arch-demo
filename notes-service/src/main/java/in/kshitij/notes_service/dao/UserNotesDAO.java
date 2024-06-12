package in.kshitij.notes_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.kshitij.notes_service.entity.UserNotes;

public interface UserNotesDAO extends JpaRepository<UserNotes, Long> {

}
