package reditt.query.service;

import reditt.dto.UserDto;

import java.util.List;

public interface UserQueryService {

    List<UserDto> getAllUsers();
}
