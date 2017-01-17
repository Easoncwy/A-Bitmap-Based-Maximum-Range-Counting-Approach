package InsertAndDelete;

import Entity.Unit;

import java.util.ArrayList;

/**
 * Created by eason on 2017/1/3.
 */
public class CmpBitMapOps {


    /**
     *
     * @param index
     * @param cbitmap
     * @return
     */
    public ArrayList<Unit> setZero(int index, ArrayList<Unit> cbitmap){

        int i,count=0,j;
        ArrayList<Unit> newCBitMap = new ArrayList<>();
        for( i = 0; i < cbitmap.size(); i++){
            Unit unit = cbitmap.get(i);
            count =unit.count;
            if(index > count){
                newCBitMap.add(unit);
                index = index - count;
            }else{
                if(index == 1){
                    if (newCBitMap.isEmpty()){
                        if (count > 1){
                            Unit firstUnit = new Unit(1, 0);
                            newCBitMap.add(firstUnit);
                        }else {
                            Unit nxtUnit = cbitmap.get(i + 1);
                            nxtUnit.count++;
                        }

                    }else {
                        Unit lastUnit = newCBitMap.get(newCBitMap.size()-1);
                        lastUnit.count++;
                    }

                    if (count - 1 > 0){
                        Unit u2 = new Unit(count-1,1);
                        newCBitMap.add(u2);
                    }
                    break;

                }else if(index == count){
                    Unit u1 = new Unit(count-1,1);
                    newCBitMap.add(u1);

                    if( i == (cbitmap.size()-1)){
                        newCBitMap.add(new Unit(1,0));
                    }else{
                        Unit u3 = cbitmap.get(i + 1);
                        u3.count++;
                    }
                    break;
                }else{
                    Unit u1 = new Unit(index - 1, 1);
                    Unit u2 = new Unit(1,0);
                    Unit u3 = new Unit(count - index, 1);
                    newCBitMap.add(u1);
                    newCBitMap.add(u2);
                    newCBitMap.add(u3);
                    break;
                }
            }
        }

        for(j = i+1; j < cbitmap.size(); j++){
            newCBitMap.add(cbitmap.get(j));
        }
        return newCBitMap;

    }


    /**
     *
     * setOne
     *
     * @param index
     * @param cbitmap
     * @return
     */
    public ArrayList<Unit> setOne(int index, ArrayList<Unit> cbitmap){
        int i,count=0,j;
        ArrayList<Unit> newCBitMap = new ArrayList<>();
        for( i = 0; i < cbitmap.size(); i++){
            Unit unit = cbitmap.get(i);
            count =unit.count;
            if(index > count){
                newCBitMap.add(unit);
                index = index - count;
            }else{
                if(index == 1){
                    if (newCBitMap.isEmpty()){
                        if (count > 1){
                            Unit firstUnit = new Unit(1, 1);
                            newCBitMap.add(firstUnit);
                        }else {
                            Unit nxtUnit = cbitmap.get(i + 1);
                            nxtUnit.count++;
                        }

                    }else {
                        Unit lastUnit = newCBitMap.get(newCBitMap.size()-1);
                        lastUnit.count++;
                    }

                    if (count -1 > 0){
                        Unit u2 = new Unit(count-1,0);
                        newCBitMap.add(u2);
                    }
                    break;

                }else if(index == count){
                    Unit u1 = new Unit(count-1,0);
                    newCBitMap.add(u1);

                    if( i == (cbitmap.size()-1)){
                        newCBitMap.add(new Unit(1,1));
                    }else{
                        Unit u3 = cbitmap.get(i+1);
                        u3.count++;
                    }
                    break;
                }else{
                    Unit u1 = new Unit(index-1,0);
                    Unit u2 = new Unit(1,1);
                    Unit u3 = new Unit(count - index, 0);
                    newCBitMap.add(u1);
                    newCBitMap.add(u2);
                    newCBitMap.add(u3);
                    break;
                }
            }
        }



        for(j = i+1; j < cbitmap.size(); j++){
            newCBitMap.add(cbitmap.get(j));
        }
        return newCBitMap;
    }

    /**
     * addOne
     *
     * @param index
     * @param cbitmap
     * @return
     */
    public ArrayList<Unit> addOne(int index, ArrayList<Unit> cbitmap){

        ArrayList<Unit> newCBitMap = new ArrayList<>();

        int size = cbitmap.size();
        for (int i = 0; i < size - 1; i++) {
            Unit unit = cbitmap.get(i);
            newCBitMap.add(unit);
        }
        Unit lastUnit = cbitmap.get(size - 1);
        if (lastUnit.bit == 1){
            ++lastUnit.count;
            newCBitMap.add(lastUnit);

        }else {
            newCBitMap.add(lastUnit);
            Unit unit = new Unit(1,1);
            newCBitMap.add(unit);
        }
        return newCBitMap;
    }

    /**
     * addZero
     *
     * @param index
     * @param cbitmap
     * @return
     */
    public ArrayList<Unit> addZero(int index, ArrayList<Unit> cbitmap){
        ArrayList<Unit> newCBitMap = new ArrayList<>();

        int size = cbitmap.size();
        for (int i = 0; i < size - 1; i++) {
            Unit unit = cbitmap.get(i);
            newCBitMap.add(unit);
        }

        Unit lastUnit = cbitmap.get(size - 1);
        if (lastUnit.bit == 0){
            ++lastUnit.count;
            newCBitMap.add(lastUnit);

        }else {
            newCBitMap.add(lastUnit);
            Unit unit = new Unit(1,0);
            newCBitMap.add(unit);
        }

        return newCBitMap;
    }
}
