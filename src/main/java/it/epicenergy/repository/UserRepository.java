package it.epicenergy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicenergy.model.User;



public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findById(Long id);
	public Optional<User> findByUserName(String userName);
	public boolean existsByEmail(String email);
	public boolean existsByUserName(String userName);
}
