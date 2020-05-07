package dataLayer;

public interface DB_Inter {

    boolean containInDB(String objectName, String table);

    Object selectFromDB(String objectName,String table);

    boolean removeFromDB(String objectName, String table);

    boolean addToDb(String name,Object obj,String table);

    //void queryMethod(String method);

}
