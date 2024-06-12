package in.kshitij.notes_service.models;

import java.util.List;

import in.kshitij.notes_service.entity.UserNotes;
import lombok.Data;

@Data
public class UserNotesResponceDTO {
	private String email;
	private List<UserNotes> notes;
}
