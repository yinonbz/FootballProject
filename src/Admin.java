public class Admin {
    private String name;

    public Admin(String name) {
        this.name = name;
    }

    public Boolean deleteSubscriber(String subscriberId){
        return true;
    }

    public Boolean closeTeam(int teamId){
        return true;
    }

    public void observeLogs(){

    }

    public Boolean executeRecommendation(){
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
