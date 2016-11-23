package com.yee.aop.exception;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;



public class BussinessException extends RuntimeException {
	private Logger logger=Logger.getLogger(BussinessException.class);
	
	private List<BussinessExceptionBean> bexbeanList;
  
	private boolean flag=false;
	private String msg;
	public BussinessException(String message){
		msg=message;
		logger.error(msg);
	}
	
	public BussinessException(String message,Throwable cause){
		msg=message;
		logger.error(msg,cause);
	}
	
	public BussinessException(BussinessExceptionBean bexbean) {
		flag=true;
		if(bexbeanList==null){
			bexbeanList=new LinkedList<BussinessExceptionBean>();
		}
		bexbeanList.add(bexbean);
		logger.error(bexbean.getErrorMessage());
	}
	
	public BussinessException(BussinessExceptionBean bexbean,Throwable cause) {
		flag=true;
		if(bexbeanList==null){
			bexbeanList=new LinkedList<BussinessExceptionBean>();
		}
		bexbeanList.add(bexbean);
		logger.error(bexbean.getErrorMessage(),cause);
	}
	public BussinessException(List<BussinessExceptionBean> bexbeanList) {
		flag=true;
		this.bexbeanList = bexbeanList;
		for(BussinessExceptionBean bex:bexbeanList){
			logger.error(bex.getErrorMessage());
		}
	}
	public BussinessException() {
		flag=true;
		if(bexbeanList==null){
		bexbeanList=new LinkedList<BussinessExceptionBean>();
		}
	}
	public void addBussinessException(BussinessExceptionBean bex){
		if(bexbeanList==null){
			bexbeanList=new LinkedList<BussinessExceptionBean>();
		}
		bexbeanList.add(bex);
		logger.error(bex.getErrorMessage());
	}
    
	public String getExceptionMessage(){
		if(flag){
			StringBuffer sb=new StringBuffer();
			if(bexbeanList==null||bexbeanList.size()==0){
				
				sb.append("");
				return sb.toString();
			}
			if(bexbeanList.size()==1){
				sb.append(bexbeanList.get(0).getErrorMessage());
				return sb.toString();
			}
			for(BussinessExceptionBean bex:bexbeanList){
				sb.append(bex.getErrorMessage());
				sb.append("\n");
			}
			return sb.toString();
		}else{
			return msg;
		}
	}
}
