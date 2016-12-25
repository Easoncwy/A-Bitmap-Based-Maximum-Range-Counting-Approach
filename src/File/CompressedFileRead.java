package File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Entity.Unit;

public class CompressedFileRead {
	/**
	 * 读取压缩后的BitMapFile,存为 < time,compBitMap >
	 *
	 * @param  compfile            压缩后的的bitmap文件位置
	 * @return compressedMap   每个时间对应一个压缩后的压缩码
	 * @throws IOException
	 * @throws ParseException
	 */
	public Map<String, ArrayList<Unit>> compressedFileRead(String compfile) throws IOException, ParseException
	{
		File f = new File(compfile);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		String line = "";
		int count = 0;
		Map<String, ArrayList<Unit>> compressedMap = new HashMap<>();
		while ( (line = br.readLine()) != null){
			String[] context = line.split("-->");
			String time = context[0];
			String bitmapString = context[1];
			ArrayList<Unit> arrayList = new ArrayList<>();		
			String[] bitmaps = bitmapString.split(":");
			for(String eachBitMap : bitmaps){
				String[] info = eachBitMap.split(",");
				int num = Integer.parseInt(info[0]);
				int bit = Integer.parseInt(info[1]);
				Unit u = new Unit(num, bit);
				arrayList.add(u);
			}
			
			compressedMap.put(time, arrayList);
			++count;
		}
		System.out.println("共读入"+  count + "条压缩BitMap");
		return compressedMap;
	}
	
}
