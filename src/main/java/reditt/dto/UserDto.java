package reditt.dto;

public class UserDto {

    private Long id;

    private String email;

    private String username;

    private boolean isActive;

    public UserDto(String email, String username, boolean isActive) {
        this.email = email;
        this.username = username;
        this.isActive = isActive;
    }

    public UserDto(Long id, String email, String username, boolean isActive) {
        this(email, username, isActive);
        this.id = id;
    }


}
