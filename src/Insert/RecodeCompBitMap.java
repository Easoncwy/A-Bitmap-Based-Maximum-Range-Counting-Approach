package Insert;

import Entity.Database;
import Entity.Interval;
import Entity.Unit;
import Entity.User;
import Method.NewAlgorithm;
import Method.Time;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by eason on 2016/12/28.
 */
public class RecodeCompBitMap {
    /**
     * 新时间点重新编码
     *
     * @param time
     * @param markUserMap
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public ArrayList<Unit> recodeCompBitMap(String time, Map<Integer, User> markUserMap) throws IOException, ParseException
    {


        Time TIME = new Time();
        BitSet bitmap = new BitSet();

        long t = TIME.uniformTime(time);

        for (Integer order: markUserMap.keySet()) {
            User user = markUserMap.get(order);
            ArrayList<Interval> intervals = user.intervals;
            for (int i = 0; i < intervals.size(); i++) {
                Interval interval = intervals.get(i);
                long startTime = TIME.uniformTime(interval.start);
                if (t < startTime){
                    if (i == 0){
                        break;
                    }else {
                        Interval preI = intervals.get(i - 1);
                        if (preI.Exist(time)){
                            bitmap.set(order);
                            break;
                        }else {
                            break;
                        }
                    }
                }
                else {
                    if ((i == intervals.size() - 1) && interval.Exist(time)){
                        bitmap.set(i);

                    }else {
                        continue;
                    }
                }
            }
        }

        NewAlgorithm na = new NewAlgorithm();

        ArrayList<Integer> bitmapList = na.convertBitmapToList(bitmap, markUserMap.size());

        for (int i = 0; i < bitmapList.size(); i++) {
            System.out.print(bitmapList.get(i));
        }

        ArrayList<Unit> compBitMap = new ArrayList<>();
        int count = 0;
        int preBit = bitmapList.get(0);

        for (int i = 0; i < bitmapList.size(); i++) {
            int temp = bitmapList.get(i);
            if (preBit == temp) {
                ++count;
            }else{
                Unit unit = new Unit(count, preBit);
                compBitMap.add(unit);
                preBit = temp;

                count = 1;
            }
            if (i == bitmap.length() - 1) {
                Unit lastUnit = new Unit(count, temp);
                compBitMap.add(lastUnit);
            }

        }

        return compBitMap;
    }




}
