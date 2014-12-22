package com.demo.shoppingcart.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataReader {

	public static String getStringFromFile(InputStream inputStream) throws Exception {
		StringBuilder buf=new StringBuilder();
	    
	    BufferedReader in=
	        new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	    String str;

	    while ((str=in.readLine()) != null) {
	      buf.append(str);
	    }

	    in.close();
		return buf.toString();
	}
}
