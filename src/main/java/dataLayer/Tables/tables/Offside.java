/*
 * This file is generated by jOOQ.
 */
package dataLayer.Tables.tables;


import dataLayer.Tables.Demodb;
import dataLayer.Tables.Indexes;
import dataLayer.Tables.Keys;
import dataLayer.Tables.tables.records.OffsideRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
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
public class Offside extends TableImpl<OffsideRecord> {

    private static final long serialVersionUID = -226025906;

    /**
     * The reference instance of <code>demodb.offside</code>
     */
    public static final Offside OFFSIDE = new Offside();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OffsideRecord> getRecordType() {
        return OffsideRecord.class;
    }

    /**
     * The column <code>demodb.offside.MatchID</code>.
     */
    public final TableField<OffsideRecord, Integer> MATCHID = createField(DSL.name("MatchID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>demodb.offside.EventID</code>.
     */
    public final TableField<OffsideRecord, Integer> EVENTID = createField(DSL.name("EventID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>demodb.offside.Time</code>.
     */
    public final TableField<OffsideRecord, String> TIME = createField(DSL.name("Time"), org.jooq.impl.SQLDataType.VARCHAR(50).defaultValue(org.jooq.impl.DSL.field("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>demodb.offside.PlayerOffsideID</code>.
     */
    public final TableField<OffsideRecord, String> PLAYEROFFSIDEID = createField(DSL.name("PlayerOffsideID"), org.jooq.impl.SQLDataType.VARCHAR(50).defaultValue(org.jooq.impl.DSL.field("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * Create a <code>demodb.offside</code> table reference
     */
    public Offside() {
        this(DSL.name("offside"), null);
    }

    /**
     * Create an aliased <code>demodb.offside</code> table reference
     */
    public Offside(String alias) {
        this(DSL.name(alias), OFFSIDE);
    }

    /**
     * Create an aliased <code>demodb.offside</code> table reference
     */
    public Offside(Name alias) {
        this(alias, OFFSIDE);
    }

    private Offside(Name alias, Table<OffsideRecord> aliased) {
        this(alias, aliased, null);
    }

    private Offside(Name alias, Table<OffsideRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Offside(Table<O> child, ForeignKey<O, OffsideRecord> key) {
        super(child, key, OFFSIDE);
    }

    @Override
    public Schema getSchema() {
        return Demodb.DEMODB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.OFFSIDE_MATCHID);
    }

    @Override
    public List<ForeignKey<OffsideRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<OffsideRecord, ?>>asList(Keys.FK_OFFSIDE_MATCH, Keys.FK_OFFSIDE_PLAYERS);
    }

    public Match match() {
        return new Match(this, Keys.FK_OFFSIDE_MATCH);
    }

    public Players players() {
        return new Players(this, Keys.FK_OFFSIDE_PLAYERS);
    }

    @Override
    public Offside as(String alias) {
        return new Offside(DSL.name(alias), this);
    }

    @Override
    public Offside as(Name alias) {
        return new Offside(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Offside rename(String name) {
        return new Offside(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Offside rename(Name name) {
        return new Offside(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
