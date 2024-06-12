package in.kshitij.users_command_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.kshitij.users_command_service.entity.UserRole;

public interface UserRoleDAO extends JpaRepository<UserRole, Long> {

}
