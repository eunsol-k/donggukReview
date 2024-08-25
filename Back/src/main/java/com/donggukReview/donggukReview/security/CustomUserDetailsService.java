package com.donggukReview.donggukReview.security;

import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Users user = usersRepository.findByUserId(username);
        
        if (user == null) {
        	return null;
        }

        return new AuthDetails(user);
    }

}
