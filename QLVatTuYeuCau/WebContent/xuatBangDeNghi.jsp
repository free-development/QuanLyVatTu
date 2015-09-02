
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
<link href="style/style-bao-cao-vat-tu-thieu.css" type="text/css"
	rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<meta charset="utf-8">
<title></title>
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
		java.sql.Date ngaybd = (java.sql.Date)session.getAttribute("ngaybd");
		java.sql.Date ngaykt = (java.sql.Date)session.getAttribute("ngaykt");
// 		Date dbd = new SimpleDateFormat ("dd-MM-yyyy").parse(ngaybd);
// 		Date dkt = new SimpleDateFormat ("dd-MM-yyyy").parse(ngaykt);
// 		Date dht = new SimpleDateFormat ("dd-MM-yyyy").parse(new Date().toString());
		ArrayList<DonVi> listDonVi = (ArrayList<DonVi>) session.getAttribute("donViList");
		ArrayList<TrangThai> listTrangThai = (ArrayList<TrangThai>) session.getAttribute("trangThaiList");
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("congVanList");
		HashMap<Integer, ArrayList<YeuCau>> yeuCauHash = (HashMap<Integer, ArrayList<YeuCau>>) session.getAttribute("yeuCau");
	       %>
	     <% 
		String exportToExcel = request.getParameter("exportToExel");
	        response.setCharacterEncoding("UTF-8");
	        request.setCharacterEncoding("UTF-8");
	        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "inline; filename=" + "Baocaobangdenghi.xls");
	            
	        }
		%>
		<table style = "margin: 0 auto;width:960px;">
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td style="text-align: left;font-size: 17px;">TỔNG CÔNG TY ĐIỆN LỰC THÀNH PHỐ CẦN THƠ</td>
			<td></td>
			<td></td>
			<td></td>
			
			
			<td style="text-align: right;font-size: 17px;">CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td style="text-align: left;font-size: 17px;">Địa chỉ: 06 Nguyễn Trãi, Q.Ninh Kiều, TP.Cần Thơ.</td>
			<td></td>
			<td></td>
			<td></td>
			
			
			<td style="text-align: right;font-size: 17px;">Độc lập - Tự do - Hạnh phúc</td>
			
		</tr>
		</table>
		<div style="text-align: center;font-size: 30px;font-weight: bold;color: solid black;margin-top:20px;">Báo cáo bảng đề nghị cấp vật tư</div>
		<% if((ngaybd!=null)&&(ngaykt!=null)){%>
			
			<div style="text-align: center;font-size: 17px;">Từ ngày:&nbsp;&nbsp;<%=DateUtil.toString(ngaybd)%>&nbsp;&nbsp;đến ngày:&nbsp;&nbsp;<%=DateUtil.toString(ngaykt)%></div>
			
			<% }%>
		<div style="margin-right: 10px;padding-left: 800px;font-size: 17px;">Ngày in:&nbsp;&nbsp;&nbsp;&nbsp;  <%=DateUtil.toString(new java.util.Date())%></div>
			<div id="view-table-bao-cao">
					<table style="text-align: center;margin: 0 auto; color: black;border: solid 1px black;width:960px;">
					<thead>
						<tr bgcolor="#199e5e"  style= "border-style: solid;border-color:black;">
							<th style="border: 1px solid black;font-size: 17px;" class="a-column">Số đến</th>
							<th style="border: 1px solid black;font-size: 17px;" class="b-column">Ngày nhận</th>
							<th style="border: 1px solid black;font-size: 17px;" class="c-column">Mã vật tư</th>
							<th style="border: 1px solid black;font-size: 17px;" class="d-column">Tên vật tư</th>
							<th style="border: 1px solid black;font-size: 17px;" class="e-column">Nơi sản xuất</th>
							<th style="border: 1px solid black;font-size: 17px;" class="f-column">Đơn vị tính</th>
							<th style="border: 1px solid black;font-size: 17px;" class="g-column">Trạng thái</th>
							<th style="border: 1px solid black;font-size: 17px;" class="k-column">Đơn vị</th>
							<th style="border: 1px solid black;font-size: 17px;" class="h-column">Chất lượng</th>
							<th style="border: 1px solid black;font-size: 17px;" class="m-column">Số lượng</th>
							
						</tr>
						</thead>
								<tbody>
								<%
								if(yeuCauHash != null) {
								 int cnt = 0;
								for(CongVan congVan  : congVanList) {
								ArrayList<YeuCau> yeuCauList = yeuCauHash.get(congVan.getCvId());
								for (YeuCau yeuCau : yeuCauList) {cnt++;
								%>
								<tr 
									<%if (cnt % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>
									style= "border-style: solid;border-color:black black black black;">
									<td style="border: 1px solid black;font-size: 17px;" class="a-column"><%=congVan.getSoDen() %></td>
									<td style="border: 1px solid black;font-size: 17px;" class="b-column"><%=congVan.getCvNgayNhan() %></td>
									<td style="border: 1px solid black;font-size: 17px;" class="c-column"><%=yeuCau.getCtVatTu().getVatTu().getVtMa() %></td>
									<td style="border: 1px solid black;font-size: 17px;" class="d-column"><%=yeuCau.getCtVatTu().getVatTu().getVtTen() %></td>
									<td style="border: 1px solid black;font-size: 17px;" class="e-column"><%=yeuCau.getCtVatTu().getNoiSanXuat().getNsxTen() %></td>
									<td style="border: 1px solid black;font-size: 17px;" class="f-column"><%=yeuCau.getCtVatTu().getVatTu().getDvt().getDvtTen() %></td>
									<td style="border: 1px solid black;font-size: 17px;" class="g-column"><%=congVan.getTrangThai().getTtTen() %></td>
									<td style="border: 1px solid black;font-size: 17px;" class="h-column"><%=congVan.getDonVi().getDvTen()%></td>
									<td style="border: 1px solid black;font-size: 17px;" class="k-column"><%=yeuCau.getCtVatTu().getChatLuong().getClTen() %></td>
									<td style="border: 1px solid black;font-size: 17px;" class="m-column"><%=yeuCau.getYcSoLuong() %></td>
	
								</tr>
									<%}} %>
							</tbody>
							<%} 
					%>
				</table>
				</div>
				<div class="group-button">
					<%
        				if (exportToExcel == null) {
   				 	 %>
   				 	 <button class="button" id="print_button" type="button" onclick="window.print();">
						<i class="fa fa-print"></i>&nbsp;&nbsp;In
					</button>
					&nbsp;&nbsp;
					<button class="button" id="print_button" type="button" onclick="location.href='<%=siteMap.xuatBangDeNghi+".jsp"+ "?exportToExel=YES" %>'">
						<i class="fa fa-print"></i>&nbsp;&nbsp;Tải file
					</button>
					&nbsp;&nbsp;
					<button type="button" id="print_button" class="button"  onclick="location.href='<%=siteMap.baoCaoBangDeNghi+".jsp" %>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
					<% } %>
					 
				</div>
		</body>
		</html>