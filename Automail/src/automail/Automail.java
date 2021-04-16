package automail;

import simulation.IMailDelivery;

public class Automail {

    public Price_cal priceFinder;
    public Robot[] robots;
    public MailPool mailPool;
    
    public Automail(MailPool mailPool, IMailDelivery delivery, int numRobots) {  	
    	/** Initialize the MailPool */
    	
    	this.mailPool = mailPool;

    	this.priceFinder = priceFinder;
    	
    	/** Initialize robots */
    	robots = new Robot[numRobots];
    	for (int i = 0; i < numRobots; i++) robots[i] = new Robot(delivery, mailPool, i);
    }
    
}
