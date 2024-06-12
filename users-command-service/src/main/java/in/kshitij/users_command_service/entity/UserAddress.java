package in.kshitij.users_command_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userAddressId;
	private String addressRow1;
	private String addressRow2;
	private String city;
	private String state;
	private String country;
}
