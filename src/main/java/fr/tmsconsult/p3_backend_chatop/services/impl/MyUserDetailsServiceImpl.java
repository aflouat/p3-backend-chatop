package fr.tmsconsult.p3_backend_chatop.services.impl;


import fr.tmsconsult.p3_backend_chatop.entities.User;
import fr.tmsconsult.p3_backend_chatop.entities.UserPrincipal;
import fr.tmsconsult.p3_backend_chatop.repositories.UserRepo;
import fr.tmsconsult.p3_backend_chatop.services.interfaces.IMyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements IMyUserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }
        
        return new UserPrincipal(user);
    }
}
