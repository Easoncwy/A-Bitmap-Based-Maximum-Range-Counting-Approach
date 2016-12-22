package Method;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
	/**
	 * 时间格式转换
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public long uniformTime(String s) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Long TIME = ((Date)sdf.parse(s)).getTime();
		
		return TIME;
		
	}
	
}
