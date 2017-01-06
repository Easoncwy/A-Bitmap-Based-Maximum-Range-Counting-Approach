package Insert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import Entity.Interval;
import Entity.Unit;
import Entity.User;
import Method.CmpBitMapOps;
import Method.SortAndFind;
import Method.Time;
import Test.PrintTest;

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

        CmpBitMapOps cbmo = new CmpBitMapOps();
        PrintTest pt = new PrintTest();

        SortAndFind saf = new SortAndFind();

        /**
         * step1,标记新加用户之前是否已经存在.
         */
        for(Integer order:markUserMap.keySet()){
			User u = markUserMap.get(order);
			if(u.userID.equals(userId)){
				index = order;
				exist = true;
				break;
			}
            /**
             * 标记最大序号
             */
            else {
                if (order > maxOrder)
                    maxOrder = order;
            }

		}

        /**
         * step2, 分情况讨论
         */
        boolean haveStart = false, haveEnd = false;
		if(exist){
            //用户已经存在

            System.out.println("新加用户已经存在");
			start = interval.start;
			end = interval.end;

            Time TIME = new Time();
            long startTime = TIME.uniformTime(start);
            long endTime =  TIME.uniformTime(end);

            /**
             *
             * step2.1.1
             * 把新区间插入到 user.intervals的指定位置
             *
             */
            ArrayList<Interval> intervalList = markUserMap.get(index).intervals;
            int size = intervalList.size();

            for (int i = 0; i < size; i++) {
                long temp = TIME.uniformTime(intervalList.get(i).start);

                if (endTime < temp){
                    intervalList.add(i, interval);
                    break;
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
            pt.printUserIntervals(markUserMap);


            /**
             * step 2.1.2
             * 判断新加的区间的开始和结束时间点是否之前已经编码
             */

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
             * 打印未插入前 , 初始压缩的comBitMap
             */

            ArrayList<String> sortedTime = saf.sortAllTime(allTimeArray);
            pt.printCompBitMap(sortedTime, compressedMap);



            /**
             * step 2.1.3
             *
             * 插入后, 改变compressedMap.
             *
             */

            ++index;//由于用户序号是从0开始计数.为了setOne方法 计数方便,需要把 index 加一

            // 将allTimeArray中属于(start, end)范围中的时间点, 做setOne操作.
            for (int i = 0; i < allTimeArray.size(); i++) {
                String time = allTimeArray.get(i);
                long t = TIME.uniformTime(time);
                if ((t > startTime) && (t < endTime)){

                    //将属于(start, end)范围中的时间点做setOne操作.
                    ArrayList<Unit> cbitmap = compressedMap.get(time);
                    ArrayList<Unit> newCBitMap = cbmo.setOne(index, cbitmap);
                    compressedMap.put(time, newCBitMap);

                }
            }

            //如果开始时间点之前已经编码
            //那么需要在它对应的bitmap中把 该用户的index位标为1.
            //这时需要改变它对应的compBitMap

			if(haveStart){
				ArrayList<Unit> cbitmap = compressedMap.get(start); 
				ArrayList<Unit> newCBitMap = cbmo.setOne(index, cbitmap);
				compressedMap.put(start, newCBitMap);
			}else {
                //对start 时间点重新编码bitmap,再压缩
                allStartTimeSet.add(start);
                allTimeArray.add(start);
                RecodeCompBitMap rcb= new RecodeCompBitMap();
                ArrayList<Unit> newCompBitMap = rcb.recodeCompBitMap(start, markUserMap);

                compressedMap.put(start, newCompBitMap);

            }


            if (haveEnd){
                ArrayList<Unit> cbitmap = compressedMap.get(end);
                ArrayList<Unit> newCBitMap = cbmo.setOne(index, cbitmap);
                compressedMap.put(end, newCBitMap);
            }else {
                //对end  时间点重新编码bitmap,再压缩
                allTimeArray.add(end);
                RecodeCompBitMap rcb= new RecodeCompBitMap();
                ArrayList<Unit> newCompBitMap = rcb.recodeCompBitMap(end, markUserMap);
                compressedMap.put(end, newCompBitMap);

            }


            /**
             * 测试
             * 打印插入后 , 改变后的的comBitMap
             */
            System.out.println("打印插入后 , 改变后的的comBitMap");
            pt.printCompBitMap(sortedTime, compressedMap);


		}

        //新加区间的用户,是新用户
        else {
            System.out.println("新加用户是新用户");
            Time TIME = new Time();

            start = interval.start;
            end = interval.end;

            long startTime = TIME.uniformTime(start);
            long endTime =  TIME.uniformTime(end);


            //用户增加一个
            User newUser = new User(userId, interval);
            markUserMap.put(maxOrder + 1, newUser);
            allUsers.add(userId);


            /**
             * 测试
             * 打印插入新区间后的所有用户数据
             */
            pt.printUserIntervals(markUserMap);



            //判断新加的区间的开始和结束时间点是否之前已经编码
            for(int i = 0; i < allTimeArray.size(); i++){
                if(allTimeArray.get(i).equals(start))
                    haveStart = true;
                if(allTimeArray.get(i).equals(end))
                    haveEnd = true;
                if(haveStart && haveEnd )
                    break;
            }

            /**
             * 测试
             * 打印未插入前 , 初始压缩的comBitMap
             */

            ArrayList<String> sortedTime = saf.sortAllTime(allTimeArray);
            pt.printCompBitMap(sortedTime, compressedMap);

            ++index;//由于用户序号是从0开始计数.为了AddOne方法 计数方便,需要把 index 加一

            // 将allTimeArray中属于(start, end)范围中的时间点, 做addOne操作.
            for (int i = 0; i < allTimeArray.size(); i++) {
                String time = allTimeArray.get(i);
                long t = TIME.uniformTime(time);
                if ((t > startTime) && (t < endTime)){

                    //将属于(start, end)范围中的时间点做addOne操作.
                    ArrayList<Unit> cbitmap = compressedMap.get(time);
                    ArrayList<Unit> newCBitMap = cbmo.addOne(index, cbitmap);
                    compressedMap.put(time, newCBitMap);
                }
            }


            // 将allTimeArray中不属于(start, end)范围中的时间点, 做addZero操作.
            for (int i = 0; i < allTimeArray.size(); i++) {
                String time = allTimeArray.get(i);
                long t = TIME.uniformTime(time);
                if ((t < startTime) || (t > endTime)){

                    //将属于(start, end)范围中的时间点做addZero操作.
                    ArrayList<Unit> cbitmap = compressedMap.get(time);
                    ArrayList<Unit> newCBitMap = cbmo.addZero(index, cbitmap);
                    compressedMap.put(time, newCBitMap);
                }
            }


            //如果开始时间点之前已经编码
            //这时需要改变它对应的compBitMap

            if(haveStart){
                ArrayList<Unit> cbitmap = compressedMap.get(start);
                ArrayList<Unit> newCBitMap = cbmo.addOne(index, cbitmap);
                compressedMap.put(start, newCBitMap);
            }else {
                //start时间点之前未编码
                //要对start 时间点重新编码bitmap,再压缩,加入到compressedMap中
                allStartTimeSet.add(start);
                allTimeArray.add(start);
                RecodeCompBitMap rcb= new RecodeCompBitMap();
                ArrayList<Unit> newCompBitMap = rcb.recodeCompBitMap(start, markUserMap);
                compressedMap.put(start, newCompBitMap);

            }


            if (haveEnd){
                ArrayList<Unit> cbitmap = compressedMap.get(end);
                ArrayList<Unit> newCBitMap = cbmo.addOne(index, cbitmap);
                compressedMap.put(end, newCBitMap);
            }else {
                //对end 时间点重新编码bitmap,再压缩
                allTimeArray.add(end);
                RecodeCompBitMap rcb= new RecodeCompBitMap();
                ArrayList<Unit> newCompBitMap = rcb.recodeCompBitMap(end, markUserMap);
                compressedMap.put(end, newCompBitMap);
            }

            /**
             * 测试
             * 打印插入后 , 改变后的的comBitMap
             */
            System.out.println("打印插入后 , 改变后的的comBitMap");

            ArrayList<String> sortedTime02 = saf.sortAllTime(allTimeArray);
            pt.printCompBitMap(sortedTime02,compressedMap);

            System.out.println("打印插入后的User");
            for (Integer i:markUserMap.keySet()) {
                System.out.print(markUserMap.get(i).getUserID() + ",");

            }
        }
	}


}
