package com.mynt.banking.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("RefreshToken")
public class RefreshToken {

    @Id
    private String key;
    private String token;

}
