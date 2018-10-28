package com.indeed.tests;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;

public class IndeedJobSearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
         
		
		//create firefox driver to drive the browser
		
		WebDriver driver= new FirefoxDriver();
		
		// find what field and enter selenium
		driver.get("http://www.indeed.com");
		// find location and enter nj
		
		// finds findjobs button and click on it
		
		// from job search result, get page title and jobs count message
	}

}
