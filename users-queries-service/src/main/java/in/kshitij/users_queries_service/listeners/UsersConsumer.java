package in.kshitij.users_queries_service.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.kshitij.users_queries_service.configs.MessageProducer;
import in.kshitij.users_queries_service.dao.UserAddressDAO;
import in.kshitij.users_queries_service.dao.UserNameDAO;
import in.kshitij.users_queries_service.dao.UsersDAO;
import in.kshitij.users_queries_service.entity.Users;

@Component
public class UsersConsumer {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UsersDAO usersDAO;
	
	@Autowired
	private UserNameDAO userNameDAO;
	
	@Autowired
	private UserAddressDAO userAddressDAO;
	
	@Autowired
	private MessageProducer messageProducer;

	@KafkaListener(topics = "new-user")
	public void addNewuser(String userJSON) {
		Users user;
		try {
			System.out.println("user " + userJSON);
			user = objectMapper.readValue(userJSON, Users.class);
			userAddressDAO.save(user.getUserAddress());
			userNameDAO.save(user.getUserName());
			usersDAO.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				messageProducer.sendMessage("rollback-user", userJSON);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
		}
	}
}
