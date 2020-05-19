package businessLayer.Utilities;

public interface HasPage {

    /**
     * The function sends an update that occurred within the page's owner and returns whether the update was successful or not
     *
     * @param update
     * @return
     */
    boolean updatePage(String update);

}
