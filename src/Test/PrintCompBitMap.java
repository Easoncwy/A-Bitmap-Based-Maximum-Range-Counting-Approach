package Test;

import Entity.Unit;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by eason on 2016/12/28.
 */
public class PrintCompBitMap {
    public PrintCompBitMap(ArrayList<String> allTimeArray, Map<String, ArrayList<Unit>> compressedMap){
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
