package ictgradschool;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class question3
 */

public class question3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private String pdfDirname = "PDF-Slides";
	private String pdfDirnameFull;
	private String PDF_EXT = ".pdf";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public question3() {
        super();
    }
    
    // servletContext() only accessible from init(), not from constructor 
    public void init( ){
        pdfDirnameFull = getServletContext().getRealPath(pdfDirname);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8' />");
		out.println("<title>Question 3: PDF Slide Display</title>");
		out.println("</head>\n<body>");
	
		
		File pdfDirectory = new File (pdfDirnameFull);
		
		if (!pdfDirectory.exists()) {
			out.println("<p>Slides folder " + pdfDirnameFull + " does not exist.</p>");
			out.println("</body>\n</html>");
			return;
		} 
		
		// Get the PDF files, storing them in fileDataList
		List<File> fileDataList = getFileDataList(pdfDirectory);		

		out.println("<table>");
		out.println("<tr><th></th><th>Presentation Title</th></tr>");
		
		for(int i = 0; i < fileDataList.size(); i++) {
			
			File fullFile = fileDataList.get(i);
			
			String pdf_href = pdfDirname + "/" + fullFile.getName();
			String display_str = fullFile.getName();
			display_str = display_str.substring(0, display_str.lastIndexOf('.'));
			display_str = display_str.replaceAll("_"," ").replaceAll("-", " ");
			String tooltip = "Right click to download " + display_str;
					
			out.append("<tr>\n<td>");				
			out.println("<a href='" + pdf_href + "'>");
			out.println("<img src='" + pdfDirname + "/pdf_icon.png' alt='" + tooltip + "' title='" + tooltip + "'/>");
			out.append("</a>");
			out.append("</td>\n<td>");
			out.append(display_str);
			out.append("</td>\n");
			out.println("</tr>");
			
		}
		out.println("</table>\n");
		
		
		out.println("</body>\n</html>");
	}

	private List<File> getFileDataList(File folder) {		
		
		List<File> fileDataList = new LinkedList<File>();		
		
		File[] files = folder.listFiles(); // File objects returned can be folders
				
		for(int i = 0; i < files.length; i++) {
			
			if (files[i].isFile()) {
				String filename = files[i].getName();
				
				if (filename.endsWith(PDF_EXT)) {
					fileDataList.add(files[i]);
				}				
			} 
		}
		
		return fileDataList;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	
}
