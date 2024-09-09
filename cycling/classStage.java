package cycling;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class classStage {
  private int numStages = 0;
  private String name;
  private double length;
  private int stageID;
  private int raceID;
  private double location;
  private int checkpointID;
  private String description;
  private LocalDateTime startTime;
  private StageType stageType;
  private CheckpointType checkpointType;
  private Double averageGradient;
  private HashMap<Integer, LocalTime> riderStageResults;
  HashMap<Integer, LocalTime> riders = new HashMap<>();
  Map<Integer, classStage> stages;

  public classStage(String name, double length, int stageID, int raceID, double location, int checkpointID, String description, LocalDateTime startTime, StageType stageType, CheckpointType checkpointType, Double averageGradient, int numStages) {
    this.name = name; 
    this.length = length;
    this.stageID = stageID;
    this.stageType = stageType;
    this.raceID = raceID;
    this.location = location;
    this.checkpointID = checkpointID;
    this.description = description;
    this.startTime = startTime;
    this.stageType = stageType;
    this.checkpointType = checkpointType;
    this.averageGradient = averageGradient;
    this.numStages = numStages;
  }
  //public classStage( name, length, numStages, description, type, raceId, startTime);



  public Map<Integer, LocalTime> getRiders() {
            return this.riders;
        }

  public String getStageName() {
    return name; 
  }

  public double getLength() {
    return length;
  }

  public int getStageID() {
    return stageID;
  }

  public String getDescription() {
    return description;
  }

  public int getRaceID() {
    return raceID;
  }

  public double getLocation() {
    return location;
  }

  public int getCheckpointID() {
    return checkpointID;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public StageType getStageType() {
    return stageType;
  }

  public CheckpointType getCheckpointTypeEnum() {
    return checkpointType;
  }

  public Double getAverageGradient() {
    return averageGradient;
  }
  public void addCheckpoint(int stageID, String name, String description){
  }
  public void addRiderStageResult(int riderID, LocalTime time) {
    riderStageResults.put(riderID, time);
}
}
