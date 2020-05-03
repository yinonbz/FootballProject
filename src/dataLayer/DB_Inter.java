package dataLayer;

public interface DB_Inter {

    boolean containInDB(String objectName);

    Object selectFromDB(String objectName);

    boolean removeFromDB(String objectName);

    boolean addToDb(String name,Object obj);

}
