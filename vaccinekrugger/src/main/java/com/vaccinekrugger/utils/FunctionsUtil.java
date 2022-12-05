package com.vaccinekrugger.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FunctionsUtil {
	public static boolean validateFormatDate(String strFecha){
	    String regx = "^\\d{4}([\\-.])(0?[1-9]|1[1-2])\\1(3[01]|[12][0-9]|0?[1-9])$";
	    Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(strFecha);
	    return matcher.find();
	}
	
	public static Date convertStringToDate(String strFecha) throws ParseException{
		return new SimpleDateFormat("yyyy-MM-dd").parse(strFecha);
	}

	public static String convertDateToString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static boolean validateFormatMail(String strMail){
	    String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	    Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(strMail);
	    return matcher.find();
	}
	
	public static boolean validateOnlyLetters(String strWord) {
        for (int x = 0; x < strWord.length(); x++) {
            char c = strWord.charAt(x);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                return false;
            }
        }
        return true;
    }
	
	public static boolean validateOnlyTenNumbers(String strWord) {
		String regex = "^\\d{10}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(strWord);
		return matcher.matches();
    }
}
