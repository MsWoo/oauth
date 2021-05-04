package com.example.oauth.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
	@Id
	@Column(name = "user_id")
	@GeneratedValue
	private Long id;

	private String email; // 이메일

	private String password; // 패스워드

	private String username;

	private boolean enabled;

	private boolean fromSocial;

//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//	private Set<Role> roles = new HashSet<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<UserRole> roles = new HashSet<>();

	public void addUserRole(UserRole userRole) {
		roles.add(userRole);
	}

}
