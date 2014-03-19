package com.projects.demo.junit;

import junit.framework.TestSuite;

public class ExampleSuite extends TestSuite {

	public ExampleSuite() {
		addTestSuite(TestMath.class);
	}

}
