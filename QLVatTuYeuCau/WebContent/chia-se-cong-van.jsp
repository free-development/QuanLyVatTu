<%@page import="java.util.HashMap"%>
<%@page import="model.CongVan"%>
<%@page import="model.NguoiDung"%>
<%@page import="model.VaiTro"%>
<%@page import="dao.VaiTroDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css"
	type="text/css">	
<link rel="stylesheet" href="style/style-chia-se.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
		type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/check.js"></script>
<script type="text/javascript" src="js/chia-se-cong-van.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
<script type="text/javascript">


</script>
<script type="text/javascript" src="js/jquery.min.js"></script>
</head>
<body>
	<%
	ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) session.getAttribute("vaiTroList");
	ArrayList<NguoiDung> nguoiDungList = (ArrayList<NguoiDung>) session.getAttribute("nguoiDungList");
	CongVan congVan = (CongVan) session.getAttribute("congVan");
	HashMap<String,NguoiDung> vtNguoiDungHash = (HashMap<String,NguoiDung>) request.getAttribute("vtNguoiDungHash");
	HashMap<String, HashMap<Integer, VaiTro>> vaiTroHash = (HashMap<String, HashMap<Integer, VaiTro>>) request.getAttribute("vaiTroHash");
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
				<li><a href="">Trang chủ</a></li>
				<li><a href="">Danh mục</a>
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
								<li><a href="<%=siteMap.cdManage + "?action=manageCd"%>">Danh
										mục chức danh</a></li>
								
							</ul>
				</li>
				<li><a href="<%=siteMap.cvManage+ "?action=manageCv" %>">Công văn</a></li>
				<li><a href="<%=siteMap.bcManage +  "?action=manageBc"%>">Báo cáo</a>
					<ul>
						<li><a href="<%=siteMap.bcvttManage+ "?action=manageBcvtt" %>"/>Báo cáo vật tư thiếu</li>
						<li><a href="<%=siteMap.bcbdnManage+ "?action=manageBcbdn" %>"/>Báo cáo bảng đề nghị cấp vật tư</li>
					</ul>
				</li>
				<li><a href="">Quản lý người dùng</a>
					<ul>
						<li><a href="<%=siteMap.ndManage + "?action=manageNd"%>">Thêm người dùng</li>
						<li><a href=""/>Khôi phục mật khẩu</li>
					</ul>
				</li>
				<li><a href="<%=siteMap.changePass + "?action=changePassWord"%>">Đổi mật khẩu</a></li>
			</ul>
			<div class="clear"></div>
		</div>
		<div id="main-content">
			
				<form id="main-form" action="<%=siteMap.updateChiaSeCv%>" method="get">
				<div id="title-content">Chia sẻ công văn</div>
					<div id="input-table" style="width: 960px; margin-left: 25px;margin-bottom: 10px;">
						<table>
							<tr>
								<th style="text-align: left">Số công văn:</th>
								<td class="b-column"><%=congVan.getCvSo() %></td>
								<th class="c-column">Ngày đến:</th>
								<td class="b-column"><%=congVan.getCvNgayNhan() %></td>
								<th class="e-column">Người lập phiếu:</th>
								<td class="f-column">NV002</td>
							</tr>
						</table>
					</div>
<!-- 					<br /> -->
<%-- 					<form action="<%=siteMap.chiaSeCv%>" method="get"> --%>
					<div id="view-table" >
						<table style="margin: 0 auto; margin-top:10px; max-height: 420px;width: 960px;display: auto;margin: 0 auto;overflow: scroll;" > 
							<tr style="background-color: #199e5e;">

								<th style="width: 100px;">Mã nhân viên</th>
								<th style="width: 200px;">Tên nhân viên</th>
								<%for (VaiTro vaiTro : vaiTroList) {%>
								<th class="thead-vaitro" style="max-width: 300px;"><%=vaiTro.getVtTen() %></th>
								<%} %>
<!-- 								<th class="four-column">Lập phiếu</th> -->
<!-- 								<th class="five-column">Cập nhật vật tư</th> -->
<!-- 								<th class="six-column">...</th> -->

							</tr>
							<% int count = 0;for(NguoiDung nguoiDung : nguoiDungList) { 
								count ++;
								String msnv = nguoiDung.getMsnv();
							%>
							<tr <% if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%> id="<%=nguoiDung.getMsnv() %>">
								<td class="tbody-nguoidung"><%=nguoiDung.getMsnv() %></td>
								<td class="tbody-nguoidung"><%=nguoiDung.getHoTen() %></td>
								<% for(VaiTro vaiTro : vaiTroList) {
									int vtId = vaiTro.getVtId();
									HashMap<Integer, VaiTro> vtHash = vaiTroHash.get(msnv);
									boolean check = false;
									if (vtNguoiDungHash.get(msnv) != null && vtHash.get(vtId) != null)
										check = true;
								%>
								<td class="checkbox">
									<input type="checkbox" name="vaiTro" <%if (check) out.print("checked "); %> value="<%	out.print(msnv + "#" + vtId); %>" >
								</td>
								<%} %>
							</tr>
							<%} %>
						</table>
					</div>
					<div class="group-button">
					<input type="hidden" value="save" name="action">
						<button class="btn">
							<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
						</button>
						<button type="reset" class="btn">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Bỏ qua
						</button>
						<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</div>
				</form>
				
				<div id="view-chia-se">
				<form action="">
				<%
					if (vtNguoiDungHash.size() != 0 || vtNguoiDungHash == null) {
				%>
				<div id="title-content">Công việc đã chia sẻ</div>
				<div id="view-table-chia-se">
					<table >
						<tr bgcolor= "#199e5e">
						<th style="text-align: center;">Chọn</th>
						<th>Msnv</th><th>Họ tên</th><th>Vai trò</th>
						</tr>
						<%
							int i = 0;
							for(String msnv :  vtNguoiDungHash.keySet()) {
								HashMap<Integer, VaiTro> vtHash = vaiTroHash.get(msnv);
								NguoiDung nguoiDung =  vtNguoiDungHash.get(msnv);
								String hoTen = nguoiDung.getHoTen();
								i++;
						%>
						<tr  <% if (i % 2 ==0) out.println("style=\"background : #CCFFFF;\"");%> >
							<td style="text-align: center;"><input type = "checkbox" class="checkbox" name = "msnv" value="<%=msnv%>"></td>
							<td><%=msnv %></td>
							<td><%=hoTen %></td>
							<td id="vaiTro<%=msnv%>">
								<%
									StringBuilder str = new StringBuilder("");
									for(Integer vtId : vtHash.keySet()) {
										VaiTro vaiTro = vtHash.get(vtId);
										str.append(vaiTro.getVtTen() + "<br>");
									}
									int end = str.length();
									str.delete(end - 4, end);
									out.println(str.toString());
								%>
								
							</td>
						</tr>
						<%}%>
					</table>
					</div>
					<div class="group-button">
					<input type="hidden" value="save" name="action">
						<button class="button" id="update" type="button">
							<i class="fa fa-pencil fa-fw"></i>&nbsp;sửa
						</button>
						<button type="reset" class="button" type="button">
							<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Bỏ qua
						</button>
					</div>
				</form>
				</div>
				<div style="color: red; text-align: center;">
				<%}%>
				</div>
			</div>
			<div id="update-form" style="top:60%;position:absolute;width:900px;left:20%;">
				<table style="width:900px;"></table>
				<div class="group-button" id="updateButton">
				<button type="button" class="button" id="updateCs">Lưu lại</button> 
				</div>		
				</div>
		</div>
</body>
</html>