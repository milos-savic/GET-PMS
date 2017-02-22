package by.get.pms.model;

/**
 * Created by Milos.Savic on 10/13/2016.
 */

// Define system roles and authorities belonging to each role. Two ways these can be used:
//
//         - @PreAuthorize("hasRole('permission_name')") annotation in front of controller methods for granular permission checking
//         - url-based security (see SecurityConfig.java)
//
//         Note:  when checking roles via hasRole, Spring ignores authorities not beginning with ROLE_.
//                Consequently, hasRole('administrator') won't work, but hasRole('ROLE_BACKEND_ADMIN')
//                will be honored just fine.

public enum UserRole {
    ROLE_ADMIN, ROLE_PROJECT_MANAGER, ROLE_DEV, ROLE_GUEST;

    public static UserRole make(String roleName) {
        for (UserRole userRole : values()) {
            if (userRole.name().equalsIgnoreCase(roleName)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException(String.format("Role name: %s isn't in predefined set of roles!", roleName));
    }
}
