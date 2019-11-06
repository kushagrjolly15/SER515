package com.asu.ser515.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asu.ser515.model.QuestionAnswer;
import com.asu.ser515.model.Teacher;
import com.asu.ser515.model.User;
import com.asu.ser515.services.helper.TeacherServletHelper;
import com.asu.ser515.services.impl.DBConnServiceImpl;

/**
 * Controller class to handle the HTTP calls from teacher quiz creation and
 * store quiz into database. The teacher will be redirected to next page from
 * this controller.
 * 
 * @author amanjotsingh
 * @date 10/06/2019
 * 
 * @author kushagrjolly
 * @date 10/21/2019
 * 
 */

@SuppressWarnings("serial")
public class TeacherServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	// doPost method to handle form submit coming from web page
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		String quizname = req.getParameter("quizname");
		String instructions = req.getParameter("instructions");
		HttpSession session = req.getSession(false);
		User teacher = new Teacher();
		System.out.println("---->" + session.getAttribute("firstname"));
		teacher.setFirstName((String) session.getAttribute("firstname"));
		teacher.setLastName((String) session.getAttribute("lastname"));
		teacher.setUserName((String) session.getAttribute("username"));
		teacher.setUser_Id((int) session.getAttribute("u_id"));
		teacher.setUserType((int) session.getAttribute("usertype"));
		DBConnServiceImpl serviceImpl = new DBConnServiceImpl();
		int quizCreated = serviceImpl.quizCreation(teacher.getUser_Id(), quizname, instructions);
		for (int i = 1; i <= 10; i++) {
			if (req.getParameter("Question" + i) != null) {
				if (req.getParameter("Solution" + i) != null) {
					String question = req.getParameter("Question" + i);
					String solution = req.getParameter("Solution" + i);
					QuestionAnswer questionaire = new QuestionAnswer(question, solution);
					int questionsCreated = serviceImpl.questionaireCreation(teacher.getUser_Id(), questionaire);
					TeacherServletHelper teacherHelper = new TeacherServletHelper();
					String teacherRouter = teacherHelper.mapTeacherToPage(quizCreated, questionsCreated);
					try {
						req.getRequestDispatcher(teacherRouter).forward(req, res);
					} catch (IOException ioExc) {
						ioExc.printStackTrace();
					} catch (ServletException servletExc) {
						servletExc.printStackTrace();
					}
				}
			}
		}
	}
}
