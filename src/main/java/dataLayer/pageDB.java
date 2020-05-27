package dataLayer;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static dataLayer.Tables.tables.PageOwner.PAGE_OWNER;
import static dataLayer.Tables.tables.PagePost.PAGE_POST;
import static dataLayer.Tables.tables.Pages.PAGES;

public class pageDB implements DB_Inter{

    String username;
    String password;
    String myDriver;
    String myUrl;
    Connection connection = null;

    public pageDB(){
        this("root","Messi1Ronaldo2",
                "org.mariadb.jdbc.Driver","jdbc:mysql://132.72.65.33:3306/demodb");
    }

    public pageDB(String username,String password, String myDriver, String myUrl){
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
    public boolean containInDB(String userName, String arg2, String arg3) {
        try {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result<?> result = create.select().from(PAGE_OWNER).where(PAGE_OWNER.OWNERID.eq(userName)).fetch();
            return (!result.isEmpty());
        } catch (DataAccessException e) {
            System.out.println("error searching page of user");
            return false;
        }
    }

    @Override
    public Map<String, ArrayList<String>> selectFromDB(String userName, String arg2, String arg3) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        if(containInDB(username,null,null)) {
            try {
                Result<?> result = create.select().from(PAGE_OWNER).where(PAGE_OWNER.OWNERID.eq(userName)).fetch();

                String pageID = String.valueOf(result.get(0).get(PAGE_OWNER.PAGEID));

                Map<String, ArrayList<String>> objDetails = new HashMap<>();
                objDetails.put("pageID", new ArrayList<>());
                objDetails.get("pageID").add(pageID);
                objDetails.put("ownerID", new ArrayList<>());
                objDetails.get("ownerID").add(userName);

                result = create.select().from(PAGES).where(PAGES.PAGEID.eq(Integer.parseInt(pageID))).fetch();
                objDetails.put("name", new ArrayList<>());
                objDetails.get("name").add(result.get(0).get(PAGES.NAME));
                objDetails.put("birthDay", new ArrayList<>());
                objDetails.get("birthDay").add(result.get(0).get(PAGES.BIRTHDAY).toString());

                result = create.select().from(PAGE_POST).where(PAGE_POST.PAGEID.eq(Integer.parseInt(pageID))).fetch();
                objDetails.put("posts", new ArrayList<>());

                for(Record r:result){
                    objDetails.get("posts").add(r.get(PAGE_POST.POST));
                }

                return objDetails;
            } catch (DataAccessException e) {
                System.out.println("error searching page of user");
                return new HashMap<>();
            } catch (IllegalArgumentException e) {
                System.out.println("error searching page of user");
                return new HashMap<>();
            }
        }

        return null;
    }

    @Override
    public boolean removeFromDB(String objectName, String arg2, String arg3) {
        try {
            if(containInDB(objectName,null,null)){
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                create.delete(PAGE_OWNER)
                        .where(PAGE_OWNER.OWNERID.eq(objectName)).execute();
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            System.out.println("error removing page from DB");
            return false;
        }
    }

    @Override
    public boolean addToDB(String userName, String pageID, String birthDay, String name, Map<String, ArrayList<String>> objDetails) {
        if (!containInDB(userName, null, null)) {
            try {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                create.insertInto(PAGE_OWNER
                        ,PAGE_OWNER.PAGEID
                        ,PAGE_OWNER.OWNERID)
                        .values(Integer.parseInt(pageID)
                                ,userName)
                        .execute();

                create.insertInto(PAGES
                        ,PAGES.PAGEID
                        ,PAGES.BIRTHDAY
                        ,PAGES.NAME)
                        .values(Integer.parseInt(pageID)
                                ,convertToDate(birthDay)
                                ,name)
                        .execute();

                for(String str: objDetails.get("posts")){
                    create.insertInto(PAGE_POST
                            ,PAGE_POST.PAGEID
                            ,PAGE_POST.POST)
                            .values(Integer.parseInt(pageID)
                                    ,str)
                            .execute();
                }
                return true;
            } catch (DataAccessException e) {
                System.out.println("error adding page to DB");
                return false;
            } catch (NumberFormatException e) {
                System.out.println("error adding page to DB due to birthday bad format");
                return false;
            }
        }
        return false;
    }

    @Override
    public int countRecords() {
        return -1;
    }

    @Override
    public ArrayList<Map<String, ArrayList<String>>> selectAllRecords(Enum<?> e,Map<String,String> arguments) {
        return null;
    }

    @Override
    public boolean update(Enum<?> e, Map<String, String> arguments) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        if(containInDB(arguments.get("userName"),null,null)) {
            Map<String, ArrayList<String>> str = selectFromDB(arguments.get("userName"), null, null);
            int pageID = Integer.parseInt(str.get("pageID").get(0));
            if (e == PAGEUPDATES.SUMBIT) {
                try {
                    create.insertInto(PAGE_POST
                            , PAGE_POST.PAGEID
                            , PAGE_POST.POST)
                            .values(pageID
                                    , arguments.get("post"))
                            .execute();
                    return true;
                } catch (DataAccessException e1) {
                    System.out.println("error submiting post to page");
                }
            }
        }
        return false;
    }

    @Override
    public boolean TerminateDB() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("error closing connection of DB");
            return false;
        }

        return true;
    }

    private LocalDate convertToDate(String date){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

            //convert String to LocalDate
            date = date.replace("-","/");
            LocalDate localDate = LocalDate.parse(date, formatter);
            return localDate;
        } catch (Exception e) {
            System.out.println("error converting date in page DB");
            return null;
        }
    }
}
