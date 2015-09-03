
<%@page import="model.NguoiDung"%>
<%@page import="util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="javax.swing.text.DateFormatter"%>
<%@page import="java.util.logging.SimpleFormatter"%>
<%@page import="model.YeuCau"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@page import="model.CTVatTu"%>
<%@page import="model.VatTu"%>
<%@page import="model.NoiSanXuat"%>
<%@page import="model.ChatLuong"%>
<%@page import="model.CongVan"%>
<%@page import="model.CongVan"%>
<%@page import="model.DonVi"%>
<%@page import="model.TrangThai"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
 <link rel="stylesheet" href="style/style.css" type="text/css">
  <link rel="stylesheet" href="style/style-bao-cao.css" type="text/css">
<link href="style/style-bao-cao-vat-tu-thieu.css" type="text/css"
	rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<meta charset="utf-8">
<title>Xuất chi tiết vật tư</title>
<style type="text/css" media="print">
#print_button{
display:none;
}
@page 
        {
            size: auto A4 landscape;
        	color: black; background: white; }
	   table 
	   { 
	   		font-size: 70%; 
	   			 }
		
</style>
</head>
<body>
	<%
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.bcbdnManage+ "?action=manageBcbdn");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   		}
   	%>
		<% 
    	ArrayList<CTVatTu> listCTVatTu = (ArrayList<CTVatTu>) session.getAttribute("allCTVatTuList");
		String exportToExcel = request.getParameter("exportToExel");
	        response.setCharacterEncoding("UTF-8");
	        request.setCharacterEncoding("UTF-8");
	        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "inline; filename=" + "Chitietvattu.xls");
	            
	        }
		%>
		<div id="greeting"><%=authentication.getHoTen() %></div>
		<div class="group-button" style="position: fixed; right: 10px;">
					<%
        				if (exportToExcel == null) {
   				 	 %>
   				 	 <button class="button" id="print_button" type="button" onclick="window.print();">
						<i class="fa fa-print"></i>&nbsp;&nbsp;In
					</button>
					&nbsp;&nbsp;
					<button class="button" id="print_button" type="button" onclick="location.href='<%=siteMap.xuatCTVatTu+".jsp"+ "?exportToExel=YES" %>'">
						<i class="fa fa-print"></i>&nbsp;&nbsp;Tải file
					</button>
					&nbsp;&nbsp;
					<button type="button" id="print_button" class="button"  onclick="location.href='<%=siteMap.ctvtManage + "?action=manageCtvt"%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
					<% } %>
					 
				</div>
		<table style = "margin: 0 auto;width:960px;">
		<tr>
			<td style="text-align: left; width: 500px;font-size: 17px;">TỔNG CÔNG TY ĐIỆN LỰC THÀNH PHỐ CẦN THƠ</td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td style="text-align: right; width: 350px;font-size: 17px;">CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</td>
			
		<tr>
			<td style="text-align: left; width: 350px;font-size: 17px;">Địa chỉ: 06 Nguyễn Trãi, Q.Ninh Kiều, TP.Cần Thơ.</td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td style="text-align: right; width: 350px;font-size: 17px;">Độc lập - Tự do - Hạnh phúc</td>
			
		</tr>
		</table>
		<div style="text-align: center;font-size: 40px;font-weight: bold;color: solid black;margin-top:20px;">Báo cáo chi tiết vật tư</div>
			
		<div style="margin-right: 10px;padding-left: 800px;font-size: 17px;">Ngày in:&nbsp;&nbsp;&nbsp;&nbsp; <%=DateUtil.toString(new java.util.Date())%></div>
			<div id="view-table-bao-cao">
					<table  style="border: solid 1px black;width:960px;font-size: 12px;">
					<thead>
						<tr bgcolor="#199e5e" style="border: 1px solid black;">
						<th style="border: 1px solid black;font-size: 17px;" class="four-column">Mã vật tư</th>
						<th style="border: 1px solid black;font-size: 17px;" class="six-column">Tên vật tư</th>
						<th style="border: 1px solid black;font-size: 17px;" class="three-column">Nơi sản xuất</th>
						<th style="border: 1px solid black;font-size: 17px;" class="a-column">Chất lượng</th>
						<th style="border: 1px solid black;font-size: 17px;" class="four-column">Đơn vị tính</th>
						<th style="border: 1px solid black;font-size: 17px;" class="five-column">Định mức</th>
						<th style="border: 1px solid black;font-size: 17px;" class="seven-column">Số lượng tồn</th>
							
						</tr>
						</thead>
									<tbody>
									<%
								if(listCTVatTu != null) {
								for(CTVatTu ctVatTu : listCTVatTu) { %>
	
										<tr style= "border-style: solid;border-color:black;" class="rowContent">
											<td style="border: 1px solid black;font-size: 17px;" class="col"><%=ctVatTu.getVatTu().getVtMa() %></td>
											<td style="border: 1px solid black;font-size: 17px;" class="col" style="text-align: left"><%=ctVatTu.getVatTu().getVtTen() %></td>
											<td style="border: 1px solid black;font-size: 17px;" class="col"><%=ctVatTu.getNoiSanXuat().getNsxTen() %></td>
											<td style="border: 1px solid black;font-size: 17px;" class="col"><%=ctVatTu.getChatLuong().getClTen() %></td>
											<td style="border: 1px solid black;font-size: 17px;" class="col"><%=ctVatTu.getVatTu().getDvt().getDvtTen() %></td>
											<td style="border: 1px solid black;font-size: 17px;" class="col"><%=ctVatTu.getDinhMuc() %></td>
											<td style="border: 1px solid black;font-size: 17px;" class="col"><%=ctVatTu.getSoLuongTon() %></td>
					
										</tr>
										<%} }%>
							</tbody>
				</table>
				</div>
				
		</body>
		</html>