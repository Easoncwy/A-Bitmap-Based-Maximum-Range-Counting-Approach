package Entity;

import java.util.ArrayList;

public class CompressedList {
	public ArrayList<Unit> compressedList;
	
    public Unit getUnit(int index){
		
		return compressedList.get(index);
	}
	
	public void add(Unit i){
		compressedList.add(i);
	}

}
