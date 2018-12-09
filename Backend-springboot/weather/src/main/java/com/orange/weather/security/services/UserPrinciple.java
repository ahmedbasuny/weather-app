package com.orange.weather.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orange.weather.model.User;

public class UserPrinciple implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
    private String name;
    
    private String mail;
    
    private String phone;
 
    @JsonIgnore
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;
	
    public UserPrinciple(Integer id, String name, 
    		String mail, String phone, String password, 
    		Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.phone = phone;
		this.password = password;
		this.authorities = authorities;
	}
    
    public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName().name())
        ).collect(Collectors.toList());
 
        return new UserPrinciple(
                user.getId(),
                user.getName(),
                user.getMail(),
                user.getPhone(),
                user.getPassword(),
                authorities
        );
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPhone() {
        return phone;
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return mail;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}
