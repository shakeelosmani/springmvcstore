package com.kaushik.simplestore.service;

import com.kaushik.simplestore.domain.Profile;
import com.kaushik.simplestore.repository.MemberDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

@Service
@Transactional
public class MemberService {
	
	@Resource
	private MemberDao memberDao;
	
	public List<Profile> getAll() {
		return memberDao.findAll();
	}

}
