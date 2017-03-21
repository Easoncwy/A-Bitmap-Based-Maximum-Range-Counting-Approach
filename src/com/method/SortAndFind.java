package com.method;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.entity.Interval;
import com.entity.User;

public class SortAndFind {
	/**
	 * 根据value 得到 key
	 * @param map     一个hashmap  user-->order
	 * @param order   hashmap里的某个user对应的order
	 * @return  user  
	 */
	public User getKey(Map<User, Integer> map, int order){
    	User user = null;
    	for (User getUser : map.keySet()) {
			if (map.get(getUser).equals(order)) {
				user = getUser;
			}
		}
		return user;
    }
	
	
	/**
	 * 把所有时间按从小到大排序
	 * 
	 * @param allTimeArray 所有时间(无序)
	 * @return 所有时间(有序)
	 * @throws ParseException
	 */
	public ArrayList<String> sortAllTime(ArrayList<String> allTimeArray)throws ParseException{
		Time TIME = new Time();
		Collections.sort(allTimeArray, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				String t1 = (String)o1;
				String t2 = (String)o2;
				long time1 = 0;
				try {
					time1 = TIME.uniformTime(t1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				long time2 = 0;
				try {
					time2 = TIME.uniformTime(t2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return (time1 > time2) ? 1 :(time1 == time2) ? 0 : -1 ;
			}
			
		});
		return allTimeArray;
	}
	
	/**
	 * 折半查找
	 * 根据时间找到该时间在 已经排好序的 所有时间数组中的 位置
	 * 
	 * @param sortedTimeArray
	 * @param time
	 * @return  
	 * @throws ParseException
	 */
	public int binarySearch(ArrayList<String> sortedTimeArray,String time) throws ParseException{
		Time TIME = new Time();
		long start = TIME.uniformTime(time);
		
		int low = 0;
		int high = sortedTimeArray.size() - 1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			long midTime = TIME.uniformTime(sortedTimeArray.get(mid));
			if (midTime > start)
				high = mid - 1;
			else if (midTime < start) 
				low = mid + 1;
			else
				return mid;
		}
		return -1;
	}
	
	/**
	 * 备用
	 * 
	 * 用于过滤假用户中
	 * @param intervalSet
	 * @param interval
	 * @return
	 * @throws ParseException
	 */
	public int binarySearchInterval(ArrayList<Interval> intervalSet, Interval interval) throws ParseException{
		Time TIME = new Time();
		String startTime = interval.start;
		String endTime = interval.end;
		long start = TIME.uniformTime(startTime);
		int size = intervalSet.size();
		
		int low = 0;
		int high = size - 1;
		
		while (low <= high) 
		{
			int mid = low + (high - low) / 2;
			Interval midInterval = intervalSet.get(mid);
			long midStart = TIME.uniformTime(midInterval.start);
//			long midEnd = TIME.uniformTime(midInterval.end);
			
			if (midInterval.Exist(startTime)) {
				if (midInterval.Exist(endTime)) {
					return mid;
				}
			}
			else if (start < midStart) {
				high = mid - 1;
			}
			else{
				low = mid + 1;
			}
		}
		return -1;
	}
	
	/**
	 * 备用
	 * 根据userID查找 用户的Oder 
	 * @param s
	 * @param markedUserMap
	 * @return
	 */
	public int getTheOrderFigure(String s, Map<User, Integer> markedUserMap){
		int orderNumber = 0;
		Set<User> markedUserSet = markedUserMap.keySet();
		
		for (User user : markedUserSet) {
			String userID = user.getUserID();
			if (s.equals(userID)) {
				orderNumber = markedUserMap.get(user);
			}
		}
		return orderNumber;
	}
	
	
	/**
	 * 备用
	 * 
	 * 得到开始时间之后的 时间数组
	 * @param sortedTimeArray
	 * @param startTime
	 * @return
	 * @throws ParseException
	 */
	public List<String> getTheTimeArrayAfterStart(ArrayList<String> sortedTimeArray, String startTime) throws ParseException{
//		String thePreEndTime = thePreviousEndTime(db, startTime);
		int startTimeindex = binarySearch(sortedTimeArray, startTime);
//		int endTimeindex = binarySearch(sortedTimeArray, thePreEndTime);
		
		int fromIndex = startTimeindex + 1;
		int toIndex = sortedTimeArray.size();
		List<String> afterStartTimeArray =  sortedTimeArray.subList(fromIndex, toIndex);
		return afterStartTimeArray;
	}
	
	
	/**
	 * 备用
	 * 顺序查找
	 * 可以用折半查找
	 * 
	 * 找到某开始时间 在 sortedTimeArray的位置
	 * @param time
	 * @param sortedTimeArray
	 * @return
	 */
	public int getTheIndexOfTime(String time, ArrayList<String> sortedTimeArray){
		int index = 0;
		for (int i = 0; i < sortedTimeArray.size(); i++) {
			if (sortedTimeArray.get(i).equals(time)) {
				index = i;
			}	
		}		
		return index;
	}
}
