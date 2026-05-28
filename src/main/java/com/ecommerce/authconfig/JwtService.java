package com.ecommerce.authconfig;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

	private final Long EXPIRATION_TIME = 86400000L;
	private final String SECRET_STR= "ASDFGHJKL12MNBVCXZ3456QWERTYU7890IOP";


	public String generateToken(UserDetails userDetails) {
		return Jwts
                .builder()
                .claim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(""))
				.subject(userDetails.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSigninKey())
				.compact();
	}
	public SecretKey getSigninKey() {
		return Keys.hmacShaKeyFor(SECRET_STR.getBytes(StandardCharsets.UTF_8));
	}
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public Boolean 	isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private Boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
}
