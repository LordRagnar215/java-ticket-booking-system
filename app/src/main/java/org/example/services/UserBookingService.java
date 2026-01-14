package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Optional;

public class UserBookingService
{
    private User user;
    private List<User> userList;
    private static final String USERS_PATH = "E:/IRCTC/app/src/main/java/org/example/localDb/users.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService() throws IOException {
        this.userList = loadUsers();     // FIXED
    }

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        this.userList = loadUsers();     // FIXED
    }
    public List<User> loadUsers() throws IOException {
        File users = new File(USERS_PATH);

        if (!users.exists()) {
            this.userList = new ArrayList<>();
            return userList;
        }

        this.userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
        return userList;
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }
        catch(IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException{
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }
    public void fetchBooking(){
        user.printTickets();
    }
    public Boolean cancelBooking(String ticketId){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter ticketId");
        ticketId = s.next();

        if (ticketId == null || ticketId.isEmpty()){
            System.out.println("ticketId cannot be null");
            return Boolean.FALSE;
        }

        String finalTicketId1 = ticketId;  //Because strings are immutable
        boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));

        String finalTicketId = ticketId;
        user.getTicketsBooked().removeIf(Ticket -> Ticket.getTicketId().equals(finalTicketId));
        if (removed) {
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return Boolean.TRUE;
        }else{
            System.out.println("No ticket found with ID " + ticketId);
            return Boolean.FALSE;
        }

    }

    public List<Train> getTrains(String source, String destination){
        try{
            System.out.println("inside get trains");
            TrainService trainService = new TrainService();
            System.out.println("inside get trains");
            return trainService.searchTrains(source,destination);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int seat){
        try{
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if(row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()){
                if(seats.get(row).get(seat) == 0){
                    seats.get(row).set(seat,1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    return true;
                }
                else{
                    return false; // seat is already booked
                }

            }
            else{
                return false; //out of bound
            }
        } catch (IOException e) {
            return Boolean.FALSE;
        }
    }
}
