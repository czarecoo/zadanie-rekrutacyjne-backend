package com.czareg.simplerestservice.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUser {
    @NonNull
    String id;
    @NonNull
    String login;
    String name;
    @JsonProperty("avatar_url")
    String avatarUrl;
    @JsonProperty("created_at")
    String createdAt;
}