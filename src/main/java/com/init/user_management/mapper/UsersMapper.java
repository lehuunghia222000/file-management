package com.init.user_management.mapper;

import com.init.user_management.dto.request.UserCreationRequest;
import com.init.user_management.dto.request.UserUpdateRequest;
import com.init.user_management.dto.response.UserResponse;
import com.init.user_management.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    Users toUsers(UserCreationRequest request);
    void updateUser(@MappingTarget Users user, UserUpdateRequest request);
    @Mapping(source = "role", target = "roles")
    UserResponse toUserResponse(Users users);
}
