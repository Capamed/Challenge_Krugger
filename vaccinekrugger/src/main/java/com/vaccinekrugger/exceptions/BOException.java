package com.vaccinekrugger.exceptions;

import java.util.Locale;

import com.vaccinekrugger.utils.MessagesUtil;

public class BOException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private static final Locale localeDefault = new Locale("es", "EC");
	private String codeMessage;
	private Object[] messageParametersValues;
	private Object data;

	public BOException() {
		super();
	}

	public BOException(String codeMessage, Throwable cause) {
		super(MessagesUtil.getMessage(codeMessage, localeDefault), cause);
		this.codeMessage  = codeMessage;
	}

	public BOException(String codeMessage) {
		super(MessagesUtil.getMessage(codeMessage, localeDefault));
		this.codeMessage  = codeMessage;
	}

	public BOException(Throwable cause) {
		super(cause);
	}
	
	public BOException(String codeMessage, Object data) {
		super(MessagesUtil.getMessage(codeMessage, localeDefault));
		this.codeMessage  = codeMessage;
		this.data = data;
	}
	
	public BOException(String codeMessage, Object[] messageParametersValues, Throwable cause) {
		super(MessagesUtil.getMessage(codeMessage, messageParametersValues, localeDefault), cause);
		this.codeMessage  = codeMessage;
		this.messageParametersValues = messageParametersValues;
	}
	
	public BOException(String codeMessage, Object[] messageParametersValues) {
		super(MessagesUtil.getMessage(codeMessage, messageParametersValues, localeDefault));
		this.codeMessage  = codeMessage;
		this.messageParametersValues = messageParametersValues;
	}
	
	public BOException(String codeMessage, Object[] messageParametersValues, Object data) {
		super(MessagesUtil.getMessage(codeMessage, messageParametersValues, localeDefault));
		this.codeMessage  = codeMessage;
		this.messageParametersValues = messageParametersValues;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String getTranslatedMessage(String strLanguage) {
		Locale locale = MessagesUtil.validateSupportedLocale(strLanguage);
		if (localeDefault.equals(locale)) {
			return super.getMessage();
		} else {
			if (messageParametersValues != null && messageParametersValues.length > 0)
				return MessagesUtil.getMessage(codeMessage, messageParametersValues, locale);
			else
				return MessagesUtil.getMessage(codeMessage, locale);
		}
	}

	public String getCodeMessage() {
		return codeMessage;
	}

	public Object[] getMessageParametersValues() {
		return messageParametersValues;
	}
}
