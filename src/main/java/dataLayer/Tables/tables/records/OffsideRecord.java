/*
 * This file is generated by jOOQ.
 */
package dataLayer.Tables.tables.records;


import dataLayer.Tables.tables.Offside;

import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OffsideRecord extends TableRecordImpl<OffsideRecord> implements Record4<Integer, Integer, String, String> {

    private static final long serialVersionUID = 1332978952;

    /**
     * Setter for <code>localsoccer.offside.MatchID</code>.
     */
    public void setMatchid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>localsoccer.offside.MatchID</code>.
     */
    public Integer getMatchid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>localsoccer.offside.EventID</code>.
     */
    public void setEventid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>localsoccer.offside.EventID</code>.
     */
    public Integer getEventid() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>localsoccer.offside.Time</code>.
     */
    public void setTime(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>localsoccer.offside.Time</code>.
     */
    public String getTime() {
        return (String) get(2);
    }

    /**
     * Setter for <code>localsoccer.offside.PlayerOffsideID</code>.
     */
    public void setPlayeroffsideid(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>localsoccer.offside.PlayerOffsideID</code>.
     */
    public String getPlayeroffsideid() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, Integer, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Offside.OFFSIDE.MATCHID;
    }

    @Override
    public Field<Integer> field2() {
        return Offside.OFFSIDE.EVENTID;
    }

    @Override
    public Field<String> field3() {
        return Offside.OFFSIDE.TIME;
    }

    @Override
    public Field<String> field4() {
        return Offside.OFFSIDE.PLAYEROFFSIDEID;
    }

    @Override
    public Integer component1() {
        return getMatchid();
    }

    @Override
    public Integer component2() {
        return getEventid();
    }

    @Override
    public String component3() {
        return getTime();
    }

    @Override
    public String component4() {
        return getPlayeroffsideid();
    }

    @Override
    public Integer value1() {
        return getMatchid();
    }

    @Override
    public Integer value2() {
        return getEventid();
    }

    @Override
    public String value3() {
        return getTime();
    }

    @Override
    public String value4() {
        return getPlayeroffsideid();
    }

    @Override
    public OffsideRecord value1(Integer value) {
        setMatchid(value);
        return this;
    }

    @Override
    public OffsideRecord value2(Integer value) {
        setEventid(value);
        return this;
    }

    @Override
    public OffsideRecord value3(String value) {
        setTime(value);
        return this;
    }

    @Override
    public OffsideRecord value4(String value) {
        setPlayeroffsideid(value);
        return this;
    }

    @Override
    public OffsideRecord values(Integer value1, Integer value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OffsideRecord
     */
    public OffsideRecord() {
        super(Offside.OFFSIDE);
    }

    /**
     * Create a detached, initialised OffsideRecord
     */
    public OffsideRecord(Integer matchid, Integer eventid, String time, String playeroffsideid) {
        super(Offside.OFFSIDE);

        set(0, matchid);
        set(1, eventid);
        set(2, time);
        set(3, playeroffsideid);
    }
}
