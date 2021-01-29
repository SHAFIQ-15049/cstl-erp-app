package software.cstl.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String WEEKEND_ADMIN = "ROLE_WEEKEND_ADMIN";

    public static final String WEEKEND_MANAGER = "ROLE_WEEKEND_MANAGER";

    private AuthoritiesConstants() {
    }
}
