package com.side.cooknow.domain.user.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.side.cooknow.domain.user.exception.UserErrorCode;
import com.side.cooknow.domain.user.exception.UserException;
import com.side.cooknow.domain.user.model.Email;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.domain.user.repository.UserRepository;
import com.side.cooknow.global.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FirebaseService firebaseService;

    public User save(final Email email) throws FirebaseAuthException {
        User user = firebaseService.getUserRecord(email);
        userRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(Email email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findByEmail(Email email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    public void delete(final User user) {
        userRepository.deleteById(user.getId());
    }

}
