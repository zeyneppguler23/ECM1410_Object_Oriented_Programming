package cycling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.time.Duration;


/**
 * CyclingPortal is a fully compiling, functioning implementor
 * of the CyclingPortal interface.
 * 
 * @author 730055520 - 730034y5
 * @version 1.0
 *
 */


public class CyclingPortalImpl implements CyclingPortal {

	private ArrayList<List<Object>> race = new ArrayList<>();
	private ArrayList<List<Object>> teams = new ArrayList<>();
	private ArrayList<List<Object>> stage = new ArrayList<>();
	private ArrayList<List<Object>> riders = new ArrayList<>();
	private ArrayList<List<Object>> riderId = new ArrayList<>();
	ArrayList<Integer> checkpointList = new ArrayList<Integer>();
	private int id = 0;
	private int theraceID = 0;
	private int[] raceIDsList = new int[1];
	private int theteamID = 0;
	private int[] teamIDsList = new int[1];
	private int CheckPointID = 0;
	private ArrayList<classRace> races;
	private int[] rankedRiderIds;
	private int[] CheckPointIDs = new int[1];
	private int raceStageId = 0;
	private int[] raceStageIdList = new int[1];
	private int[] raceStageIdArray;
	private int theriderid = 0;
	private int[] riderIDList = new int[1];
	int raceIndex = 0;
	private HashMap<Integer, Double> stageLengths = new HashMap<Integer, Double>();
	private HashMap<String, Integer> stageNameToId = new HashMap<String, Integer>();
	private HashMap<Integer, String> stageIdToName = new HashMap<Integer, String>();
	private HashMap<String, Object> stageNames = new HashMap<String, Object>();
	public HashMap<Integer,List<Integer>> raceStages = new HashMap<Integer, List<Integer>>();
	private HashMap<Integer, Integer> stageRaces = new HashMap<Integer, Integer>();
	private HashMap<Integer, CheckPointId> CheckPointIds = new HashMap<Integer, CheckPointId>();
	private HashMap<Integer, CheckPointId> CheckPointIdName = new HashMap<Integer, CheckPointId>();
	public HashMap<Integer, int[]> stageCheckpoints = new HashMap<Integer, int[]>(); //stage ID and list of the checkpointIDs -- use that to remove checkpoint 
	private int[] Checkpoints = new int[220];  //list of checkpoints
	private HashMap<Integer, Integer> CheckpointStage = new HashMap<Integer, Integer>();
	public HashMap<Integer, int[]> stagecheckpoint = new HashMap<Integer, int[]>();
	private HashMap<Integer, String> StagePreparation = new HashMap<Integer, String>();
	private HashMap<Integer, String> RaceIdNames = new HashMap<Integer, String>();
	private HashMap<Integer, Double> stageIDtolenght = new HashMap<Integer, Double>();
	private HashMap<Integer, Integer> riderPoints = new HashMap<Integer, Integer>();
	private HashMap<Integer, LocalTime> stageTimes = new HashMap<Integer, LocalTime>();
	private Map<Integer, LocalTime> ridersTimesMap = new HashMap<>();
	private Map<Integer, LocalTime> riderselapsedTimesMap = new HashMap<>();
	private Map<Integer, Integer> PointsMap = new HashMap<>();
	private Map<Integer, Integer> MPointsMap = new HashMap<>();
	private StageType tip =null;
	private HashMap<Integer, Long> ridersTotalElapsedTimes = new HashMap<Integer, Long>(); //Initialize a map to store rider's adjusted elapsed times in all stages of the race
    private Map<Integer, Map<Integer, LocalTime>> stageAdjustedTimes = new HashMap<Integer, Map<Integer, LocalTime>>(); //this contains adjusted times for each rider in each stage
	private Map<Integer, Map<Integer, Integer>> stagePoints = new HashMap<Integer, Map<Integer, Integer>>(); // this contains points for each rider in each stage
	private Map<Integer, Map<Integer, Integer>> stageMountainPoints = new HashMap<Integer, Map<Integer, Integer>>(); // this contains mountain points for each rider in each stage
	private HashMap<Integer, LocalTime[]> riderResults = new HashMap<Integer, LocalTime[]>();
	private HashMap<Integer, LocalTime>stageRiderElapsed = new HashMap<Integer, LocalTime>();
	private HashMap<Integer, Map<Integer, CheckPointId> >stageCheckpointIds = new HashMap<Integer, Map<Integer, CheckPointId>>();
	private HashMap<Integer, Map<Integer, LocalTime[]> >stageRiders = new HashMap<Integer, Map<Integer, LocalTime[]>>();
	private List<Integer> validCheckpointList = new ArrayList<>();
	public void setRidersData(List<List<Object>> ridersData) {
		this.riders = new ArrayList<>(ridersData); // Convert to ArrayList
		System.out.println("Riders data set: " + this.riders);
	}
	
	public void setRaceStagesData(Map<Integer, List<Integer>> raceStagesData) {
		this.raceStages = new HashMap<>(raceStagesData); // Convert to HashMap
		System.out.println("Race stages data set: " + this.raceStages);
	}

	@Override
	public int[] getRaceIds() {
		return raceIDsList; //Return IDList
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		//Error Stacks to check on Exceptions
		if (!name.getClass().equals(String.class)||name.equals(null)){
			throw new InvalidNameException("Name must be String!");
		};
		for (List<Object> raceDetails : race) {
			String existingName = (String) raceDetails.get(1); 
			if (existingName.equals(name)) {
				throw new IllegalNameException("Team already exists: " + name);
			}
		}

		//Adding race it arraylist and updating raceIDsList
		theraceID++;
		race.add(Arrays.asList(theraceID,name, description));
		int[] updatedRaceIDsList = Arrays.copyOf(raceIDsList, theraceID);
		updatedRaceIDsList[theraceID - 1] = theraceID;
		raceIDsList = updatedRaceIDsList;
		return theraceID;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		// Search for the race with the given raceId
		for (List<Object> raceDetails : race) {
			int id = (int) raceDetails.get(0);
			if (id == raceId) {
				// Found the race, return its details
				String name = (String) raceDetails.get(1); // Assuming race name is stored at index 1
				String description = (String) raceDetails.get(2); // Assuming race description is stored at index 2
				return "Race ID: " + id + "\nName: " + name + "\nDescription: " + description;
			}
		}
		
		// If raceId is not recognized, throw IDNotRecognisedException
		throw new IDNotRecognisedException("Race ID not recognized: " + raceId);
		
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {

		//Checking if raceId exists
		boolean idFound = false;
		for (int id : raceIDsList) {
    	if (id == raceId) {
        	idFound = true;
        	break;
    		}
		}

		if (!idFound) {
    		throw new IDNotRecognisedException("ID Not Recognised: " + raceId);
			}
		
		//Removing race and its results
		int[]thatstages =null;

		race.removeIf(race -> (int) race.get(0) == raceId);

		raceIDsList = Arrays.stream(raceIDsList).filter(id -> id != raceId).toArray();

		try {
			thatstages = getRaceStages(raceId);
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}
		
		if (thatstages != null) {
			for (int stageId : thatstages) {
				for (List<Object> riderDetails : riders) {
					Iterator<Object> iterator = riderDetails.iterator();
					while (iterator.hasNext()) {
						Object obj = iterator.next();
						if (obj instanceof List) {
							List<Object> innerList = (List<Object>) obj;
							int stageInInnerList = (int) innerList.get(0);
							if (stageInInnerList == stageId) {
								iterator.remove(); // Remove rider results associated with the deleted race
								break; // Exit the loop after removing the result for the specified stage
							}
						}
					}
				}
			}
		}
	}


@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// check if the provided raceId exists in the system using stream().noneMatch()
		//noneMatch() of Stream class returns whether no elements of this stream match the provided predicate.
		//stream(): This method is called on a collection, converting it into a Stream. A Stream is a sequence of elements that supports various operations.
		//filter(): This method is an intermediate operation that takes a Predicate (a functional interface representing a boolean-valued function) as an argument. 
		//It filters the elements of the stream based on whether they satisfy the given predicate. Only elements that match the predicate will be included in the resulting stream.
		//count(): This method is a terminal operation that returns the count of elements in the stream. 
		if (race.stream().noneMatch(raceDetails -> (int) raceDetails.get(0) == raceId)) {
			// If the race ID is not recognized, throw IDNotRecognisedException. 
			throw new IDNotRecognisedException("Race ID not recognized: " + raceId);
		}
	//Otherwise, it counts the number of stages associated with the specified raceId using stream().filter().count().
	//stream().filter().count() method used to count the number of stages associated with a specific race ID.
		return (int) stage.stream().filter(stageDetails -> (int) stageDetails.get(0) == raceId).count();
		//lambda expression (parameter -> expression) == taking a parameter and returning to a value. 
	}

@Override
public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type)
        throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
	

	//stage name exist?
	//throw illegalnameexception if the name already exist in the platorm
	if (stage.stream().anyMatch(stageDetails -> {
		Object stageDetail = stageDetails.get(1);
		if (stageDetail instanceof String) {
			return ((String) stageDetail).equals(stageName);
		} else {
			return false; // Or handle the case when stageDetail is not a String
		}
	})) {
		throw new IllegalNameException("A stage name already exists in the platform: " + stageName);
	}

	// Check if the length is valid
	if (length < 5) {
		throw new InvalidLengthException("Invalid stage length: " + length);
	}

	// @return the unique ID of the stage.
	// return the unique ID to the stage after the new stage added to the list
	// Create a new stage and add it to the list

	// @return the unique ID of the stage.
	// Return the unique ID of the stage after the new stage is added to the list
	// Create a new stage and add it to the list
	int uniqueStageID = stage.size() + 1; //+1 is used to generate a unique identifier in this stage

	//adding new stage to the list 
	stage.add(Arrays.asList(raceId, uniqueStageID, stageName, description, length, startTime, type));
	stageLengths.put(uniqueStageID, length);
	List<Integer> stages = raceStages.getOrDefault(raceId, new ArrayList<>());
    stages.add(uniqueStageID);
    raceStages.put(raceId, stages);



	//Return the unique ID of the stage
	return uniqueStageID;
}


	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		//check if the raceID exist in the system -as always
		if (race.stream().noneMatch(raceDetails -> (int) raceDetails.get(0) == raceId)) {
			//if not throw IDNotRecognisedException
			throw new IDNotRecognisedException("Race ID not recognized: " + raceId);
		}
		//Use an ArrayList to store the stage IDs
    	List<Integer> raceStageIdList = new ArrayList<>();
		for (List<Object> stageDetails : stage) {
        	int stageRaceId = (int) stageDetails.get(0); // Assuming race ID is at index 0
        	if (stageRaceId == raceId) {
            	raceStageIdList.add((int) stageDetails.get(1)); // Assuming stage ID is at index 1
        	}
    	}

    	//Convert the ArrayList to an array
    	int[] raceStageId = raceStageIdList.stream().mapToInt(Integer::intValue).toArray();

    	return raceStageId;
	}

	@Override
	// Gets the length of a stage in a race, in kilometres.
	//@param stageId The ID of the stage being queried.
	// return stages lenght
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		//stage ID exist? 
		if (stageLengths.containsKey(stageId)) { //The java.util.Map.containsKey() method is used to check whether a particular key is being mapped into the Map or not
            // Return the length of the stage
            return stageLengths.get(stageId);
        } else {
            // Throw an exception if the stageId is not recognized
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
        }
    }
	//Map is a way to keep track of information in the form of key-value pairs. 
	//In Java, a map can consist of virtually any number of key-value pairs, 
	//but the keys must always be unique — non-repeating.

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		//if the ID exists 
		if (race.stream().noneMatch(raceDetails -> (int) raceDetails.get(0) == stageId)) {
			// If the ID is not recognized, throw IDNotRecognisedException. 
			throw new IDNotRecognisedException("ID not recognized: " + stageId);
		}

		if (stageIdToName.containsKey(stageId)) {
    		String thisName = stageIdToName.get(stageId); 
    		stageNameToId.remove(thisName); 
    		stageIdToName.remove(stageId); 
    		stageNames.remove(thisName);

    if (stageNameToId.containsValue(stageId)) {
        String stageNameToRemove = null;
        for (Map.Entry<String, Integer> entry : stageNameToId.entrySet()) {
            if (entry.getValue() == stageId) {
                stageNameToRemove = entry.getKey();
                break;
            }
        }
        if (stageNameToRemove != null) {
            stageNameToId.remove(stageNameToRemove);
            stageIdToName.remove(stageId);
            stageNames.remove(stageNameToRemove);

            // Remove stage from raceStages
            List<Integer> racesToRemoveFrom = raceStages.get(stageId);
            if (racesToRemoveFrom != null) {
                for (Integer raceId : racesToRemoveFrom) {
                    List<Integer> stagesInRace = raceStages.get(raceId);
                    if (stagesInRace != null) {
                        stagesInRace.remove(stageId);
                    }
                }
            }

            // Remove stage from stageRaces
            stageRaces.remove(stageId);
        }
    } else {
        throw new IDNotRecognisedException("Stage ID not recognized");
    	}
		}
	}
		

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
				int newcheckpointId = id;
		// Check if the race ID exists
    	boolean raceStageIdBool = false;
    	for (List<Object> raceStageIdList : race) {
        	int id = (int) raceStageIdList.get(0);
        	if (id == stageId) {
			//the unique ID of the stage.
            raceStageIdBool = true;	
            break;
        	}
    	}
		//If the ID does not match to any race in the system.
		//Throw exception
    	if (!raceStageIdBool) {
        	throw new IDNotRecognisedException(" ID not recognized: " + stageId);
    	}
		//if(RaceIdNames.get(raceId) == null){
			//throw new IDNotRecognisedException("Race ID not recognized: " + stageId);
		//}
		
		//@throws InvalidStageTypeException  Time-trial stages cannot contain any checkpoint. ---check if the stage type is not time-trial
        for (List<Object> stage : race) {
            int id = (int) stage.get(0);
            String stageType = (String) stage.get(2);
            if (id == stageId && stageType.equals("time-trial")) {
                throw new InvalidStageTypeException("Time-trial stages cannot contain any checkpoint");
            }
        }
		
		//@throws InvalidLocationException   If the location is out of bounds of the stage length.  Check if the location is within the bounds of the stage length
        if (location < 0 || location > length) {
            throw new InvalidLocationException("Location is out of bounds of the stage length");
        }

		// @throws InvalidStageStateException If the stage is "waiting for results". Check if the stage is not in "waiting for results" state
        for (List<Object> stage : race) {
            int id = (int) stage.get(0);
            String state = (String) stage.get(1);
            if (id == stageId && state.equals("waiting for results")) {
                throw new InvalidStageStateException("Stage is in 'waiting for results' state");
            }
		
		CheckPointId newCheckpoint = new CheckPointId (length, averageGradient, location, type, id);
		CheckPointIds.put(id , newCheckpoint);
		Checkpoints[id] = id; //list of id 
		//id = id of the checkpoint
		id++;
		stageCheckpoints.remove(stageId);//remove the old one
		stageCheckpoints.put(stageId, Checkpoints);	//put the new one

		
		}
		return newcheckpointId;
		
	}
		

	
	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {

		int newId = id;

				//Check if the race ID exists
				boolean raceStageIdBool = false;
				for (List<Object> raceStageIdList : race) {
					int raceId = (int) raceStageIdList.get(0);
					if (raceId == stageId) {
						raceStageIdBool = true;
						break;
					}
				}
				
				if (!raceStageIdBool) {
					throw new IDNotRecognisedException("Race ID not recognized: " + stageId);
				}
			
				//@throws InvalidStageTypeException  Time-trial stages cannot contain any checkpoint.
				for (List<Object> stage : race) {
					int id = (int) stage.get(0);
					String stageType = (String) stage.get(2);
					if (id == stageId && stageType.equals("time-trial")) {
						throw new InvalidStageTypeException("Time-trial stages cannot contain any checkpoint");
					}
				}
			
				//Check if the location is within the bounds of the stage length otherwise, throw an exception
				Double length = stageLengths.get(stageId);
				if (location < 0 || location > length) {
					throw new InvalidLocationException("Location is out of bounds of the stage length");
				}
			
				//Check if the stage is not in "waiting for results" state, otherwise throw an exception
				for (List<Object> stage : race) {
					int id = (int) stage.get(0);
					String state = (String) stage.get(1);
					if (id == stageId && state.equals("waiting for results")) {
						throw new InvalidStageStateException("Stage is in 'waiting for results' state");
					}
				}
			
				//create a new checkpoint
				CheckPointId newCheckpoint = new CheckPointId(location, id);
				int[] existedcheckpoints = stageCheckpoints.get(stageId);
				if (existedcheckpoints == null) { //It checks if there are any existing checkpoints for the stage.
					existedcheckpoints = new int[1]; //If there are no existing checkpoints, it initializes a new array of size 1 to store the new checkpoint ID.
				} else {
					int existednumber = existedcheckpoints.length; 
					int[] newCheckpoints = Arrays.copyOf(existedcheckpoints, existednumber + 1);
					existedcheckpoints = newCheckpoints;
				}
				existedcheckpoints[existedcheckpoints.length - 1] = CheckPointID; //It adds the ID of the newly created checkpoint
				CheckPointIds.put(id, newCheckpoint); //It puts the new checkpoint ID (id) and its corresponding CheckPointId object into the CheckPointIds map.
				Checkpoints[id] = id; //It stores the new checkpoint ID in the Checkpoints array at the index equal to the id.
				id++; 
				stageCheckpoints.put(stageId, existedcheckpoints); //It updates the stageCheckpoints map with the modified array of checkpoints for the specified stageId.
				
			
				return newId;
			}

	

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		stageCheckpoints.put(checkpointId, Checkpoints);

		
		stageCheckpoints.get(checkpointId);
		int [] removeCheckpoint = stageCheckpoints.get(checkpointId);
		for(Integer i: removeCheckpoint){
			if (removeCheckpoint[i] == checkpointId){
				removeCheckpoint[i] = -1;
			}

		}
		//if null:
		if(stageCheckpoints.get(checkpointId)== null){
			throw new IDNotRecognisedException("Race ID not recognized: " + checkpointId);
		}
		if (race.stream().noneMatch(raceDetails -> (int) raceDetails.get(0) == checkpointId)) {
			// If the race ID is not recognized, throw IDNotRecognisedException. 
			throw new IDNotRecognisedException("Race ID not recognized: " + checkpointId);
		}
		for (List<Object> stage : race) {
					int id = (int) stage.get(0);
					String state = (String) stage.get(1);
					if (id == checkpointId && state.equals("waiting for results")) {
						throw new InvalidStageStateException("Stage is in 'waiting for results' state");
					}
				}
		//remove the data from all the hashmaps
		stageCheckpoints.remove(checkpointId);
		stagecheckpoint.remove(checkpointId);
		stageCheckpoints.put(checkpointId, removeCheckpoint);
		StagePreparation.remove(checkpointId);
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		StagePreparation.remove(stageId);
		StagePreparation.put(stageId, "waiting for results");

		if (race.stream().noneMatch(StagePreparation -> (int) StagePreparation.get(0) == stageId)) {
			//If the race ID is not recognized, throw IDNotRecognisedException. 
			throw new IDNotRecognisedException("ID not recognized: " + stageId);
		}
		if(StagePreparation.get(stageId)== null){
			throw new IDNotRecognisedException("ID not recognized: " + stageId);
		}
		for (List<Object> stage : race) {
					int id = (int) stage.get(0);
					String state = (String) stage.get(1);
					if (id == stageId && state.equals("waiting for results")) {
						throw new InvalidStageStateException("Stage is in 'waiting for results' state");
					}
				}
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {

		if (race.stream().noneMatch(StagePreparation -> (int) StagePreparation.get(0) == stageId)) {
			//If the race ID is not recognized, throw IDNotRecognisedException. 
			throw new IDNotRecognisedException("ID not recognized: " + stageId);
		}
		if (!stageCheckpoints.containsKey(stageId)) {
			return new int[0];
		}
		int[] checkpoints = stageCheckpoints.get(stageId);

		List<Integer> validCheckpointList = new ArrayList<>(); //to store the valid checkpoints(just for this part)
		for (int checkpointId : checkpoints) {
			if (checkpointId != -1) { //to check if the checkpoint removed before
				validCheckpointList.add(checkpointId); //if it is valid add to the list
			}
		}
		int[] validCheckpoints = new int[validCheckpointList.size()]; //convert the list to the array
		for (int i = 0; i < validCheckpointList.size(); i++) {
			validCheckpoints[i] = validCheckpointList.get(i);
		}
		return validCheckpoints;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {

		//Checking for exceptions
		if (!name.getClass().equals(String.class)||name.equals(null)){
			throw new InvalidNameException("Name must be String!");
		};
		for (List<Object> teamDetails : teams) {
			String existingName = (String) teamDetails.get(1); // Name is stored at index 1
			if (existingName.equals(name)) {
				throw new IllegalNameException("Team already exists: " + name);
			}
		}

		//Adding teams to its arraylist and updating teamIDList
		theteamID++;
		teams.add(Arrays.asList(theteamID, name, description));
		int[] updatedteamIDsList = Arrays.copyOf(teamIDsList, theteamID);
		updatedteamIDsList[theteamID - 1] = theteamID;
		teamIDsList = updatedteamIDsList;
		return theteamID;
	}
	
	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		if (teams.stream().noneMatch(raceDetails -> (int) raceDetails.get(0) == teamId)) {
			//if not throw IDNotRecognisedException
			throw new IDNotRecognisedException("Race ID not recognized: " + teamId);
		}
		
		//Removing rider and therefore all results that rider has done and riders
		teams.removeIf(teams -> (int) teams.get(0) == teamId);

		teamIDsList = Arrays.stream(teamIDsList).filter(id -> id != teamId).toArray();

		for (List<Object> riderDetails : riders) {
			int existingID = (int) riderDetails.get(0); 
			if (existingID==teamId) {
				riders.remove(riderDetails);
			}
		}
	}

	@Override
	public int[] getTeams() {
		return teamIDsList; //Returnin IDList of teams
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {

		if (teams.stream().noneMatch(raceDetails -> (int) raceDetails.get(0) == teamId)) {
			//if not throw IDNotRecognisedException
			throw new IDNotRecognisedException("Race ID not recognized: " + teamId);
		}

		//Getting riders only of specific team
		int n=1;
		int[] getriderIDList= new int[n];
		for (List<Object> riderDetails : riders) {
			if((int)riderDetails.get(0)==teamId){
			int existingID = (int) riderDetails.get(1);
			n++;
			int[] updatedriderIDList = Arrays.copyOf(getriderIDList, n);
			updatedriderIDList[n - 1] = existingID;
			getriderIDList = updatedriderIDList;		
		}
	}
		return getriderIDList; //Returning riderIds of the teams riders
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {

		if (teams.stream().noneMatch(teamsDetails -> (int) teamsDetails.get(0) == teamID)) {
			//if not throw IDNotRecognisedException
			throw new IDNotRecognisedException("Race ID not recognized: " + teamID);
		}

		for (List<Object> riderDetails : teams) {
			String existingName = (String) riderDetails.get(1); 
			if (existingName.equals(name)) {
				//if not throw IllegalArgumentException
				throw new IllegalArgumentException("Rider already exists: " + name);
			}
		}
		//Adding rider to riders list and adding riderIDlist
		theriderid++;
		int[] updatedRiderIDsList = Arrays.copyOf(riderIDList, theriderid);
		updatedRiderIDsList[theriderid - 1] = theriderid;
		riderIDList = updatedRiderIDsList;

		riders.add(Arrays.asList(teamID,theriderid, name, yearOfBirth));
		return theriderid;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {

		if (riders.stream().noneMatch(raceDetails -> (int) raceDetails.get(1) == riderId)) {
			//if not throw IDNotRecognisedException
			throw new IDNotRecognisedException("Race ID not recognized: " + riderId);
		}

		//Removing riders therefore their results on a race
		riders.removeIf(riders -> (int) riders.get(1) == riderId);

		riderIDList = Arrays.stream(riderIDList).filter(id -> id != riderId).toArray();
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		
		//Making a list of what are we gonna add
		List<Object> addthat= new ArrayList<>(Arrays.asList(stageId,checkpoints));

		//Checking for exceptions
		for (List<Object> stage : race) {
					int id = (int) stage.get(0);
					String state = (String) stage.get(1);
					if (id == stageId && state.equals("waiting for results")) {
						throw new InvalidStageStateException("Stage is in 'waiting for results' state");
					}
				}

		
		
		if (riders.stream().noneMatch(raceDetails -> (int) raceDetails.get(1) == riderId)) {
			//if not throw IDNotRecognisedException
			throw new IDNotRecognisedException("Race ID not recognized: " + riderId);
		}

		if (riders.stream().noneMatch(raceDetails -> (int) raceDetails.get(1) == riderId)) {
			//if not throw IDNotRecognisedException
			throw new IDNotRecognisedException("Race ID not recognized: " + riderId);
		}

		for (List<Object> riderDetails : riders) {
			int existingID = (int) riderDetails.get(1); 
			if (existingID==riderId) {
				for (Object obj : riderDetails){
					if(obj instanceof List){
						List<Object> innerList = (List<Object>) obj;
						int stageInInnerList = (int) innerList.get(0);
						if(stageInInnerList==stageId){
							throw new DuplicatedResultException("Duplicated Result"+ addthat);
						}
					}
				}
			}
		}

		boolean riderFound = false;

		Iterator<List<Object>> iterator = riders.iterator();
while (iterator.hasNext()) {
    List<Object> tempList = iterator.next();
    int existingID = (int) tempList.get(1);
    if (existingID == riderId) {
        tempList.add(Arrays.asList(stageId, checkpoints));
        riderFound = true;
        break;
    }
}
if (!riderFound) {
    throw new IDNotRecognisedException("Rider ID not recognized" + riderId);
}
	}


  @Override
  public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException { 

		//Exception Checking stacks
	if (!stageLengths.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
	}
        
	if (riders.stream().noneMatch(raceDetails -> (int) raceDetails.get(1) == riderId)) {
			//if not throw IDNotRecognisedException
			throw new IDNotRecognisedException("Rider ID not recognized: " + riderId);
		}
		//Getting results from riders list about appropriate stage
	  for (List<Object> riderDetails : riders) {
		  int existingID = (int) riderDetails.get(1); 
		  if (existingID == riderId) {
			  for (Object obj : riderDetails) {
				  if (obj instanceof List) {
					  List<Object> innerList = (List<Object>) obj;
					  int stageInInnerList = (int) innerList.get(0);
					  if (stageInInnerList == stageId) {
						  List<LocalTime> riderResults = innerList.stream()
								  .filter(item -> item instanceof LocalTime)
								  .map(item -> (LocalTime) item)
								  .collect(Collectors.toList());
						  LocalTime[] end = riderResults.toArray(new LocalTime[riderResults.size()]);
						  return end;
					  }
				  }
			  }
		  }
	  }  
	  
	  // If the rider ID is not found, throw an exception
	  throw new IDNotRecognisedException("Rider ID not recognised: " + riderId);
  }
  

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {

		//Checking Exceptions stacks 
		if (!stageLengths.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
		}

	if (riders.stream().noneMatch(raceDetails -> (int) raceDetails.get(1) == riderId)) {
			//if not throw IDNotRecognisedException
			throw new IDNotRecognisedException("Race ID not recognized: " + riderId);
		}

		// Iterate over the riders to find their results in the specified stage
    for (List<Object> riderDetails : riders) {
        for (Object obj : riderDetails) {
            if (obj instanceof List) {
                List<Object> innerList = (List<Object>) obj;
                int stageInInnerList = (int) innerList.get(0);
                if (stageInInnerList == stageId) {
                    int currentRiderId = (int) riderDetails.get(1);
                    List<LocalTime> riderResults = innerList.stream()
                            .filter(item -> item instanceof LocalTime)
                            .map(item -> (LocalTime) item)
                            .collect(Collectors.toList());
                    LocalTime finalResult = riderResults.get(-1);
                    ridersTimesMap.put(currentRiderId, finalResult);
                }
            }
        }
    }

    // Sort the rider IDs and their corresponding times by the adjusted elapsed time
    List<Map.Entry<Integer, LocalTime>> elapsedSortedList = new ArrayList<>(ridersTimesMap.entrySet());
    elapsedSortedList.sort(Map.Entry.comparingByValue());

    // Adjust the sorted list based on the specified criteria
    for (int i = 1; i < elapsedSortedList.size(); i++) {
        // Get the LocalTime values for the current and previous entries
        LocalTime currentTime = elapsedSortedList.get(i).getValue();
        LocalTime previousTime = elapsedSortedList.get(i - 1).getValue();

        // Calculate the duration between the current and previous times
        long secondsBetween = Duration.between(previousTime, currentTime).getSeconds();

        // If the duration is less than 1 second, update the previous time to be the same as the current time
        if (secondsBetween < 1) {
            elapsedSortedList.get(i - 1).setValue(currentTime);
        }
    }

    // Find and return the adjusted elapsed time for the specified rider ID
    for (Map.Entry<Integer, LocalTime> entry : elapsedSortedList) {
        if (entry.getKey() == riderId) {
            return entry.getValue();
        }
    }


    // If the rider ID is not found in the adjusted elapsed times, return null
    return null;
	}
	

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {

		//Exception checking stacks
		if (!stageLengths.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
	}
        
	if (riders.stream().noneMatch(raceDetails -> (int) raceDetails.get(1) == riderId)) {
			//if not throw IDNotRecognisedException
			throw new IDNotRecognisedException("Race ID not recognized: " + riderId);
		}
		boolean riderFound = false;

    	for (List<Object> riderDetails : riders) {
        	int existingID = (int) riderDetails.get(1); // Rider ID is at index 1
        	if (existingID == riderId) {
            	riderFound = true;
            
            // Iterate over the rider's details and remove the results for the specified stage
            	Iterator<Object> iterator = riderDetails.iterator();
            	while (iterator.hasNext()) {
                	Object obj = iterator.next();
                	if (obj instanceof List) {
                    	List<Object> innerList = (List<Object>) obj;
                    	int stageInInnerList = (int) innerList.get(0); // Stage ID is at index 0
                    	if (stageInInnerList == stageId) {
                        	iterator.remove();
                        	break; // Exit the loop after removing the result for the specified stage
                    	}
                	}
            	}
            	break; // Exit the outer loop after finding the rider
        	}
    	}

    	// If the rider ID is not found, throw an exception
    	if (!riderFound) {
        	throw new IDNotRecognisedException("Rider ID not recognised: " + riderId);
    	}
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {

		//Exception Stack
		if (!stageLengths.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
	}

		//Getting final results of all riders and sort it
		for (List<Object> riderDetails : riders) {
			for(Object obj : riderDetails){ 
			if (obj instanceof List) {
				List<Object> innerList = (List<Object>) obj;
				int stageInInnerList = (int) innerList.get(0);
				if (stageInInnerList == stageId) {
					int thatriderid= (int) riderDetails.get(1);
					List<LocalTime> riderResults = innerList.stream()
							.filter(item -> item instanceof LocalTime)
							.map(item -> (LocalTime) item)
							.collect(Collectors.toList());
					LocalTime finalresult = riderResults.get(-1);
					ridersTimesMap.put(thatriderid,finalresult);
				}
			}	
			}
		}

		//Sorting
		List<Map.Entry<Integer, LocalTime>> sortedList = new ArrayList<>(ridersTimesMap.entrySet());
    	sortedList.sort(Map.Entry.comparingByValue());
		int[] sortedRiderIds = sortedList.stream().mapToInt(Map.Entry::getKey).toArray();
    
    	return sortedRiderIds;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		//Exception Stack
		if (!stageLengths.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
	}
		//Getting adjusted times add adding to hashmap
		int[] listofids = getRidersRankInStage(stageId);
		for(int ids :listofids){
			LocalTime timetoadd = getRiderAdjustedElapsedTimeInStage(stageId, ids);
			riderselapsedTimesMap.put(ids, timetoadd);
		}

		//Sorting for ranking
		List<Map.Entry<Integer, LocalTime>> allelapsedSortedList = new ArrayList<>(riderselapsedTimesMap.entrySet());
    	allelapsedSortedList.sort(Map.Entry.comparingByValue());
		LocalTime[] elapsedSortedRiderIds = allelapsedSortedList.stream()
            .map(Map.Entry::getValue)
            .toArray(LocalTime[]::new);

		return elapsedSortedRiderIds;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		//Exception Stack
		if (!stageLengths.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
		}

		//Getting stagetype
		for(List<Object>inner : stage){
			if((int)inner.get(1)==stageId){
				tip = (StageType) inner.get(6);
				break;
			}
		}

		//Pointing based on ranks (sorry for bad look)
		int[] ouridlist = getRidersRankInStage(stageId);
		switch (tip) {
			case FLAT:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							PointsMap.put(ourid,50);
							break;
						case 1:
							PointsMap.put(ourid,30);
							break;
						case 2:
							PointsMap.put(ourid,20);
							break;
						case 3:
							PointsMap.put(ourid,18);
							break;
						case 4:
							PointsMap.put(ourid,16);
							break;
						case 5:
							PointsMap.put(ourid,14);
							break;
						case 6:
							PointsMap.put(ourid,12);
							break;
						case 7:
							PointsMap.put(ourid,10);
							break;
						case 8:
							PointsMap.put(ourid,8);
							break;
						case 9:
							PointsMap.put(ourid,7);
							break;
						case 10:
							PointsMap.put(ourid,6);
							break;
						case 11:
							PointsMap.put(ourid,5);
							break;
						case 12:
							PointsMap.put(ourid,4);
							break;
						case 13:
							PointsMap.put(ourid,3);
							break;
						case 14:
							PointsMap.put(ourid,2);
							break;
						default:
							break;
					}
				}
				break;
			case MEDIUM_MOUNTAIN:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							PointsMap.put(ourid,30);
							break;
						case 1:
							PointsMap.put(ourid,25);
							break;
						case 2:
							PointsMap.put(ourid,22);
							break;
						case 3:
							PointsMap.put(ourid,19);
							break;
						case 4:
							PointsMap.put(ourid,17);
							break;
						case 5:
							PointsMap.put(ourid,15);
							break;
						case 6:
							PointsMap.put(ourid,13);
							break;
						case 7:
							PointsMap.put(ourid,11);
							break;
						case 8:
							PointsMap.put(ourid,9);
							break;
						case 9:
							PointsMap.put(ourid,7);
							break;
						case 10:
							PointsMap.put(ourid,6);
							break;
						case 11:
							PointsMap.put(ourid,5);
							break;
						case 12:
							PointsMap.put(ourid,4);
							break;
						case 13:
							PointsMap.put(ourid,3);
							break;
						case 14:
							PointsMap.put(ourid,2);
							break;
						default:
							break;
					}
				}
				break;
			case HIGH_MOUNTAIN:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							PointsMap.put(ourid,20);
							break;
						case 1:
							PointsMap.put(ourid,17);
							break;
						case 2:
							PointsMap.put(ourid,15);
							break;
						case 3:
							PointsMap.put(ourid,13);
							break;
						case 4:
							PointsMap.put(ourid,11);
							break;
						case 5:
							PointsMap.put(ourid,10);
							break;
						case 6:
							PointsMap.put(ourid,9);
							break;
						case 7:
							PointsMap.put(ourid,8);
							break;
						case 8:
							PointsMap.put(ourid,7);
							break;
						case 9:
							PointsMap.put(ourid,6);
							break;
						case 10:
							PointsMap.put(ourid,5);
							break;
						case 11:
							PointsMap.put(ourid,4);
							break;
						case 12:
							PointsMap.put(ourid,3);
							break;
						case 13:
							PointsMap.put(ourid,2);
							break;
						case 14:
							PointsMap.put(ourid,1);
							break;
						default:
							break;
					}
				}
				break;
			case TT:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							PointsMap.put(ourid,20);
							break;
						case 1:
							PointsMap.put(ourid,17);
							break;
						case 2:
							PointsMap.put(ourid,15);
							break;
						case 3:
							PointsMap.put(ourid,13);
							break;
						case 4:
							PointsMap.put(ourid,11);
							break;
						case 5:
							PointsMap.put(ourid,10);
							break;
						case 6:
							PointsMap.put(ourid,9);
							break;
						case 7:
							PointsMap.put(ourid,8);
							break;
						case 8:
							PointsMap.put(ourid,7);
							break;
						case 9:
							PointsMap.put(ourid,6);
							break;
						case 10:
							PointsMap.put(ourid,5);
							break;
						case 11:
							PointsMap.put(ourid,4);
							break;
						case 12:
							PointsMap.put(ourid,3);
							break;
						case 13:
							PointsMap.put(ourid,2);
							break;
						case 14:
							PointsMap.put(ourid,1);
							break;
						default:
							break;
					}
				}
				break;
		
			default:
				break;
		}
		
		//Getting checkpoint type for all pointing
		CheckPointId ctip = CheckPointIds.get(stageId);		

		//Pointing based on checkpoint type
		int ctype = 0;
		if (ctip.equals(CheckpointType.SPRINT)) {
			ctype =1;
		}else if(ctip.equals(CheckpointType.C4)){
			ctype = 2;
		}else if(ctip.equals(CheckpointType.C3)){
			ctype =3;
		}else if(ctip.equals(CheckpointType.C2)){
			ctype=4;
		}else if(ctip.equals(CheckpointType.C1)){
			ctype=5;
		}else if(ctip.equals(CheckpointType.HC)){
			ctype=6;
		}

		switch (ctype) {
			case 1:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							PointsMap.compute(ourid, (key, value) -> value + 20);
							break;
						case 1:
							PointsMap.compute(ourid, (key, value) -> value + 17);
							break;
						case 2:
							PointsMap.compute(ourid, (key, value) -> value + 15);
							break;
						case 3:
							PointsMap.compute(ourid, (key, value) -> value + 13);
							break;
						case 4:
							PointsMap.compute(ourid, (key, value) -> value + 11);
							break;
						case 5:
							PointsMap.compute(ourid, (key, value) -> value + 10);
							break;
						case 6:
							PointsMap.compute(ourid, (key, value) -> value + 9);
							break;
						case 7:
							PointsMap.compute(ourid, (key, value) -> value + 8);
							break;
						case 8:
							PointsMap.compute(ourid, (key, value) -> value + 7);
							break;
						case 9:
							PointsMap.compute(ourid, (key, value) -> value + 6);
							break;
						case 10:
							PointsMap.compute(ourid, (key, value) -> value + 5);
							break;
						case 11:
							PointsMap.compute(ourid, (key, value) -> value + 4);
							break;
						case 12:
							PointsMap.compute(ourid, (key, value) -> value + 3);
							break;
						case 13:
							PointsMap.compute(ourid, (key, value) -> value + 2);
							break;
						case 14:
							PointsMap.compute(ourid, (key, value) -> value + 1);
							break;
						default:
							break;
					}
				}
				
				break;
			case 2:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							PointsMap.compute(ourid, (key, value) -> value + 1);
							break;
						default:
							break;
					}
				}
				break;
			case 3:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							PointsMap.compute(ourid, (key, value) -> value + 2);
							break;
						case 1:
							PointsMap.compute(ourid, (key, value) -> value + 1);
							break;
						default:
							break;
					}
				}
				break;
			case 4:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							PointsMap.compute(ourid, (key, value) -> value + 5);
							break;
						case 1:
							PointsMap.compute(ourid, (key, value) -> value + 3);
							break;
						case 2:
							PointsMap.compute(ourid, (key, value) -> value + 2);
							break;
						case 3:
							PointsMap.compute(ourid, (key, value) -> value + 1);
							break;

					}
				}
				break;
					
			case 5:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							PointsMap.compute(ourid, (key, value) -> value + 10);
							break;
						case 1:
							PointsMap.compute(ourid, (key,value) -> value + 8);
							break;
						case 2:
							PointsMap.compute(ourid, (key, value) -> value + 6);
							break;
						case 3:
							PointsMap.compute(ourid, (key, value) -> value + 4);
							break;
						case 4:
							PointsMap.compute(ourid, (key, value) -> value + 2);
							break;
						case 5:
							PointsMap.compute(ourid, (key, value) -> value + 1);
							break;
					}
				}
				break;
					
				
			case 6:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							PointsMap.compute(ourid, (key, value) -> value + 20);
							break;
						case 1:
							PointsMap.compute(ourid, (key, value) -> value + 15);
							break;
						case 2:
							PointsMap.compute(ourid, (key, value) -> value + 12);
							break;
						case 3:
							PointsMap.compute(ourid, (key, value) -> value + 10);
							break;
						case 4:
							PointsMap.compute(ourid, (key, value) -> value + 8);
							break;
						case 5:
							PointsMap.compute(ourid, (key, value) -> value + 6);
							break;
						case 6:
							PointsMap.compute(ourid, (key, value) -> value + 4);
							break;
						case 7:
							PointsMap.compute(ourid, (key, value) -> value + 2);
							break;
					}
				}
				break;
			default:
				break;
		}
		

		//Returning riderspoints
		int[] SortedPoints = PointsMap.entrySet().stream()
            .mapToInt(Map.Entry::getValue)
            .toArray();
		return SortedPoints;
	}
	
	

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException { 
		//Exception Stack
		if (!stageLengths.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
	}
		// Compute the new value for the riderId key
		int[] ouridlist = getRidersRankInStage(stageId);

		//Getting checkpoint type for pointing
		CheckPointId ctip = CheckPointIds.get(stageId);		

		//Pointing based on checkpoint type
		int ctype = 0;
		if (ctip.equals(CheckpointType.SPRINT)) {
			ctype =1;
		}else if(ctip.equals(CheckpointType.C4)){
			ctype = 2;
		}else if(ctip.equals(CheckpointType.C3)){
			ctype =3;
		}else if(ctip.equals(CheckpointType.C2)){
			ctype=4;
		}else if(ctip.equals(CheckpointType.C1)){
			ctype=5;
		}else if(ctip.equals(CheckpointType.HC)){
			ctype=6;
		}
		

		switch (ctype) {
			case 1:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							MPointsMap.put(ourid,20);
							break;
						case 1:
							MPointsMap.put(ourid,17);
							break;
						case 2:
							MPointsMap.put(ourid,15);
							break;
						case 3:
							MPointsMap.put(ourid, 13);
							break;
						case 4:
							MPointsMap.put(ourid, 11);
							break;
						case 5:
							MPointsMap.put(ourid, 10);
							break;
						case 6:
							MPointsMap.put(ourid, 9);
							break;
						case 7:
							MPointsMap.put(ourid, 8);
							break;
						case 8:
							MPointsMap.put(ourid, 7);
							break;
						case 9:
							MPointsMap.put(ourid, 6);
							break;
						case 10:
							MPointsMap.put(ourid, 5);
							break;
						case 11:
							MPointsMap.put(ourid, 4);
							break;
						case 12:
							MPointsMap.put(ourid, 3);
							break;
						case 13:
							MPointsMap.put(ourid, 2);
							break;
						case 14:
							MPointsMap.put(ourid, 1);
							break;
						default:
							break;
					}
				}
				
				break;
			case 2:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							MPointsMap.put(ourid, 1);
							break;
						default:
							break;
					}
				}
				break;
			case 3:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							MPointsMap.put(ourid, 2);
							break;
						case 1:
							MPointsMap.put(ourid, 1);
							break;
						default:
							break;
					}
				}
				break;
			case 4:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							MPointsMap.put(ourid, 5);
							break;
						case 1:
							MPointsMap.put(ourid, 3);
							break;
						case 2:
							MPointsMap.put(ourid, 2);
							break;
						case 3:
							MPointsMap.put(ourid, 1);
							break;
					}
				}
				break;
			case 5:
				for(int j=0; j<ouridlist.length;j++){
					int ourid2 = ouridlist[j];
					switch (j) {
						case 0:
							MPointsMap.put(ourid2, 10);
							break;
						case 1:
							MPointsMap.put(ourid2, 8);
							break;
						case 2:
							MPointsMap.put(ourid2, 6);
							break;
						case 3:
							MPointsMap.put(ourid2, 4);
							break;
						case 4:
							MPointsMap.put(ourid2, 2);
							break;
						case 5:
							MPointsMap.put(ourid2, 1);
							break;
					}
				}
				break;
			case 6:
				for(int i=0; i<ouridlist.length;i++){
					int ourid = ouridlist[i];
					switch (i) {
						case 0:
							MPointsMap.put(ourid, 20);
							break;
						case 1:
							MPointsMap.put(ourid, 15);
							break;
						case 2:
							MPointsMap.put(ourid, 12);
							break;
						case 3:
							MPointsMap.put(ourid, 10);
							break;
						case 4:
							MPointsMap.put(ourid, 8);
							break;
						case 5:
							MPointsMap.put(ourid, 6);
							break;
						case 6:
							MPointsMap.put(ourid, 4);
							break;
						case 7:
							MPointsMap.put(ourid, 2);
							break;
					}
				}
				break;

			default:
				break;
		}
		//Returning riders points
		int[] MSortedPoints = PointsMap.entrySet().stream()
            .mapToInt(Map.Entry::getValue)
            .toArray();
		return MSortedPoints;
	}

	@Override
	public void eraseCyclingPortal() {
		//Deleting Variables
		teams.clear();
		race.clear();
		stage.clear();
		riders.clear();
		checkpointList.clear();
		raceIDsList = new int[0];
		id=0;
		theraceID =0;
		teamIDsList = new int[0];
		CheckPointID =0;
		races.clear();
		rankedRiderIds= new int[0];
		CheckPointIDs = new int[0];
		raceStageId = 0;
		raceStageIdList = new int[0];
		raceStageIdArray = new int[0];
		theriderid = 0;
		riderIDList = new int[0];
		raceIndex=0;
		stageLengths.clear();
		stageNameToId.clear();
		stageNames.clear(); 
		raceStages.clear();
		stageIdToName.clear();
		stageRaces.clear();
		CheckPointIds.clear();
		CheckPointIdName.clear();
		stageCheckpoints.clear();
		CheckpointStage.clear();
		stagecheckpoint.clear();
		StagePreparation.clear();
		RaceIdNames.clear();
		stageIDtolenght.clear();
		riderPoints.clear();
		stageTimes.clear();
		ridersTimesMap.clear();
		riderselapsedTimesMap.clear();
		PointsMap.clear();
		MPointsMap.clear();
		tip = null;
		ridersTotalElapsedTimes.clear();
		stageAdjustedTimes.clear();
		stagePoints.clear();
		stageMountainPoints.clear();
		riderResults.clear();
		stageRiderElapsed.clear();
		stageCheckpointIds.clear();
		stageRiders.clear();
		riderId.clear();
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		//Saving Variables
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
			outputStream.writeObject(this);
		} catch (IOException e) {
			// If an IOException occurs, throw it to indicate failure
			throw new IOException(e);
		}
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		
	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {

		int[]thatstages =null;
		int thatraceID =0;

		for (int i = 0; i < race.size(); i++) {
        List<Object> raceDetails = race.get(i);
        String raceName = (String) raceDetails.get(1);
        if (raceName.equals(name)) {
			thatraceID = (int) raceDetails.get(0);
            raceIndex = i-1;
            break;
        	}
    	}

		race.removeIf(race -> (String) race.get(1) == name);

		raceIDsList = Arrays.stream(raceIDsList).filter(id -> id != raceIndex).toArray();

		try {
			thatstages = getRaceStages(thatraceID);
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}
		
		if (thatstages != null) {
			for (int stageId : thatstages) {
				for (List<Object> riderDetails : riders) {
					Iterator<Object> iterator = riderDetails.iterator();
					while (iterator.hasNext()) {
						Object obj = iterator.next();
						if (obj instanceof List) {
							List<Object> innerList = (List<Object>) obj;
							int stageInInnerList = (int) innerList.get(0);
							if (stageInInnerList == stageId) {
								iterator.remove(); // Remove rider results associated with the deleted race
								break; // Exit the loop after removing the result for the specified stage
							}
						}
					}
				}
			}
		}
	}
		
	@Override
    public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
			if (!raceStages.containsKey(raceId)) {
				throw new IDNotRecognisedException("ID not recognized: " + raceId);
			}
		
			Map<Integer, LocalTime> ridersTotalElapsedTimes = new HashMap<>();
		
			// Iterate over each rider
			for (List<Object> riderDetails : riders) {
				int riderId = (int) riderDetails.get(1);
				LocalTime totalElapsed = LocalTime.MIN;
		
				// Iterate over each stage to calculate the total adjusted elapsed time for the rider
				for (int stageId : raceStages.get(raceId)) {
					LocalTime adjustedElapsedTime = getRiderAdjustedElapsedTimeInStage(stageId, riderId);
					if (adjustedElapsedTime != null) {
						totalElapsed = totalElapsed.plus(Duration.between(LocalTime.MIN, adjustedElapsedTime));
					}
				}
		
				ridersTotalElapsedTimes.put(riderId, totalElapsed);
			}
		
			//Convert the total elapsed times to an array of LocalTime
			List<LocalTime> timesList = new ArrayList<>(ridersTotalElapsedTimes.values());
		
			//Print the list to debug
			System.out.println("Times List: " + timesList);
		
			return timesList.toArray(new LocalTime[0]);
		}

    @Override
    public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		//checks if the provided raceId exists in the list of races (race). If not, it throws an IDNotRecognisedException.
        if (race.stream().noneMatch(raceDetails -> (int) raceDetails.get(0) == raceId)) {
            throw new IDNotRecognisedException("ID not recognized: " + raceId);
        }

        HashMap<Integer, Integer> ridersTotalPoints = new HashMap<>(); //ridersTotalPoints to store the total points earned by each rider.
        for (List<Object> riderDetails : riders) { //It iterates over each rider (riderDetails) in the list of riders.
            int riderId = (int) riderDetails.get(1); //For each rider, it retrieves the rider's ID (riderId) from the second element of riderDetails.
            int totalPoints = 0;
            for (int stageId : stagePoints.getOrDefault(raceId, new HashMap<>()).keySet()) {
                Integer points = stagePoints.get(raceId).get(riderId);
                if (points != null) {
                    totalPoints += points;
                }
            }
            ridersTotalPoints.put(riderId, totalPoints); //After processing all stages for the rider, it stores the riderId and totalPoints in the ridersTotalPoints map.

        }

        List<Integer> sortedRiderIds = new ArrayList<>(ridersTotalPoints.keySet()); //After processing all riders, the method creates a list of rider IDs (sortedRiderIds) from the keys of the ridersTotalPoints map.
        sortedRiderIds.sort(Comparator.comparingInt(ridersTotalPoints::get)); //It sorts the sortedRiderIds list based on the total points earned by each rider, using a comparator that compares the points.

        int[] result = new int[sortedRiderIds.size()]; //int array named result with the size equal to the number of riders (sortedRiderIds.size()).
        for (int i = 0; i < sortedRiderIds.size(); i++) { //iterates over the sorted rider IDs and assigns each rider ID to the corresponding index in the result array.
            result[i] = sortedRiderIds.get(i);
        }

        return result; //Finally, the method returns the result array containing the rider IDs sorted by their total points earned in the race.
    }

    @Override
    public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
        if (race.stream().noneMatch(raceDetails -> (int) raceDetails.get(0) == raceId)) { //ID valid?
            throw new IDNotRecognisedException("ID not recognized: " + raceId); // if the provided raceId exists in the list of races (race). If not, it throws an IDNotRecognisedException.
        }

        HashMap<Integer, Integer> ridersTotalMountainPoints = new HashMap<>(); //The method initializes a HashMap<Integer, Integer> named ridersTotalMountainPoints to store the total mountain points earned by each rider.
        for (List<Object> riderDetails : riders) {
            int riderId = (int) riderDetails.get(1); //It iterates over the stages (stageId) of the race (raceId) and retrieves the mountain points earned by the rider in each stage from the stageMountainPoints map.
            int totalMountainPoints = 0; 
            for (int stageId : stageMountainPoints.getOrDefault(raceId, new HashMap<>()).keySet()) { //It iterates over each rider (riderDetails) in the list of riders.
                Integer points = stageMountainPoints.get(raceId).get(riderId); //For each rider, it retrieves the rider's ID (riderId) from the second element of riderDetails.

                if (points != null) { 
                    totalMountainPoints += points;
                }
            }
            ridersTotalMountainPoints.put(riderId, totalMountainPoints); //After processing all stages for the rider, it stores the riderId and totalMountainPoints in the ridersTotalMountainPoints map.
        }

        List<Integer> sortedRiderIds = new ArrayList<>(ridersTotalMountainPoints.keySet()); //After processing all riders, the method creates a list of rider IDs (sortedRiderIds) from the keys of the ridersTotalMountainPoints map.
        sortedRiderIds.sort(Comparator.comparingInt(ridersTotalMountainPoints::get)); //It sorts the sortedRiderIds list based on the total mountain points earned by each rider, using a comparator that compares the points.

        int[] result = new int[sortedRiderIds.size()];  //The method initializes an int array named result with the size equal to the number of riders (sortedRiderIds.size()).
        for (int i = 0; i < sortedRiderIds.size(); i++) { //It iterates over the sorted rider IDs and assigns each rider ID to the corresponding index in the result array.
            result[i] = sortedRiderIds.get(i);
        }

        return result; //Finally, the method returns the result array containing the rider IDs sorted by their total mountain points earned in the race.
    }

    @Override
	//ID Valid?
    public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
        if (!raceStages.containsKey(raceId)) {
			throw new IDNotRecognisedException("ID not recognized: " + raceId);
		}
	
		Map<Integer, Long> ridersTotalElapsedTimes = new HashMap<>(); //store the relevant data 
	
		// Iterate over each rider
		for (List<Object> riderDetails : riders) {  
			// Get the rider ID from the second element of riderDetails list
			int riderId = (int) riderDetails.get(1);
			long totalElapsedSeconds = 0;
	
			// Iterate over each stage to calculate the total adjusted elapsed time for the rider
			for (int stageId : raceStages.get(raceId)) {
				LocalTime adjustedElapsedTime = getRiderAdjustedElapsedTimeInStage(stageId, riderId);
				if (adjustedElapsedTime != null) {
					long elapsedTimeInSeconds = Duration.between(LocalTime.MIN, adjustedElapsedTime).getSeconds();
					totalElapsedSeconds += elapsedTimeInSeconds;
				}
			}
	
			ridersTotalElapsedTimes.put(riderId, totalElapsedSeconds); //put the data to store it after itireting 
		}
	
		// Sort the riders by their total elapsed times
		List<Integer> sortedRiderIds = new ArrayList<>(ridersTotalElapsedTimes.keySet());
		sortedRiderIds.sort(Comparator.comparingLong(ridersTotalElapsedTimes::get));
	
		// Convert the sorted rider IDs to an array
		int[] result = new int[sortedRiderIds.size()];
		for (int i = 0; i < sortedRiderIds.size(); i++) {
			result[i] = sortedRiderIds.get(i);
		}
	
		return result;
	}

    @Override
    public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
        if (!raceStages.containsKey(raceId)) { //ıd exists?
            throw new IDNotRecognisedException("ID not recognized: " + raceId); //It checks if the provided raceId exists in the raceStages map. If not, it throws an IDNotRecognisedException.
        }

        Map<Integer, Integer> riderPoints = new HashMap<>();  //to store the total points earned by each rider.

        for (int stageId : raceStages.get(raceId)) { //It iterates over each stage (stageId) of the race identified by raceId.
            int[] checkpoints = stageCheckpoints.getOrDefault(stageId, new int[0]); //For each stage, it retrieves the checkpoints (checkpoints) from the stageCheckpoints map for that stage.
            if (checkpoints == null || checkpoints.length == 0) {  //f there are no checkpoints or if the checkpoints array is empty, it skips to the next stage.
                continue;
            }

            for (int checkpointId : checkpoints) { //It iterates over each rider and updates their points in the riderPoints map. If the rider's ID already exists in the map, it increments their points by 1; otherwise, it initializes their points to 1.
                int[] riders = stagecheckpoint.getOrDefault(checkpointId, new int[0]);
                for (int riderId : riders) {
                    riderPoints.put(riderId, riderPoints.getOrDefault(riderId, 0) + 1);
                }
            }
        }

        List<Map.Entry<Integer, Integer>> sortedRiders = new ArrayList<>(riderPoints.entrySet());
		//sorts the list of entries based on the values (points) in descending order using a comparator.
        sortedRiders.sort(Map.Entry.<Integer, Integer>comparingByValue().reversed());

        int[] result =new int[sortedRiders.size()]; //prepare the result array
        for (int i = 0; i < sortedRiders.size(); i++) {
            result[i] = sortedRiders.get(i).getKey();
        }

        return result; //Finally, the method returns the result array containing the rider IDs sorted by their total points earned in the race, in descending order.
    }

    @Override
    public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		//same method
        if (!raceStages.containsKey(raceId)) {
            throw new IDNotRecognisedException("ID not recognized: " + raceId);
        }

        riderPoints.clear(); //Before processing, the method clears any existing data in the riderPoints map to ensure that it starts with a clean slate for the current race.

        for (int stageId : raceStages.get(raceId)) { //he method iterates over each stage (stageId) of the race identified by raceId.
            if (!stageCheckpoints.containsKey(stageId)) { //For each stage, it checks if there are any checkpoints associated with that stage. If not, it skips to the next stage.
                continue;
            }
            int[] checkpoints = stageCheckpoints.get(stageId);

            for (int checkpointId : checkpoints) { //If the rider's ID already exists in the map, it increments their points by 1; otherwise, it initializes their points to 1.
                int[] riders = stagecheckpoint.getOrDefault(checkpointId, new int[0]);
                for (int riderId : riders) {
                    riderPoints.put(riderId, riderPoints.getOrDefault(riderId, 0) + 1);
                }
            }
        }

        List<Map.Entry<Integer, Integer>> sortedRiders = new ArrayList<>(riderPoints.entrySet());
		//It sorts the list of entries based on the values (mountain points) in descending order using a comparator.
        sortedRiders.sort(Map.Entry.<Integer, Integer>comparingByValue().reversed());

        int[] result = new int[sortedRiders.size()];
        for (int i = 0; i < sortedRiders.size(); i++) {
            result[i] = sortedRiders.get(i).getKey();
        }

        return result; //Finally, the method returns the result array containing the rider IDs sorted by their total mountain points earned in the race, in descending order.
    }
}