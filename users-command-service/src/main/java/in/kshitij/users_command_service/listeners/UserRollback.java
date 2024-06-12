package in.kshitij.users_command_service.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.kshitij.users_command_service.dao.UserAddressDAO;
import in.kshitij.users_command_service.dao.UserNameDAO;
import in.kshitij.users_command_service.dao.UsersDAO;
import in.kshitij.users_command_service.entity.Users;

@Component
public class UserRollback {
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UsersDAO usersDAO;
	
	@Autowired
	private UserNameDAO userNameDAO;
	
	@Autowired
	private UserAddressDAO userAddressDAO;
	
	@KafkaListener(topics = "rollback-user")
	public void rollBackUser(String userJSON) {
		Users user;
		try {
			System.out.println("user " + userJSON);
			user = objectMapper.readValue(userJSON, Users.class);
			userNameDAO.deleteById(user.getUserName().getUserNameId());
			userAddressDAO.deleteById(user.getUserAddress().getUserAddressId());
			usersDAO.deleteById(user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

}
