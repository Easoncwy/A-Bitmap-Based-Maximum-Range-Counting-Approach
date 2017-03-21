package com.insertdelete;

import com.entity.Interval;
import com.entity.Unit;
import com.entity.User;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by eason on 2016/12/28.
 */
public class PrintAndOther {

    public void printUserIntervals(Map<Integer, User> markUserMap){
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

    }





    public void printCompBitMap(ArrayList<String> allTimeArray, Map<String, ArrayList<Unit>> compressedMap){
        for (String time:allTimeArray) {
            ArrayList<Unit> compBitMap = compressedMap.get(time);

            System.out.print(time + "-->");

            String compbitmap = "";
            for (int i = 0; i < compBitMap.size(); i++) {
                Unit unit = compBitMap.get(i);
                int count = unit.count;
                int bit = unit.bit;
                compbitmap += count + "," + bit + ":";
            }
            System.out.println(compbitmap);
        }
    }





}
