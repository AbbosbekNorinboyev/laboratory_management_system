package uz.brb.laboratorymanagementsystem.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.brb.laboratorymanagementsystem.dto.ShortDto;
import uz.brb.laboratorymanagementsystem.dto.response.Response;
import uz.brb.laboratorymanagementsystem.dto.response.UserResponse;
import uz.brb.laboratorymanagementsystem.entity.AuthUser;
import uz.brb.laboratorymanagementsystem.exception.ResourceNotFoundException;
import uz.brb.laboratorymanagementsystem.mapper.UserMapper;
import uz.brb.laboratorymanagementsystem.repository.AuthUserRepository;
import uz.brb.laboratorymanagementsystem.service.AuditService;
import uz.brb.laboratorymanagementsystem.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static uz.brb.laboratorymanagementsystem.utils.Utils.localDateTimeFormatter;
import static uz.brb.laboratorymanagementsystem.utils.Utils.normalizeText;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthUserRepository authUserRepository;
    private final UserMapper userMapper;
    private final EntityManager entityManager;
    private final AuditService auditService;

    @Override
    public Response<?> get(Long id) {
        AuthUser authUser = authUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + id));
        auditService.saveLog(
                "User",
                id,
                "users.found",
                null,
                null,
                Map.of(
                        "id", authUser.getId(),
                        "email", authUser.getEmail(),
                        "username", authUser.getUsername(),
                        "fullName", authUser.getFullName(),
                        "isActive", authUser.getIsActive()
                ),
                normalizeText("User successfully found")
        );
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser successfully found")
                .success(true)
                .data(userMapper.toResponse(authUser))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll(Pageable pageable) {
        List<AuthUser> users = authUserRepository.findAll(pageable).getContent();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser list successfully found")
                .success(true)
                .data(userMapper.responseList(users))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> update(UserResponse userResponse) {
        AuthUser authUser = authUserRepository.findById(userResponse.getId())
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + userResponse.getId()));
        userMapper.update(authUser, userResponse);
        authUserRepository.save(authUser);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser successfully updated")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> me(AuthUser user) {
        if (user == null) {
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("USER IS NULL")
                    .success(false)
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        auditService.saveLog(
                "User",
                user.getId(),
                "users.found",
                null,
                null,
                Map.of(
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "username", user.getUsername(),
                        "fullName", user.getFullName(),
                        "isActive", user.getIsActive()
                ),
                normalizeText("User successfully found")
        );
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser successfully found")
                .success(true)
                .data(userMapper.toResponse(user))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> roleStatistics() {
        List<Object[]> roleStatistics = authUserRepository.roleStatistics();
        List<ShortDto> shortDtoList = roleStatistics.stream()
                .map(row -> new ShortDto(
                        String.valueOf(row[1]),  // r.name
                        (Long) row[0])) // count(*)
                .toList();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Role statistics")
                .success(true)
                .data(shortDtoList)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> search(String fullName, String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuthUser> query = builder.createQuery(AuthUser.class);
        Root<AuthUser> root = query.from(AuthUser.class);

        List<Predicate> predicates = new ArrayList<>();

        if (fullName != null && !fullName.isEmpty()) {
            predicates.add(builder.like(root.get("fullName"), "%" + fullName + "%"));
        }
        if (username != null && !username.isEmpty()) {
            predicates.add(builder.like(root.get("username"), "%" + username + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));

        List<AuthUser> authUsers = entityManager.createQuery(query).getResultList();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser list successfully found")
                .success(true)
                .data(userMapper.responseList(authUsers))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
