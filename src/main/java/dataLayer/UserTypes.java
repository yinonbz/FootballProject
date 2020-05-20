package dataLayer;

public enum UserTypes {
    ADMIN,
    AR,
    COACH,
    PLAYER,
    REFEREE,
    TEAMMANAGER,
    TEAMOWNER,
    FAN;

    @Override
    public String toString() {
        return name();
    }
}
