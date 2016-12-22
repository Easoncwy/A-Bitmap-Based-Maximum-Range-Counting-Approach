package Entity;

import java.util.ArrayList;

public class Database {
	
    public ArrayList<User> data;
	
	public Database(){
		data = new ArrayList<User>();
	}
	public void insert(User u){
		data.add(u);
	}
	
	public int getSize(){
		return data.size();
	}
	/*
	 * 根据下标得到对应的user
	 */
	public User getUser(int index){
		return data.get(index);
	}
	
	
	public void insertI(Interval i){
		data.get(getSize()-1).add(i);
	}
	

}
