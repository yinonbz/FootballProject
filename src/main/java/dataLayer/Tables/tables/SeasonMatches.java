/*
 * This file is generated by jOOQ.
 */
package dataLayer.Tables.tables;


import dataLayer.Tables.Demodb;
import dataLayer.Tables.Indexes;
import dataLayer.Tables.Keys;
import dataLayer.Tables.tables.records.SeasonMatchesRecord;

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
public class SeasonMatches extends TableImpl<SeasonMatchesRecord> {

    private static final long serialVersionUID = 1363363783;

    /**
     * The reference instance of <code>demodb.season_matches</code>
     */
    public static final SeasonMatches SEASON_MATCHES = new SeasonMatches();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SeasonMatchesRecord> getRecordType() {
        return SeasonMatchesRecord.class;
    }

    /**
     * The column <code>demodb.season_matches.LeagueID</code>.
     */
    public final TableField<SeasonMatchesRecord, String> LEAGUEID = createField(DSL.name("LeagueID"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>demodb.season_matches.SeasonID</code>.
     */
    public final TableField<SeasonMatchesRecord, Integer> SEASONID = createField(DSL.name("SeasonID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>demodb.season_matches.MatchID</code>.
     */
    public final TableField<SeasonMatchesRecord, Integer> MATCHID = createField(DSL.name("MatchID"), org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("NULL", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * Create a <code>demodb.season_matches</code> table reference
     */
    public SeasonMatches() {
        this(DSL.name("season_matches"), null);
    }

    /**
     * Create an aliased <code>demodb.season_matches</code> table reference
     */
    public SeasonMatches(String alias) {
        this(DSL.name(alias), SEASON_MATCHES);
    }

    /**
     * Create an aliased <code>demodb.season_matches</code> table reference
     */
    public SeasonMatches(Name alias) {
        this(alias, SEASON_MATCHES);
    }

    private SeasonMatches(Name alias, Table<SeasonMatchesRecord> aliased) {
        this(alias, aliased, null);
    }

    private SeasonMatches(Name alias, Table<SeasonMatchesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> SeasonMatches(Table<O> child, ForeignKey<O, SeasonMatchesRecord> key) {
        super(child, key, SEASON_MATCHES);
    }

    @Override
    public Schema getSchema() {
        return Demodb.DEMODB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.SEASON_MATCHES_LEAGUEID);
    }

    @Override
    public List<ForeignKey<SeasonMatchesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<SeasonMatchesRecord, ?>>asList(Keys.FK_SEASON_MATCHES_LEAGUE, Keys.FK_SEASON_MATCHES_MATCH);
    }

    public League league() {
        return new League(this, Keys.FK_SEASON_MATCHES_LEAGUE);
    }

    public Match match() {
        return new Match(this, Keys.FK_SEASON_MATCHES_MATCH);
    }

    @Override
    public SeasonMatches as(String alias) {
        return new SeasonMatches(DSL.name(alias), this);
    }

    @Override
    public SeasonMatches as(Name alias) {
        return new SeasonMatches(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SeasonMatches rename(String name) {
        return new SeasonMatches(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SeasonMatches rename(Name name) {
        return new SeasonMatches(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, Integer, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
