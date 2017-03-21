package com.entity;

public class Unit {
	public int bit;
	public int count;
	
	public Unit(int count, int bit){
		this.bit = bit;
		this.count = count;
	}

	public Unit(Unit u){
		this.bit = u.bit;
		this.count = u.count;
	}

	public int getBit(){
		return bit;
	}
	public int getCount(){
		return count;
	}
	
}

