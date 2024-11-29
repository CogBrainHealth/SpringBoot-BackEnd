package server.brainboost.src.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.brainboost.src.user.dto.SignUpDTO;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(SignUpDTO signUpDTO) {

        String username = signUpDTO.getUsername();
        String password = signUpDTO.getPassword();

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        UserEntity newUser = new UserEntity(username, encodedPassword);

        userRepository.save(newUser);
    }
}
