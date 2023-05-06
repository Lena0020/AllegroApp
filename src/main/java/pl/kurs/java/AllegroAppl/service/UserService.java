package pl.kurs.java.AllegroAppl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kurs.java.AllegroAppl.model.dto.*;
import pl.kurs.java.AllegroAppl.model.entity.User;
import pl.kurs.java.AllegroAppl.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    public UserDTO findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserDTO::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
//        User user = userRepository.findByUsername(username).orElse(null);
//        return user != null ? new UserDTO(user) : null;
    }

    User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public void updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + userDTO.getId()));
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    public void createUser(UserDTO userDTO) {
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        userRepository.save(new User(userDTO.getName(), userDTO.getUsername(), hashedPassword));

        // restTemplate wysyla do banku i tokenService request o utworzenie usera

        // Create the user in the TokenService
        UserCreateRequestTokenService userCreateRequestTokenService = new UserCreateRequestTokenService( userDTO.getUsername());
        restTemplate.postForObject("localhost:8082/users", userCreateRequestTokenService, Void.class);

        // Create the user in the Bank
        UserCreateRequestBank userCreateRequestBank = new UserCreateRequestBank(userDTO.getName(), userDTO.getUsername(), 1000);
        restTemplate.postForObject("localhost:8081/users", userCreateRequestBank, Void.class);
    }

}
