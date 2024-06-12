package in.kshitij.notes_service.models;

import java.util.List;

import lombok.Data;

@Data
public class AllUserNotesResponceDTO {
	private List<UserNotesResponceDTO> data;
}
