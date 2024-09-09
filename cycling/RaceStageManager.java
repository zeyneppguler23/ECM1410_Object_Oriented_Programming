package cycling;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RaceStageManager {
    private Map<String, Integer> stageNameToId = new HashMap<String, Integer>();
    private Map<Integer, String> stageIdToName = new HashMap<Integer, String>();
    private Map<String, Object> stageNames = new HashMap<String, Object>();
    private Map<Integer, Set<Integer>> raceStages = new HashMap<Integer, Set<Integer>>();

    // Method to add a stage
    public void addStage(int id, String name, Object stage) {
        stageNameToId.put(name, id);
        stageIdToName.put(id, name);
        stageNames.put(name, stage);
    }

    // Method to remove a stage
    public void removeStage(int id) {
        String name = stageIdToName.get(id);
        stageNameToId.remove(name);
        stageIdToName.remove(id);
        stageNames.remove(name);
    }

    // Method to associate a stage with a race
    public void addStageToRace(int raceId, int stageId) {
        raceStages.computeIfAbsent(raceId, k -> new HashSet<>()).add(stageId);
    }

    // Method to retrieve the stages associated with a race
    public Set<Integer> getStagesForRace(int raceId) {
        return raceStages.getOrDefault(raceId, new HashSet<>());
    }

    // Method to retrieve the name based on stage ID
    public String getNameForStage(int stageId) {
        return stageIdToName.get(stageId);
    }

    // Method to retrieve the ID based on stage name
    public Integer getIdForStage(String stageName) {
        return stageNameToId.get(stageName);
    }
}


