package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;

public class price_cal {

    public double activity_unit;
    public double service_fee;
    public MailItem mailitem;
    public WifiModem floor;
    public double activity_cost;
    public price_cal(double activity_unit, double service_fee, MailItem mailitem) {
        this.activity_unit = activity_unit;
        this.service_fee = service_fee;
        this.mailitem = mailitem;
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

    public double Totalprice(MailItem mailitem){
        return 0 ;
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
