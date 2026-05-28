package com.ecommerce.user.entity;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.audit.BaseAudit;
import com.ecommerce.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseAudit implements UserDetails {


	private static final long serialVersionUID = 1L;
	private String name;
	private String address;

	@Column(unique =  true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	private Long phone;
	private Boolean isActive = true;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role roles;

	@Override
	@NullMarked
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(roles.name()));
	}

	@Override
	@NullMarked
	public String getUsername() { return email; }

	@Override
	public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

	@Override
	public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

	@Override
	public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

	@Override
	public boolean isEnabled() { return isActive; }
	
}
