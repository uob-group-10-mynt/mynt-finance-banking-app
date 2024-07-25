package com.mynt.banking.auth;

import com.mynt.banking.config.ApplicationConfig;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.TimeToLive;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("RefreshToken")
public class RefreshToken {

    @Id
    private String key;

    private String token;

    @TimeToLive
    private Long expiration;

    private ApplicationConfig appConfig;

    @PostConstruct
    public void init() {
        if (expiration == null) {
            expiration = appConfig.getRefreshExpiration();
        }
    }
}
