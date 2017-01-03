package Test;

import java.util.ArrayList;

/**
 * Created by eason on 2017/1/3.
 */
public class RemoveElementInArrayList {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        list.remove(5);
        System.out.println("删掉 5 以后");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }





    }


}
