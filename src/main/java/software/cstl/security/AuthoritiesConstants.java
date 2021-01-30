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

    public static final String HOLIDAY_ADMIN = "ROLE_HOLIDAY_ADMIN";

    public static final String HOLIDAY_MANAGER = "ROLE_HOLIDAY_MANAGER";

    public static final String ATTENDANCE_ADMIN = "ROLE_ATTENDANCE_ADMIN";

    public static final String ATTENDANCE_MANAGER = "ROLE_ATTENDANCE_MANAGER";

    private AuthoritiesConstants() {
    }
}
