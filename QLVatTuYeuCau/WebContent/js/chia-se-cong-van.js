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
//$(document).ready(function() {
//	$('#update').click(function(){
//		alert('OK');
//	});
//});


function loadPageCscv(pageNumber) {
	
	var page = 0;
	var p = 0;
	if (pageNumber == 'Next') {
		var lastPage = document.getElementsByClassName('page')[9].value;
		p = (lastPage) / 5;
		page = p * 5;
	}
	else if (pageNumber == 'Previous') {
		var firstPage = document.getElementsByClassName('page')[0].value;
		p = (firstPage - 1) / 5;
		page =  p * 5 - 1;
	}
	else {
		page = pageNumber;
	}
	$.ajax({
		url: "/QLVatTuYeuCau/loadPageCscv.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "pageNumber": page},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(objectList) {
	  		var size = objectList[1];
	  		var ndList = objectList[0];
	  		var length = ndList.length;
	  			$('#view-table table .rowContent').remove();
				for(i = 0; i < length; i++ ) {
					var nd = ndList[i];
					var cells = '';
					var style = '';
					if (i % 2 == 0)
						style = 'style=\"background : #CCFFFF;\"';
					cells =   '<td>' + nd.nguoiDung.msnv + '</td>'
								+ '<td>' + nd.nguoiDung.hoTen + '</td>'
								+ '<td>' + nd.vaiTro.vtTen + '</td>'
					var row = '<tr ' +style + 'class = \"rowContent\">' + cells + '</tr>';
					 $('#view-table table tr:first').after(row);
				}
					var button = '';
					if(pageNumber == 'Next') {
						for (var i = 0; i < 10; i++) {
							var t = ((p -1) * 5 + i + 1);
							
							button += '<input type=\"button\" value=\"' + ((p -1) * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageCscv(' + ((p -1)*5 + i)  +')\">&nbsp;';
							if (t > size)
								break;
						}
						button = '<input type=\"button\" value=\"<<\" onclick= \"loadPageCscv(\'Previous\')\">&nbsp;'  + button;
						if ((p + 1) * 5 < size)
							button += '<input type=\"button\" value=\">>\" onclick= \"loadPageCscv(\'Next\');\">';
						$('#paging').html(button);
					} else if (pageNumber == 'Previous'){
						if (p > 0)
							p = p -1;
						for (var i = 0; i < 10; i++)
							button += '<input type=\"button\" value=\"' + (p * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageCscv(' + (p * 5 + i)  +')\">&nbsp;';
						
						button = button + '<input type=\"button\" value=\">>\" onclick= \"loadPageCscv(\'Next\');\">';
						if (p >= 1)
							button = '<input type=\"button\" value=\"<<\" onclick= \"loadPageCscv(\'Previous\')\">&nbsp;' + button;
						$('#paging').html(button);	
					}
	  	}
	});
}

$(document).ready(function() {
	$('#update').click(function() {
		var msnv = $('#view-chia-se input:checkbox[name=msnv]:checked').val();
		//alert(msnv);
		var msnvList = [];
		$.each($('#view-chia-se input:checkbox[name=msnv]:checked'), function(){            
			msnvList.push($(this).val());
		});

		if (msnvList.length == 0)
			alert('Bạn phải chọn 1 nhân viên để thay đổi!!');
		else if (msnvList.length > 1)
			alert('Bạn chỉ được chọn 1 nhân viên để thay đổi!!');
		else {
			$.ajax({
				url: "/QLVatTuYeuCau/preUpdateYeuCau.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "msnv": msnv},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	success: function(objectList) {
			  		var nguoiDung = objectList[0];
			  		var vaiTroList = objectList[1];
			  		var vtCongVanList= objectList[2];
			  		var head = '';
			  		var length = vaiTroList.length;
			  		for(var i = 0; i < length; i++) {
			  			head += '<th>' + vaiTroList[i].vtTen + '</th>';
			  		}
			  		head = '<tr><th>Msnv</th>' + head + '</tr>';
			  		
			  		var content = '';
			  		for(var i = 0; i < length; i++) {
			  			content += '<td><input type=\"checkbox\" class=\"checkbox\" name=\"vaiTro\" value=\"' + vaiTroList[i].vtId + '\" id=\"' + vaiTroList[i].vtId + '\"></td>';
			  		}
			  		content = '<tr><td>' + nguoiDung +'</td>' + content + '</tr>';
			  		var button = '<button type=\"button\" class=\"button\" id=\"updateCs\">Luu lai</button>';
			  		
			  		$('#update-form table').html(head + content + button);
			  	//	$('#updateButton').html(button);
//			  		alert(vtCongVanList.length);
			  		for (var i = 0; i < vtCongVanList.length; i++) {
//			  			alert('#update #'+vtCongVanList[i].vtId);
			  			$('#'+vtCongVanList[i].vtId).prop('checked',true);
			  		}
			  	}
			});
			$('#main-form').hide();
			$('#update-form').show();
			
		}	
	});   
});
$(document).ready(function() {
	$('#updateCs').click(function() {
		var vaiTro = $('#update-form input:checkbox[name=vaiTro]:checked').val();
//		alert(msnv);
		var vaiTroList = [];
		$.each($('#update-form input:checkbox[name=vaiTro]:checked'), function(){            
			vaiTroList.push($(this).val());
		});
		var str = vaiTroList.join(', ');
			$.ajax({
				url: "/QLVatTuYeuCau/updateYeuCau.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "vaiTroList": str},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	success: function(objectList) {
//			  		alert('OK');
			  		var vaiTroList = objectList[0];
			  		var msnv = objectList[1];
			  		
			  		var length = vaiTroList.length;
			  		if (vaiTroList.length==0) {
			  			$('#view-chia-se table tr').has('input[name="msnv"]:checked').remove();
//			  			alert('Ban da d')
			  		}
			  		else {
			  			
			  			var content = '';
			  			//alert('#' + msnv + ' input:checkbox[name=vaiTro]');
			  			$('#' + msnv +' input:checkbox').prop('checked',false);
				  		for (var i = 0; i < vaiTroList.length; i++) {
				  			content += vaiTroList[i].vtTen + '<br>';
//				  			$('#view-table input[name=' +   + ']')
				  			$('#view-table input:checkbox[name=vaiTro][value='+ (msnv + '#' + vaiTroList[i].vtId ) + ']').prop('checked',true);
				  		}
			  			$('#vaiTro' + msnv).html(content);
			  		}
			  	}
			});
			$('#main-form').show();
			$('#update-form').hide();
			
	});   
});