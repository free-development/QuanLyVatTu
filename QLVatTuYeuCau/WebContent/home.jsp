
<%@page import="model.CTVatTu"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="model.VaiTro"%>
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
    		String nhanVienMa = request.getServletContext().getInitParameter("nhanVienMa");
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
										<li><a href="<%=siteMap.ctvtManage + "?action=manageCtvt"%>">Vật tư tồn kho</a></li>
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
							<li><a href="<%=siteMap.cvManage+ "?action=manageCv" %>">Công văn</a></li>
							<%if (!chucDanh.equalsIgnoreCase(vanThuMa)){ %>
							<li><a>Báo cáo</a>
								<ul>
									<li><a href="<%=siteMap.bcvttManage+ "?action=manageBcvtt" %>"/>Báo cáo vật tư thiếu</li>
									<li><a href="<%=siteMap.bcbdnManage+ "?action=manageBcbdn" %>"/>Báo cáo bảng đề nghị cấp vật tư</li>
								</ul>
							</li>
							<%} %>
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
				
<!-- 				<table style = "margin: 0 auto;width: 1024spx; vertical-align:top ;"> -->
<!-- 				<tr  style="vertical-align:top ;"> -->
				<table style = "margin: 0 auto;width: 1124spx; vertical-align:top ;">
				<tr >
				<td style=" vertical-align:top ;"">
					<% if (chucDanh.equalsIgnoreCase(truongPhongMa) || chucDanh.equalsIgnoreCase(vanThuMa)){
						ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList");
						ArrayList<CTVatTu> ctVatTuListAlert = (ArrayList<CTVatTu>) request.getAttribute("ctVatTuListAlert");
						ArrayList<NhatKy> nhatKyList = (ArrayList<NhatKy>) request.getAttribute("nhatKyList");
					%>
					<div class="view-tbao">
						<table style="width:550px;">
						<tr ><th colspan = "5" style="text-align: center; width: 300px; font-size: 20px;color:red;"><i class="fa fa-sign-out"></i>&nbsp;Công việc</th></tr>
						<% if (chucDanh.equalsIgnoreCase(truongPhongMa) && ctVatTuListAlert != null && ctVatTuListAlert.size() > 0) { %>
						<tr style="background-color:white;">
							<th colspan ="5" style="text-align: center; width: 180px;color:red;">Vật tư dưới định mức: </th>
						</tr>
						<tr style="background-color: #199e5e;">
							<th>Mã vật tư</th>
							<th>Mã nơi sản xuất</th>
							<th>Mã chất lượng</th>
							<th>Định mức</th>
							<th>Số lượng tồn</th>
						</tr>
						
						<% int cnt=0; for (CTVatTu ctVatTu : ctVatTuListAlert) {cnt++;%>
						<tr style = "<% if (cnt % 2 == 1) out.print("background: #CCFFFF; ");%>";>
							<td colspan ="1" style="text-align: left;"><%=ctVatTu.getVatTu().getVtMa()%> </td>
							<td colspan ="1" style="text-align: left;"><%=ctVatTu.getNoiSanXuat().getNsxMa()%> </td>
							<td colspan ="1" style="text-align: left;"><%=ctVatTu.getChatLuong().getClMa()%> </td>
							<td colspan ="1" style="text-align: left;"><%=ctVatTu.getDinhMuc()%> </td>
							<td colspan ="1" style="text-align: left;"><%=ctVatTu.getSoLuongTon()%> </td>
						</tr>
						<%}} %>
						
						<tr style="background-color: #199e5e;">
<!-- 							<td style="text-align: center;" colspan="2"> -->
<!-- 							<i class="fa fa-sign-out"></i>&nbsp;Thông báo -->
<!-- 							</td> -->
							<th colspan = "3" style="text-align: center; width: 180px;">Công văn cần xử lý</th>
							<th colspan = "2">Trạng thái</th>
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
							<td colspan = "3" style="text-align: center;"><a style="color: blue; text-decoration: underline; " href='<%=siteMap.searchCongVan + "?congVan=" + congVan.getCvId() %>'> Công văn số <%=congVan.getSoDen()  + " có ngày nhận " + congVan.getCvNgayNhan()%></a></td>
							<td colspan = "2" style="text-align: center;"><div style="<%=style%>"><%=trangThai.getTtTen() %></div></td>
						</tr>
						<%count ++;} %>
						</table>
					</div>
					<%} else if (chucDanh.equalsIgnoreCase(nhanVienMa)){ 
					ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList"); 
					
					ArrayList<ArrayList<VaiTro>> vaiTroList = (ArrayList<ArrayList<VaiTro>>) request.getAttribute("vaiTroList");
					ArrayList<ArrayList<String>> trangThaiList = (ArrayList<ArrayList<String>>) request.getAttribute("trangThaiList");
 				%> 
					<div class="view-tbao"> 
 						<table style="width:450px;"> 
						<tr ><th colspan = "2" style="text-align: center; width: 300px; font-size: 20px;color:red;"><i class="fa fa-sign-out"></i>&nbsp;Công việc</th></tr> 
<!--  						<tr style="background: #CCFFFF">  -->
<!--  							<td style="text-align: center;" colspan="2">  -->
<!-- 							<i class="fa fa-sign-out"></i>&nbsp;Thông báo -->
<!--  							</td>   -->
<!-- <!--  							<th style="text-align: center; width: 180px;">Công văn cần xử lý</th>  --> 
<!--  						</tr>  -->
 						<%int count = 0;  
 						for (CongVan congVan : congVanList) {
 							String style ="";
 							String noiDung = "Vai trò công văn có số đến " + congVan.getSoDen() + " nhận ngày " + congVan.getCvNgayNhan() + ":";
 							ArrayList<VaiTro> vaiTroCongVanList = vaiTroList.get(count);
 							ArrayList<String> trangThaiCongVanList = trangThaiList.get(count);
 							int i = 0;
 							for (VaiTro vaiTro : vaiTroCongVanList) {
 								String trangThai = trangThaiCongVanList.get(i);
 								noiDung += "<br>&nbsp;&nbsp;+ " + vaiTro.getVtTen() + ": " + trangThai + ".";
 								i++;
 							}
							
							
						%>
						<tr style = " <% if (count % 2 == 1) out.print("background: #CCFFFF; ");%>";>
							<td style="text-align: left;"><a style="color: blue; text-decoration: underline; " href="<%=siteMap.searchCongVan + "?congVan=" + congVan.getCvId()%>"> Công văn số <%=noiDung %></a></td>
						</tr>
						<%count ++;} %>
						</table>
					</div>
					<%} %>
				</td>
				<td style="vertical-align:top ;">
					<div class="view-nky" >
						<table style="width:800px;">
						<tr>
						<th colspan ="3" style="text-align: center; font-size: 20px;color:red;">
							<i class="fa fa-sign-out"></i>&nbsp;Nhật ký hoạt động
						</th>
						</tr>
						<tr style="background-color: #199e5e;">
							<th style="width: 40px;">STT</th>
							<th style="width: 200px;">Hoạt động</th>
							<th style="width: 200px;">Nội dung</th>
							<th style="width: 110px;">Thời gian</th>
						</tr>
						<%
						int count2 = 0;
						ArrayList<NhatKy> nhatKyList = (ArrayList<NhatKy>) request.getAttribute("nhatKyList");
						for (NhatKy nhatKy : nhatKyList) {
							String cvId = "";
							String hoatDong = "";
							String hoatDongTemp = nhatKy.getHoatDong();
							String link = "";
							if (hoatDongTemp.indexOf("#") != -1) {
								String[] temp = hoatDongTemp.split("\\#");
								cvId = temp[0];
								hoatDong = temp[1];
								link = "<a style=\"color: blue; text-decoration: underline;\" href=\"" + siteMap.searchCongVan + "?congVan=" + cvId + "\">" + hoatDong + "<a>"; 
							} else if (hoatDongTemp.indexOf("#") == -1)
								link = "<a style=\"color: blue; text-decoration: underline;\" href=\"" + siteMap.cvManage + "?action=manageCv\">" + hoatDongTemp + "</a>";
// 							else if (hoatDongTemp.indexOf("Chia sẻ") != -1)	{
// 								String[] temp = hoatDongTemp.split("\\#");
// 								cvId = temp[0];
// 								hoatDong = temp[1];
// 								link = "<a style=\"color: blue; text-decoration: underline;\" href=\"" + siteMap.cscvManage + "\"?action=chiaSeCv&congVan=\" + cvId +\">" + hoatDong + "<a>";
// 							}
							
						%>
						<tr style = "<% if (count2 % 2 == 0) out.print("background: #CCFFFF; ");%>";>
							<td style="text-align: center;"><%=count2 + 1 %></td>
							
							
							<td ><%out.print(link + " "); %></td>
							<td style="""><%=nhatKy.getNoiDung() %></td>
							<td style="text-align: center;"><%=nhatKy.getThoiGian() %></td>
						</tr>
						<%count2++;} %>
						</table>
					</div>
					</td>
					</tr>
				</table>

				
				</div>
        </div>
    </body>
</html>
