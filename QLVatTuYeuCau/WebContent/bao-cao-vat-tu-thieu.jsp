
<%@page import="model.NguoiDung"%>
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
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
 <link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-bao-cao-bang-de-nghi.css" type="text/css"
	rel="stylesheet">

<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript">
		function showForm(formId, check){
			if (check)
				document.getElementById(formId).style.display="block";
			else document.getElementById(formId).style.display="none";
			var f = document.getElementById('main-form'), s, opacity;
			s = f.style;
			opacity = check? '10' : '100';
			s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
			s.filter = 'alpha(opacity='+opacity+')';
			for(var i=0; i<f.length; i++) f[i].disabled = check;
		}
		function confirmDelete(){
			return confirm('Bạn có chắc xóa');
		}
	</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
		<%
    		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    		if (nguoiDung == null) {
    			request.setAttribute("url", siteMap.bcbdnManage+ "?action=manageBcbdn");
    			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
    			dispatcher.forward(request, response);
    			return;
    		}
    		String adminMa = request.getServletContext().getInitParameter("adminMa");
    		String chucDanh = nguoiDung.getChucDanh().getCdMa();
    		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
    		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
    	%>
		<%
			String loaiBc = (String) session.getAttribute("action"); 
// 	        String exportToExcel = request.getParameter("exportToExel");
// 	        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) {
// 	            response.setContentType("application/vnd.ms-excel");
// 	            response.setHeader("Content-Disposition", "inline; filename=" + "excel.xls");
// 	        }
		%>
	<div class="wrapper">
		<div class="header">
			<!--
					<img src="img/logo.png" alt="" id="logo" width=80 height=80/><br/>
					<img src="img/textlogo.png" alt="" id="logo" width=80 height=20/>
	-->
			<div id="top_title">Văn phòng điện tử</div>
			<div id="bottom-title">Công ty điện lực cần thơ</div>
			<div class="search_form" id="search">
				<form action="" method="post">
					<!--
							<span class="search-select">
								<select name="" ><option disabled selected>--Tùy chọn kiếm kiềm--</option></select>
								<option value=""></option>
							</span>
-->

					<span class="search-text"> &nbsp; <input type="search"
						class="search" name="search_box" name="search"
						placeholder="Tìm kiếm" />
					</span> <span class="search-button"> &nbsp;
						<button class="btn-search">
							<i class="fa fa-search"></i>
						</button>
					</span>
				</form>
			</div>

		</div>
		<div class="main_menu">
					<ul>
						<li><a href="<%=siteMap.homePageManage%>">Trang chủ</a></li>
						<%if (adminMa.equalsIgnoreCase(chucDanh)) {%>
						
						<li><a>Danh mục</a>
							<ul>
										<li><a href="<%=siteMap.nsxManage + "?action=manageNsx"%>">Danh
												mục nơi sản xuất</a></li>
										<li><a href="<%=siteMap.clManage + "?action=manageCl"%>">Danh
												mục chất lượng</a></li>
										<li><a href="<%=siteMap.vattuManage + "?action=manageVattu"%>">Danh
												mục vật tư</a></li>
										<li><a href="<%=siteMap.ctvtManage + "?action=manageCtvt"%>">Danh
												mục chi tiết vật tư</a></li>
										<li><a href="<%=siteMap.bpsdManage +  "?action=manageBpsd"%>">Danh
												mục bộ phận sử dụng</a></li>
										<li><a href="<%=siteMap.mdManage + "?action=manageMd"%>">Danh
												mục mục đích</a></li>
										<li><a href="<%=siteMap.vtManage + "?action=manageVt"%>">Danh mục vai trò</a></li>
										<li><a href="<%=siteMap.dvtManage + "?action=manageDvt"%>">Danh mục đơn vị tính</a></li>
										<li><a href="<%=siteMap.cdManage + "?action=manageCd"%>">Danh
												mục chức danh</a></li>
										
									</ul>
						</li>
						<%} %>
						<%if (!chucDanh.equalsIgnoreCase(adminMa)) {%>
							<li><a href="<%=siteMap.cvManage+ "?action=manageCv" %>">Công văn</a></li>
							<%if (!chucDanh.equalsIgnoreCase(vanThuMa)){ %>
							<li><a>Báo cáo</a>
								<ul>
									<li><a href="<%=siteMap.bcvttManage+ "?action=manageBcvtt" %>"/>Báo cáo vật tư thiếu</li>
									<li><a href="<%=siteMap.bcbdnManage+ "?action=manageBcbdn" %>"/>Báo cáo bảng đề nghị cấp vật tư</li>
								</ul>
							</li>
							<%}} %>
						<%if (adminMa.equalsIgnoreCase(chucDanh)) {%>
						<li><a>Quản lý người dùng</a>
							<ul>
								<li><a href="<%=siteMap.ndManage + "?action=manageNd"%>">Thêm người dùng</li>
								<li><a href="<%=siteMap.updateNguoiDung%>"/>Cập nhật thông tin</li>
								<li><a href="<%=siteMap.resetPassword%>"/>Khôi phục mật khẩu</li>
								<li><a href="<%=siteMap.lockNguoiDung%>"/>Khóa tài khoản</li>
								<li><a href="<%=siteMap.resetNguoiDung%>"/>Khôi phục tài khoản</li>
							</ul>
						</li>
						<%} %>
						<li><a>Tài khoản</a>
							<ul>
								<li><a href="<%=siteMap.changePassPage + ".jsp"%>">Đổi mật khẩu</a></li>
								<li><a href="<%=siteMap.logout + "?action=logout"%>">Đăng xuất</a></li>
							</ul>
						</li>		
					</ul>
					<div class="clear"></div>
				</div>
		<div id="greeting"style="color: #6600FF;height:20px;"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chào:&nbsp;<%=nguoiDung.getHoTen() %></b></div>
		<div id="main-content">
			<div id="title-content"style="margin-top: 20px;">Báo cáo vật tư thiếu</div>
			<br>
			<form id="option-form"  method="get" action="<%=siteMap.bcvttManage%>">
				<fieldset style="background-color:#dceaf5;width:700px;margin:0 auto;">
					<legend style="margin: 0 auto;font-size: 18px">Tùy chọn báo cáo</legend>
					<table style="margin: 0 auto; padding-bottom: 20px; cellspading: 30px;margin-top: 10px;">

                        <tr>
                            <th style="text-align: left">Thời gian:</th>
                            <td style="text-align: left; " colspan="2" >Từ ngày &nbsp;
                            <input type="date" class="text" name="ngaybd" >
                            &nbsp;&nbsp;&nbsp;&nbsp; đến&nbsp;
                            <input type="date" class="text" name="ngaykt"></td>
                        </tr>
                        
                        <tr>
							<th style="text-align: left; padding-right: 10px;">Chế độ báo cáo:</th>
							<td style="font-size: 20px"><input name="action" type="radio" value="chitiet" required title="Bạn phải chọn chế độ báo cáo"/>&nbsp;&nbsp;Chi tiết</td>
						<td style="font-size: 20px"><input name="action" type="radio" value="tonghop"/>&nbsp;&nbsp;Tổng hợp</td>
	                    </tr>
					</table>
<!-- 					<input type="hidden" name="action" value="baocaovtt"> -->
					<input style="margin-left: 300px;margin-bottom: 20px;"type="submit" value="Xem" class="button"/>
				</fieldset>
				
			</form>
			<br>
			<br>
			<% if(loaiBc != null  &&  "chitiet".equalsIgnoreCase(loaiBc)){
			
 				ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("congVanList");			
		   		HashMap<Integer, ArrayList<YeuCau>> yeuCauHash = (HashMap<Integer, ArrayList<YeuCau>>) session.getAttribute("yeuCau");
		   		
		    %>
			
				
					<div style="text-align: center;font-size: 20px;color:firebrick;font-weight: bold;margin-top:10px;">Chi tiết vật tư thiếu</div>
					<div id="view-table-bao-cao" style="max-height: 520px;width: 1000px;display: auto;border: 1px dotted #CCCCCC;margin: 0 auto;overflow: scroll;">
					<table style="margin: 0 auto;width:1000px;border: 1px dotted black;">
							<tr bgcolor="#199e5e"style="border: 1px dotted black;">
								<th style="border: 1px dotted black;" class="one-column">Số đến</th>
								<th style="border: 1px dotted black;" class="one-column">Số công văn</th>
								<th style="border: 1px dotted black;" class="three-column">Ngày nhận</th>
								<th style="border: 1px dotted black;" class="two-column">Mã vật tư</th>
								<th style="border: 1px dotted black;" class="three-column">Tên vật tư</th>
								<th style="border: 1px dotted black;" class="three-column">Nơi sản xuất</th>
								<th style="border: 1px dotted black;" class="three-column">Chất lượng</th>
								<th style="border: 1px dotted black;" class="six-column">Đơn vị tính</th>
								<th style="border: 1px dotted black;" class="one-column">Số lượng thiếu</th>
								<th style="border: 1px dotted black;">Link công văn</th>
							</tr>
						
									<% 								
										if(yeuCauHash != null){
										int count = 0;	
										for(CongVan congVan  : congVanList) { 
										ArrayList<YeuCau> yeuCauList = yeuCauHash.get(congVan.getCvId());
										for (YeuCau yeuCau : yeuCauList) { count++;
									%>
												
									<tr
										<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>
										style="border: 1px solid black;">
										<td class="a-column"style="text-align: center;"><%=congVan.getSoDen() %></td>
										<td class="a-column"style="text-align: center;"><%=congVan.getCvSo() %></td>
										<td class="b-column"style="text-align: center;"><%=congVan.getCvNgayNhan() %></td>
										<td class="a-column"style="text-align: center;"><%=yeuCau.getCtVatTu().getVatTu().getVtMa() %></td>
										<td class="b-column"style="text-align: left;"><%=yeuCau.getCtVatTu().getVatTu().getVtTen() %></td>
										<td class="c-column"style="text-align: center;"><%=yeuCau.getCtVatTu().getNoiSanXuat().getNsxTen() %></td>
										<td class="d-column"style="text-align: left;"><%=yeuCau.getCtVatTu().getChatLuong().getClTen() %></td>
										<td class="e-column"style="text-align: center;"><%=yeuCau.getCtVatTu().getVatTu().getDvt().getDvtTen() %></td>
										<td class="e-column"style="text-align: center;"><%=yeuCau.getYcSoLuong() %></td>
										<td style="text-align: center;"><a style="color: blue;text-decoration: underline;" href="<%=siteMap.cvManage + "?action=download&file=" + congVan.getCvId()%>">Xem</td>
									</tr>	
								<%}} }%>			
					</table>
				</div>
	
				<div class="group-button"style ="text-align: center;margin-top:10px;">			 
					<button class="button" type="button" onclick="location.href='<%=siteMap.xuatFile+ ".jsp"%>'">
						<i class="fa fa-print"></i>&nbsp;&nbsp;Xuất file
					</button>
					&nbsp;
					<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
				</div>
			
					<% }%>
					
				<% if(loaiBc != null && "tonghop".equalsIgnoreCase(loaiBc)){	
	   		HashMap<Integer, CTVatTu> ctvtHash = (HashMap<Integer, CTVatTu>) session.getAttribute("ctvtHash");
	   		HashMap<Integer, Integer> yeuCauHash = (HashMap<Integer, Integer>) session.getAttribute("yeuCau");
	   		HashMap<Integer, ArrayList<Integer>> cvIdHash = (HashMap<Integer, ArrayList<Integer>>) session.getAttribute("cvIdHash");
	   		HashMap<Integer, ArrayList<Integer>> soDenHash = (HashMap<Integer, ArrayList<Integer>>) session.getAttribute("soDenHash");
	   		%>
			
<!-- 			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("congVanList");	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("congVanList");	 -->
<!-- 			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("congVanList");	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("congVanList");	 -->
				<div style="text-align: center;font-size: 20px;color:firebrick;font-weight: bold;margin-top:10px;">Tổng hợp vật tư thiếu</div>
				<div id="view-table-bao-cao" style="max-height: 420px;width: 1000px;display: auto;border: 1px dotted #CCCCCC;margin: 0 auto;overflow: scroll;">
				<table style="margin: 0 auto;width:1000px;border: 1px dotted black;" >
					<tr bgcolor="#199e5e" style="border: 1px dotted black;">
						<th style="border: 1px dotted black;" class="two-column">Mã vật tư</th>
						<th style="border: 1px dotted black;" class="three-column">Tên vật tư</th>
						<th style="border: 1px dotted black;" class="three-column">Nơi sản xuất</th>
						<th style="border: 1px dottedblack;" class="three-column">Chất lượng</th>
						<th style="border: 1px dotted black;" class="six-column">Đơn vị tính</th>
						<th style="border: 1px dotted black;" class="one-column">Tổng số lượng thiếu</th>
						<th style="border: 1px dotted black;" class="one-column">Tổng số lượng thiếu</th>
						<th style="border: 1px dotted black;" class="one-column">Công văn liên quan (số đến)</th>
					</tr >
								<%
							if(yeuCauHash != null){
							int count = 0;
							for(Integer key  : yeuCauHash.keySet()) { count++;
							CTVatTu ctvt = ctvtHash.get(key);
 							//for (CongVan congVan : congVanList) {
							%>
									
					<tr
						<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\""); else out.println("style=\"background : #FFFFFF;\"");%>
						style="border: 1px solid black;">
						<td class="a-column"style="text-align: center;"><%=ctvt.getVatTu().getVtMa() %></td>
						<td class="b-column"style="text-align: left;"><%=ctvt.getVatTu().getVtTen() %></td>
						<td class="c-column"style="text-align: center;"><%=ctvt.getNoiSanXuat().getNsxTen() %></td>
						<td class="d-column"style="text-align: left;"><%=ctvt.getChatLuong().getClTen() %></td>
						<td class="e-column"style="text-align: center;"><%=ctvt.getVatTu().getDvt().getDvtTen() %></td>
						<td class="e-column"style="text-align: center;"><%=yeuCauHash.get(key) %></td>
						<td class="e-column"style="text-align: center;"><%=yeuCauHash.get(key) %></td>
						<td>
							<%
							ArrayList<Integer> cvIdList = cvIdHash.get(key);
							ArrayList<Integer> soDenList = soDenHash.get(key);
							int length = cvIdList.size();
							StringBuilder cell = new StringBuilder ("");
							for(int i = 0; i < length; i++) {
								int soDen = soDenList.get(i);
								int cvId = cvIdList.get(i);
								cell.append("<a style=\"color: red; text-decoration: underline; \" href=" + siteMap.searchCongVan + "?congVan=" + cvId + "> " + soDen + "</a>" + ", ");
							}
							int len = cell.length() ;
							cell.delete(len - 2, len);
							out.println(cell);
							%>
						</td>
<%-- 						<td class="e-column"style="text-align: left;"><%=congVan.getCvSo()%></td> --%>
						</td>
					</tr>
					<%} %>
				</table>
			</div>

			<div class="group-button"style ="text-align: center;margin-top:10px;">
<!--     <a href="excel.jsp?exportToExcel=YES">Export to Excel</a> -->

				<button class="button" type="button" onclick="location.href='<%=siteMap.xuatFile+ ".jsp"%>'">
					<i class="fa fa-print"></i>&nbsp;&nbsp;Xuất file
				</button>
				&nbsp;
				<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
			</div>
				<%}}%>
	</div>
	</div>
</body>
</html>
