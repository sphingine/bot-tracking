package com.marlabs.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<AuthenticationModel> query = builder.createQuery(AuthenticationModel.class);
		Root<AuthenticationModel> root = query.from(AuthenticationModel.class);
        query.select(root).where(builder.equal(root.get("jwtToken"), jwtToken));
  
        Query<AuthenticationModel> q = getCurrentSession().createQuery(query);
        return q.getResultList();
	}
}
