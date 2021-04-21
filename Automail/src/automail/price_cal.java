package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;

import static java.lang.Math.abs;

public class price_cal {

    private int lookupTry;
    private int lookupPass;
    private int lookupFail;
    private double totalActivity;
    private double totalActivityCost;
    private double totalServiceCost;
    private int totalDelivered;
    protected final double FLOORMOVEACTIVITY = 5;
    protected final double LOOKUPACTIVITY = 0.1;
    private double markup;
    public double activity_cost;
    public double predict_cost;
    private double ppActivity;
    public price_cal(double ppActivity, double markup) {
        this.ppActivity = ppActivity; //<----   Activity unit price
        this.predict_cost = 0;
        this.markup = markup;
        this.totalActivity = 0;
        this.totalActivityCost = 0;
        this.totalServiceCost = 0;
        this.totalDelivered = 0;
    }


    public double getMarkup() {
        return markup;
    }


    public double cal_predict(MailItem mailitem) throws Exception {
        int lookup = 0;
        WifiModem floor = WifiModem.getInstance(getfloor(mailitem));
        //Initialise lookup price to be negative
        double lookupPrice = -1;
        while (lookupPrice < 0){
            lookupPrice = floor.forwardCallToAPI_LookupPrice(getfloor(mailitem));
            lookup++;
        }
        double activity = calc_activity(mailitem, lookup);
        double activityCost = activity_cost(activity);
        double Cost = activityCost + lookupPrice;
        double totalCost = Cost*(1+markup);
        return totalCost;
    }

    /**
     *
     * @return activity based on the acvitiy from floor changes and lookups
     */

    public double calc_activity(MailItem mailitem, int lookups){
        double lookupCost = lookups * LOOKUPACTIVITY;
        double movementCost = deltaFloor(mailitem) * FLOORMOVEACTIVITY;

        return (lookupCost + movementCost);
    }
    /**
     *
     * @return activity price
     */
    public double activity_cost(double activity){

        activity_cost = activity * ppActivity;
        return activity_cost;
    }
    /**
     *
     * @return the starting floor of the mail item
     */
    private int mailStartingFloor(MailItem mailitem){
        return mailitem.getStartingFloor();
    }

    /**
     *
     * @return the destination floor of the mail item
     */
    private int getfloor(MailItem mailitem){
        return mailitem.getDestFloor();
    }
    /**
     *
     * @return the difference of floors of the mail item
     */
    private int deltaFloor(MailItem mailitem){
        int starting = mailStartingFloor(mailitem);
        int destination = getfloor(mailitem);
        // make sure the result always positive
        return abs(starting - destination);
    }

    /**
     *
     * @param mailitem the mailitem information
     * @throws Exception for the lookup service fee
     */
    public void signMailPrice(MailItem mailitem) throws Exception {

        int lookup = 0;
        WifiModem floor = WifiModem.getInstance(getfloor(mailitem));
        //Initialise lookup price to be negative
        double lookupPrice = -1;
        while (lookupPrice < 0){
            lookupPrice = floor.forwardCallToAPI_LookupPrice(getfloor(mailitem));
            lookup++;
        }
        lookupTry += lookup;
        lookupFail += lookup - 1;
        lookupPass += 1;

        double activity = calc_activity(mailitem, lookup);
        double activityCost = activity_cost(activity);
        double Cost = activityCost + lookupPrice;
        double totalCost = Cost*(1+markup);
        mailitem.sign(activity, totalCost, lookupPrice, Cost);
        this.totalDelivered  += 1;
        this.totalServiceCost += lookupPrice;
        this.totalActivity += activity;
        this.totalActivityCost += activityCost;

    }

    /**
     *
     * @return total number of mailitems are delivered
     */
    public int getTotalDelivered(){return totalDelivered;}

    /**
     *
     * @return sum of service fee for all mailitem
     */
    public double getTotalServiceCost(){return totalServiceCost;}

    /**
     *
     * @return the sum of activity unit for all mailitem
     */

    public double getTotalActivity(){return totalActivity;}

    /**
     *
     * @return sum of activity cost for all mailitem
     */
    public double getTotalActivityCost(){return totalActivityCost;}

    /**
     *
     * @return the number of time to lookup the service fee
     */
    public int getLookupTry(){return lookupTry;}

    /**
     *
     * @return the number of time to lookup the service fee success
     */
    public int getLookupPass(){return lookupPass;}

    /**
     *
     * @return the number of time to lookup the service fee fail
     */
    public int getLookupFail(){return lookupFail;}
}
