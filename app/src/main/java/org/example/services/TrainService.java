package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;
import org.example.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService
{
    private List<Train> trainList;
    private static final String TRAINS_PATH = "E:/IRCTC/app/src/main/java/org/example/localDb/trains.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public TrainService() throws IOException {
        File file = new File(TRAINS_PATH);
        trainList = objectMapper.readValue(file,new TypeReference<List<Train>>() {});
    }

    public List<Train> searchTrains(String source, String destination) {
        System.out.println("Inside train");
        final String srcLower = source.trim().toLowerCase();
        final String destLower = destination.trim().toLowerCase();

        System.out.println("Searching trains from: '" + srcLower + "' to: '" + destLower + "'");

        return trainList.stream()
                .filter(train -> {
                    List<String> stations = train.getStations();
                    if (stations == null || stations.isEmpty()) {
                        System.out.println("Train " + train.getTrainId() + " has no stations.");
                        return false;
                    }

                    // Convert station names to lowercase
                    List<String> lowerStations = stations.stream()
                            .map(s -> s.trim().toLowerCase())
                            .collect(Collectors.toList());

                    System.out.println("Train " + train.getTrainId() + " stations: " + lowerStations);

                    int srcIndex = lowerStations.indexOf(srcLower);
                    int destIndex = lowerStations.indexOf(destLower);

                    boolean found = srcIndex != -1 && destIndex != -1 && srcIndex < destIndex;
                    if (!found) {
                        System.out.println("Train " + train.getTrainId() + " does not match criteria.");
                    }
                    return found;
                })
                .collect(Collectors.toList());
    }



    public void addTrain(Train newTrain){
        Optional<Train> existingTrain = trainList.stream().filter(train -> train.getTrainId().equalsIgnoreCase(newTrain.getTrainId())).findFirst();

        if(existingTrain.isPresent()){
            updateTrain(newTrain);
        }
        else{
            trainList.add(newTrain);
            saveTrainListToFile();
        }
    }

    private void saveTrainListToFile() {
        try{
            objectMapper.writeValue(new File(TRAINS_PATH),trainList);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void updateTrain(Train updatedTrain) {
        OptionalInt index = IntStream.range(0,trainList.size()).filter( i -> trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId())).findFirst();

        if(index.isPresent()){
            trainList.set(index.getAsInt(),updatedTrain);
            saveTrainListToFile();
        }
        else{
            addTrain(updatedTrain);
        }
    }


}
