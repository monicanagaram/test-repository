package com.thoughtworks.sel;

public class Defaultseleniumclass {
	//constructor

	public Defaultseleniumclass (String serverHost,int serverPort,String browserStartCommand,String browserURL){

		System.out.println("server host:"+serverHost);
		System.out.println("server port:"+serverPort);
		System.out.println("browser start command:"+browserStartCommand);
		System.out.println("browser url:"+browserURL);
	}
	//method
	public void start(){
		System.out.println("starting the selenium");
	}



	public void click(String locater){
		System.out.println("Clicked on an element:" +locater);
	}

	public void type(String locater,String value){
		System.out.println("selected the element as:"+locater+ "typed the value as:"+value);
	}
}
