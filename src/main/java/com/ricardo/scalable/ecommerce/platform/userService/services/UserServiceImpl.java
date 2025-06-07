package com.ricardo.scalable.ecommerce.platform.userService.services;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Role;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
import com.ricardo.scalable.ecommerce.platform.libs_common.events.UserBirthdayEvent;
import com.ricardo.scalable.ecommerce.platform.libs_common.events.UserRegisteredEvent;
import com.ricardo.scalable.ecommerce.platform.userService.exceptions.PasswordDoNotMatchException;
import com.ricardo.scalable.ecommerce.platform.userService.messaging.EventPublisher;
import com.ricardo.scalable.ecommerce.platform.userService.model.dto.UserRegisterDto;
import com.ricardo.scalable.ecommerce.platform.userService.model.dto.UserUpdateInfoDto;
import com.ricardo.scalable.ecommerce.platform.userService.model.dto.UserUpdatePasswordDto;
import com.ricardo.scalable.ecommerce.platform.userService.model.repositories.RoleRepository;
import com.ricardo.scalable.ecommerce.platform.userService.model.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EventPublisher<UserRegisteredEvent> userRegisteredEventPublisher;

    @Autowired
    private EventPublisher<UserBirthdayEvent> userBirthdayEventPublisher;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(UserRegisterDto user) {
        User userToCreate = new User();
        userToCreate.setAvatar(user.getAvatar());
        userToCreate.setFirstName(user.getFirstName());
        userToCreate.setLastName(user.getLastName());
        userToCreate.setUsername(user.getUsername());
        userToCreate.setEmail(user.getEmail());
        userToCreate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToCreate.setBirthDate(user.getBirthDate());
        userToCreate.setPhoneNumber(user.getPhoneNumber());
        userToCreate.setRoles(getRoles(user));
        userToCreate.setEnabled(true);
        
        User savedUser = userRepository.save(userToCreate);

        publishUserRegisteredEvent(savedUser);
        
        return savedUser;
    }

    private void publishUserRegisteredEvent(User user) {
        UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent();
        userRegisteredEvent.setUserId(user.getId());
        userRegisteredEvent.setEmail(user.getEmail());
        userRegisteredEvent.setName(user.getFirstName() + " " + user.getLastName());
        userRegisteredEvent.setRegisteredAt(LocalDateTime.now());

        userRegisteredEventPublisher.publish("user-registered-topic", userRegisteredEvent);
    }

    @Override
    public void notifyUserBirthdays() {
       List<User> birthdayUsers = userRepository.findByBirthDate(LocalDate.now());

        for (User user : birthdayUsers) {
            UserBirthdayEvent birthdayEvent = new UserBirthdayEvent();
            birthdayEvent.setUserId(user.getId());
            birthdayEvent.setName(user.getFirstName() + " " + user.getLastName());
            birthdayEvent.setEmail(user.getEmail());
            birthdayEvent.setBirthday(user.getBirthDate());

            userBirthdayEventPublisher.publish("user-birthday-topic", birthdayEvent);
        }
    }

    @Override
    @Transactional
    public Optional<User> update(UserUpdateInfoDto userUpdated, Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        return userOptional.map(dbUser -> {
            dbUser.setAvatar(userUpdated.getAvatar());
            dbUser.setFirstName(userUpdated.getFirstName());
            dbUser.setLastName(userUpdated.getLastName());
            dbUser.setEmail(userUpdated.getEmail());
            dbUser.setUsername(userUpdated.getUsername());
            dbUser.setBirthDate(userUpdated.getBirthDate());
            dbUser.setPhoneNumber(userUpdated.getPhoneNumber());
            if (userUpdated.isEnabled() == null) {
                dbUser.setEnabled(true);
            } else {
                dbUser.setEnabled(userUpdated.isEnabled());
            }

            return Optional.of(userRepository.save(dbUser));
        }).orElseGet(Optional::empty);
    }

    @Override
    @Transactional
    public Optional<User> updatePassword(UserUpdatePasswordDto userUpdated, Long id)
            throws PasswordDoNotMatchException {
                
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            String oldPassword = userUpdated.getOldPassword();
            String newPassword = userUpdated.getNewPassword();
            User dbUser = userOptional.orElseThrow();
            String oldHashedPassword = dbUser.getPassword();

            if (passwordEncoder.matches(oldPassword, oldHashedPassword)) {
                dbUser.setPassword(passwordEncoder.encode(newPassword));
            } else {
                throw new PasswordDoNotMatchException("La actual contraseña es incorrecta");
            }
            return Optional.of(userRepository.save(dbUser));
        };

        return Optional.empty();
    }


    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> addUserRoles(User user, Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        return userOptional.map(dbUser -> {
            user.getRoles()
                .forEach(role -> {
                    if (role.getName().equals("ROLE_ADMIN")) {
                        dbUser.setAdmin(true);
                    }
                    dbUser.getRoles().add(role);
                });

            return Optional.of(userRepository.save(dbUser));
        }).orElseGet(Optional::empty);
    }

    @Override
    public Optional<User> blockUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        return userOptional.map(dbUser -> {
            dbUser.setEnabled(false);
            return Optional.of(userRepository.save(dbUser));
        }).orElseGet(Optional::empty);
    }

    @Override
    public Optional<User> unlockUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        return userOptional.map(dbUser -> {
            dbUser.setEnabled(true);
            return Optional.of(userRepository.save(dbUser));
        }).orElseGet(Optional::empty);
    }

    private List<Role> getRoles(UserRegisterDto user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");

        optionalRole.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> optionalAdminRole = roleRepository.findByName("ROLE_ADMIN");
            optionalAdminRole.ifPresent(roles::add);
        }

        return roles;
    }

}
