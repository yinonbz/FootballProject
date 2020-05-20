package dataLayer;

import org.junit.*;

import java.util.Date;

import static org.junit.Assert.*;

public class DBMatchTest {

    static DBMatch DB;

    @Before
    public void startTest(){
        DB = new DBMatch();
        Date date = new Date();
        DB.addMatchToDB("Champions",2019,"200006","Manchester United","Manchester City", "Old Trafford","0:0",date);
    }


    @Test
    public void addRefToMatch(){
        assertTrue(DB.addRefereeToMatch("200006","Love"));
    }

    @Test
    public void upDateMainRef(){
        assertTrue(DB.updateMainRefereeToMatch("200006","Love"));
    }

    @Test
    public void updateScore(){
        String score = "3:0";
        assertTrue(DB.updateScore("200006",score));
    }

    @Test
    public void updateNumOfFans(){
        assertTrue(DB.updateNumOfFans("200006",20000));
    }

}