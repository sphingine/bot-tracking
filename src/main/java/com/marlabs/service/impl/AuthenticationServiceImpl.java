package com.marlabs.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.dao.impl.AuthenticationDAOImpl;
import com.marlabs.model.AuthenticationModel;
import com.marlabs.service.AuthenticationService;
import com.marlabs.util.QueryUtils;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	QueryUtils queryUtils;
	
	@Autowired
	AuthenticationDAOImpl authenticationDAO;
	
	
	@Transactional
	public String getAuthenticationQuery(String jwtToken) throws NoSuchAlgorithmException {
		String query = queryUtils.getQuery();
		AuthenticationModel authModel = new AuthenticationModel(jwtToken, query, queryUtils.getSum(query));
		authenticationDAO.saveOrUpdate(authModel);
		return query;

	}
	
	@Transactional
	public boolean validateAuthenticationQuery(String jwtToken, String query, int sum) {
		boolean isValid = false;
		List<AuthenticationModel> models = authenticationDAO.getAuthenticationModel(jwtToken);
		
		if (null != models 
				&& !models.isEmpty()) {
			int correctResult = models.get(0).getSum();
			String correctQuery = models.get(0).getQuery();
			
			if (correctResult == sum && correctQuery.equalsIgnoreCase(query)) {
				isValid = true;
			}
		}
		return isValid;
	}
}