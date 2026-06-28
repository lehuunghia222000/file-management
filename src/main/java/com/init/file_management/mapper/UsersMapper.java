package com.init.file_management.mapper;

import com.init.file_management.dto.request.UserCreationRequest;
import com.init.file_management.dto.request.UserUpdateRequest;
import com.init.file_management.dto.response.UserResponse;
import com.init.file_management.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    Users toUsers(UserCreationRequest request);
    void updateUser(@MappingTarget Users user, UserUpdateRequest request);
    UserResponse toUserResponse(Users users);
}
