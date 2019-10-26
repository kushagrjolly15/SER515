package com.asu.ser515.controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asu.ser515.model.User;
import com.asu.ser515.services.helper.LoginServletHelper;
import com.asu.ser515.services.impl.DBConnServiceImpl;

/**
 * Controller class to handle the login functionality of the application. It
 * will authenticate the user and redirect to their respective web pages.
 * 
 * @author amanjotsingh
 * @date 09/25/2019
 * 
 * @author kushagrjolly
 * @date 09/29/2019
 * 
 */

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	// doPost method to handle form submit coming from web page
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		String password = req.getParameter("password");
		String userName = req.getParameter("username");
		DBConnServiceImpl serviceImpl = new DBConnServiceImpl();
		User olduser = serviceImpl.authenticateUser(userName, password);
		System.out.println(olduser.getU_ID());
		LoginServletHelper loginServletHelper = new LoginServletHelper();
		String userPage = loginServletHelper.mapUserToPage(olduser.getUserType());
		try {
			HttpSession session = req.getSession(true);
			session.setAttribute("firstname", olduser.getFirstName());
			session.setAttribute("lastname", olduser.getLastName());
			session.setAttribute("usertype", olduser.getUserType());
			session.setAttribute("u_id", olduser.getU_ID());
			session.setAttribute("username", olduser.getUserName());
			req.getRequestDispatcher(userPage).forward(req, res);
		} catch (IOException ioExc) {
			ioExc.printStackTrace();
		} catch (ServletException servletExc) {
			servletExc.printStackTrace();
		}
	}
}
	
