package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.User;
import edu.utez.sisabe.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final LogbookService logbookService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, LogbookService logbookService) {
        this.userRepository = userRepository;
        this.logbookService = logbookService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> findAllByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    public User findUserById(String id) {
        return userRepository.findUserById(id);
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    public User save(User user) {
        logbookService.save(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User user){
        return userRepository.save(user);
    }

    public void updatePassword(User user) {
        User userAux = userRepository.findUserById(user.getId());
        if (userAux != null) {
            user.setRole(userAux.getRole());
            user.setUsername(userAux.getUsername());
            logbookService.update(userAux, user);
            userAux.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(userAux);
        }
    }

    public void delete(User user) {
        logbookService.delete(userRepository.findUserById(user.getId()));
        userRepository.delete(user);
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}