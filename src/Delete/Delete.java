package Delete;

import Entity.Interval;
import Entity.Unit;
import Entity.User;
import Method.CmpBitMapOps;
import Method.Time;
import Test.PrintTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by eason on 2016/12/26.
 */
public class Delete {
    public Delete(String userId , Interval deleteI,
           Map<Integer, User> markUserMap,
           ArrayList<String> allTimeArray,
           HashSet<String> allStartTimeSet,
           ArrayList<String> allUsers,
           Map<String, ArrayList<Unit>> compressedMap)throws Exception{

        Time TIME = new Time();
        CmpBitMapOps cbmo = new CmpBitMapOps();

        String start,end;
        int index = -1;
        User user = null;
        start = deleteI.start;
        end = deleteI.end;

        for(Integer order:markUserMap.keySet()){
            user = markUserMap.get(order);
            if(user.userID.equals(userId)){
                System.out.println("删除用户" + userId + "的一个区间");

                ArrayList<Interval> intervals = user.intervals;
                for (int i = 0; i < intervals.size(); i++) {
                    Interval interval = intervals.get(i);
                    if ((interval.start.equals(start)) && (interval.end.equals(end))){
                        index = order;
                        user.intervals.remove(i);
                        break;

                    }

                }
            }
        }

        /**
         * 测试
         * 打印删除区间后的
         */
        System.out.println("打印删除区间后的用户数据");
        PrintTest ptc = new PrintTest();
        ptc.printUserIntervals(markUserMap);




        if (user.isEmpty()){
            allUsers.remove(userId);
            markUserMap.remove(index);






            //如果删除这一区间后,该 user 就没有时间区间了
            long startTime = TIME.uniformTime(start);
            long endTime =  TIME.uniformTime(end);

            //删除这一区间后, 判断 deleteI 的 开始时间点 和 结束时间点还存不存在
            boolean haveStart = false;
            boolean haveEnd = false;

            Set aftDelTimeSet = new HashSet<>();

            for (Integer order: markUserMap.keySet()) {
                User u = markUserMap.get(order);
                ArrayList<Interval> intervals = u.intervals;
                for (int i = 0; i < intervals.size(); i++){
                    aftDelTimeSet.add(intervals.get(i).start);
                    aftDelTimeSet.add(intervals.get(i).end);
                }
            }

            if (aftDelTimeSet.contains(start)){
                System.out.println("开始时间点还存在");
                haveStart = true;
            }

            if (aftDelTimeSet.contains(end)){
                System.out.println("结束时间点还存在");
                haveEnd = true;
            }



            ++index;//由于用户序号是从0开始计数.需要把 index 加一















        }else {
            //如果删除这一区间后,该 user 还有时间区间

            long startTime = TIME.uniformTime(start);
            long endTime =  TIME.uniformTime(end);

            //删除这一区间后, 判断 deleteI 的 开始时间点 和 结束时间点还存不存在
            boolean haveStart = false;
            boolean haveEnd = false;

            Set aftDelTimeSet = new HashSet<>();

            for (Integer order: markUserMap.keySet()) {
                User u = markUserMap.get(order);
                ArrayList<Interval> intervals = u.intervals;
                for (int i = 0; i < intervals.size(); i++){
                    aftDelTimeSet.add(intervals.get(i).start);
                    aftDelTimeSet.add(intervals.get(i).end);
                }
            }

            if (aftDelTimeSet.contains(start)){
                System.out.println("开始时间点还存在");
                haveStart = true;
            }

            if (aftDelTimeSet.contains(end)){
                System.out.println("结束时间点还存在");
                haveEnd = true;
            }



            ++index;//由于用户序号是从0开始计数.需要把 index 加一

            // 将allTimeArray中属于(start, end)范围中的时间点, 做setZero操作.
            for (int i = 0; i < allTimeArray.size(); i++) {
                String time = allTimeArray.get(i);
                long t = TIME.uniformTime(time);
                if ((t > startTime) && (t < endTime)){

                    //将属于(start, end)范围中的时间点做setZero操作.
                    ArrayList<Unit> cbitmap = compressedMap.get(time);
                    ArrayList<Unit> newCBitMap = cbmo.setZero(index, cbitmap);
                    compressedMap.put(time, newCBitMap);

                }
            }

            if (!haveStart){
                allTimeArray.remove(start);
                allStartTimeSet.remove(start);
                compressedMap.remove(start);
            }else {
                ArrayList<Unit> cbitmap = compressedMap.get(start);
                ArrayList<Unit> newCBitMap = cbmo.setZero(index, cbitmap);
                compressedMap.put(start, newCBitMap);

            }


            if (!haveEnd){
                allTimeArray.remove(end);
                compressedMap.remove(end);
            }else {
                ArrayList<Unit> cbitmap = compressedMap.get(end);
                ArrayList<Unit> newCBitMap = cbmo.setZero(index, cbitmap);
                compressedMap.put(end, newCBitMap);
            }


            /**
             * 测试
             * 打印删除后 , 改变后的的comBitMap
             */
            System.out.println("打印删除后 , 改变后的的comBitMap");
            ptc.printCompBitMap(allTimeArray, compressedMap);



        }
    }






}
