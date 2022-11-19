package com.herohuapp.parkhyeoncheolboot2.domain.simple_users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SimpleUsersRepository extends JpaRepository<SimpleUsers, Long> {
	@Query("SELECT p FROM SimpleUsers p where p.username = :username")
	SimpleUsers findByName(@Param("username") String username);
	
	Page<SimpleUsers> findByUsernameContaining(String keyword, Pageable pageble);
}
