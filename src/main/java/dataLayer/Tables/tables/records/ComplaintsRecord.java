/*
 * This file is generated by jOOQ.
 */
package dataLayer.Tables.tables.records;


import dataLayer.Tables.tables.Complaints;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ComplaintsRecord extends UpdatableRecordImpl<ComplaintsRecord> implements Record6<Integer, String, String, String, String, Boolean> {

    private static final long serialVersionUID = 113229556;

    /**
     * Setter for <code>demodb.complaints.ComplaintID</code>.
     */
    public void setComplaintid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>demodb.complaints.ComplaintID</code>.
     */
    public Integer getComplaintid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>demodb.complaints.WriterID</code>.
     */
    public void setWriterid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>demodb.complaints.WriterID</code>.
     */
    public String getWriterid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>demodb.complaints.Content</code>.
     */
    public void setContent(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>demodb.complaints.Content</code>.
     */
    public String getContent() {
        return (String) get(2);
    }

    /**
     * Setter for <code>demodb.complaints.handlerID</code>.
     */
    public void setHandlerid(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>demodb.complaints.handlerID</code>.
     */
    public String getHandlerid() {
        return (String) get(3);
    }

    /**
     * Setter for <code>demodb.complaints.Comment</code>.
     */
    public void setComment(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>demodb.complaints.Comment</code>.
     */
    public String getComment() {
        return (String) get(4);
    }

    /**
     * Setter for <code>demodb.complaints.isAnswered</code>.
     */
    public void setIsanswered(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>demodb.complaints.isAnswered</code>.
     */
    public Boolean getIsanswered() {
        return (Boolean) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, String, String, String, Boolean> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, String, String, String, String, Boolean> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Complaints.COMPLAINTS.COMPLAINTID;
    }

    @Override
    public Field<String> field2() {
        return Complaints.COMPLAINTS.WRITERID;
    }

    @Override
    public Field<String> field3() {
        return Complaints.COMPLAINTS.CONTENT;
    }

    @Override
    public Field<String> field4() {
        return Complaints.COMPLAINTS.HANDLERID;
    }

    @Override
    public Field<String> field5() {
        return Complaints.COMPLAINTS.COMMENT;
    }

    @Override
    public Field<Boolean> field6() {
        return Complaints.COMPLAINTS.ISANSWERED;
    }

    @Override
    public Integer component1() {
        return getComplaintid();
    }

    @Override
    public String component2() {
        return getWriterid();
    }

    @Override
    public String component3() {
        return getContent();
    }

    @Override
    public String component4() {
        return getHandlerid();
    }

    @Override
    public String component5() {
        return getComment();
    }

    @Override
    public Boolean component6() {
        return getIsanswered();
    }

    @Override
    public Integer value1() {
        return getComplaintid();
    }

    @Override
    public String value2() {
        return getWriterid();
    }

    @Override
    public String value3() {
        return getContent();
    }

    @Override
    public String value4() {
        return getHandlerid();
    }

    @Override
    public String value5() {
        return getComment();
    }

    @Override
    public Boolean value6() {
        return getIsanswered();
    }

    @Override
    public ComplaintsRecord value1(Integer value) {
        setComplaintid(value);
        return this;
    }

    @Override
    public ComplaintsRecord value2(String value) {
        setWriterid(value);
        return this;
    }

    @Override
    public ComplaintsRecord value3(String value) {
        setContent(value);
        return this;
    }

    @Override
    public ComplaintsRecord value4(String value) {
        setHandlerid(value);
        return this;
    }

    @Override
    public ComplaintsRecord value5(String value) {
        setComment(value);
        return this;
    }

    @Override
    public ComplaintsRecord value6(Boolean value) {
        setIsanswered(value);
        return this;
    }

    @Override
    public ComplaintsRecord values(Integer value1, String value2, String value3, String value4, String value5, Boolean value6) {
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
     * Create a detached ComplaintsRecord
     */
    public ComplaintsRecord() {
        super(Complaints.COMPLAINTS);
    }

    /**
     * Create a detached, initialised ComplaintsRecord
     */
    public ComplaintsRecord(Integer complaintid, String writerid, String content, String handlerid, String comment, Boolean isanswered) {
        super(Complaints.COMPLAINTS);

        set(0, complaintid);
        set(1, writerid);
        set(2, content);
        set(3, handlerid);
        set(4, comment);
        set(5, isanswered);
    }
}
