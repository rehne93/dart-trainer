package de.baernreuther.dart.security;

import de.baernreuther.dart.security.database.User;
import de.baernreuther.dart.security.database.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserInitialization implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        User user = new User("board1", Objects.requireNonNull(passwordEncoder.encode("test")));

        this.userRepository.saveAndFlush(user);
    }
}
