<%@page import="model.DonVi"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-bo-phan.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="style/style-bo-phan.css"
	type="text/css">

<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bophan.js"></script>
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
    		ArrayList<DonVi> listDonVi = (ArrayList<DonVi>) request.getAttribute("donViList");
			Long size = (Long) request.getAttribute("size");
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
			<div id="title-content">Danh mục bộ phận sử dụng</div>

				<form id="main-form">
					<div id="view-table-bo-phan">
						<table>
							<tr>
								<th class="left-column"><input type="checkbox"
									class="checkAll"></th>
								<th class="mid-column">Mã BPSD</th>
								<th class="column-2">Tên bộ phận</th>
								<th class="mid-column">Số điện thoại</th>
								<th class="column-4">Địa chỉ</th>
								<th class="column-5">Email</th>
							</tr>
							<%
							if(listDonVi != null) {
							int count = 0;
							for(DonVi donVi : listDonVi) {count++ ;%>
							<tr class="rowContent"
								<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
								<td class="left-column"><input type="checkbox" name="dvMa"
									value="<%=donVi.getDvMa() %>" class="checkbox"></td>
								<td class="col"><%=donVi.getDvMa() %></td>
								<td class="col"><%=donVi.getDvTen()%></td>
								<td class="col"><%=donVi.getSdt()%></td>
								<td class="col"><%=donVi.getDiaChi()%></td>
								<td class="col"><%=donVi.getEmail()%></td>
							</tr>
							<%} }%>
						</table>
					
					</div>
					<div id = "paging" >
							<table style ="border-style: none;">
								<tr>
									<td>Trang</td>
									<td>
										<%
											long pageNum = size / 10;
											for(int i = 0; i <= pageNum; i++) { %>
												<input type="button" value="<%=i+1%>" class="page">
										<%} %>
									</td>
								</tr>
							</table>
						</div>
					<div class="group-button-bo-phan">
						<input type="hidden" name="action" value="deleteBpsd">
						<button type="button" class="button"
							onclick="showForm('add-form', true)">
							<i class="fa fa-plus-circle"></i>&nbsp;Thêm
						</button>
						&nbsp;&nbsp;
						<button type="button" class="button"
							onclick="preUpdateBp('update-form', true);">
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Thay đổi
						</button>
						&nbsp;&nbsp;
						<button class="button" type="button" onclick="confirmDelete()">
							<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
						</button>
						&nbsp;&nbsp;
						<button class="button" type="reset">
							<i class="fa fa-spinner"></i>&nbsp;&nbsp;Bỏ qua
						</button>
						&nbsp;&nbsp;
						<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
							<i class="fa fa-sign-out"></i>Thoát
						</button>
					</div>
				</form>
				
				<!-------------- --add-form-------------- -->
				<form id="add-form" method="get" action="<%=siteMap.bpsdManage + "?action=manageBpsd"%>">
					<div class="input-table-bo-phan">
						<table>
							<div class="form-title">Thêm bộ phận sử dụng</div>
							<tr>
								<td class="input"><label for="MBPSD" class="input" >Mã
										BPSD</label></td>
								<td><input name="dvMa" type="text" class="text" required onkeypress="changedvMa();"
									autofocus size="3" maxlength="3" pattern="[a-zA-Z0-9]{3}"
									title="Mã bộ phận sử dụng chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"><div id="requiredvMa" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label for="MBPSD">Tên BPSD</label></td>
								<td><input name="dvTen" size="30px" align=left type="text"
									class="text" required onkeypress="changedvTen();"
									title="Tên bộ phận sử dụng không được để trống"><div id="requiredvTen" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Số điện thoại</label></td>
								<td><input name="sdt" size="15px" maxlength="11" onkeypress="changeSdt();"
									type="text" class="text"><div id="requireSdt" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Địa chỉ</label></td>
								<td><input name="diaChi" size="30px" align=left type="text" onkeypress="changeDiachi();"
									class="text"><div id="requireDiachi" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Email</label></td>
								<td><input name="email" size="30px" align=left type="text" onkeypress="changeEmail();"
									class="text"><div id="requireEmail" style="color: red"></div></td>
							</tr>

							<!--
                        <tr>
                            <td class="input"><label>Số TK</label></td>
							<td><input name="" size="15px" align=left type="text" class="text"></td>                            
                        </tr>
                        <tr>
                        <td class="input"><label>Mã số thuế</label></td>
                            <td><input name="" align=left type="text" class="text"></td>
                        </tr>
                        <tr>
                            <td class="input"><label>Loại BP</label></td>
							<td><select name = "" required class="select">
                                    <option class="option" value=""></option>
                                    <option value="" class="option">AAAAAAAAAA</option>
                                    <option>AAAAAAAAAA</option>
                                </select>
                            </td>
                        </tr>
-->
						</table>
					</div>
					<div class="button-group">
<!-- 						<input type="hidden" name="action" value="AddBpsd"> -->
						<button type="button" class="button" onclick="addBp();">
							<i class="fa fa-plus-circle"></i>&nbsp;Thêm
						</button>
						<button type="reset" class="button">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
						</button>
						<button type="button" class="button"
							onclick="showForm('add-form', false)">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>
				</form>

				<!-- ---------------Update form-------------- -->
				<form id="update-form">
					<div class="input-table">
						<table>
							<div class="form-title">Cập nhật bộ phận sử dụng</div>
							<tr>
								<td class="input"><label for="MBPSD" class="input">Mã
										BPSD</label></td>
								<td><input name="dvMaUpdate" type="text" class="text"
									required  size="2" maxlength="3" readonly style="background-color: #D1D1E0;"
									pattern="[a-zA-Z0-9]{3}"
									title="Mã bộ phận sử dụng chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"></td>
							</tr>
							<tr>
								<td class="input"><label for="MBPSD">Tên BPSD</label></td>
								<td><input name="dvTenUpdate" size="30px" align=left
									type="text" class="text" required autofocus onkeypress="changedvTenUp();"
									title="Tên bộ phận sử dụng không được để trống"><div id="requiredvTenUp" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Số điện thoại</label></td>
								<td><input name="sdtUpdate" size="15px" align=left onkeypress="changeSdtUp();"
									type="text" class="text" maxlength="12"><div id="requireSdtUp" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Địa chỉ</label></td>
								<td><input name="diaChiUpdate" size="30px" align=left onkeypress="changeDiachiUp();"
									type="text" class="text"><div id="requireDiachiUp" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Email</label></td>
								<td><input name="emailUpdate" size="30px" align=left onkeypress="changeEmailUp();"
									type="text" class="text"><div id="requireEmailUp" style="color: red"></div></td>
							</tr>

							<!--
                        <tr>
                            <td class="input"><label>Số TK</label></td>
							<td><input name="" size="15px" align=left type="text" class="text"></td>                            
                        </tr>
                        <tr>
                        <td class="input"><label>Mã số thuế</label></td>
                            <td><input name="" align=left type="text" class="text"></td>
                        </tr>
                        <tr>
                            <td class="input"><label>Loại BP</label></td>
							<td><select name = "" required class="select">
                                    <option class="option" value=""></option>
                                    <option value="" class="option">AAAAAAAAAA</option>
                                    <option>AAAAAAAAAA</option>
                                </select>
                            </td>
                        </tr>
-->
						</table>
					</div>
					<div class="button-group">
						<button type="button" class="button" onclick="confirmUpdateBp();">
							<i class="fa fa-plus-circle"></i>&nbsp;Lưu lại
						</button>
						<button type="button" class="button" onclick="resetUpdateBP();">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
						</button>
						<button type="button" class="button"
							onclick="showForm('update-form', false)">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>
				</form>
			</div>
		</div>
</body>
</html>