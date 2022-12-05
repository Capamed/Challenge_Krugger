package com.vaccinekrugger.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaccinekrugger.exceptions.BOException;


public class FunctionsUtil {
	public static boolean validateFormatDate(String strFecha){
	    String regx = "(^(((0[1-9]|1[0-9]|2[0-8])[/](0[1-9]|1[012]))|((29|30|31)[/](0[13578]|1[02]))|((29|30)[/](0[4,6,9]|11)))[/](19|[2-9][0-9])\\d\\d$)|(^29[/]02[/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)";
	    Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(strFecha);
	    return matcher.find();
	}
	
	public static Date convertStringToDate(String strFecha) throws ParseException{
		return new SimpleDateFormat("dd/MM/yyyy").parse(strFecha);
	}

	public static String convertDateToString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}

	public static String convertirFechaHoraMinString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return sdf.format(date);
	}
	
	public static Date convertirFechaHoraMinDate(String strDate) throws BOException{
		try {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(strDate);
		} catch (ParseException e) {
			throw new BOException("age.warn.fechaFinInvalida");
		}
	}
	
}
