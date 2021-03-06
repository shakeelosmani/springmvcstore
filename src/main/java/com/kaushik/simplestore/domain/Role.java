package com.kaushik.simplestore.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "role", catalog = "simplestore")
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = 6464512438578201997L;
	
	private Byte id;
	private String name;
	private Set<Profile> members = new HashSet<Profile>(0);

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public Role(String name, Set<Profile> members) {
		this.name = name;
		this.members = members;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Byte getId() {
		return this.id;
	}

	public void setId(Byte id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<Profile> getMembers() {
		return this.members;
	}

	public void setMembers(Set<Profile> members) {
		this.members = members;
	}

}
