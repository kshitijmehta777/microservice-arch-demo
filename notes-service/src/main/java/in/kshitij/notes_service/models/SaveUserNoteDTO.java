package in.kshitij.notes_service.models;

import lombok.Data;

@Data
public class SaveUserNoteDTO {
	private String email;
	private String note;
}
