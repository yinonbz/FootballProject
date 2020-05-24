/*
 * This file is generated by jOOQ.
 */
package dataLayer.Tables.tables;


import dataLayer.Tables.Demodb;
import dataLayer.Tables.Indexes;
import dataLayer.Tables.Keys;
import dataLayer.Tables.tables.records.PageOwnerRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
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
public class PageOwner extends TableImpl<PageOwnerRecord> {

    private static final long serialVersionUID = 685501258;

    /**
     * The reference instance of <code>demodb.page_owner</code>
     */
    public static final PageOwner PAGE_OWNER = new PageOwner();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PageOwnerRecord> getRecordType() {
        return PageOwnerRecord.class;
    }

    /**
     * The column <code>demodb.page_owner.pageID</code>.
     */
    public final TableField<PageOwnerRecord, Integer> PAGEID = createField(DSL.name("pageID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>demodb.page_owner.ownerID</code>.
     */
    public final TableField<PageOwnerRecord, String> OWNERID = createField(DSL.name("ownerID"), org.jooq.impl.SQLDataType.VARCHAR(50).defaultValue(org.jooq.impl.DSL.field("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * Create a <code>demodb.page_owner</code> table reference
     */
    public PageOwner() {
        this(DSL.name("page_owner"), null);
    }

    /**
     * Create an aliased <code>demodb.page_owner</code> table reference
     */
    public PageOwner(String alias) {
        this(DSL.name(alias), PAGE_OWNER);
    }

    /**
     * Create an aliased <code>demodb.page_owner</code> table reference
     */
    public PageOwner(Name alias) {
        this(alias, PAGE_OWNER);
    }

    private PageOwner(Name alias, Table<PageOwnerRecord> aliased) {
        this(alias, aliased, null);
    }

    private PageOwner(Name alias, Table<PageOwnerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> PageOwner(Table<O> child, ForeignKey<O, PageOwnerRecord> key) {
        super(child, key, PAGE_OWNER);
    }

    @Override
    public Schema getSchema() {
        return Demodb.DEMODB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PAGE_OWNER_OWNERID, Indexes.PAGE_OWNER_PAGEID);
    }

    @Override
    public List<ForeignKey<PageOwnerRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PageOwnerRecord, ?>>asList(Keys.FK_PAGE_OWNER_PAGES);
    }

    public Pages pages() {
        return new Pages(this, Keys.FK_PAGE_OWNER_PAGES);
    }

    @Override
    public PageOwner as(String alias) {
        return new PageOwner(DSL.name(alias), this);
    }

    @Override
    public PageOwner as(Name alias) {
        return new PageOwner(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PageOwner rename(String name) {
        return new PageOwner(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PageOwner rename(Name name) {
        return new PageOwner(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
