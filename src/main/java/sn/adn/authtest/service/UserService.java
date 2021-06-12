package sn.adn.authtest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sn.adn.authtest.dao.IUser;
import sn.adn.authtest.domaine.User;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private IUser userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.getUserByEmail(username);
        if (u == null)
            throw  new UsernameNotFoundException(username);
        List<GrantedAuthority> granteds = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(u.toString(), u.getPassword(), granteds);
    }
}
