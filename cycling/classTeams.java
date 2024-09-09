package cycling;

import java.util.List;

public class classTeams {
    private String name;
    private String description;
    private int teamId;
    private List<Object>[] rider;



    public classTeams(String name, String description, int teamId, List<Object>... rider){
        this.name = name;
        this.name = description;
        this.teamId = teamId;
        this.rider = rider;

    }

    public String getname(){
        return name;
    }
    
    public String getdescription(){
        return description;
    }

    public int getteamId(){
        return teamId;
    }

    public List<Object>[] getrider(){
        return rider;
    }

}
