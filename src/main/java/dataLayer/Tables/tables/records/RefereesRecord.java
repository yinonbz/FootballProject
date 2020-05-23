/*
 * This file is generated by jOOQ.
 */
package dataLayer.Tables.tables.records;


import dataLayer.Tables.enums.RefereesRoleref;
import dataLayer.Tables.tables.Referees;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RefereesRecord extends UpdatableRecordImpl<RefereesRecord> implements Record2<String, RefereesRoleref> {

    private static final long serialVersionUID = 47856497;

    /**
     * Setter for <code>localsoccer.referees.refereeID</code>.
     */
    public void setRefereeid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>localsoccer.referees.refereeID</code>.
     */
    public String getRefereeid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>localsoccer.referees.roleRef</code>.
     */
    public void setRoleref(RefereesRoleref value) {
        set(1, value);
    }

    /**
     * Getter for <code>localsoccer.referees.roleRef</code>.
     */
    public RefereesRoleref getRoleref() {
        return (RefereesRoleref) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, RefereesRoleref> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, RefereesRoleref> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Referees.REFEREES.REFEREEID;
    }

    @Override
    public Field<RefereesRoleref> field2() {
        return Referees.REFEREES.ROLEREF;
    }

    @Override
    public String component1() {
        return getRefereeid();
    }

    @Override
    public RefereesRoleref component2() {
        return getRoleref();
    }

    @Override
    public String value1() {
        return getRefereeid();
    }

    @Override
    public RefereesRoleref value2() {
        return getRoleref();
    }

    @Override
    public RefereesRecord value1(String value) {
        setRefereeid(value);
        return this;
    }

    @Override
    public RefereesRecord value2(RefereesRoleref value) {
        setRoleref(value);
        return this;
    }

    @Override
    public RefereesRecord values(String value1, RefereesRoleref value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RefereesRecord
     */
    public RefereesRecord() {
        super(Referees.REFEREES);
    }

    /**
     * Create a detached, initialised RefereesRecord
     */
    public RefereesRecord(String refereeid, RefereesRoleref roleref) {
        super(Referees.REFEREES);

        set(0, refereeid);
        set(1, roleref);
    }
}
