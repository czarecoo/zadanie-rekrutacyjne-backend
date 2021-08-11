package com.czareg.simplerestservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "githubClient", url = "https://api.github.com/users")
public interface GithubClient {
    @GetMapping("/{login}")
    GithubUser getGithubUser(@PathVariable("login") String login);
}