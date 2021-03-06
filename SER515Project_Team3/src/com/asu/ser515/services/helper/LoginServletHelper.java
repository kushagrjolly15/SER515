package com.asu.ser515.services.helper;

/**
 * @author amanjotsingh
 * @date 10/24/2019
 * 
 * Helper class to determine which page to route to as per the user.
 * */

public class LoginServletHelper {
	
	private static String errorPage = "/error.html";
	private static String exceptionPage = "/exception.html";
	private static String adminPage = "/admin.jsp";
	private static String teacherPage = "/teacherLandingPage.jsp";
	private static String studentPage= "/studentLandingPage.jsp";
	
	// method to map the user to it's page
	public String mapUserToPage(int dbResult) {
		if (dbResult == 0) {
			return errorPage;
		}
		if (dbResult == -1) {
			return exceptionPage;
		}
		else {
			DBConnServiceHelper dbHelper = new DBConnServiceHelper();
			String role = dbHelper.mapDBtoUsertype(dbResult);
			if (role.equalsIgnoreCase("admin")) {
				return adminPage;
			}
			if (role.equalsIgnoreCase("teacher")) {
				return teacherPage;
			}
			if (role.equalsIgnoreCase("studentGrade_1") || role.equalsIgnoreCase("studentGrade_6")) {
				return studentPage;
			}
		}
		return null;
	}
}
