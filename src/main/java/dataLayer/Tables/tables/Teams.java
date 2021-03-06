/*
 * This file is generated by jOOQ.
 */
package dataLayer.Tables.tables;


import dataLayer.Tables.Demodb;
import dataLayer.Tables.Keys;
import dataLayer.Tables.tables.records.TeamsRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Teams extends TableImpl<TeamsRecord> {

    private static final long serialVersionUID = 152788758;

    /**
     * The reference instance of <code>demodb.teams</code>
     */
    public static final Teams TEAMS = new Teams();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TeamsRecord> getRecordType() {
        return TeamsRecord.class;
    }

    /**
     * The column <code>demodb.teams.name</code>.
     */
    public final TableField<TeamsRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>demodb.teams.establishedYear</code>.
     */
    public final TableField<TeamsRecord, Integer> ESTABLISHEDYEAR = createField(DSL.name("establishedYear"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>demodb.teams.isActive</code>.
     */
    public final TableField<TeamsRecord, Boolean> ISACTIVE = createField(DSL.name("isActive"), org.jooq.impl.SQLDataType.BIT.defaultValue(org.jooq.impl.DSL.field("b'0'", org.jooq.impl.SQLDataType.BIT)), this, "");

    /**
     * The column <code>demodb.teams.teamManagerID</code>.
     */
    public final TableField<TeamsRecord, String> TEAMMANAGERID = createField(DSL.name("teamManagerID"), org.jooq.impl.SQLDataType.VARCHAR(50).defaultValue(org.jooq.impl.DSL.field("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>demodb.teams.closedByAdmin</code>.
     */
    public final TableField<TeamsRecord, Boolean> CLOSEDBYADMIN = createField(DSL.name("closedByAdmin"), org.jooq.impl.SQLDataType.BIT.defaultValue(org.jooq.impl.DSL.field("b'1'", org.jooq.impl.SQLDataType.BIT)), this, "");

    /**
     * Create a <code>demodb.teams</code> table reference
     */
    public Teams() {
        this(DSL.name("teams"), null);
    }

    /**
     * Create an aliased <code>demodb.teams</code> table reference
     */
    public Teams(String alias) {
        this(DSL.name(alias), TEAMS);
    }

    /**
     * Create an aliased <code>demodb.teams</code> table reference
     */
    public Teams(Name alias) {
        this(alias, TEAMS);
    }

    private Teams(Name alias, Table<TeamsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Teams(Name alias, Table<TeamsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Teams(Table<O> child, ForeignKey<O, TeamsRecord> key) {
        super(child, key, TEAMS);
    }

    @Override
    public Schema getSchema() {
        return Demodb.DEMODB;
    }

    @Override
    public UniqueKey<TeamsRecord> getPrimaryKey() {
        return Keys.KEY_TEAMS_PRIMARY;
    }

    @Override
    public List<UniqueKey<TeamsRecord>> getKeys() {
        return Arrays.<UniqueKey<TeamsRecord>>asList(Keys.KEY_TEAMS_PRIMARY);
    }

    @Override
    public List<ForeignKey<TeamsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TeamsRecord, ?>>asList(Keys.FK_TEAMS_TEAMMANAGERS);
    }

    public Teammanagers teammanagers() {
        return new Teammanagers(this, Keys.FK_TEAMS_TEAMMANAGERS);
    }

    @Override
    public Teams as(String alias) {
        return new Teams(DSL.name(alias), this);
    }

    @Override
    public Teams as(Name alias) {
        return new Teams(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Teams rename(String name) {
        return new Teams(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Teams rename(Name name) {
        return new Teams(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<String, Integer, Boolean, String, Boolean> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
