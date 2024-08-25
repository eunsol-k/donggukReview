package com.donggukReview.donggukReview.common;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {
	private Key hmacKey;
	private long expirationTime;

	@Autowired
	private UsersRepository usersRepository;

	public JwtUtils(@Value("${jwt.secret}") String secretKey,
					@Value("${jwt.expiration-time}") long accessTokenExpTime) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.hmacKey = Keys.hmacShaKeyFor(keyBytes);
		this.expirationTime = accessTokenExpTime;
	}

	/**
	 * Access Token 생성
	 *
	 * @param user
	 * @return Access Token String
	 */
	public String createAccessToken(Users user) {
		return createToken(user, expirationTime);
	}

	/**
	 * JWT 생성
	 *
	 * @param user
	 * @param expireTime
	 * @return JWT String
	 */
	private String createToken(Users user, long expireTime) {
		Instant now = Instant.now();

		return Jwts.builder().claim("name", user.getUserNickname())
				.claim("role", user.getUserRole()).claim("sub", user.getUserId())
				.claim("jti", String.valueOf(user.getId())).claim("iat", Date.from(now))
				.claim("exp", Date.from(now.plus(expirationTime, ChronoUnit.MILLIS))).signWith(hmacKey).compact();
	}

	private Claims getAllClaimsFromToken(String token) {
		Jws<Claims> jwt = Jwts.parser().setSigningKey(hmacKey).build().parseClaimsJws(token);

		return jwt.getPayload();
	}

	public Object getRoleFromToken(String token) {
		final Claims claims = getAllClaimsFromToken(token);

		return claims.get("role");
	}

	public String getSubjectFromToken(String token) {
		final Claims claims = getAllClaimsFromToken(token);

		return claims.getSubject();
	}

	private Date getExiprationDateFromToken(String token) {
		final Claims claims = getAllClaimsFromToken(token);
		return claims.getExpiration();
	}

	private boolean isTokenExpired(String token) {
		Date expiration = getExiprationDateFromToken(token);
		return expiration.before(new Date());
	}

	public boolean validateToken(String token, Users user) {
		// 토큰 유효기간 체크
		if (isTokenExpired(token)) {
			return false;
		}

		// 토큰 내용을 검증
		String subject = getSubjectFromToken(token);
		String userId = user.getUserId();

		return subject != null && subject.equals(userId);
	}

	public Users getUser(String token) {
		String userId = getSubjectFromToken(token);
		Users user = usersRepository.findByUserId(userId);

        return user;
	}

	public Authentication getAuthentication(String jwtToken) {
		String role = (String) getRoleFromToken(jwtToken);

		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(role.replaceAll(" ", "").split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return new UsernamePasswordAuthenticationToken(getSubjectFromToken(jwtToken), null, authorities);
	}

}