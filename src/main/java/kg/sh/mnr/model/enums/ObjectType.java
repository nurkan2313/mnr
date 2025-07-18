package kg.sh.mnr.model.enums;

public enum ObjectType {
    EXPORT("Экспорт"),
    IMPORT("Импорт"),
    IMPORT_EXPORT("Импорт-Экспорт");

    private final String displayName;

    ObjectType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

