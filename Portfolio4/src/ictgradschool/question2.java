package ictgradschool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterUser
 */

public class question2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int COMPLETED_FORM_COUNT = 3;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public question2() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		out.println("<!doctype html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Register for ICT GradSchool Newsletter</title>");
		out.println("<meta charset='UTF-8' />");
		out.println("</head>");
		out.println("<body>");

		HttpSession session = request.getSession();

		Enumeration<String> params = request.getParameterNames();
		if (params.hasMoreElements()) {
			// This is the result of either a submit form, or clear form

			if (request.getParameter("cleardata") != null) {
				
				session.setAttribute("name",  null);
				session.setAttribute("lastName", null);
				session.setAttribute("email",  null);
				createForm(session, out);


			} else {
				int numFieldsFilledIn = storeFormDataSession(request);

				if (numFieldsFilledIn == 0) {
					out.println("<p>No information entered.</p>");
					out.println("<p>To return to the registration page, click <a href='question2'>here</a>.</p>");
				}

				else if (numFieldsFilledIn != COMPLETED_FORM_COUNT) {
					out.println("<p>The data you've entered so far has been saved.</p>");
					out.println("<p>To continue your registration click <a href='question2'>here</a>.</p>");
				} else {
					out.println("<p>You have been registered!</p>");
				}
			}
		} else {

			createForm(session, out);
		}

		// close off the HTML page
		out.println("</body>");
		out.println("</html>");
	}

	protected int storeFormDataSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int count = 0;
		
		if(request.getParameter("name")!=null && !request.getParameter("name").isEmpty()){
			session.setAttribute("name",  request.getParameter("name"));
			count +=1;
			}
		if(request.getParameter("lastName")!=null && !request.getParameter("lastName").isEmpty()){
			session.setAttribute("lastName",  request.getParameter("lastName"));
			count +=1;
		}
		if(request.getParameter("email")!=null && !request.getParameter("email").isEmpty()){
			session.setAttribute("email",  request.getParameter("email"));
			count +=1;
		}
		
	    return count;
	    
	}

	protected Properties getFormDataSession(HttpSession session) {

		Properties userDataProperties = new Properties();

		// Retrieves the three form fields from the session
		// and (for the ones that exist) stores them in a Properties class
		
		if (session.getAttribute("name") != null) {
			userDataProperties.put("name", session.getAttribute("name").toString());
		} 
		
		if (session.getAttribute("lastName") != null) {
			userDataProperties.put("lastName", session.getAttribute("lastName").toString()); 
		}
		
		if (session.getAttribute("email") != null) {
			userDataProperties.put("email", session.getAttribute("email").toString()); 
		}
		
		return userDataProperties;
	}

	protected void createForm(HttpSession session, PrintWriter out) {

		Properties formFields = this.getFormDataSession(session);
		String nameField = (String) formFields.get("name");
		if(nameField==null){
			nameField = "";
		}
		String lastNameField = (String) formFields.get("lastName");
		if(lastNameField==null){
			lastNameField = "";
		}
		String emailField = (String) formFields.get("email");
		if(emailField==null){
			emailField = "";
		}
		
		out.println(
				"<form style='width:500px;margin:auto;' id='userform_id' name='userform' method='get' action='question2'>");

		out.println("<fieldset><legend>Register for the ICT GradSchool Newsletter:</legend>");

		out.println("<p>Form elements go here!</p>");

		out.println("<p>Firstname:</p>");
		
		out.print("<input type='text' name='name' value='" + nameField + "' id='name'>");

		out.println("<p>Last Name (No digits):</p>");
			
		out.print("<input type='text' name='lastName' value='" + lastNameField + "' pattern='[A-Za-z]+' id='lastName'>");

		out.println("<p>Your email:</p>");

	
		out.print("<input type='email' name='email' value='" + emailField + "' id='email'>");

		out.println("<input type='submit' name='submit_button' id='submit_id' value='Register' /></p>");

		out.println("</fieldset>");
		out.println("</form>");

		out.println(
				"<form  style='width:500px;margin:auto;' id='clearform_id' name='clearform' method='get' action='question2'>");
		out.println("<input type='hidden' name='cleardata' id='cleardata_id' value='clear' /></p>");
		out.println("<input type='submit' name='clear_button' id='clear_id' value='Clear Fields' /></p>");

		out.println("</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
