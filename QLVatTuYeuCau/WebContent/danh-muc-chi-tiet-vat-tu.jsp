<%@page import="model.CTVatTu"%>
<%@page import="model.VatTu"%>
<%@page import="model.NoiSanXuat"%>
<%@page import="model.ChatLuong"%>
<%@page import="map.siteMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-ctvt.css"css" type="text/css" rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/chi-tiet-vat-tu.js"></script>
<script>
    $(document).ready(function() {
        $('.checkAll').click(function(event) {  //on click 
            if(this.checked) { // check select status
                $('.checkbox').each(function() { //loop through each checkbox
                    this.checked = true;  //select all checkboxes with class "checkbox1"               
                });
            }else{
                $('.checkbox').each(function() { //loop through each checkbox
                    this.checked = false; //deselect all checkboxes with class "checkbox1"                       
                });         
            }
        });
        
    }); 
	</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
    
    	ArrayList<CTVatTu> listCTVatTu = (ArrayList<CTVatTu>) session.getAttribute("ctVatTuList");
		Long pageNum = (Long) request.getAttribute("size")/10;
   		
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
								<li><a href="<%=siteMap.dvtManage + "?action=manageDvt"%>">Danh mục đơn vị tính</a></li>
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
			<div id="title-content">Danh mục chi tiết vật tư</div>
			<form id="main-form">
					<div id="view-table-chi-tiet" style="height: 480px; margin: 0 auto;">
						<table>
							<tr style="background: #199e5e">
<!-- 								<th class="left-column"><input type="checkbox" -->
<!-- 									class="checkAll"></th> -->
								<th class="six-column">Mã vật tư</th>
								<th class="three-column">Tên vật tư</th>
								<th class="six-column">Nơi sản xuất</th>
								<th class="six-column">Chất lượng</th>
								<th class="four-column">Đơn vị tính</th>
								<th class="five-column">Định mức</th>
								<th class="seven-column">Số lượng tồn</th>
							</tr>
							<%
									if(listCTVatTu != null) {
									int count = 0;
									for(CTVatTu ctVatTu : listCTVatTu) { count++;%>
		
							<tr class="rowContent"
								<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
<!-- 								<td class="left-column"><input type="checkbox" name="vtMa" -->
<%-- 									value="<%=ctVatTu.getVatTu().getVtMa() %>" class="checkbox"></td> --%>
								<td class="col"><%=ctVatTu.getVatTu().getVtMa() %></td>
								<td class="col" style="text-align: left;"><%=ctVatTu.getVatTu().getVtTen() %></td>
								<td class="col"><%=ctVatTu.getNoiSanXuat().getNsxTen() %></td>
								<td class="col"><%=ctVatTu.getChatLuong().getClTen() %></td>
								<td class="col"><%=ctVatTu.getVatTu().getDvt().getDvtTen() %></td>
								<td class="col"><%=ctVatTu.getDinhMuc() %></td>
								<td class="col"><%=ctVatTu.getSoLuongTon() %></td>
		
							</tr>
							<%} }%>
		
						</table>
								
									
					</div>
			<div id = "paging" >
								<%
										String str = "";
										String pages = ""; 
										long p = (pageNum < 10 ? pageNum : 10);
									for(int i = 0; i < p; i++) {
										str += "<input type=\"button\" value=\"" + (i+1) + "\" class=\"page\" onclick= \"loadPageCTVatTu(" + i +")\">&nbsp;";
									}
									if (pageNum > 10)
										// str = "<input type=\"button\" value=\"<<Previous\" onclick= \"loadPageCtvtYc(\'Previous\')\">&nbsp;"  + str + "<input type=\"button\" value=\"Next>>\" onclick= \"loadPageCtvtYc(\'Next\');\">";
										str += "<input type=\"button\" value=\"Sau >>\" onclick= \"loadPageCTVatTu(\'Next\');\">";
									out.println(str);	
								%>
					
			</div>
						<div class="group-button" style="text-align: center;">		
 						<button type="button" class="button" 
							onclick="showForm('import-form', true)"> 
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Import 
						</button>
						
						<button class="button" type="button" onclick="location.href='<%=siteMap.xuatCTVatTu+".jsp"%>'">
							<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xuất File
						</button>
						&nbsp;
						<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>			
				</form>
				</div>
						<form id="import-form" action="<%=siteMap.readExcel %>" method="post" enctype="multipart/form-data" >
								<input type="file" name="file" accept=".xls, .xlsx" class="text" style="padding-left: 0px;">
<div class="button-group" style="margin-top: -40px;"><input value="uploadFile" name="action" type="submit" class="button">
								<input value="Thoát" onclick="showForm('import-form',false);" type="button" class="button">
								</div>
						</form>
				
	
	</div>
</body>
</html>