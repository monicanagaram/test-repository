package com.openqa.selenium;

public class FirefoxDriver implements WebDriver {
	
	public FirefoxDriver(){
		System.out.println("Launching Firefox");
	}
	

		public static void main(String[] args) {
			// TODO Auto-generated method stub

		}
		@Override
		public void click(String locator) {

			System.out.println("clicked on an element in Firefox:"+locator);

		}
		@Override
		public void sendkeys(String locater, String value) {

			System.out.println("typed in an element in Firefox:"+locater+"value entered:"+value);
		}

	}
