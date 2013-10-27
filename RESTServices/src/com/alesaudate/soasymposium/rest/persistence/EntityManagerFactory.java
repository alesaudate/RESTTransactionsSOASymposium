package com.alesaudate.soasymposium.rest.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerFactory {
	
	
	private javax.persistence.EntityManagerFactory emf;
	
	private static EntityManagerFactory instance;
	
	private EntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("default");
	}
	
	public static synchronized EntityManagerFactory getInstance() {
		if (instance == null) {
			instance = new EntityManagerFactory();
		}
		return instance;
	}
	
	
	public EntityManager getEntityManager () {
		return emf.createEntityManager();
	}

}
