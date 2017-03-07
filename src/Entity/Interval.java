package Entity;

import java.text.ParseException;

import Method.Time;

public class Interval {
	public  String start;
	public  String end;
	
	public Interval(String start, String end){
		this.start = start;
		this.end = end;
	}
	
	public String getStart(){
		return start;
	}
	public String getEnd(){
		return end;
	}
	
	public boolean Exist(String t) throws ParseException{
		Time time = new Time();
		Long TIME = time.uniformTime(t);
		Long startTIME = time.uniformTime(start);
		Long endTIME = time.uniformTime(end);
		
		if ((startTIME <= TIME) && (TIME <= endTIME)) {
			return true;
		}
		return false;
	}
	public boolean contains(String t) throws Exception{
		Time time = new Time();
		Long TIME = time.uniformTime(t);
		Long startTIME = time.uniformTime(start);
		Long endTIME = time.uniformTime(end);
		if ((startTIME <= TIME) && (TIME <= endTIME)){
			return true;
		}
		return false;


	}
	public boolean smaller(String t) throws ParseException{
		Time time = new Time();
		Long TIME = time.uniformTime(t);
		Long startTIME = time.uniformTime(start);
		Long endTIME = time.uniformTime(end);
		if (endTIME < TIME)
			return true;
		return false;
	}



	
	public boolean bigger(String t) throws ParseException{
		Time time = new Time();
		Long TIME = time.uniformTime(t);
		Long startTIME = time.uniformTime(start);
		Long endTIME = time.uniformTime(end);
		if (startTIME > TIME)
			return true;
		return false;
	}
	
	public boolean equalOrGreater(int duration) throws ParseException{
		Time time = new Time();
		
		long startTIME = time.uniformTime(start);
		long endTIME = time.uniformTime(end);
		
		if ((endTIME - startTIME) >= duration) {
			return true;
		}
		return false;
		
	}
	public boolean before(String t) throws ParseException{
		Time time = new Time();
		long TIME = time.uniformTime(t);
		long startTIME = time.uniformTime(start);
		long endTIME = time.uniformTime(end);

		if (endTIME < TIME) {
			return true;
		}
		return false;

	}


	
	public boolean containsInterval(Interval interval) throws ParseException{
		Time time = new Time();
		long intervalStartTime = time.uniformTime(interval.start);
		long intervalEndTime = time.uniformTime(interval.end);
		
		long startTIME = time.uniformTime(start);
		long endTIME = time.uniformTime(end);
		
		if ((startTIME <= intervalStartTime) && (intervalEndTime <= endTIME)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isEqualInterval(Interval interval) throws ParseException{
		Time time = new Time();
		long intervalStartTime = time.uniformTime(interval.start);
		long intervalEndTime = time.uniformTime(interval.end);
		
		long startTIME = time.uniformTime(start);
		long endTIME = time.uniformTime(end);
		
		if ((startTIME == intervalStartTime) && (intervalEndTime == endTIME)) {
			return true;
		}
		return false;
	}



}
