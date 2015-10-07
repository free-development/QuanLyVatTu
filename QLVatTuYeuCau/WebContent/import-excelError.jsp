<%@page import="map.siteMap"%>
<%@page import="model.CTVatTu"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Import Error</title>
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-ctvt.css"css" type="text/css" rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/chi-tiet-vat-tu.js"></script>
</head>
<body>
	<%
		String status = (String) request.getAttribute("status");
	if (status != null && status.equals("formatException"))
			out.println("<script>alert('Danh sách chi tiết vật tư bị lỗi khi thêm!')</script>");
	%>
	<%
	ArrayList<CTVatTu> listCTVatTu = (ArrayList<CTVatTu>) request.getAttribute("ctvtListError");
	ArrayList<String> statusError = (ArrayList<String>) request.getAttribute("statusError");
		Long size = (Long) request.getAttribute("size");

		Long pageNum = size/10;
   		
    %>
	<div class="wrapper">
		<div id="main-content">
			<div id="title-content">Danh sách chi tiết vật tư bị lỗi</div>
			<form id="main-form">
					<div id="view-table-chi-tiet" style="height: 500px; margin: 0 auto; overflow: auto;" class="scroll_content">
						<table>
							<tr style="background: #199e5e">
							<th>Số TT</th>
								<th class="four-column">Mã vật tư</th>
								<th class="three-column">Tên vật tư</th>
								<th class="six-column">Mã nơi sản xuất</th>
								<th class="six-column">Mã chất lượng</th>
								<th class="four-column">Đơn vị tính</th>
								<th class="four-column">Lỗi</th>
							</tr>
							<%
									if(listCTVatTu != null) {
									int count = 0;
									int i = 1;
									for(CTVatTu ctVatTu : listCTVatTu) { count++;%>
		
							<tr class="rowContent"
								<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
								<td><%=i++ %></td>
								<td class="col"><%String vtMa = ctVatTu.getVatTu().getVtMa(); if (vtMa != null) out.println(vtMa); else out.println("");%></td>
								<td class="col" style="text-align: left;"><%String vtTen = ctVatTu.getVatTu().getVtTen(); if (vtTen != null) out.println(vtTen); else out.println("");%></td>
								<td class="col" style="text-align: left;"><%String nsxMa = ctVatTu.getNoiSanXuat().getNsxMa(); if (nsxMa != null) out.println(nsxMa); else out.println(""); %></td>
								<td class="col" style="text-align: left;"><%String clMa = ctVatTu.getChatLuong().getClMa(); if (clMa != null) out.println(clMa); else out.println("");%></td>
								<td class="col"><%String dvt = ctVatTu.getVatTu().getDvt().getDvtTen(); if (dvt != null) out.println(dvt);else out.println(""); %></td>
								<td class="col"><%=statusError.get(count - 1) %></td>
							</tr>
							<%} }%>
		
						</table>
					</div>
						<div class="group-button" style="text-align: center;">		
						<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>			
				</form>
				</div>
	</div>
</body>
</html>