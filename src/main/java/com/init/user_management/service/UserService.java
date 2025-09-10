package com.init.user_management.service;

import com.init.user_management.dto.request.UserCreationRequest;
import com.init.user_management.dto.request.UserUpdateRequest;
import com.init.user_management.dto.response.UserResponse;
import com.init.user_management.entity.Users;
import com.init.user_management.enums.Roles;
import com.init.user_management.exception.ApplicationException;
import com.init.user_management.exception.ErrorCode;
import com.init.user_management.mapper.UsersMapper;
import com.init.user_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsersMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new ApplicationException(ErrorCode.USER_EXISTED);
        }
        Users user = userMapper.toUsers(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Roles.USER.name());
        user.setRole(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers() {

        List<Users> users = userRepository.findAll();

        return users.stream().map(user -> userMapper.toUserResponse(user)).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found")));
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUserById(String userId, UserUpdateRequest request) {
        Users user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user));
    }
}
