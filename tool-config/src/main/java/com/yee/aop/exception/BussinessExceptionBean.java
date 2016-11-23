package com.yee.aop.exception;

import com.yee.util.AppUtil;
import org.springframework.context.MessageSource;

import java.util.Locale;


public class BussinessExceptionBean {

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BussinessExceptionBean(String errorCode) {
		setErrorMessage(getMessage(errorCode));
	}

	public BussinessExceptionBean(String errorCode, String[] args) {
		setErrorMessage(getMessage(errorCode, args));
	}

	/**
	 * 获取国际化文字(不带参数)
	 * 
	 * @author chenyiqiang
	 * @param code
	 * 
	 */
	private String getMessage(String code) {
		return getMessage(code, null);
	}

	/**
	 * 获取国际化文字(带参数)
	 * 
	 * @author chenyiqiang
	 * @param code
	 * @param args
	 * 
	 */
	private String getMessage(String code, String[] args) {
		String message = "";
		MessageSource messageSource = (MessageSource) AppUtil
				.getBean("messageSource");
		message = messageSource.getMessage(code, args, Locale.CHINA);
		return message;
	}
}
