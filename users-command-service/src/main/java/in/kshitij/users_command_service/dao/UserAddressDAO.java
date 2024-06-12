package in.kshitij.users_command_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.kshitij.users_command_service.entity.UserAddress;

public interface UserAddressDAO extends JpaRepository<UserAddress, Long> {

}
