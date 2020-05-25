package dataLayer;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static dataLayer.Tables.tables.Complaints.COMPLAINTS;

public class ComplaintsDB implements DB_Inter{

    String username;
    String password;
    String myDriver;
    String myUrl;
    Connection connection = null;

    public ComplaintsDB(){
        this("root","Messi1Ronaldo2",
                "org.mariadb.jdbc.Driver","jdbc:mysql://localhost:3306/demodb");
    }

    public ComplaintsDB(String username,String password, String myDriver, String myUrl){
        this.username = username;
        this.password = password;
        this.myDriver = myDriver;
        this.myUrl = myUrl;

        //connect to DB and save to field in class.
        try {
            Class.forName(myDriver);
            connection = DriverManager.getConnection(myUrl,username,password);
            System.out.println("Successful connection to server db ");
        } catch (SQLException e) {
            System.out.println("error connecting to server. connection is now null");
        } catch (ClassNotFoundException e) {
            System.out.println("error connecting to driver");
        }
    }

    @Override
    public boolean containInDB(String objectName,String arg1,String arg2) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().
                from(COMPLAINTS)
                .where(COMPLAINTS.COMPLAINTID.eq(Integer.parseInt(objectName))).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, ArrayList<String>> selectFromDB(String objectName,String arg1,String arg2) {
        if(containInDB(objectName,null,null)) {

            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result<?> result = create.select().
                    from(COMPLAINTS)
                    .where(COMPLAINTS.COMPLAINTID.eq(Integer.parseInt(objectName))).fetch();

            Map<String,ArrayList<String>> complaintsDetails = new HashMap<>();
            ArrayList<String> HandlerID = new ArrayList<>();
            HandlerID.add(result.get(0).get(COMPLAINTS.HANDLERID));
            complaintsDetails.put("HandlerID",HandlerID);
            ArrayList<String> complaintID = new ArrayList<>();
            complaintID.add(String.valueOf(result.get(0).get(COMPLAINTS.COMPLAINTID)));
            complaintsDetails.put("complaintID",complaintID);
            ArrayList<String> WriterID = new ArrayList<>();
            WriterID.add(result.get(0).get(COMPLAINTS.WRITERID));
            complaintsDetails.put("WriterID",WriterID);
            ArrayList<String> comment= new ArrayList<>();
            comment.add(result.get(0).get(COMPLAINTS.COMMENT));
            complaintsDetails.put("comment",comment);
            ArrayList<String> content= new ArrayList<>();
            content.add(result.get(0).get(COMPLAINTS.CONTENT));
            complaintsDetails.put("content",content);
            ArrayList<String> isAnswered= new ArrayList<>();
            isAnswered.add(String.valueOf(result.get(0).get(COMPLAINTS.ISANSWERED)));
            complaintsDetails.put("isAnswered",isAnswered);
            return complaintsDetails;
        }
        return null;
    }

    @Override
    public boolean removeFromDB(String objectName,String arg1,String arg2) {
        if(containInDB(objectName,null,null)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.delete(COMPLAINTS)
                    .where(COMPLAINTS.COMPLAINTID.eq(Integer.parseInt(objectName)))
                    .execute();
            return true;
        }
        return false;
    }

    @Override
    public boolean addToDB(String complaintID, String handlerID, String writerID, String isAnswered, Map<String, ArrayList<String>> objDetails) {
        if(!containInDB(complaintID,null,null)) {
            //get subscriber from db
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(COMPLAINTS
                    , COMPLAINTS.COMPLAINTID
                    , COMPLAINTS.HANDLERID
                    , COMPLAINTS.WRITERID
                    , COMPLAINTS.COMMENT
                    ,COMPLAINTS.CONTENT
                    ,COMPLAINTS.ISANSWERED)
                    .values(Integer.valueOf(complaintID)
                            ,handlerID
                            ,writerID
                            ,objDetails.get("comment").get(0)
                            ,objDetails.get("content").get(0)
                            ,Boolean.valueOf(isAnswered))
                    .execute();
            return true;
        }
        return false;
    }

    @Override
    public int countRecords() {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        return create.selectCount().from(COMPLAINTS).fetchOne(0,int.class);
    }

    @Override
    public ArrayList<Map<String, ArrayList<String>>> selectAllRecords(Enum<?> e,Map<String,String> arguments) {
        ArrayList<Map<String, ArrayList<String>>> allComplaints = new ArrayList<>();
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select(COMPLAINTS.COMPLAINTID).
                from(COMPLAINTS)
                .fetch();

        for(Record r: result){
            allComplaints.add(selectFromDB(String.valueOf(r.get(COMPLAINTS.COMPLAINTID)),null,null));
        }
        return allComplaints;
    }

    @Override
    public boolean update(Enum<?> e, Map<String, String> arguments) {
        return false;
    }

    @Override
    public boolean TerminateDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("error closing connection of DB");
            return false;
        }

        return true;
    }
}
