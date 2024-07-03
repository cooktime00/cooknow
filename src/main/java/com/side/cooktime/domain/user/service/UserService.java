package com.side.cooktime.domain.user.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.side.cooktime.domain.user.model.User;
import com.side.cooktime.domain.user.repository.UserRepository;
import com.side.cooktime.global.FirebaseService;
import com.side.cooktime.global.config.auth.AuthenticationFacade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FirebaseService firebaseService;
    private final AuthenticationFacade authenticationFacade;

    public void save() throws FirebaseAuthException {
        User user = firebaseService.getUserRecord();
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }

    public User find() throws FirebaseAuthException {
        User user = firebaseService.getUserRecord();
        return userRepository.findByEmail(user.getEmailValue()).orElseGet(() -> userRepository.save(user));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public void delete() {
        Long id = authenticationFacade.getAuthenticatedUserId();
        userRepository.deleteById(id);
    }

}
