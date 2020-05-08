package dataLayer;

public interface DB_Inter {

    boolean containInDB(String objectName, String table,String where,String groupby);

    Object selectFromDB(String objectName,String table,String where,String groupby);

    boolean removeFromDB(String objectName, String table,String where,String groupby);

    boolean addToDb(String name,Object obj,String table,String where,String groupby);

    //void queryMethod(String method);

}
