package com.czareg.simplerestservice.service.user;

import lombok.NonNull;
import lombok.Value;

@Value
public class User {
    @NonNull
    String id;
    @NonNull
    String login;
    String name;
    String type;
    String avatarUrl;
    String createdAt;
    int calculations;
}