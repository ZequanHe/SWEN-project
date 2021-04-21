package automail;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.ListIterator;

import exceptions.ItemTooHeavyException;

/**
 * addToPool is called when there are mail items newly arrived at the building to add to the MailPool or
 * if a robot returns with some undelivered items - these are added back to the MailPool.
 * The data structure and algorithms used in the MailPool is your choice.
 * 
 */
public class MailPool {

	private class Item {
		int destination;
		MailItem mailItem;
		// Use stable sort to keep arrival time relative positions
		
		public Item(MailItem mailItem) {
			destination = mailItem.getDestFloor();
			this.mailItem = mailItem;
		}
	}
	
	public class ItemComparator implements Comparator<Item> {
		@Override
		public int compare(Item i1, Item i2) {
			int order = 0;
			if (i1.destination < i2.destination) {
				order = 1;
			} else if (i1.destination > i2.destination) {
				order = -1;
			}
			return order;
		}
	}

	private LinkedList<Item> expressPool;
	private LinkedList<Item> pool;
	private LinkedList<Robot> robots;
	private price_cal calculator;
	private double ExpressPrice;

	public MailPool(int nrobots, price_cal priceCalculator, double ExpressPrice){
		// Start empty
		pool = new LinkedList<Item>();
		robots = new LinkedList<Robot>();
		expressPool = new LinkedList<Item>();
		this.calculator = priceCalculator;
		this.ExpressPrice = ExpressPrice;
	}



	/**
	 * Adds an item to the correct mail pool based on its price
	 * @param mailItem the mail item being added.
	 */
	public void addPoolAction(MailItem mailItem) throws Exception {
		//This calls the correct method to put the mail item in the right mailpool based on its price
		//calculated via the Price_cal class.

		double mailFee = calculator.cal_predict(mailItem);
		if (mailFee >= ExpressPrice){
			addToExpressPool(mailItem);
		}else{
			addToPool(mailItem);

		}

	}
	/**
	 * Adds an item to the mail pool
	 * @param mailItem the mail item being added.
	 */
	public void addToPool(MailItem mailItem) {
		Item item = new Item(mailItem);
		pool.add(item);
		pool.sort(new ItemComparator());
	}

	/**
	 * Adds an item to the Express mail pool
	 * @param mailItem the mail item being added.
	 */
	public void addToExpressPool(MailItem mailItem) {
		Item item = new Item(mailItem);
		expressPool.add(item);
		expressPool.sort(new ItemComparator());
	}
	
	
	
	/**
     * load up any waiting robots with mailItems, if any.
     */
	public void loadItemsToRobot() throws ItemTooHeavyException {
		//List available robots
		ListIterator<Robot> i = robots.listIterator();
		while (i.hasNext()) loadItem(i);
	}
	
	//load items to the robot
	private void loadItem(ListIterator<Robot> i) throws ItemTooHeavyException {
		Robot robot = i.next();
		assert(robot.isEmpty());
		//System.out.printf("P1: %3d%n", pool.size());
		//System.out.printf("P2: %3d%n", expressPool.size());
		if (expressPool.size() > 0) {
			ListIterator<Item> j = expressPool.listIterator();
			try {
				robot.addToHand(j.next().mailItem); // hand first as we want higher priority delivered first
				j.remove();
				robot.updateMailStartFloor(1);
				if (expressPool.size() > 0) {
					robot.addToTube(j.next().mailItem);
					j.remove();
				}
				robot.dispatch(); // send the robot off if it has any items to deliver
				i.remove();       // remove from mailPool queue
			} catch (Exception e) {
				throw e;
			}
		}
		else if (pool.size() > 0) {
			ListIterator<Item> j = pool.listIterator();
			try {
			robot.addToHand(j.next().mailItem); // hand first as we want higher priority delivered first
			j.remove();
			robot.updateMailStartFloor(1);
			if (pool.size() > 0) {
				robot.addToTube(j.next().mailItem);
				j.remove();
			}
			robot.dispatch(); // send the robot off if it has any items to deliver
			i.remove();       // remove from mailPool queue
			} catch (Exception e) { 
	            throw e; 
	        } 
		}
	}

	/**
     * @param robot refers to a robot which has arrived back ready for more mailItems to deliver
     */	
	public void registerWaiting(Robot robot) { // assumes won't be there already
		robots.add(robot);
	}

}
