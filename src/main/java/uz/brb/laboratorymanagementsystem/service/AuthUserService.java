package uz.brb.laboratorymanagementsystem.service;

import uz.brb.laboratorymanagementsystem.dto.request.LoginRequest;
import uz.brb.laboratorymanagementsystem.dto.request.RegisterRequest;
import uz.brb.laboratorymanagementsystem.dto.request.UpdatePasswordRequest;
import uz.brb.laboratorymanagementsystem.dto.response.Response;

public interface AuthUserService {
    Response<?> register(RegisterRequest registerRequest);

    Response<?> login(LoginRequest loginRequest);

    Response<?> changePassword(UpdatePasswordRequest request);
}