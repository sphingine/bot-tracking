package com.marlabs.dao.impl;

import org.springframework.stereotype.Repository;

import com.marlabs.dao.AuthenticationDAO;
import com.marlabs.model.AuthenticationModel;

@Repository
public class AuthenticationDAOImpl extends GenericDAOImpl<AuthenticationModel> implements AuthenticationDAO {
	
}
