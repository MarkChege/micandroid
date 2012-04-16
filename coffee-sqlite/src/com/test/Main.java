package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
	
	public static void main(String[] args) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:db/test.db");
			Statement stmt = conn.createStatement();
			String sql = "insert into user values(1, 'test', 'coffee')";
			stmt.execute(sql);
			conn.close();
//			1317735900000</startTime><endTime>1317909540000</endTime>
			//1318135623478
			//1318129200000
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1318129200000L)));
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2011-10-04 21:45:00").getTime());
//			SQLiteDatabase db = new SQLiteDatabase
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}