package com.projects.demo.junit;

import android.test.AndroidTestCase;

public class TestMath extends AndroidTestCase {
	static final String LOG_TAG = "MathTest";
	private int i1;
	private int i2;
	
	@Override
	protected void setUp() throws Exception {
		i1 = 2;
		i2 = 3;
	}
	
	public void testAdd() {
		assertTrue("testAdd failed", ((i1 + i2) == 5));
	}
	
	public void testDec() {
		assertTrue("testDec failed", ((i2 - i1) == 1)); 
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Override 
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
		//Log.d( LOG_TAG, "testAndroidTestCaseSetupProperly" );
	}        
}    
