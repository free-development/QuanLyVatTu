
<%@page import="model.TrangThai"%>
<%@page import="model.NhatKy"%>
<%@page import="model.CongVan"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.NguoiDung"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
        <link rel="stylesheet" href="style/style-giao-dien-chinh.css" type="text/css">
		<link rel="stylesheet" href="style/style.css" type="text/css">
    <link href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery.min.js">
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
		};
		$(window).load(function(){
// 			$("body").css("cursor", "auto");		
			//Set the cursor ASAP to "Wait"
		    document.body.style.cursor='wait';

		    //When the window has finished loading, set it back to default...
		    window.onload=function(){document.body.style.cursor='wait';} 
		});
	</script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />  
    </head>
    <body>
    	<%
    		
    		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
    		if (authentication == null) {
    			request.setAttribute("url", "index");
    			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
    			dispatcher.forward(request, response);
    			return;
    		}
    		
    			
    		String adminMa = request.getServletContext().getInitParameter("adminMa");
    		String chucDanh = authentication.getChucDanh().getCdMa();
    		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
    		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
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
							
							<span class="search-text">
								&nbsp;
							<input type="search" class="search" name="search_box" name="search" placeholder="Tìm kiếm" />
							</span>
							<span class="search-button">
							&nbsp;
							<button class="btn-search"><i class="fa fa-search" ></i></button>
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
						<div id="greeting"style="color: #6600FF;height:20px;"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chào:&nbsp;<%=authentication.getHoTen() %></b></div>
				<div id="main-content">
				<% if (chucDanh.equalsIgnoreCase(truongPhongMa)){
					ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList"); 
					ArrayList<NhatKy> nhatKyList = (ArrayList<NhatKy>) request.getAttribute("nhatKyList");
				%>
				<table style = "margin: 0 auto;width: 900px; vertical-align:top ;">
				<tr>
				<td>
					<div class="view-tbao">
						<table>
						<tr ><th colspan = "2" style="text-align: center; width: 300px; font-size: 20px;">Công việc</th></tr>
						<tr style="background: #CCFFFF">
<!-- 							<td style="text-align: center;" colspan="2"> -->
<!-- 							<i class="fa fa-sign-out"></i>&nbsp;Thông báo -->
<!-- 							</td> -->
							<th style="text-align: center; width: 180px;">Công văn cần xử lý</th>
							<th>Trạng thái</th>
						</tr>
						<%int count = 0; 
						for (CongVan congVan : congVanList) {
							String style ="";
							
							TrangThai trangThai = congVan.getTrangThai();
							String ttMa = trangThai.getTtMa();
							if (ttMa.equalsIgnoreCase("CGQ"))
								style = "color: red";
							else
								style = "color: yello";
							
						%>
						<tr style = "<% if (count % 2 == 1) out.print("background: #CCFFFF; ");%>";>
							<td style="text-align: center;"><a style="color: blue; text-decoration: underline; " href='<%=siteMap.searchCongVan + "?congVan=" + congVan.getCvId() %>'> Công văn số <%=congVan.getSoDen() %></a></td>
							<td style="text-align: center;"><div style="<%=style%>"><%=trangThai.getTtTen() %></div></td>
						</tr>
						<%count ++;} %>
						</table>
					</div>
				</td>
				<td style="vertical-align:top ;">
					<div class="view-nky" >
						<table>
						<tr>
						<th style="text-align: center; font-size: 20px;">
							<i class="fa fa-sign-out"></i>&nbsp;Nhật ký hoạt động
						</th>
						</tr>
						<%
						int count2 = 0;
						for (NhatKy nhatKy : nhatKyList) {%>
						<tr style = "<% if (count2 % 2 == 0) out.print("background: #CCFFFF; ");%>";>
							<td><a style="color: blue; text-decoration: underline;" href="<%=siteMap.cscvManage + "?action=chiaSeCv&congVan=" + nhatKy.getCvId()%>"><%=nhatKy.getNoiDung() %></td>
						</tr>
						<%count2++;} %>
						</table>
					</div>
					</td>
					</tr>
				</table>
				<%} else if (chucDanh.equalsIgnoreCase(vanThuMa)){
// 					ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList"); 
					ArrayList<NhatKy> nhatKyList = (ArrayList<NhatKy>) request.getAttribute("nhatKyList");
				%>
				<table style = "margin: 0 auto;width: 900px; ">
				<tr>
				<td style="vertical-align:top ;">
					<div class="view-tbao" ">
						<table>
						<tr ><th colspan = "2" style="text-align: center; width: 300px; font-size: 20px;">Công việc</th></tr>
						</table>
					</div>
				</td>
				<td style="vertical-align:top ;">
					<div class="view-nky" >
						<table>
						<tr>
						<th style="text-align: center; font-size: 20px;">
							<i class="fa fa-sign-out"></i>&nbsp;Nhật ký hoạt động
						</th>
						</tr>
						<%
						int count2 = 0;
						for (NhatKy nhatKy : nhatKyList) {
						String noiDung = nhatKy.getNoiDung();
						int checkXoa = noiDung.indexOf("xóa");
						String href = "";
						if (checkXoa == -1)
							href = siteMap.searchCongVan + "?congVan=" + nhatKy.getCvId();
						else 
							href = siteMap.cvManage+ "?action=manageCv";
						%>
						<tr style = "<% if (count2 % 2 == 0) out.print("background: #CCFFFF; ");%>";>
							<td><a style="color: blue; text-decoration: underline;" href="<%=href%>"><%=noiDung %></a></td>
						</tr>
						<%count2++;} %>
						</table>
					</div>
					</td>
					</tr>
				</table>
				<%} else if (chucDanh.equals(adminMa)){
					
				%>	
				
				<%} else{	
					ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList"); 
					ArrayList<NhatKy> nhatKyList = (ArrayList<NhatKy>) request.getAttribute("nhatKyList");
				%>
					<table style = "margin: 0 auto;width: 900px; ">
				<tr>
				<td style="vertical-align:top ;">
					<div class="view-tbao" ">
						<table>
						<tr ><th style="text-align: center; width: 300px; font-size: 20px;">Công việc</th></tr>
						<%int count = 0; 
						for (CongVan congVan : congVanList) {
							String style ="";
							
							TrangThai trangThai = congVan.getTrangThai();
							String ttMa = trangThai.getTtMa();
							if (ttMa.equalsIgnoreCase("CGQ"))
								style = "color: red";
							else
								style = "color: yello";
							
						%>
						<tr style = "<% if (count % 2 == 1) out.print("background: #CCFFFF; ");%>";>
							<td style="text-align: center;"><a style="color: blue; text-decoration: underline; " href='<%=siteMap.searchCongVan + "?congVan=" + congVan.getCvId() %>'> Công văn số <%=congVan.getSoDen() %></a></td>
							<td style="text-align: center;"><div style="<%=style%>"><%=trangThai.getTtTen() %></div></td>
						</tr>
						<%count ++;} %>
						</table>
					</div>
				</td>
				<td style="vertical-align:top ;">
					<div class="view-nky" >
						<table>
						<tr>
						<th style="text-align: center; font-size: 20px;">
							<i class="fa fa-sign-out"></i>&nbsp;Nhật ký hoạt động
						</th>
						</tr>
						<%
						int count2 = 0;
						for (NhatKy nhatKy : nhatKyList) {%>
						<tr style = "<% if (count2 % 2 == 0) out.print("background: #CCFFFF; ");%>";>
							<td><a style="color: blue; text-decoration: underline;" href="<%=siteMap.ycvtManage + "?cvId=" + nhatKy.getCvId()%>"><%=nhatKy.getNoiDung() %></a></td>
						</tr>
						<%count2++;} %>
						</table>
					</div>
					</td>
					</tr>
				</table>
				<%} %>
				</div>
				
        </div>
    </body>
</html>
