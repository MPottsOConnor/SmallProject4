<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="java.io.File, java.io.IOException, java.util.List, java.util.LinkedList"%>

<!DOCTYPE html>
<%!String PDF_EXT = ".pdf";

	List<File> getFileDataList(File folder) {

		List<File> fileDataList = new LinkedList<File>();

		File[] files = folder.listFiles(); // File objects returned can be folders

		for (int i = 0; i < files.length; i++) {

			if (files[i].isFile()) {
				String filename = files[i].getName();

				if (filename.endsWith(PDF_EXT)) {
					fileDataList.add(files[i]);
				}
			}
		}

		return fileDataList;
	}%>
<%
	final long serialVersionUID = 1L;

	String pdfDirname = "PDF-Slides";
	String pdfDirnameFull = getServletContext().getRealPath(pdfDirname);
	File pdfDirectory = new File(pdfDirnameFull);
	List<File> fileDataList = getFileDataList(pdfDirectory);
%>

<html>
<head>
<meta charset="UTF-8" />
<title>Question 3: PDF Slide Display</title>
</head>
<body>


	<%
		if (!pdfDirectory.exists()) {
	%>
	<p>Slides folder <%=pdfDirnameFull%> does not exist.</p>
</body>
</html>
<%
	return;
	}
%>

<table>
	<tr>
		<th></th>
		<th>Presentation Title</th>
	</tr>
	<%
		for (int i = 0; i < fileDataList.size(); i++) {
			File fullFile = fileDataList.get(i);
			String pdf_href = pdfDirname + "/" + fullFile.getName();
			String display_str = fullFile.getName();
			display_str = display_str.substring(0, display_str.lastIndexOf('.'));
			display_str = display_str.replaceAll("_", " ").replaceAll("-", " ");
			String tooltip = "Right click to download " + display_str;
	%>

	<tr>
		<td><a href='<%=pdf_href%>'><img src='<%=pdfDirname%>/pdf_icon.png' alt='<%=tooltip%>' title='<%=tooltip%>' />
		</a></td>
		<td><%=display_str%></td>
	</tr>

	<%
		}
	%>

</table>


</body>
</html>
