package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;

import static java.lang.Math.abs;

public class price_cal {

    //public double activity_unit;
    protected final double FLOORMOVEACTIVITY = 5;
    protected final double LOOKUPACTIVITY = 0.1;
    private double markup;
    //public double service_fee;
    public MailItem mailitem;
    public WifiModem floor;
    public double activity_cost;
    public double predict_cost;
    private double ppActivity;
    public price_cal(double ppActivity, double markup) {
        //this.activity_unit = activity_unit;
        this.ppActivity = ppActivity; //<----   Activity unit price
        //this.service_fee = service_fee;
        //this.mailitem = mailitem;
        this.predict_cost = 0;
        this.markup = markup;
    }

  //  public void setPredict_cost(double predict_cost) {
   //     this.predict_cost = predict_cost;
  //  }


 //   public void setActivity_unit(double activity_unit) {
  //      this.activity_unit = activity_unit;
  //  }

    public double getMarkup() {
        return markup;
    }

    /*
    public void setService_fee(double service_fee) {
        this.service_fee = service_fee;
    }

    public double getActivity_unit() {
        return activity_unit;
    }

    public double getService_fee() {
        return service_fee;
    }
 */
/*
    public MailItem getMailitem() {
        return mailitem;
    }
*/

/*    public double Totalprice(){
        return ((activity_cost() + getService_fee()) * getCount_up()) ;
    }
*/

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
 //   public double activity_cost(double activity) {
 //       activity_cost = activity * 0.224;
 //   }

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


        return abs(starting - destination);
    }

/*    public double getfee(MailItem mailitem) throws Exception {

        floor = WifiModem.getInstance(getMailitem().destination_floor);
        return floor.forwardCallToAPI_LookupPrice(getMailitem().destination_floor);
    }
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

        double activity = calc_activity(mailitem, lookup);
        double activityCost = activity_cost(activity);
        double Cost = activityCost + lookupPrice;
        double totalCost = Cost*(1+markup);
        mailitem.sign(activity, totalCost, lookupPrice, Cost);

    }
}
