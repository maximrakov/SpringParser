package org.itmo.telegramservice.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ApplicationUserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("ivan", "$2a$12$0f9oUToTvmvfgem3..pYNe2G9z6YGfwkheiNxW1v9xi5u.VkqUAQ.",new ArrayList<>());
    }


}
