package com.activityconflict;

import com.entity.Interval;
import com.query.SortAndFind;
import com.query.Time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eason on 2017/6/2.
 */
public class ActivityConflict {

    public Map<ArrayList<Interval>, Integer> ActivityConflict (
            Map<Interval, Integer> map1,
            Map<Interval, Integer> map2,
            Map<Interval, Integer> map3)
    {

        Map<ArrayList<Interval>, Integer> plans = new HashMap<>();
        int count = 0;

        for (Interval inter1:map1.keySet())
        {
            ArrayList<Interval> intervals = new ArrayList<>();
            intervals.add(inter1);
            for (Interval inter2:map2.keySet())
            {
                intervals.add(inter2);
                for (Interval inter3:map3.keySet())
                {
                    intervals.add(inter3);
                    try {
                        if (isConflict(intervals)){
                            ++count;
                            System.out.println("活动不发生冲突");
                            int sum = map1.get(inter1) + map2.get(inter2) + map3.get(inter3);
                            plans.put(intervals, sum);
                        }
                    }catch (Exception e){
                        System.err.println("catch a Exception!");
                    }

                }

            }
        }
        System.out.println("有" + count + "个活动不发生冲突");
        return plans;

    }



    public boolean isConflict(ArrayList<Interval> intervals) throws Exception{
        Time TIME = new Time();
        SortAndFind saf = new SortAndFind();
        ArrayList<Interval> sortedIntervals = saf.sortAllIntervals(intervals);
        boolean isConflict = true;

        for (int i = 1; i < sortedIntervals.size(); i++) {
            Interval secInter = sortedIntervals.get(i);
            Interval firInter = sortedIntervals.get(i - 1);
            long secStartTime = TIME.uniformTime(secInter.start);
            long firEndTime = TIME.uniformTime(firInter.end);
            if (secStartTime < firEndTime){
               break;
            }else {
                if (i == sortedIntervals.size() - 1)
                    isConflict = false;
            }

        }
        return isConflict;
    }

    public void outputPlans(Map<ArrayList<Interval>, Integer> plans){
        for (ArrayList<Interval> list: plans.keySet()) {
            for (int i = 0; i < list.size(); i++) {
                System.out.printf(list.get(i) + ",");
            }
            System.out.println("-->" + plans.get(list));

        }
    }








}
