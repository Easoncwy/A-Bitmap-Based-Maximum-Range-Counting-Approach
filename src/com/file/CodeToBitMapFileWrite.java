
package com.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import com.entity.Database;
import com.entity.Interval;
import com.entity.User;
import com.method.NewAlgorithm;
public class CodeToBitMapFileWrite {
	/**
	 * 给每个独特的time编码成bitmap, 写入文件
	 *
	 * @param file 写入的BitMap的文件路径
	 * @param db   
	 * @param markUserMap   
	 * @param allTimeArray  
	 * @return  codedMap  每个时间对应一个BitMap，Time-->BitMap
	 * @throws IOException
	 * @throws ParseException
	 */
	public Map<String, BitSet> codeToBitMap(
			String file,
			String info_bitmapCoding,
			Database db,
			Map<Integer,User> markUserMap,
			ArrayList<String> allTimeArray) throws IOException, ParseException
	{
		File f = new File(file);
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);

		NewAlgorithm al = new NewAlgorithm();
        //codeToBitMap用于存储每个时刻对应的bitmap编码键值对
        Map<String, BitSet> codedMap = new HashMap<>();
    	BitSet bitmap = null;
    	/*
    	 * 计数
    	 */
    	int count = 1;
    	int costTime = 0;

		File outfile = new File(info_bitmapCoding);
    	FileWriter filewriter = new FileWriter(outfile);
    	BufferedWriter buffwriter = new BufferedWriter(filewriter);
    	for (String time : allTimeArray) {
    		Calendar c = Calendar.getInstance();
    		String s = "";
    		bitmap = new BitSet();
    		
    		/** 建立bitmap*/
    		for (Integer order:markUserMap.keySet()){
    			User newUser = markUserMap.get(order);
    			int index = newUser.index;
    			for (int i = index; i < newUser.intervals.size(); i++) {
					Interval interval = newUser.getInterval(i);
					if (interval.Exist(time)) {
						int userOrderFigure = order;
						bitmap.set(userOrderFigure, true);
						newUser.index = index;
						break;
					}
					else {
						index++;
						if(interval.bigger(time))
							break;
					}
					
				}
    		}
			codedMap.put(time, bitmap);
			
			s = time + ",";
			ArrayList<Integer> bitmapArray = al.convertBitmapToList(bitmap,markUserMap.size());
			for (int i = 0; i < bitmapArray.size(); i++) {
				s += bitmapArray.get(i); 
			}
			s += "\n";
			bw.write(s);

			Calendar ca = Calendar.getInstance();
			long cost = ca.getTimeInMillis() - c.getTimeInMillis();
			costTime += cost;
			System.out.println("第" + count + "个bitmap编码完成");
			System.out.println("时间花费: " + Integer.toString((int) cost) + " ms");
			buffwriter.write("第" + count + "个bitmap编码完成\n");
			buffwriter.write("时间花费: " + Integer.toString((int) cost) + " ms\n");
			count++;	
		}
    	buffwriter.write("bitmap编码总共花费" + costTime + "ms\n");
    	bw.close();
    	buffwriter.close();
        return codedMap;
	}
}
