package dataLayer;

import java.util.ArrayList;
import java.util.Map;

public interface DB_Inter {



    boolean containInDB(String objectName);

    Map<String,ArrayList<String>> selectFromDB(String objectName);

    boolean removeFromDB(String objectName);

    boolean addToDB(String str1,String str2,String str3,String str4, Map<String,ArrayList<String>> objDetails);

    int countRecords();

    ArrayList<Map<String,ArrayList<String>>> selectAllRecords(Enum<?> e);

    boolean TerminateDB();



}
