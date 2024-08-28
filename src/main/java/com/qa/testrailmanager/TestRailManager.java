package com.qa.testrailmanager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;


public class TestRailManager {

	public static String TEST_RUN_ID = "163";
	public static String TEST_RAIL_USERNAME = "vishal.giram@eqanim.com";
	public static String TEST_RAIL_PASSWORD = "Vishal@123";
	
	public static String TEST_RAIL_ENGINE_URL = "https://wciinc.testrail.io/";
	
	public static int TEST_CASE_PASS_STATUS = 1;
	public static int TEST_CASE_FAIL_STATUS = 5;
	
	
	public static void addResultsForTestCase(String testCaseId, int status , String error) {
		
		@SuppressWarnings("unused")
		String testrunid = TEST_RUN_ID;
		APIClient client = new APIClient(TEST_RAIL_ENGINE_URL);
		client.setUser(TEST_RAIL_USERNAME);
		client.setPassword(TEST_RAIL_PASSWORD);
		
		Map<String,Object>data = new HashMap<String,Object>();
		data.put("status_id", status);
		data.put("comment", "This test is executed by automation Script "+error);
		
		if(testCaseId != null) {
		try {
			System.out.println("add_result_for_case/"+TEST_RUN_ID+"/"+testCaseId);
			client.sendPost("add_result_for_case/"+TEST_RUN_ID+"/"+testCaseId, data);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (APIException e) {
			e.printStackTrace();
		}
		}else {
			System.out.println("TestCase Not Having the testcase ID");
		}
		
	}
	
	
}
