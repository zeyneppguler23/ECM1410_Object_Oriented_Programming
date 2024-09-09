package cycling;

import java.util.List;

public class classRiders {
    private int teamId;
    private int riderId;
    private String name;
    private int yearOfBirth;
    private List<Object>[] result;

    public classRiders(int teamdId, int riderId, String name, int yearOfBirth, List<Object>... result){
        this.teamId = teamdId;
        this.riderId = riderId;
        this.name = name;
        this.yearOfBirth=yearOfBirth;
        this.result=result;
    }
    
    public int getteamId(){
        return teamId;
    }

    public int getriderId(){
        return riderId;
    }
    
    public String getname(){
        return name;
    }

    public int getyearOfBirth(){
        return yearOfBirth;
    }

    public List<Object>[] getresult(){
        return result;
    }
}
