package test;

import java.util.Date;

public class test {
	public static void main(String[] args){
		Date date = new Date();
		System.out.println(date.toString());
		String dateStr="2016-01-01"; 
		java.sql.Date date11 = new java.sql.Date(Integer.valueOf(dateStr.substring(0, 4)).intValue()-1900, Integer.valueOf(dateStr.substring(5, 7)).intValue()-1, Integer.valueOf(dateStr.substring(8)).intValue());
		System.out.println(date11.toString());
	}
	
}
