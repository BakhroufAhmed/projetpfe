package erp.bll.entities;

import javax.persistence.Id;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;

import com.fasterxml.jackson.core.sym.Name;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
//@Data @AllArgsConstructor @NoArgsConstructor
public class AppUser {
	@Id @GeneratedValue
	private Long id ;
	@Column(unique=true)
	private String username;
	
	private String password;
	public AppUser() {
		super();
	}
	public AppUser(Long id, String username, String password, Collection<AppRole> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonSetter
	public void setPassword(String password) {
		this.password = password;
	}
	public Collection<AppRole> getRoles() {
		return roles;
	}
	public void setRoles(Collection<AppRole> roles) {
		this.roles = roles;
	}
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<AppRole> roles=new ArrayList<>();
	
	

}*/

@Entry(
		  base = "ou=people", 
		  objectClasses = { "person", "inetOrgPerson", "top" })
@Data @AllArgsConstructor @NoArgsConstructor
		public class AppUser {
		    @Id
		    private Name id;
		     
		    private @Attribute(name = "cn") String username;
		    private @Attribute(name = "sn") String password;
		    private @Attribute(name= "sn") String roles;
			public Object getAuthorities() {
				// TODO Auto-generated method stub
				return null;
			}
		 
		    // standard getters/setters
		}
