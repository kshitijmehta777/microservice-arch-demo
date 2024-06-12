package in.kshitij.users_queries_service.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.kshitij.users_queries_service.dao.UsersDAO;
import in.kshitij.users_queries_service.entity.Users;
import in.kshitij.users_queries_service.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usersQ")
public class UserApis {

	@Autowired
	private UsersDAO usersDAO;

	@GetMapping("/getAllUsers")
	public List<Users> getAllUsers() {
		log.info("getAllUsers");
		return usersDAO.findAll();
	}

	@GetMapping("/getuser/{userId}")
	public Users getUser(@PathVariable Long userId) {
		log.info("getUser : " + userId);
		return usersDAO.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
	}

	@GetMapping("/findUsersByEmail")
	public Users findUsersByEmail(@RequestParam(name = "email", required = true) String email) {
		log.info("findUsersByEmail : " + email);
		return usersDAO.findOne(Example.of(Users.builder().email(email).build()))
				.orElseThrow(() -> new UserNotFoundException("user not found"));
	}

}
