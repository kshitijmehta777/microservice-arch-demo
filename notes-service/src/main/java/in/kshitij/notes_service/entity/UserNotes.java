package in.kshitij.notes_service.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserNotes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userNotesId;
	@Column(nullable = false)
	private Long userId;
	private String note;
	@CreationTimestamp
	private LocalDateTime entryTime;
}
