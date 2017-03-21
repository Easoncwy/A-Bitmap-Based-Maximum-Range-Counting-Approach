package com.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 备用,用来读取未压缩的bitmap文件
 * @author eason
 *
 */
public class BitMapFileRead {
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public Map<String, BitSet> bitmapFileRead(String file) throws IOException, ParseException
	{
		Map<String, BitSet> codedMap = new HashMap<>();
		File f = new File(file);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		String line = "";
		int count = 0;
		while ( (line = br.readLine()) != null){
			String[] context = line.split(",");
			String time = context[0];
			String bitmapString = context[1];
			BitSet bitmap = new BitSet();
			for (int i = 0; i < bitmapString.length(); i++) {
				if (bitmapString.charAt(i) == '1') {
					bitmap.set(i);
				} 
			}
			codedMap.put(time, bitmap);
			++count;
		}
		System.out.println("共读入"+  count + "条BitMap");
		return codedMap;
	}
}
