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
    /*
    public ActivityConflict(
            Map<Interval, Integer> map1,
            Map<Interval, Integer> map2,
            Map<Interval, Integer> map3)
    {

        Map<List<Interval>, Integer> plans = new HashMap<>();

        for (Interval inter1:map1.keySet())
        {
            List<Interval> intervals = new ArrayList<>();
            intervals.add(inter1);
            for (Interval inter2:map2.keySet())
            {
                intervals.add(inter2);
                for (Interval inter3:map3.keySet())
                {
                    intervals.add(inter3);


                }

            }
        }

    }
    */


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








}
