package cycling;

public class CheckPointId {
    private double length;
    private double averageGradient;
    private double location;
    private CheckpointType type;
    private int CheckPointID;

public CheckPointId(double length, double averageGradient, double location, CheckpointType type, int CheckPointID){
    this.length = length;
    this.averageGradient = averageGradient;
    this.location = location;
    this.type = type;
    this.CheckPointID = CheckPointID;
}
public CheckPointId(double location, int CheckPointID){
    this.location = location;
    this.CheckPointID = CheckPointID;
    this.type = CheckpointType.SPRINT;
}


public double getLength() {
    return length;
}

public double averageGradient() {
    return averageGradient;
}

public double location() {
    return location;
}

public CheckpointType type() {
    return type;
} 

public int CheckPointID() {
    return CheckPointID;
}
public void stageCheckpoint(int stageId, int checkpointId) {
}

public void addCheckpointToStage(int stageId, int checkpointId) {
}

public void setStageState(int stageId, String state) {
}

}
