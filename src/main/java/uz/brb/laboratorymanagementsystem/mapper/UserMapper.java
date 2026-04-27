package uz.brb.laboratorymanagementsystem.mapper;

import org.springframework.stereotype.Component;
import uz.brb.laboratorymanagementsystem.dto.response.UserResponse;
import uz.brb.laboratorymanagementsystem.entity.AuthUser;
import uz.brb.laboratorymanagementsystem.entity.RoleEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public UserResponse toResponse(AuthUser entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .phoneNumber(entity.getPhoneNumber())
                .password(entity.getPassword())
                .username(entity.getUsername())
                .isActive(entity.getIsActive())
                .lastLoginAt(entity.getLastLoginAt())
                .roles(entity.getRoles()
                        .stream()
                        .map(RoleEntity::getName)
                        .toList())
                .build();
    }

    public List<UserResponse> responseList(List<AuthUser> users) {
        if (users != null && !users.isEmpty()) {
            return users.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(AuthUser entity, UserResponse response) {
        if (response == null) {
            return;
        }
        if (response.getFullName() != null && !response.getFullName().trim().isEmpty()) {
            entity.setFullName(response.getFullName());
        }
        if (response.getPassword() != null && !response.getPassword().trim().isEmpty()) {
            entity.setPassword(response.getPassword());
        }
        if (response.getUsername() != null && !response.getUsername().trim().isEmpty()) {
            entity.setUsername(response.getUsername());
        }
    }
}
