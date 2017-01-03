package Delete;

import Entity.Interval;
import Entity.Unit;
import Entity.User;
import Test.PrintTestCases;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by eason on 2016/12/26.
 */
public class Delete {
    public Delete(String userId , Interval deleteI,
           Map<Integer, User> markUserMap,
           ArrayList<String> allTimeArray,
           HashSet<String> allStartTimeSet,
           ArrayList<String> allUsers,
           Map<String, ArrayList<Unit>> compressedMap){

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
                        System.out.println("删除用户" + userId + "的第" + i + "个区间");
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
        PrintTestCases ptc = new PrintTestCases();
        ptc.printUserIntervals(markUserMap);



        //如果删除这一区间后,该 user 就没有时间区间了

        if (user.isEmpty()){
            //移除 user
            markUserMap.remove(index, user);
            //大于index的User 对应的order全部减一.
            for (Integer order:markUserMap.keySet()) {
                if (order > index){
                    --order;
                }


            }




            allUsers.remove(userId);
            //把compressedMap里的每一条 压缩后的bitmap在把user序号的位删除.






        }else {
            //如果删除这一区间后,该 user 还有时间区间

            //判断 deleteI 的 开始时间点 和 结束时间点还存不存在

            






        }












    }






}
