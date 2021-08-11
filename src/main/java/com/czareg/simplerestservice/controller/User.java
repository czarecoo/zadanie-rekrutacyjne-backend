package com.czareg.simplerestservice.controller;

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