package ictgradschool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class question1
 */
// @WebServlet("/question1")
public class question1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public question1() {
		super();
	}

	/**
	 * We set the directory that the transactions will be written to in the
	 * during servlet configuration in web.xml
	 */
	protected String transactionDir = null;

	/**
	 * 
	 * @param servletConfig
	 *            a ServletConfig object containing information gathered when
	 *            the servlet was created, including information from web.xml
	 */
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		this.transactionDir = servletConfig.getInitParameter("transaction-directory");
	}

	/** init(ServletConfig) => void **/

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// You can uncomment the following line to check the Web server
		// if necessary
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());

		PrintWriter out = response.getWriter();

		String refNum = request.getParameter("refNum");

		// If a transaction directory hasn't been set in the web.xml, then
		// default to one within our web context (which might be in some
		// strange directory deep within .metadata in the workspace)
		if (this.transactionDir == null) {
			ServletContext servletContext = getServletContext();
			this.transactionDir = servletContext.getRealPath("/Transactions");
		}
		File jsonFile = new File(this.transactionDir, refNum + ".json");
		

		JSONObject jsonObject = new JSONObject();
	
		// adds address and its field to jsonObject
		String addressField = "";
		if (request.getParameter("address") != null) {
			addressField = request.getParameter("address");
		}
		

		// adds postcode and its field to jsonObject
		String postCodeField = "";
		if (request.getParameter("postCode") != null) {
			postCodeField = request.getParameter("postCode");
		}		
	
		// adds card name and its field to jasonObject
		String cardNameField = "";
		if (request.getParameter("cardName") != null) {
			cardNameField = request.getParameter("cardName");
		}
					
		// adds card type and its field to jsonObject
		String cardTypeField = "";
		if (request.getParameter("cardType") != null) {
			cardTypeField = request.getParameter("cardType");
		}
	

		// concatonates and adds fields of card number
		String cardNumber = "";

		if (request.getParameter("cardNo1") != null && request.getParameter("cardNo2") != null
				&& request.getParameter("cardNo3") != null && request.getParameter("cardNo4") != null) {
			cardNumber = request.getParameter("cardNo1") + "-" + request.getParameter("cardNo2") + "-"
					+ request.getParameter("cardNo3") + "-" + request.getParameter("cardNo4");
		}
	
		//create billingAddressObject JSON Object to hold the data for address and post code
		JSONObject billingAddressObject = new JSONObject();
		billingAddressObject.put("address", addressField);
		billingAddressObject.put("postCode", postCodeField );
		//put billingAddressObject into jsonObject
		jsonObject.put("billingAddress", billingAddressObject);
		//create creditCardObject JSON Object to hold the data for card name, type and number		
		JSONObject creditCardObject = new JSONObject();
		creditCardObject.put("cardName", cardNameField);
		creditCardObject.put("cardType", cardTypeField);
		creditCardObject.put("cardNo", cardNumber);
		//put creditCardObject into jsonObject
		jsonObject.put("creditCard", creditCardObject);
		
		out.println("<p>Location to save json file = " + jsonFile + "</p>");

		boolean isSuccessful = saveJSONObject(jsonFile, jsonObject, out);

		if (isSuccessful) {
			out.println("<p>Data saved: OK</p>");
		}

	}

	/** doGet(HttpServletRequest, HttpServletResponse) => void **/

	/**
	 * No special actions for POST so chains throught to doGet()
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/** doPost(HttpServletRequest, HttpServletResponse) => void **/

	/**
	 * Writes the given JSONObject (in JSON format) to the specified file path
	 * 
	 * @param file
	 * @param jsonRecord
	 * @return true if file written successfully, false otherwise
	 */
	private boolean saveJSONObject(File file, JSONObject jsonRecord, PrintWriter out) {
		boolean statusOK = true;

		// code that returns false if file exists removed because ideal
		// situation will result in an override

		// Added code to ensure the JSON object's folder exists.
		File folder = file.getParentFile();
		if (!folder.exists()) {
			folder.mkdirs();
		}

		String json_string = JSONObject.toJSONString(jsonRecord);

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(json_string);

			// Please uncomment throw exception below to test error message
            // throw new IOException();

		} catch (IOException e) {
			e.printStackTrace();
			statusOK = false;
			out.println("<p>An error occured</p>");
			out.println("<p>File not found</p>");
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				statusOK = false;
				out.println("<p>An error occured</p>");
				out.println("<p>File write error</p>");
			}
		}

		return statusOK;
	}

}
