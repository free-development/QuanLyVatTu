<%@page import="model.CongVan"%>
<%@page import="model.File"%>
<%@page import="map.siteMap"%>
<%@page import="java.util.HashMap"%> 
<%@page import="java.util.ArrayList"%>
<%@page import="util.FileUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
// 	GstFormsAttachFile gstFormFile = (GstFormsAttachFile) request.getAttribute("gstFormFile"); 
	
	response.setContentType("APPLICATION/OCTET-STREAM");   
	java.io.File file = (java.io.File) request.getAttribute("file");
// 		java.io.File file = new java.io.File(gstFormFile.getPath());
	response.setHeader("Content-Disposition","attachment; filename=\"" + FileUtil.getNameFile(file) + "." + FileUtil.getExtension(file));
	
	java.io.FileInputStream fileInputStream=new java.io.FileInputStream(file);  
	          
	int i;   
	while ((i=fileInputStream.read()) != -1) {  
	  out.write(i);   
	}   
	fileInputStream.close();
 %>
</body>
</html>