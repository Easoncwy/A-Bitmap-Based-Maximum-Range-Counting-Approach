package Method;

import Entity.Interval;
import Entity.Unit;
import Entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by eason on 2016/12/26.
 */
public class Delete {
    public Delete(String userId , Interval interval,
           Map<Integer, User> markUserMap,
           ArrayList<String> allTimeArray,
           HashSet<String> allStartTimeSet,
           ArrayList<String> allUsers,
           Map<String, ArrayList<Unit>> compressedMap){

        String start,end;
        int index = -1;
        User user = null;
        for(Integer order:markUserMap.keySet()){
            user = markUserMap.get(order);
            if(user.userID.equals(userId)){
                index = order;
                user.intervals.remove(interval);
                break;
            }
        }
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





        }












    }






}
