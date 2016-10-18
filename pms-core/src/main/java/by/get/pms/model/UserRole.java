package by.get.pms.model;

/**
 * Created by Milos.Savic on 10/13/2016.
 */
public enum UserRole {
    ADMIN, PROJECT_MANAGER, DEV;

    public static UserRole make(String roleName) {
        for (UserRole userRole : values()) {
            if (userRole.name().equalsIgnoreCase(roleName)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException(String.format("Role name: %s isn't in predefined set of roles!", roleName));
    }
}
