package com.emaunzpa.db;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.emaunzpa.dao.UserDAO;
import com.emaunzpa.model.Authorities;
import com.emaunzpa.model.User;

public class UserDriver implements UserDAO {
	
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction = null;
	private static Logger log;
	
	public UserDriver() {
		log = Logger.getLogger(UserDriver.class);
	}

	@Override
	public User findUserByUsername(String username) {
		
		User user = null;
		session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			user = session.get(User.class, username);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			log.error(e.getMessage());
		} finally {
			session.close();
		}
		
		return user;
	}
	
	public boolean addUser(User user, Authorities authorities) {
	
		session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			session.save(user);
			session.save(authorities);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			log.error(e.getMessage());
		} finally {
			session.close();
	    }		
		
		return true;
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		UserDriver.log = log;
	}

}
