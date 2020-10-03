package com.marlabs.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marlabs.model.AuthenticationModel;


@Repository
public class GenericDAOImpl<T> {

	@Autowired
	private SessionFactory sessionFactory;

	protected final Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public void saveOrUpdate(T entity) {
		getCurrentSession().saveOrUpdate( entity );
	}
	
	public List<AuthenticationModel> getAuthenticationModel(String jwtToken) {
		Criteria criteria =  getCurrentSession().createCriteria(AuthenticationModel.class);
		criteria.add(Restrictions.eq("jwtToken", jwtToken));
		return criteria.list();
	}
}
