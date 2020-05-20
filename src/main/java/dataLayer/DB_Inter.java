package dataLayer;

import java.util.ArrayList;
import java.util.Map;

public interface DB_Inter {

    boolean containInDB(String objectName);

    Object selectFromDB(String objectName);

    boolean removeFromDB(String objectName);

    boolean addToDb(String username, String password, String name, Map<String, ArrayList<String>> objDetails);

    //void queryMethod(String method);

}
