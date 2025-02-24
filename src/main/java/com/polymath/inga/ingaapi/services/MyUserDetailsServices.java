package com.polymath.inga.ingaapi.services;

import com.polymath.inga.ingaapi.models.UserPrincipal;
import com.polymath.inga.ingaapi.models.Users;
import com.polymath.inga.ingaapi.repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsServices implements UserDetailsService {
    private final UserRepo repo;

    public MyUserDetailsServices(UserRepo userRepo) {
        this.repo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = repo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));
        return new UserPrincipal(user);
    }
}
