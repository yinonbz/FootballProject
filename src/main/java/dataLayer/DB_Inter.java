package dataLayer;

import java.util.ArrayList;
import java.util.Map;

public interface DB_Inter {



    boolean containInDB(String objectName,String arg2,String arg3);

    Map<String,ArrayList<String>> selectFromDB(String objectName,String arg2,String arg3);

    boolean removeFromDB(String objectName,String arg2,String arg3);

    boolean addToDB(String str1,String str2,String str3,String str4, Map<String,ArrayList<String>> objDetails);

    int countRecords();

    ArrayList<Map<String,ArrayList<String>>> selectAllRecords(Enum<?> e, Map<String,String> arguments);

    boolean update (Enum<?> e,Map<String,String> arguments);

    boolean TerminateDB();



}
