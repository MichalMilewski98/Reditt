package reditt.service;

import reditt.dto.UserDto;
import reditt.model.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    User getUser(Long id);

    void addUser(User user);



}
