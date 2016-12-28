package Method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import Entity.Interval;
import Entity.Unit;
import Entity.User;
import Test.PrintCompBitMap;

public class Insert {
	/**
	 *
	 * @param userId
	 * @param interval
	 * @param markUserMap
	 * @param allTimeArray
	 * @param allStartTimeSet
	 * @param allUsers
     * @param compressedMap
     */
	public Insert(String userId ,Interval interval,
			Map<Integer, User> markUserMap, 
			ArrayList<String> allTimeArray, 
			HashSet<String> allStartTimeSet,
			ArrayList<String> allUsers,
			Map<String, ArrayList<Unit>> compressedMap)throws Exception{
		
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
            /**
             * ???
             */
            else {
                if (index > maxOrder)
                    maxOrder = index;
            }

		}
        System.out.println();
        System.out.println("***************index = " + index);


        boolean haveStart = false, haveEnd = false;
		if(exist){
            //用户已经存在
			start = interval.start;
			end = interval.end;

            Time TIME = new Time();
            long startTime = TIME.uniformTime(start);
            long endTime =  TIME.uniformTime(end);

            //把新区间插入到 user.intervals的指定位置
            ArrayList<Interval> intervalList = markUserMap.get(index).intervals;
            int size = intervalList.size();
            for (int i = 0; i < size; i++) {
                long temp = TIME.uniformTime(intervalList.get(i).start);

                if (endTime < temp){
                    intervalList.add(i, interval);
                }else {
                    if (i == size - 1)
                        intervalList.add(interval);
                    continue;
                }
            }

            /**
             * 测试
             * 打印插入新区间后的所有用户数据
             */

            for (Integer order:markUserMap.keySet()) {
                User u = markUserMap.get(order);
                String userID = u.userID;

                System.out.print(userID + ",");
                for (int i = 0; i < u.intervals.size(); i++) {
                    Interval userI = u.intervals.get(i);
                    System.out.print("[" + userI.start + "," + userI.end + "]");

                }
                System.out.println();
            }


			//判断新加的区间的开始和结束时间点是否之前已经编码
			for(int i = 0; i < allTimeArray.size(); i++){
				if(allTimeArray.get(i).equals(start)){
                    System.out.println("开始点已经编码");
                    haveStart = true;
                }

				if(allTimeArray.get(i).equals(end)){
                    System.out.println("结束点已经编码");
                    haveEnd = true;
                }
				if(haveStart && haveEnd )
                {
                    System.out.println("开始点和结束点之前都已经编码");
                    break;
                }
			}

            /**
             * 测试
             * 打印未插入前 , 初始的comBitMap
             */
            PrintCompBitMap printBeforeComp = new PrintCompBitMap(allTimeArray, compressedMap);

            ++index;
            //如果开始时间点之前已经编码
            //那么需要在它对应的bitmap中把 该用户的index位标为1.
            //这时需要改变它对应的compBitMap
			if(haveStart){
				ArrayList<Unit> cbitmap = compressedMap.get(start); 
				ArrayList<Unit> newCBitMap = setOne(index, cbitmap);
				compressedMap.put(start, newCBitMap);
			}else {
                //对start 时间点重新编码bitmap,再压缩
                allStartTimeSet.add(start);
                allTimeArray.add(start);

            }


            if (haveEnd){
                ArrayList<Unit> cbitmap = compressedMap.get(end);
                ArrayList<Unit> newCBitMap = setOne(index, cbitmap);
                compressedMap.put(end, newCBitMap);
            }else {
                //对end  时间点重新编码bitmap,再压缩
                allTimeArray.add(end);
            }

            /**
             * 测试
             * 打印插入后 , 改变后的的comBitMap
             */
            System.out.println("打印插入后 , 改变后的的comBitMap");

            PrintCompBitMap printAfterComp = new PrintCompBitMap(allTimeArray, compressedMap);




            // 判断allTimeArray中是否存在属于(start, end)范围中的时间点
            for (int i = 0; i < allTimeArray.size(); i++) {
                String t = allTimeArray.get(i);
                long time = TIME.uniformTime(t);
                if ((time > startTime) && (time < endTime)){
                    //将属于(start, end)范围中的时间点做setOne操作.





                }
            }











            //对allTimeArray中,属于(start, end)范围中的时间点,做setOne操作.





























		}else {
            //新加区间的用户,是新用户

            start = interval.start;
            end = interval.end;

            //用户增加一个
            User newUser = new User(userId, interval);
            markUserMap.put(maxOrder + 1, newUser);
            allUsers.add(userId);

            //判断新加的区间的开始和结束时间点是否之前已经编码
            for(int i = 0; i < allTimeArray.size(); i++){
                if(allTimeArray.get(i).equals(start))
                    haveStart = true;
                if(allTimeArray.get(i).equals(end))
                    haveEnd = true;
                if(haveStart && haveEnd )
                    break;
            }


            //如果开始时间点之前已经编码
            //那么需要在它对应的bitmap中把 该用户的index位标为1.
            //这时需要改变它对应的compBitMap


            if(haveStart){
                ArrayList<Unit> cbitmap = compressedMap.get(start);
                ArrayList<Unit> newCBitMap = setOne(index, cbitmap);
                compressedMap.put(start, newCBitMap);
            }else {
                //对start 时间点重新编码bitmap,再压缩
                allStartTimeSet.add(start);
                allTimeArray.add(start);

            }


            if (haveEnd){
                ArrayList<Unit> cbitmap = compressedMap.get(end);
                ArrayList<Unit> newCBitMap = setOne(index, cbitmap);
                compressedMap.put(end, newCBitMap);
            }else {
                //对end 时间点重新编码bitmap,再压缩
                allTimeArray.add(end);
            }

            //对allTimeArray中,属于(start, end)范围中的时间点,做setOne操作.



            //对allTimeArray中,不属于(start, end)范围中时间点,做setZero操作.






        }




	}

	/**
	 *
	 * @param index
	 * @param cbitmap
     * @return
     */
	public ArrayList<Unit> setOne(int index, ArrayList<Unit> cbitmap){
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
                    if (count -1 > 0){
                        Unit u2 = new Unit(count-1,0);
                        newCBitMap.add(u2);
                        break;
                    }

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
