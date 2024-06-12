package in.kshitij.notes_service.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import brave.Tracer;
import in.kshitij.notes_service.dao.UserNotesDAO;
import in.kshitij.notes_service.entity.UserNotes;
import in.kshitij.notes_service.exceptions.UserNotFoundException;
import in.kshitij.notes_service.models.AllUserNotesResponceDTO;
import in.kshitij.notes_service.models.SaveUserNoteDTO;
import in.kshitij.notes_service.models.UserNotesResponceDTO;
import in.kshitij.notes_service.models.Users;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/notes")
public class NotesApis {

	@Autowired
	private RestClient.Builder rcb;

	@Autowired
	private Tracer tracer;

	@Autowired
	private UserNotesDAO userNotesDAO;

	@GetMapping("/getNotes")
	public UserNotesResponceDTO getUserNotes(@RequestParam(name = "email", required = true) String email) {
		log.info("getUserNotes : " + email);
		Users user = rcb.baseUrl("http://users-queries-service").build().get()
				.uri(uriBuilder -> uriBuilder.path("/usersQ").path("/findUsersByEmail").queryParam("email", email)
						.build())
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
				}).body(Users.class);

		if (user != null && user.getUserId() != null) {
			UserNotesResponceDTO notesResponceDTO = new UserNotesResponceDTO();
			List<UserNotes> userNotes = userNotesDAO
					.findAll(Example.of(UserNotes.builder().userId(user.getUserId()).build()));
			notesResponceDTO.setEmail(email);
			notesResponceDTO.setNotes(userNotes);
			return notesResponceDTO;
		} else {
			throw new UserNotFoundException("User not found.");
		}

	}

	@GetMapping("/getAllNotes")
	public AllUserNotesResponceDTO getAllNotes() {
		log.info("getAllNotes");
		AllUserNotesResponceDTO allUserNotesResponceDTO = new AllUserNotesResponceDTO();
		List<UserNotesResponceDTO> data = new ArrayList<>();
		Map<Long, List<UserNotes>> userNotes = userNotesDAO.findAll().stream()
				.collect(Collectors.groupingBy(UserNotes::getUserId));
		userNotes.forEach((userId, userNotesList) -> {
			Users user = rcb.baseUrl("http://users-queries-service")
					.defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).build().get()
					.uri(uriBuilder -> uriBuilder.path("/usersQ").path("/getuser").path("/" + userId).build())
					.accept(MediaType.APPLICATION_JSON).retrieve()
					.onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {

					}).body(Users.class);

			if (user != null && user.getUserId() != null) {
				UserNotesResponceDTO userNotesResponceDTO = new UserNotesResponceDTO();
				userNotesResponceDTO.setEmail(user.getEmail());
				userNotesResponceDTO.setNotes(userNotesList);
				data.add(userNotesResponceDTO);
			}
		});
		allUserNotesResponceDTO.setData(data);
		return allUserNotesResponceDTO;
	}

	@PostMapping("/saveNote")
	public UserNotesResponceDTO saveNote(@RequestBody SaveUserNoteDTO saveUserNoteDTO) {
		log.info("saveNote : " + saveUserNoteDTO.getEmail() + " , note : " + saveUserNoteDTO);
		Users user = rcb.baseUrl("http://users-queries-service").build().get()
				.uri(uriBuilder -> uriBuilder.path("/usersQ").path("/findUsersByEmail")
						.queryParam("email", saveUserNoteDTO.getEmail()).build())
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
				}).body(Users.class);
		if (user != null && user.getUserId() != null) {
			UserNotesResponceDTO notesResponceDTO = new UserNotesResponceDTO();
			UserNotes userNote = new UserNotes();
			userNote.setUserId(user.getUserId());
			userNote.setNote(saveUserNoteDTO.getNote());
			userNotesDAO.save(userNote);
			List<UserNotes> userNotes = userNotesDAO
					.findAll(Example.of(UserNotes.builder().userId(user.getUserId()).build()));
			notesResponceDTO.setEmail(user.getEmail());
			notesResponceDTO.setNotes(userNotes);
			return notesResponceDTO;
		} else {
			throw new UserNotFoundException("User not found.");
		}

	}

}
