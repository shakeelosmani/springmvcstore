package com.kaushik.simplestore.repository;

import org.springframework.data.repository.Repository;

import com.kaushik.simplestore.domain.Profile;

import java.util.List;

public interface MemberDao extends Repository<Profile, Integer> {
	
	List<Profile> findAll();
	
	List<Profile> findByUsername(String username);
	
}
