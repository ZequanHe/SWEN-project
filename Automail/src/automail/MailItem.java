package automail;

import java.util.Map;
import java.util.TreeMap;

// import java.util.UUID;

/**
 * Represents a mail item
 */
public class MailItem {

    /** Represents the destination floor to which the mail is intended to go */
    protected final int destination_floor;
    private int starting_floor;

    private double activity;
    private double totalPrice;
    private double serviceFee;
    private double cost;
    /** The mail identifier */
    protected final String id;
    /** The time the mail item arrived */
    protected final int arrival_time;
    /** The weight in grams of the mail item */
    protected final int weight;

    /**
     * Constructor for a MailItem
     * @param dest_floor the destination floor intended for this mail item
     * @param arrival_time the time that the mail arrived
     * @param weight the weight of this mail item
     */
    public MailItem(int dest_floor, int arrival_time, int weight){
        this.destination_floor = dest_floor;
        this.id = String.valueOf(hashCode());
        this.arrival_time = arrival_time;
        this.weight = weight;
    }

    @Override
    public String toString(){
        return String.format("Mail Item:: ID: %6s | Arrival: %4d | Destination: %2d | Weight: %4d",
                id, arrival_time, destination_floor, weight);
    }

    /**
     *
     * @return print the cost information about a mailitem
     */
    public String price_string(){
        return String.format( "| Charge: %.2f| Cost: %.2f | Fee: %.2f | Activity : %.2f", totalPrice,cost,serviceFee,activity);
    }

    /**
     * constructor a cost information for a Mailitem
     * @param activ the total activity unit for this mail item
     * @param total_price the total cost for this mail item with mark up
     * @param service the service fee fot this mail tiem
     * @param cost the total cost of the activity cost and service fee without mark up
     */
    public void sign(double activ, double total_price, double service, double cost){
        this.activity = activ;
        this.totalPrice = total_price;
        this.serviceFee = service;
        this.cost = cost;
    }


    /**
     *
     * @return the destination floor of the mail item
     */
    public int getDestFloor() {
        return destination_floor;
    }

    /**
     *
     * @return the current floor of the mail item
     */
    public int getStartingFloor(){ return starting_floor; }

    /**
     *
     * @param floor becomes the new current_floor value
     */
    public void updateStartingFloor(int floor){
        this.starting_floor = floor;
    }

    /**
     *
     * @return the ID of the mail item
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return the arrival time of the mail item
     */
    public int getArrivalTime(){
        return arrival_time;
    }

    /**
     *
     * @return the weight of the mail item
     */
    public int getWeight(){
        return weight;
    }

    static private int count = 0;
    static private Map<Integer, Integer> hashMap = new TreeMap<Integer, Integer>();

    @Override
    public int hashCode() {
        Integer hash0 = super.hashCode();
        Integer hash = hashMap.get(hash0);
        if (hash == null) { hash = count++; hashMap.put(hash0, hash); }
        return hash;
    }
}
