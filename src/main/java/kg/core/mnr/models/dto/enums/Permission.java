package kg.core.mnr.models.dto.enums;

public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    ADMIN_ACCESS("admin:access"),
    CUSTOMS_ACCESS("customs:access"),
    BORDER_ACCESS("border:access"),
    POLICE_ACCESS("prosecutor:access"),
    AGENCY_ACCESS("agency:access");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

