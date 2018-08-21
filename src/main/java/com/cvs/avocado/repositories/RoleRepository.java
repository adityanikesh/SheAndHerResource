package com.cvs.avocado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvs.avocado.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
