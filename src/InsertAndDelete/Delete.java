package InsertAndDelete;

import Entity.Interval;
import Entity.Unit;
import Entity.User;
import Method.SortAndFind;
import Method.Time;

import java.util.*;

/**
 * Created by eason on 2016/12/26.
 */
public class Delete {
    /**
     *
     * @param userId         待删除区间的用户ID
     * @param deleteI        待删除区间
     * @param markUserMap
     * @param allTimeArray
     * @param allStartTimeSet
     * @param allUsers
     * @param compressedMap
     * @throws Exception
     */
    public Delete(String userId , Interval deleteI,
           Map<Integer, User> markUserMap,
           ArrayList<String> allTimeArray,
           HashSet<String> allStartTimeSet,
           ArrayList<String> allUsers,
           Map<String, ArrayList<Unit>> compressedMap)throws Exception{

        Time TIME = new Time();
        CmpBitMapOps cbmo = new CmpBitMapOps();
        SortAndFind saf = new SortAndFind();
//        ArrayList<String> sortedTime = saf.sortAllTime(allTimeArray);

        String start,end;
        int index = -1;
        User user = null;
        start = deleteI.start;
        end = deleteI.end;

        //把这个区间从该用户的区间List里删除
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

        ++index;//由于用户序号是从0开始计数.需要把 index 加一
        boolean haveStart = false;
        boolean haveEnd = false;


        //判断删除区间后该用户的区间列表是否为空
        if (user.isEmpty()){
            //如果删除这一区间后,该 user 就没有时间区间了
            allUsers.remove(userId);
            markUserMap.remove(index);


            //判断 deleteI 的 开始时间点 和 结束时间点还存不存在
            judgeStartEnd(markUserMap,start,end,haveStart,haveEnd);

            /**
             *
             * 将allTimeArray中属于(start, end)范围中的时间点, 做setZero操作.
             * 并且对start 和 end 做
             */
            Zero(start, end,index, allTimeArray, compressedMap, allStartTimeSet,haveStart,haveEnd);


        }else {
            //如果删除这一区间后,该 user 还有时间区间


            //判断 deleteI 的 开始时间点 和 结束时间点还存不存在
            judgeStartEnd(markUserMap,start,end,haveStart,haveEnd);

            /**
             *
             * 将allTimeArray中属于(start, end)范围中的时间点, 做setZero操作.
             * 并且对start 和 end 做
             */
            Zero(start,end,index,allTimeArray,compressedMap,allStartTimeSet,haveStart,haveEnd);
        }
    }

    /**
     * 判断开始时间点 和 结束时间点还存不存在
     * @param markUserMap
     * @param start
     * @param end
     * @param haveStart
     * @param haveEnd
     */
    public void judgeStartEnd(Map<Integer, User> markUserMap,
                              String start,String end,
                              boolean haveStart,boolean haveEnd) {

        //删除用户区间后的所有时间点集合
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

    }

    /**
     * 将allTimeArray中属于(start, end)范围中的时间点, 做setZero操作.
     * 并且对start 和 end 做判断,分情况讨论
     *
     * @param start
     * @param end
     * @param index
     * @param allTimeArray
     * @param compressedMap
     * @param allStartTimeSet
     * @param haveStart
     * @param haveEnd
     * @throws Exception
     */

    public void Zero(String start, String end, int index,
                     ArrayList<String> allTimeArray,
                     Map<String, ArrayList<Unit>> compressedMap,
                     Set<String> allStartTimeSet,
                     boolean haveStart, boolean haveEnd)throws Exception{
        Time TIME = new Time();
        CmpBitMapOps cbmo = new CmpBitMapOps();
        long startTime,endTime;
        startTime = TIME.uniformTime(start);
        endTime = TIME.uniformTime(end);



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
    }

}
