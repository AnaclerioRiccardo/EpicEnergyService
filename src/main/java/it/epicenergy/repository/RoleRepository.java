package it.epicenergy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicenergy.model.Role;
import it.epicenergy.model.Roles;



public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleName(Roles role);
}
