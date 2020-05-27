package businessLayer.Utilities;

public class Complaint {

    private int id;
    private String writer;
    private String complaintContent;
    private String handler;
    private String comment;
    private boolean isAnswered;

    /**
     * constructor 
     * @param writer the writer of the 
     * @param complaintContent
     * @param handler
     * @param comment
     */
    public Complaint(int id, String writer, String complaintContent, String handler, String comment, boolean isAnswered) {
        this.id=id;
        this.writer = writer;
        this.complaintContent = complaintContent;
        this.handler = handler;
        this.comment = comment;
        this.isAnswered=isAnswered;
    }

    /**
     * constructor of complaint
     * @param complaintContent the content of the complaint
     * @param writer the writer of the complaint
     */
    public Complaint (String complaintContent, String writer){
        this.complaintContent = complaintContent;
        this.writer = writer;
        isAnswered=false;

    }



    /**
     * getter of the writer field
     * @return the writer object
     */
    public String getWriter() {
        return writer;
    }

    /**
     * getter of the complaint's content
     * @return the content of the complaint
     */
    public String getComplaintContent() {
        return complaintContent;
    }

    /**
     * getter of the handler of the complaint
     * @return the handler of the complaint
     */
    public String getHandler() {
        return handler;
    }

    /**
     * getter of the comment of the handler
     * @return tje comment of the handler
     */
    public String getComment() {
        return comment;
    }

    /**
     * a getter of the answered field
     * @return the answered field
     */
    public boolean isAnswered() {
        return isAnswered;
    }

    /**
     * a setter of the answered field
     * @param answered
     */
    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    /**
     * the setter of the writer of the complaint
     * @param writer the writer of the complaint
     */
    public void setWriter(String writer) {
        this.writer = writer;
    }

    /**
     * setter of the content of the complaint
     * @param complaintContent the content of the complaint
     */
    public void setComplaintContent(String complaintContent) {
        this.complaintContent = complaintContent;
    }

    /**
     * setter of the handler's complaint
     * @param handler the handler of the complaint
     */
    public void setHandler(String handler) {
        this.handler = handler;
    }

    /**
     * a setter of the complaint's comment
     * @param comment the comment of the complaint
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * getter of the id
     * @return the id of the complaint
     */
    public int getId() {
        return id;
    }

    /**
     * setter of the id
     * @param id the id of the complaint
     */
    public void setId(int id) {
        this.id = id;
    }
}
