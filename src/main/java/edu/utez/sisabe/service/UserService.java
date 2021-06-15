package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.User;
import edu.utez.sisabe.repository.UserRepository;
import edu.utez.sisabe.util.EmailService;
import edu.utez.sisabe.util.PasswordGenerator;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final LogbookService logbookService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailService emailService;

    public UserService(UserRepository userRepository, LogbookService logbookService,
                       BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.logbookService = logbookService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }

    public List<User> findAll(){return userRepository.findAll();}

    public User findUserById(String id) {
        return userRepository.findUserById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    public User save(User user) {
        logbookService.save(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void update(User user) { userRepository.save(user); }

    public void updatePassword(User user) throws MessagingException {
        User userAux = userRepository.findUserByUsername(user.getUsername());
        String passGenerated = PasswordGenerator.getPassword();
        user = new User(userAux.getId(), userAux.getUsername(), bCryptPasswordEncoder.encode(passGenerated),
                userAux.getRole(), userAux.getEnabled());
        logbookService.update(userAux, user);
        userRepository.save(user);
        emailService.sendEmail(user.getUsername(), passGenerated);
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