package kg.sh.mnr.model.enums;

public enum DocStatus {
    USED("Использован"),
    UNUSED("Не использован");

    private final String description;

    DocStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
