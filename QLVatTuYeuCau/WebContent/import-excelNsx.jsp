<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Import file</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style-noi-san-xuat.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-muc-dich.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
</head>
<body>
	<%
		String status = (String) request.getAttribute("status");
		if (status != null && status.equals("unknownFile"))
			out.println("<script>alert('File nhập không được hỗ trợ. Vui lòng chọn file excel')</script>");
		else if (status != null && status.equals("formatException"))
			out.println("<script>alert('Lỗi kiểu dữ liệu. Vui lòng chọn đúng mẫu!!!l')</script>");
	%>
<%-- 	<form action="<%=siteMap.readExcelNsx %>" method="post" enctype="multipart/form-data" > --%>
<!-- 		<input type="file" name="file" accept=".xls, .xlsx"> -->
<!-- 		<input value="uploadFile" name="action" type="submit"> -->
<!-- 	</form> -->
	
	<form id="import-formnsx" action="<%=siteMap.readExcelNsx %>" method="post" enctype="multipart/form-data" style="height: 200px;text-align: center;" onsubmit="document.body.style.cursor='wait'; return true;">
									<input type="file" name="file" accept=".xls, .xlsx" class="text" style="padding-left: 0px;">
									<div class="group-button">
										<input value="uploadFile" name="action" type="submit" class="button" style="width: 100px;font-size: 17px;text-align: center;" onclick="document.body.style.cursor='wait'; return true;">
										<input value="Thoát" onclick="showForm2('view-table-bo-phan','import-formct', false);" type="button" class="button"  style="width: 70px;text-align: center;font-size: 17px;">
									</div>
	</form>
</body>
</html>