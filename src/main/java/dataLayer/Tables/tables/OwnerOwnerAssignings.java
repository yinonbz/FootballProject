/*
 * This file is generated by jOOQ.
 */
package dataLayer.Tables.tables;


import dataLayer.Tables.Demodb;
import dataLayer.Tables.Indexes;
import dataLayer.Tables.Keys;
import dataLayer.Tables.tables.records.OwnerOwnerAssigningsRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OwnerOwnerAssignings extends TableImpl<OwnerOwnerAssigningsRecord> {

    private static final long serialVersionUID = -919860220;

    /**
     * The reference instance of <code>demodb.owner_owner_assignings</code>
     */
    public static final OwnerOwnerAssignings OWNER_OWNER_ASSIGNINGS = new OwnerOwnerAssignings();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OwnerOwnerAssigningsRecord> getRecordType() {
        return OwnerOwnerAssigningsRecord.class;
    }

    /**
     * The column <code>demodb.owner_owner_assignings.ownerID</code>.
     */
    public final TableField<OwnerOwnerAssigningsRecord, String> OWNERID = createField(DSL.name("ownerID"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>demodb.owner_owner_assignings.teamID</code>.
     */
    public final TableField<OwnerOwnerAssigningsRecord, String> TEAMID = createField(DSL.name("teamID"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>demodb.owner_owner_assignings.assigneeID</code>.
     */
    public final TableField<OwnerOwnerAssigningsRecord, String> ASSIGNEEID = createField(DSL.name("assigneeID"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * Create a <code>demodb.owner_owner_assignings</code> table reference
     */
    public OwnerOwnerAssignings() {
        this(DSL.name("owner_owner_assignings"), null);
    }

    /**
     * Create an aliased <code>demodb.owner_owner_assignings</code> table reference
     */
    public OwnerOwnerAssignings(String alias) {
        this(DSL.name(alias), OWNER_OWNER_ASSIGNINGS);
    }

    /**
     * Create an aliased <code>demodb.owner_owner_assignings</code> table reference
     */
    public OwnerOwnerAssignings(Name alias) {
        this(alias, OWNER_OWNER_ASSIGNINGS);
    }

    private OwnerOwnerAssignings(Name alias, Table<OwnerOwnerAssigningsRecord> aliased) {
        this(alias, aliased, null);
    }

    private OwnerOwnerAssignings(Name alias, Table<OwnerOwnerAssigningsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> OwnerOwnerAssignings(Table<O> child, ForeignKey<O, OwnerOwnerAssigningsRecord> key) {
        super(child, key, OWNER_OWNER_ASSIGNINGS);
    }

    @Override
    public Schema getSchema() {
        return Demodb.DEMODB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.OWNER_OWNER_ASSIGNINGS_OWNERID);
    }

    @Override
    public List<ForeignKey<OwnerOwnerAssigningsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<OwnerOwnerAssigningsRecord, ?>>asList(Keys.FK_OWNER_OWNER_ASSIGNINGS_TEAMS, Keys.FK_OWNER_OWNER_ASSIGNINGS_SUBSCRIBERS);
    }

    public Teams teams() {
        return new Teams(this, Keys.FK_OWNER_OWNER_ASSIGNINGS_TEAMS);
    }

    public Subscribers subscribers() {
        return new Subscribers(this, Keys.FK_OWNER_OWNER_ASSIGNINGS_SUBSCRIBERS);
    }

    @Override
    public OwnerOwnerAssignings as(String alias) {
        return new OwnerOwnerAssignings(DSL.name(alias), this);
    }

    @Override
    public OwnerOwnerAssignings as(Name alias) {
        return new OwnerOwnerAssignings(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public OwnerOwnerAssignings rename(String name) {
        return new OwnerOwnerAssignings(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public OwnerOwnerAssignings rename(Name name) {
        return new OwnerOwnerAssignings(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
