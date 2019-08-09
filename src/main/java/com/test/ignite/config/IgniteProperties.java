package com.test.ignite.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@Component
public class IgniteProperties {

    @Value("${ignite.enable}")
    private Boolean enable = false;

    private IgniteType type = IgniteType.JDBC;

    private String instanceName = UUID.randomUUID().toString();

}
