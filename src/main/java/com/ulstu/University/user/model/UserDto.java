package com.ulstu.University.user.model;

public class UserDto {
    private final long id;
    private final String login;
    private final UserRole userRole;

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.userRole = user.getRole();
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
