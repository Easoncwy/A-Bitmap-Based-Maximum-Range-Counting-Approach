package Method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import Entity.Interval;
import Entity.Unit;
import Entity.User;

public class Insert {
	public Insert(String userId ,Interval interval,
			Map<Integer, User> markUserMap, 
			ArrayList<String> allTimeArray, 
			HashSet<String> allStartTimeArray,
			ArrayList<String> allUsers,
			Map<String, ArrayList<Unit>> compressedMap){
		
		String start,end;
		int index = -1;
		int maxOrder = 0;  //之前使用过的用来mark用户的最大顺序
		boolean exist = false; //标志新加用户是否之前已经存在。
		for(Integer order:markUserMap.keySet()){
			User u = markUserMap.get(order);
			if(u.userID.equals(userId)){
				index = order;
				exist = true;
				break;
			}
			else{
				if(index > maxOrder)
					maxOrder = index;
			}
		}
		
		boolean haveStart = false, haveEnd = false;
		if(exist){
			//用户已经存在
			start = interval.start;
			end = interval.end;
			//判断新加的区间的开始和结束时间点是否之前已经编码
			for(int i = 0; i < allTimeArray.size(); i++){
				if(allTimeArray.get(i).equals(start))
					haveStart = true;
				if(allTimeArray.get(i).equals(end))
					haveEnd = true;
				if(haveStart && haveEnd )
					break;
			}
			if(haveStart){
				ArrayList<Unit> cbitmap = compressedMap.get(start); 
				ArrayList<Unit> newCBitMap = setOne(index,cbitmap);
				compressedMap.put(start, newCBitMap);
			}
		}
	}
	public ArrayList<Unit> setOne(int index,ArrayList<Unit>cbitmap){
		int i,count=0,j;
		ArrayList<Unit> newCBitMap = new ArrayList<>();
		for( i = 0; i < cbitmap.size(); i++){
			Unit unit = cbitmap.get(i);
			count =unit.count;
			if(index > count){
				newCBitMap.add(unit);
				index = index - count;
			}else{
				if(index == 1){
					Unit lastUnit = newCBitMap.get(newCBitMap.size()-1);
					lastUnit.count++;
					Unit u2 = new Unit(count-1,0);
					newCBitMap.add(u2);
					break;
				}else if(index == count){
					Unit u1 = new Unit(count-1,0);
					newCBitMap.add(u1);
					if( i == (cbitmap.size()-1)){
						newCBitMap.add(new Unit(1,1));
					}else{
						Unit u3 = cbitmap.get(i+1);
						u3.count++;
					}
					break;
				}else{
					Unit u1 = new Unit(index-1,0);
					Unit u2 = new Unit(1,1);
					Unit u3 = new Unit(count - index, 0);
					newCBitMap.add(u1);
					newCBitMap.add(u2);
					newCBitMap.add(u3);
					break;
				}
			}
		}
		for(j = i+1; j < cbitmap.size(); j++){
			newCBitMap.add(cbitmap.get(j));
		}
		return newCBitMap;
	}
}
