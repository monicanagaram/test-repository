package com.openqa.selenium;

public class InternetExplorerDriver implements WebDriver {

	public InternetExplorerDriver(){
		System.out.println("Launching IE");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void click(String locator) {
		
		System.out.println("clicked on an element in IE:"+locator);
		
	}
	@Override
	public void sendkeys(String locater, String value) {
		
		System.out.println("typed in an element in IE:"+locater+"value entered:"+value);
	}

}
