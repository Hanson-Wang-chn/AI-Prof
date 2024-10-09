package jwt;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author l
 */
@Data
@Accessors(chain = true)
public class JWTUser {

    private boolean valid;
    private String userId;
    private String role;

    public JWTUser() {
        this.valid = false;
    }


}
