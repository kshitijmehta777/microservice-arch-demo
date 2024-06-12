package in.kshitij.users_command_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserName {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userNameId;
	private String firstName;
	private String middleName;
	private String lastName;
}
