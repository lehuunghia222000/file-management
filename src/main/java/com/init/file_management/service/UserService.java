package com.init.file_management.service;

import com.init.file_management.dto.request.UserCreationRequest;
import com.init.file_management.dto.request.UserUpdateRequest;
import com.init.file_management.dto.response.UserResponse;
import com.init.file_management.entity.Users;
import com.init.file_management.enums.Roles;
import com.init.file_management.exception.ApplicationException;
import com.init.file_management.exception.ErrorCode;
import com.init.file_management.mapper.UsersMapper;
import com.init.file_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Users user = new Users();
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Roles.STAFF);
        userRepository.save(user);
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .role(user.getRole())
                .build();
    }

    private UserResponse mapToUserResponse(Users user) {
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .role(user.getRole())
                .build();
    }

    public List<UserResponse> getUsers() {

        List<Users> users = userRepository.findAll();

        return users.stream().map(this::mapToUserResponse).toList();
    }

    public UserResponse getUserById(String userId) {
        return userRepository.findById(userId)
                .map(this::mapToUserResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponse updateUserById(String userId, UserUpdateRequest request) {
        Users user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .role(user.getRole())
                .build();
    }

    public Users findByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(()
                -> new ApplicationException(ErrorCode.USER_NOT_EXISTED));
    }
}
