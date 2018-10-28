package com.openqa.selenium;

public class ChromeDriver implements WebDriver {

	public ChromeDriver(){
		System.out.println("Launching Chrome");
	}
	

	@Override
	public void click(String locator) {
		
		System.out.println("clicked on an element in Chrome:"+locator);
		
	}
	@Override
	public void sendkeys(String locater, String value) {
		
		System.out.println("typed in an element in Chrome:"+locater+"value entered:"+value);
	}

}
