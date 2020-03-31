import java.util.List;

public class Guest {
    private int guestId;

    public Guest(int guestId) {
        this.guestId = guestId;
    }

    public Boolean register(){
        return true;
    }

    public Boolean login(){
        return true;
    }

    public List<Object> search(String searchInput){
        return null;
    }

    public void openPage(){

    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }
}
