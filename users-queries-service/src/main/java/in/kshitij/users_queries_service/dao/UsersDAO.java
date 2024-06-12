package in.kshitij.users_queries_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.kshitij.users_queries_service.entity.Users;

public interface UsersDAO extends JpaRepository<Users, Long> {

}
