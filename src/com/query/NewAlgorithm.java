package com.query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import com.entity.Interval;
import com.entity.Unit;
import com.entity.User;

/**
 * @author supreme
 * 
 */
public class NewAlgorithm {
	
	public HashMap<Interval, ArrayList<User>> maxMap;
	public NewAlgorithm(){
		maxMap = new HashMap<>();
	}
	/**
	 * 把bitmap转换为 0，1数组
	 * 
	 * @param bitSet
	 * @return arrayList  0，1数组
	 */
	public ArrayList<Integer> convertBitmapToList(BitSet bitSet,int length){
        ArrayList<Integer> arrayList = new ArrayList<>();
        int size = bitSet.size();
        for (int i = 0; i < length; i++) {
            if (bitSet.get(i))
                arrayList.add(1);
            else
                arrayList.add(0);
        }
        return arrayList;
    }
	
	/**
	 * 压缩与函数
	 * 
	 * @param startTime    开始时间
	 * @param nextTime    开始时间之后的某一时间
	 * @param compressedMap   每个时间对应的压缩后的bitmap   time-->compressedBitMap
	 * @return compressedArray  压缩与后的 compressedBitMap
	 */
	public ArrayList<Unit> compressedAndOp(
			String startTime, 
			String nextTime, 
			Map<String, ArrayList<Unit>> compressedMap)
	{
		ArrayList<Unit> compressedA = compressedMap.get(startTime);
		ArrayList<Unit> compressedB = compressedMap.get(nextTime);

		//ArrayList<Unit> compA = new ArrayList<Unit>(compressedA.size());
		Stack<Unit> compA = new Stack<Unit>();
		for (int i = compressedA.size()-1 ; i >=0 ; i--)
			compA.push(compressedA.get(i));
	
		//ArrayList<Unit> compB = new ArrayList<Unit>(compressedB.size());
		Stack<Unit> compB = new Stack<Unit>();
		for (int i = compressedB.size()-1 ; i >=0 ; i--)
			compB.push(compressedB.get(i));
		ArrayList<Unit> resArray = new ArrayList<>();

		while(!compA.isEmpty()){
			Unit ua = compA.pop();
			Unit ub = compB.pop();
			int resBit = ua.bit & ub.bit;
			int diff = ua.count - ub.count;
			if( diff > 0){
				//ua.count > ub.count
				Unit remainU = new Unit(diff, ua.bit);
				Unit newU = new Unit(ub.count,resBit);
				compA.push(remainU);
				resArray.add(newU);
			}
			else if(diff == 0 ){
				Unit newU = new Unit(ub.count,resBit);
				resArray.add(newU);
			}
			else if( diff < 0 ){
				Unit remainU = new Unit(-diff, ub.bit);
				Unit newU = new Unit(ua.count, resBit);
				compB.push(remainU);
				resArray.add(newU);
			}
		}
		
		/** 压缩 */
		ArrayList<Unit> compressedArray = new ArrayList<>();
		int preBit = resArray.get(0).bit;
		int num = 0;
		for (int i = 0; i < resArray.size(); i++) {
			Unit unit = resArray.get(i);
			if (preBit == unit.bit) {
				num += unit.count;
				if (i == resArray.size() - 1) {
					compressedArray.add(new Unit(num,preBit));
				}	
			}else{
				Unit u = new Unit(num, preBit);
				compressedArray.add(u);
				if (i == resArray.size() - 1) {
					compressedArray.add(new Unit(unit.count, unit.bit));
				}
				preBit = unit.bit;
				num = unit.count;
			}
			
		}
		return compressedArray;
	}
	
	/**
	 * 得到真和假的用户集合
	 * 
	 * @param compressedArray  压缩与操作后的 结果compressedBitMap
	 * @param markUserMap   
	 * @return  fakeUserSet    真假用户集合
	 */
	public ArrayList<User> getFakeUserSet(ArrayList<Unit> compressedArray,
										  Map<Integer, User> markUserMap,
										  Set<String> allUsers)
	{
		ArrayList<User> fakeUserSet = new ArrayList<>();

		int preOrder = 0;
		for (int i = 0; i < compressedArray.size(); i++) {
			Unit unit = compressedArray.get(i);
			int bit = unit.bit;
			if (bit == 1) {
				for (int j = 0; j < unit.count; j++) {
					User user = markUserMap.get(preOrder + j);
					if (allUsers.contains(user.userID)){
						fakeUserSet.add(user);
					}
				}
			}
			preOrder += unit.count;
		}
		return fakeUserSet;
		
	}
	
	
	/**
	 * 过滤假用户
	 * 
	 * @param interval     待选最优区间
	 * @param fakeUserSet  真假用户集合
	 * @return trueUserSet  真实用户集合
	 * @throws ParseException
	 */
	
	public ArrayList<User> filterFakeUser(Interval interval, ArrayList<User> fakeUserSet) throws ParseException
	{
		ArrayList<User> trueUserSet = new ArrayList<>();
		String start = interval.start;
		String end = interval.end;
		int flag = 0;
		for (User user : fakeUserSet) 
		{

			flag = user.index;
			ArrayList<Interval> userIntervalSet = user.intervals;

			for (int i = flag; i < userIntervalSet.size(); i++) {
				Interval userInterval = userIntervalSet.get(i);
				if (userInterval.Exist(start)) {
					if(userInterval.Exist(end)){
						if (user.userID.equals("u009273"))
							System.out.println(user.userID);
						trueUserSet.add(user);
						user.index = flag;
						break;
					}else{
						//某个区间包含开始时间点，不包含结束时间点
						//则该用户肯定不满足
						user.index = flag;
						break;
					}
				}else {
					flag++;
					if(userInterval.bigger(start)){
						break;
					}
				}
			}



		}
		return trueUserSet;
	}
	
	/**
	 * 主方法
	 * 得到最优区间，及其覆盖的最多的用户
	 * 
	 * @param compressedMap
	 * @param duration
	 * @param markUserMap
	 * @param sortedTimeArray
	 * @param allStartTimeSet
	 * @return maxUserHashMap   每个最优区间对应一个最多覆盖用户集合。
	 * @throws ParseException
	 * @throws IOException
	 */
	public Map<Interval, ArrayList<User>> getMaxHashMap(
			Map<String, ArrayList<Unit>> compressedMap,
			long duration,
			Map<Integer, User> markUserMap, 
			ArrayList<String> sortedTimeArray,
			Set<String> allStartTimeSet,
			Set<String> allUsers,
			String output) throws Exception
	{

		Calendar ca = Calendar.getInstance();

		Time TIME = new Time();

		
		int max = 0;
		
		/** 
		 * 筛选假的用户的时间
		 */
		long filterUserTime = 0;
		/** 根据bitmap得到的用户的时间 */
		long getFakeUserTime = 0;
		/**
		 * 区间调整时间
		 */
		long intervalAdjust = 0;

		Map<Interval, ArrayList<User>> trueMap = new HashMap<>();
		Set<ArrayList<User>> tempSet = new HashSet<>();

		for (int i = 0; i < sortedTimeArray.size(); i++ )
		{
			if(allStartTimeSet.contains(sortedTimeArray.get(i)))
			{
				String startTime = sortedTimeArray.get(i);
				String endTime = null;
				ArrayList<User> preUserSet = new ArrayList<>();
				int preSize = 0;
				int get = 0;

				for (int j =  i+ 1; j < sortedTimeArray.size(); j++) {

					String nextTime = sortedTimeArray.get(j);
					
					long start = TIME.uniformTime(startTime);
					long after = TIME.uniformTime(nextTime);
					ArrayList<User> trueUserSet = new ArrayList<>();
					if ((after - start) >= duration) 
					{
						get ++;
						/*
						 * 计算getFakeUserSet部分花费时间
						 */
						Calendar c1 = Calendar.getInstance();
						ArrayList<Unit> compResBitMap = compressedAndOp(startTime, nextTime, compressedMap);
						ArrayList<User> fakeUserSet = getFakeUserSet(compResBitMap, markUserMap, allUsers);

						Calendar c2 = Calendar.getInstance();
						long getFakeUserSet_cost = c2.getTimeInMillis() - c1.getTimeInMillis();
			    	
						getFakeUserTime += getFakeUserSet_cost;
					
						/*
						 * 计算filterFakeUser部分花费时间
						 */
						if(fakeUserSet.size() < preUserSet.size())
							break;
						Calendar cx = Calendar.getInstance();

						trueUserSet = filterFakeUser(new Interval(startTime, nextTime), fakeUserSet);
						Calendar cy = Calendar.getInstance();
				    	long filterCost = cy.getTimeInMillis() - cx.getTimeInMillis();
				    	filterUserTime += filterCost;
						if(trueUserSet.isEmpty()) {
							preSize = 0;
							preUserSet.clear();
							break;
						}
	//					System.out.println("filterFakeUser算法花费时间: " + Integer.toString((int) finalCost) + " ms\n");
					
					}else{
						continue;
					}

					int size = trueUserSet.size();
					if( get == 1){
						preSize = size;
						preUserSet.addAll(trueUserSet);
						endTime = nextTime;
					}else if (size == preSize) 
					{
						endTime = nextTime;
					}else{
						break;
					}


				}//计算以startTime开始的最优区间的for循环结束

				if (preSize >= max) {
					max = preSize;
				}

                //区间调整
				Calendar c_s = Calendar.getInstance();
				if (tempSet.isEmpty()){
					tempSet.add(preUserSet);
					Interval interval = new Interval(startTime, endTime);
					trueMap.put(interval, preUserSet);
				}else {
					if (tempSet.contains(preUserSet)){
						continue;
					}else {
						tempSet.add(preUserSet);
						Interval interval = new Interval(startTime, endTime);
						trueMap.put(interval, preUserSet);
					}
				}
				Calendar c_e = Calendar.getInstance();
				long cost = c_e.getTimeInMillis() - c_s.getTimeInMillis();
				intervalAdjust += cost;
				//区间调整结束

			}

		}

//		Map<Interval, ArrayList<User>> maxMap = new HashMap<>();
		for (Interval it : trueMap.keySet()) {
			ArrayList<User> userSet = trueMap.get(it);
			int size = userSet.size();
			if (size == max) {
				maxMap.put(it, userSet);
			}
		}

		/**
		 * 区间调整
		 */

		/*
		Map<ArrayList<User>, ArrayList<Interval>> tempMap = new HashMap<>();
		for (Interval it: maxMap.keySet()){
			ArrayList<User> userSet = maxMap.get(it);
			if (!tempMap.containsKey(userSet)){
				ArrayList<Interval> list = new ArrayList<>();
				list.add(it);
				tempMap.put(userSet, list);
			}else {
				tempMap.get(userSet).add(it);
			}
		}
		*/

		/*
		for (ArrayList<User> set: tempMap.keySet()) {
			ArrayList<Interval> list = tempMap.get(set);
			for (User u: set) {
				System.out.printf(u.getUserID() + ",");
			}
			System.out.printf("-->");
			for (int i = 0; i < list.size(); i++) {
				Interval interval = list.get(i);

				System.out.print("[" + interval.getStart() + "," + interval.getEnd() + "]" + ",");
			}
			System.out.println();

		}
		*/

		Calendar cb = Calendar.getInstance();
		long finalcost = cb.getTimeInMillis() - ca.getTimeInMillis();

		output(maxMap, output, max, getFakeUserTime, filterUserTime, intervalAdjust,finalcost);

		return maxMap;
	}
	
	
	
	/**
	 * 打印查询结果
	 * 
	 * @param maxUserHashMap
	 * @throws ParseException
	 * @throws IOException 
	 */
	public void output(Map<Interval, ArrayList<User>> maxUserHashMap, String output, int max, long time1, long time2, long time3,long time4) throws ParseException, IOException{
		File outfile = new File(output);
    	FileWriter fw = new FileWriter(outfile);
    	BufferedWriter bw = new BufferedWriter(fw);
		bw.write("getFakeUserTime部分花费时间: " + Integer.toString((int) time1) + " ms\n");
		bw.write("filterFakeUser部分花费时间: " + Integer.toString((int) time2) + " ms\n");
		bw.write("intervalAdjust部分花费时间: " + Integer.toString((int) time3) + " ms\n");
		bw.write("总共花费时间: " + Integer.toString((int) time4) + " ms\n");
		bw.write("最多能覆盖" + max + "个用户" + "\n");
		for (Interval it : maxUserHashMap.keySet()) {
			String start = it.getStart();
			String end = it.getEnd();
			String optInterval = "[" + start + "," + end + "]";
			bw.write(optInterval + "-->");
			ArrayList<User> maxUserSet = maxUserHashMap.get(it);
			for (int j = 0; j < maxUserSet.size(); j++) {
				User user = maxUserSet.get(j);
				String userId = user.userID;
				if( j==0 )
					bw.write(userId);
				else
					bw.write("," + userId);
			}
			bw.write("\n");
		}
		bw.close();
	}
	
}
