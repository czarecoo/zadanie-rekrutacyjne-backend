package com.czareg.simplerestservice.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @Column(name = "LOGIN", nullable = false)
    private String login;

    @Column(name = "REQUEST_COUNT", nullable = false)
    private long requestCount;
}