package com.example.feedserver.userinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfo {
    private int userId;
    private String username;
    private String email;

    public UserInfo(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
