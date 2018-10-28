package testMyRCProject;

import com.thoughtworks.sel.Defaultseleniumclass;

//import com.thoughtworks.selenium.DefaultSelenium;

public class testSample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Defaultseleniumclass selenium = new Defaultseleniumclass("localhost",4444, "firefox", "http://gmail.com");
		selenium.start();
		selenium.click("submit button");
		selenium.type("text box", "training");
	}

}
 