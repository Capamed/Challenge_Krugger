package com.vaccinekrugger.utils;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessagesUtil {

	private static ResourceBundle bundle;
	private static String fileMessages = "messages";
	public static final Locale Locale = new Locale("es", "EC");
	private static final String LOCALE_ES="es-EC";
	private static final String LOCALE_ING="en-US";

	public static String getMessage(String strKey, Locale locale) {
		if(isKey(strKey)) {
			bundle = ResourceBundle.getBundle(fileMessages, locale);
			return new String(bundle.getString(strKey).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
		}else
			return strKey;
	}

	public static String getMessage(String strKey, Object[] arrParameters, Locale locale) {
		bundle = ResourceBundle.getBundle(fileMessages, locale);
		String strMensaje = new String(bundle.getString(strKey).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
		if (arrParameters != null && arrParameters.length > 0) {
			for (int i = 0; i < arrParameters.length; i++) {
				strMensaje = strMensaje.replace("{" + i + "}",
						(isKey(arrParameters[i].toString())
								? new String(bundle.getString(arrParameters[i].toString())
										.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8)
								: arrParameters[i].toString()));
			}
		}
		return strMensaje;
	}
	
	private static boolean isKey(String strKey) {
		if (strKey != null && (strKey.contains(".warn.") || strKey.contains(".error.") || strKey.contains(".info.")
				|| strKey.contains(".campos.") || strKey.contains(".response.") || strKey.contains(".etiquetas.")))
			return true;
		else
			return false;
	}
	
	public static String getMessageFieldMandatory(String strKeyCampo, Locale locale) {
		return MessagesUtil.getMessage("project.warn.fieldMandatory",
				new Object[] { MessagesUtil.getMessage(strKeyCampo, locale) }, locale);
	}
	
	public static Locale validateSupportedLocale(String strLanguage) {
		if(strLanguage == null || (!LOCALE_ES.equals(strLanguage) && !LOCALE_ING.equals(strLanguage)))
			strLanguage=LOCALE_ES;
		String[] arrLang = strLanguage.split("-");
    	return new Locale(arrLang[0], arrLang[1]);
	}

}
