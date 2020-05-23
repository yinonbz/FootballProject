/*
 * This file is generated by jOOQ.
 */
package dataLayer.Tables.tables.records;


import dataLayer.Tables.enums.PlayersFieldjob;
import dataLayer.Tables.tables.Players;

import java.time.LocalDate;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PlayersRecord extends UpdatableRecordImpl<PlayersRecord> implements Record6<String, String, LocalDate, PlayersFieldjob, Integer, String> {

    private static final long serialVersionUID = -2081050353;

    /**
     * Setter for <code>demodb.players.playerID</code>.
     */
    public void setPlayerid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>demodb.players.playerID</code>.
     */
    public String getPlayerid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>demodb.players.teamID</code>.
     */
    public void setTeamid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>demodb.players.teamID</code>.
     */
    public String getTeamid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>demodb.players.birthDate</code>.
     */
    public void setBirthdate(LocalDate value) {
        set(2, value);
    }

    /**
     * Getter for <code>demodb.players.birthDate</code>.
     */
    public LocalDate getBirthdate() {
        return (LocalDate) get(2);
    }

    /**
     * Setter for <code>demodb.players.fieldJob</code>.
     */
    public void setFieldjob(PlayersFieldjob value) {
        set(3, value);
    }

    /**
     * Getter for <code>demodb.players.fieldJob</code>.
     */
    public PlayersFieldjob getFieldjob() {
        return (PlayersFieldjob) get(3);
    }

    /**
     * Setter for <code>demodb.players.salary</code>.
     */
    public void setSalary(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>demodb.players.salary</code>.
     */
    public Integer getSalary() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>demodb.players.teamOwnerID_fictive</code>.
     */
    public void setTeamowneridFictive(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>demodb.players.teamOwnerID_fictive</code>.
     */
    public String getTeamowneridFictive() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<String, String, LocalDate, PlayersFieldjob, Integer, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<String, String, LocalDate, PlayersFieldjob, Integer, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Players.PLAYERS.PLAYERID;
    }

    @Override
    public Field<String> field2() {
        return Players.PLAYERS.TEAMID;
    }

    @Override
    public Field<LocalDate> field3() {
        return Players.PLAYERS.BIRTHDATE;
    }

    @Override
    public Field<PlayersFieldjob> field4() {
        return Players.PLAYERS.FIELDJOB;
    }

    @Override
    public Field<Integer> field5() {
        return Players.PLAYERS.SALARY;
    }

    @Override
    public Field<String> field6() {
        return Players.PLAYERS.TEAMOWNERID_FICTIVE;
    }

    @Override
    public String component1() {
        return getPlayerid();
    }

    @Override
    public String component2() {
        return getTeamid();
    }

    @Override
    public LocalDate component3() {
        return getBirthdate();
    }

    @Override
    public PlayersFieldjob component4() {
        return getFieldjob();
    }

    @Override
    public Integer component5() {
        return getSalary();
    }

    @Override
    public String component6() {
        return getTeamowneridFictive();
    }

    @Override
    public String value1() {
        return getPlayerid();
    }

    @Override
    public String value2() {
        return getTeamid();
    }

    @Override
    public LocalDate value3() {
        return getBirthdate();
    }

    @Override
    public PlayersFieldjob value4() {
        return getFieldjob();
    }

    @Override
    public Integer value5() {
        return getSalary();
    }

    @Override
    public String value6() {
        return getTeamowneridFictive();
    }

    @Override
    public PlayersRecord value1(String value) {
        setPlayerid(value);
        return this;
    }

    @Override
    public PlayersRecord value2(String value) {
        setTeamid(value);
        return this;
    }

    @Override
    public PlayersRecord value3(LocalDate value) {
        setBirthdate(value);
        return this;
    }

    @Override
    public PlayersRecord value4(PlayersFieldjob value) {
        setFieldjob(value);
        return this;
    }

    @Override
    public PlayersRecord value5(Integer value) {
        setSalary(value);
        return this;
    }

    @Override
    public PlayersRecord value6(String value) {
        setTeamowneridFictive(value);
        return this;
    }

    @Override
    public PlayersRecord values(String value1, String value2, LocalDate value3, PlayersFieldjob value4, Integer value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PlayersRecord
     */
    public PlayersRecord() {
        super(Players.PLAYERS);
    }

    /**
     * Create a detached, initialised PlayersRecord
     */
    public PlayersRecord(String playerid, String teamid, LocalDate birthdate, PlayersFieldjob fieldjob, Integer salary, String teamowneridFictive) {
        super(Players.PLAYERS);

        set(0, playerid);
        set(1, teamid);
        set(2, birthdate);
        set(3, fieldjob);
        set(4, salary);
        set(5, teamowneridFictive);
    }
}
