package com.example.oauth.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.example.oauth.entity.User;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class UserRepository {
	private final EntityManager em;

	public void saveUser(User user){
		em.persist(user);
	}
	
	public User findUserByUsername(String username){
		TypedQuery<User> query = em.createQuery("select m from User as m where m.username = ?1", User.class)
			.setParameter(1, username);
		return query.getSingleResult();
	}
	
	public User findUserById(Long id){
		TypedQuery<User> query = em.createQuery("select m from User as m where m.id = ?1", User.class)
			.setParameter(1, id);
		return query.getSingleResult();
	}


	public User findUserByEmail(String email){
		TypedQuery<User> query = em.createQuery("select m from User as m where m.email = ?1", User.class)
			.setParameter(1, email);
		try {
	        return query.getSingleResult();
	    } catch (NoResultException nre) {
	        return null;
	    }
	}
	
	public List<User> findAllUser(){
		List<User> users = em.createQuery("SELECT u FROM User u").getResultList();
		return users;
	}
	
	public void deleteUser(Long id) {
		Query query = em.createQuery("DELETE FROM User WHERE id = ?1")
			.setParameter(1, id);
		query.executeUpdate();
	}
	
	public void deleteUserRole(Long id) {
		Query query = em.createNativeQuery("DELETE FROM user_roles WHERE user_user_id = ?1")
			.setParameter(1, id);
		query.executeUpdate();
	}
	
	public void giveAdmin(Long id) {
		Query query = em.createNativeQuery("INSERT INTO user_roles (user_user_id, roles) VALUES (?1, 1)")
		.setParameter(1, id);
		query.executeUpdate();
	}
	
}
