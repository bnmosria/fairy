package com.bopera.pointofsales.auth.service;

import com.bopera.pointofsales.auth.model.response.AuthResponse;
import com.bopera.pointofsales.persistence.model.UserInfoDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

	private final String secret;

	public JwtService(@Value("${jwt.secret}") String secret) {
		this.secret = secret;
	}

	public AuthResponse generateToken(UserInfoDetails userInfoDetails) {
		Map<String, Object> claims = new HashMap<>();

		userInfoDetails.getAuthorities()
				.forEach(grantedAuthority -> claims.put("roles", grantedAuthority.getAuthority()));

		return createToken(claims, userInfoDetails.getUsername());
	}

	private AuthResponse createToken(Map<String, Object> claims, String userName) {
		Instant instant = LocalDateTime.now().toInstant(ZoneOffset.UTC);

		Date issuedAt = Date.from(instant);
		Date expiration = Date.from(instant.plus(10, ChronoUnit.HOURS));

		String accessToken = Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(issuedAt)
				.setExpiration(expiration)
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

		return AuthResponse.builder()
				.accessToken(accessToken)
				.roles((String) claims.get("roles"))
				.expiresIn(expiration)
				.build();
	}

	private Key getSignKey() {
		byte[] keyBytes= Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
