package fr.tmsconsult.p3_backend_chatop.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IMyUserDetailsService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
