package com.example.ologyprofbackenddemo.common.jwt;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author l
 */
@Data
@Accessors(chain = true)
public class JwtUser {

    private boolean valid;
    private String userId;

    public JwtUser() {
        this.valid = false;
    }

}
