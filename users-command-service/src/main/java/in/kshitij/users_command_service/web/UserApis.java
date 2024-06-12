package in.kshitij.users_command_service.web;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import in.kshitij.users_command_service.configs.MessageProducer;
import in.kshitij.users_command_service.dao.UserAddressDAO;
import in.kshitij.users_command_service.dao.UserNameDAO;
import in.kshitij.users_command_service.dao.UserRoleDAO;
import in.kshitij.users_command_service.dao.UsersDAO;
import in.kshitij.users_command_service.entity.UserAddress;
import in.kshitij.users_command_service.entity.UserName;
import in.kshitij.users_command_service.entity.UserRole;
import in.kshitij.users_command_service.entity.Users;
import in.kshitij.users_command_service.exceptions.EmailAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usersC")
public class UserApis {

	@Autowired
	private UsersDAO usersDAO;

	@Autowired
	private UserNameDAO userNameDAO;

	@Autowired
	private UserAddressDAO userAddressDAO;

	@Autowired
	private UserRoleDAO userRoleDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageProducer messageProducer;

	@PostMapping("/save")
	public Users saveUsers(@RequestBody Users users) {
		log.info("save Users : " + users.getEmail());
		if (usersDAO.exists(Example.of(Users.builder().email(users.getEmail()).build()))) {
			throw new EmailAlreadyExistsException("Email already exists for user");
		}
		String userPassword = passwordEncoder.encode(users.getPassword());
		UserName userName = userNameDAO.save(users.getUserName());
		UserAddress userAddress = userAddressDAO.save(users.getUserAddress());
		Set<UserRole> userRoles = users.getUserRoles().stream().map(role -> {
			return userRoleDAO.findOne(Example.of(role));
		}).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
		users.setPassword(userPassword);
		users.setUserName(userName);
		users.setUserAddress(userAddress);
		users.setUserRoles(userRoles);
		users = usersDAO.save(users);
		try {
			messageProducer.sendMessage("new-user", users);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return users;
	}

}
