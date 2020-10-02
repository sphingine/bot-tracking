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

	private Class< T > clazz;

	@Autowired
	private SessionFactory sessionFactory;

	public void setClazz(Class<T> clazzToSet) {
		clazz = clazzToSet;
	}

	protected final Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}

	public T update(T entity) {
		return (T) getCurrentSession().merge(entity);
	}

	public void save(T entity) {
		getCurrentSession().persist( entity );
	}
	
	public void saveOrUpdate(T entity) {
		getCurrentSession().saveOrUpdate( entity );
	}

	public T findOne(long id) {
		return (T) getCurrentSession().get(clazz, id);
	}
	
	public List getAuthenticationModel(String jwtToken) {
		Criteria criteria =  getCurrentSession().createCriteria(AuthenticationModel.class);
		criteria.add(Restrictions.eq("jwtToken", jwtToken));
		return criteria.list();
	}
}
