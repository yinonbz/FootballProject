package dataLayer;

import java.util.ArrayList;
import java.util.Map;

public interface DB_Inter {

    boolean containInDB(String objectName);

    Map<String,ArrayList<String>> selectFromDB(String objectName);

    boolean removeFromDB(String objectName);

    boolean addToDb(String username,String password,String name,String type, Map<String,ArrayList<String>> objDetails);

    public boolean TerminateDB(String objectName);

}
