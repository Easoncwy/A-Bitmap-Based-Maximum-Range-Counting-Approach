package Entity;

import java.util.ArrayList;

public class User {
	public String userID;
	public int index;
	public ArrayList<Interval> intervals;
	
	public User(String u, Interval i){
		userID = u;
		intervals = new ArrayList<Interval>();
		intervals.add(i);
		index = 0;
	}
	public boolean isEmpty(){
		if (intervals.size() == 0)
			return true;
		return false;
	}

	public Interval getInterval(int index){
		
		return intervals.get(index);
	}
	
	public String getUserID(){
		return userID;
	}

	public void add(Interval i){
		intervals.add(i);
	}





}
