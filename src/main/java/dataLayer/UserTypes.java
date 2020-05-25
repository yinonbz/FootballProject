package dataLayer;

public enum UserTypes {
    SUBSCRIBER,
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
