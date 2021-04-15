package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;

public class price_cal {

    public double activity_unit;
    public double service_fee;
    public MailItem mailitem;
    public WifiModem floor;
    public double activity_cost;
    public double predict_cost;
    public double count_up;
    public price_cal(double activity_unit, double service_fee, MailItem mailitem, double count_up) {
        this.activity_unit = activity_unit;
        this.service_fee = service_fee;
        this.mailitem = mailitem;
        this.predict_cost = 0;
        this.count_up = count_up;
    }

    public void setPredict_cost(double predict_cost) {
        this.predict_cost = predict_cost;
    }

    public double getCount_up() {
        return count_up;
    }

    public void setActivity_unit(double activity_unit) {
        this.activity_unit = activity_unit;
    }

    public void setService_fee(double service_fee) {
        this.service_fee = service_fee;
    }

    public double getActivity_unit() {
        return activity_unit;
    }

    public double getService_fee() {
        return service_fee;
    }


    public MailItem getMailitem() {
        return mailitem;
    }

    public double Totalprice(){
        return ((activity_cost() + getService_fee()) * getCount_up()) ;
    }

    public double activity_cost(){
        activity_cost = getActivity_unit() *0.224;
        return activity_cost;
    }

    public double getfloor(){
        return mailitem.destination_floor;
    }

    public double getfee(MailItem mailitem) throws Exception {
        floor = WifiModem.getInstance(getMailitem().destination_floor);
        return floor.forwardCallToAPI_LookupPrice(getMailitem().destination_floor);
    }
}
