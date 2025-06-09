package kg.sh.mnr.model.enums;


import java.util.Set;

public enum Role {
    ADMIN(Set.of(Permission.USER_READ, Permission.USER_WRITE, Permission.ADMIN_ACCESS)),
    CUSTOMS(Set.of(Permission.USER_READ, Permission.CUSTOMS_ACCESS)),
    BORDER(Set.of(Permission.USER_READ, Permission.BORDER_ACCESS)),
    POLICE(Set.of(Permission.USER_READ, Permission.POLICE_ACCESS)),
    AGENCY(Set.of(Permission.USER_READ, Permission.AGENCY_ACCESS));


    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}