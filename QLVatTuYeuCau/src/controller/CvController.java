package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;

import dao.CongVanDAO;
import dao.DonViDAO;
import dao.FileDAO;
import dao.MucDichDAO;
import dao.NguoiDungDAO;
import dao.NhatKyDAO;
import dao.TrangThaiDAO;
import dao.VTCongVanDAO;
import dao.VaiTroDAO;
import map.siteMap;
import model.CongVan;
import model.DonVi;
import model.File;
import model.MucDich;
import model.NguoiDung;
import model.NhatKy;
import model.TrangThai;
import model.VTCongVan;
import model.VaiTro;
import util.DateUtil;
import util.FileUtil;
import util.JSonUtil;
import util.Mail;
import util.SendMail;


@Controller
public class CvController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	int page = 1;
	private HttpSession session;
	 @Autowired
	private   ServletContext context; 
	
	private final String tempPath = "File/Temp/"; 
    private final String pathFile = "File/File/";
    private String root = "";
    private final int maxSize = 52428800;
    
    private int year = 0;
    private int month = 0;
    private int date = 0;
    private String ttMa = "";
    private String column = "";
    private Object columnValue = "";
    private Integer cvId = 0;
    
    private String truongPhongMa = "";
    private String adminMa = "";
    private String vanThuMa = "";
    private int vtCapVt = 0;
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	chain.doFilter(request, response);
    }
    
    public ModelAndView getCongvan( HttpServletRequest request) {
    	truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
    	adminMa = context.getInitParameter("adminMa");
    	vtCapVt = Integer.parseInt(context.getInitParameter("capPhatId"));
    	session = request.getSession(false);
    	NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    	String msnv = nguoiDung.getMsnv();
    	
    	VTCongVanDAO vtcvDAO =  new VTCongVanDAO();
    	
    	TrangThaiDAO trangThaiDAO =  new TrangThaiDAO();
    	CongVanDAO congVanDAO =  new CongVanDAO();
    	FileDAO fileDAO =  new FileDAO();
    	String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		
		if (truongPhongMa.equalsIgnoreCase(cdMa) || vanThuMa.equalsIgnoreCase(cdMa) || adminMa.equalsIgnoreCase(cdMa)) {
			msnvTemp = null;
			// danh sach vai tro nguoi dung doi voi cong van
		}
		Integer cv = (Integer) request.getAttribute("cvId");
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		if (cv != null) {
			conditions.put("cvId", cv);
			this.cvId = cv;
		} else 
			this.cvId = 0;
		
		orderBy.put("cvId", false);
    	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(msnvTemp, conditions, null, 0, 3);
    	//chung
		HashMap<Integer, File> fileHash = new HashMap<Integer, File>();
		if (cdMa.equals(vanThuMa) || cdMa.equals(truongPhongMa) || cdMa.equals(adminMa)) {
			MucDichDAO mucDichDAO =  new MucDichDAO();
	    	DonViDAO donViDAO =  new DonViDAO();
			ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
			ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) mucDichDAO.getAllMucDich();
	//		ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) trangThaiDAO.getAllTrangThai();
			donViDAO.disconnect();
			mucDichDAO.disconnect();
			request.setAttribute("mucDichList", mucDichList);
			request.setAttribute("donViList", donViList);
			
		}
		long size = 0;
		if (cv != null)
			size = 1;
		else
			size = congVanDAO.size(msnvTemp, null);
		// danh sach nguoi xu ly cong van
    	ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>> ();
		ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>> ();
		ArrayList<String> ttMaList =  new ArrayList<String>();
		if (msnvTemp != null) {
			VaiTroDAO vaiTroDAO =  new VaiTroDAO();
			for(CongVan congVan : congVanList) {
				int cvId = congVan.getCvId();
				fileHash.put(cvId, fileDAO.getByCongVanId(cvId));
//				ArrayList<VTCongVan> vtCongVan = vtcvDAO.getVtCongVan()
				ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnvTemp);
				ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
				vaiTroList.add(vaiTro);
				vtCongVanList.add(vtcvList);
			}
			vaiTroDAO.disconnect();
			request.setAttribute("vaiTroList", vaiTroList);
			request.setAttribute("vtCongVanList", vtCongVanList);
			
		}
		else if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)) {
			for(CongVan congVan : congVanList) {
				System.out.println(congVan.getSoDen());
				int cvId = congVan.getCvId();
				fileHash.put(cvId, fileDAO.getByCongVanId(cvId));
				
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				nguoiXlCongVan.add(nguoiXl);
				ttMaList.add(congVan.getTrangThai().getTtMa());
			}
			request.setAttribute("nguoiXlCongVan", nguoiXlCongVan);
			
		}
		ArrayList<Integer> yearList = new ArrayList<Integer>();
		if (cv != null)
			yearList.add(congVanList.get(0).getCvNgayNhan().getYear() + 1900);
		else 
			yearList = congVanDAO.groupByYearLimit(msnvTemp, null, 5);
		request.setAttribute("congVanList", congVanList);
		request.setAttribute("fileHash", fileHash);
		
		request.setAttribute("yearList", yearList);
		request.setAttribute("size", size);
		request.setAttribute("ttMaList", ttMaList);
		congVanDAO.disconnect();
		
		trangThaiDAO.disconnect();
		fileDAO.disconnect();
		
		vtcvDAO.disconnect();

		return new ModelAndView(siteMap.congVan);
    }
    
    @RequestMapping("/cvManage")
	public ModelAndView manageCV(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	request.getCharacterEncoding();
//		response.getCharacterEncoding();
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
    	request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession(false);
		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
		if (nguoiDung == null)
			response.sendRedirect("login.jsp");
		
		String action = request.getParameter("action");
		if("manageCv".equalsIgnoreCase(action)) {
			this.cvId = 0;
			return getCongvan(request);
		}
		 year = 0;
		 month = 0;
		 date = 0;
		 ttMa = "";
		 column = "";
		 
		 columnValue = "";
		 cvId = 0;
		
		return new ModelAndView("login");
	}
    @RequestMapping("/downloadFileMn")
   	public void downloadFileMn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");
    	if("download".equals(action)) {
			try {
				FileDAO fileDAO = new FileDAO();
				int cvId = Integer.parseInt(request.getParameter("file"));
				model.File f = fileDAO.getByCongVanId(cvId);
	//			java.io.File file = new java.io.File(f.getDiaChi());
				fileDAO.close();
				RequestDispatcher dispatcher = request.getRequestDispatcher("downloadFile.html");
				
				request.setAttribute("path", f.getDiaChi());
				dispatcher.forward(request, response);
				return;
			} catch (NumberFormatException e){
				System.out.println("Cannot convert to int");
			}
    	}
	}
    @RequestMapping("/searchCongVan")
   	public ModelAndView searchCongVan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       	try {
       		int cvId = Integer.parseInt(request.getParameter("congVan"));
       		request.setAttribute("cvId", cvId);
       		return getCongvan(request);
       	} catch (NumberFormatException e) {
       		return new ModelAndView(siteMap.login);
       	}
    }
    @RequestMapping("addCongVan")
    public ModelAndView addCV(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	request.ge
    	HttpSession session = request.getSession(false);
    	NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
    	root =  request.getRealPath("/");
    	//root =  "/home/quoioln/DATA/";
    //	root =  "/home/quoioln/DATA/";
//    	request.getCharacterEncoding();
//		response.getCharacterEncoding();
    	response.setContentType ("text/html;charset=utf-8");
    	request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
    	CongVanDAO congVanDAO = new CongVanDAO();
    	FileDAO fileDAO = new FileDAO();
    	
    	int cvId = congVanDAO.getLastInsert();
    	
    	MultipartRequest multipartRequest = new  MultipartRequest(request, root + tempPath, maxSize);
    	
		String action = multipartRequest.getParameter("action");
		String cvSo = multipartRequest.getParameter("cvSo");
//		System.out.println(cvSo);
		CongVan congVanCheck = congVanDAO.getByCvSo(cvSo);
//		if (congVanCheck != null && congVanCheck.getDaXoa() == 0) {
//			request.setAttribute("error", "error");
//			return getCongvan(request);
//		}
		Date cvNgayGoi = DateUtil.parseDate(multipartRequest.getParameter("ngayGoi"));
		Date cvNgayNhan = DateUtil.parseDate(multipartRequest.getParameter("ngayNhan"));
		String mdMa = multipartRequest.getParameter("mucDich");
		String dvMa = multipartRequest.getParameter("donVi");
		String trichYeu = multipartRequest.getParameter("trichYeu");
		String butPhe = multipartRequest.getParameter("butPhe");
		String moTa = multipartRequest.getParameter("moTa");
		int soDen = congVanDAO.getSoDenMax();
		if (cvNgayNhan.getDate() == 1 && soDen != 1)
			soDen = 1;
		// upload file
		String fileFullName = "";
		Enumeration<String> listFileName = multipartRequest.getFileNames();		
		String fileName = "";
		while(listFileName.hasMoreElements()) {
			fileFullName = multipartRequest.getFilesystemName(listFileName.nextElement().toString());
			//int i = fileName.lastIndexOf("."); 
			
			
			java.io.File file = new java.io.File(root + tempPath + fileFullName);
			fileName = FileUtil.getNameFile(file);
			String fileExtension = FileUtil.getExtension(file);
			if(fileExtension.length() > 0) {
				 fileName = fileName + "-" + cvId + "." + fileExtension;
			 } else {
				 fileName = fileName + "-" + cvId;
			 }
				
			file.renameTo(new java.io.File(root + pathFile + fileName));
		}
		if (congVanCheck != null) {
			congVanCheck.setSoDen(soDen);
			congVanCheck.setCvSo(cvSo);
			congVanCheck.setButPhe(butPhe);
			congVanCheck.setCvNgayDi(cvNgayGoi);
			congVanCheck.setCvNgayNhan(cvNgayNhan);
			congVanCheck.setDonVi(new DonVi(dvMa));
			congVanCheck.setMucDich(new MucDich(mdMa));
			congVanCheck.setTrangThai(new TrangThai("CGQ",""));
			congVanCheck.setTrichYeu(trichYeu);
			congVanCheck.setDaXoa(0);
			congVanDAO.updateCongVan(congVanCheck);
			cvId = congVanCheck.getCvId();
			File file = fileDAO.getByCongVanId(cvId);
			file.setDiaChi(root + pathFile + fileName);
			file.setMoTa(moTa);
			fileDAO.updateFile(file);
			
		} else {
			
			congVanDAO.addCongVan(new CongVan (soDen, cvSo, cvNgayNhan, cvNgayGoi, trichYeu, butPhe, new MucDich(mdMa), new TrangThai("CGQ",""), new DonVi(dvMa),0));
			fileDAO.addFile(new File(root + pathFile + fileName, moTa, cvId));
			String account  = context.getInitParameter("account");
	    	String password = context.getInitParameter("password");
	    	String truongPhongMa = context.getInitParameter("truongPhongMa");
	    	String host = context.getInitParameter("hosting");
	    	
			SendMail sendMail = new SendMail(account, password);
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			ArrayList<NguoiDung> nguoiDungList = (ArrayList<NguoiDung>) nguoiDungDAO.getTruongPhong(truongPhongMa);
			for (NguoiDung nguoiDung : nguoiDungList) {
				Mail mail = new Mail();
				mail.setFrom(account);
				mail.setTo(nguoiDung.getEmail());
				mail.setSubject("Công việc được chia sẻ");
				String content = "Chào " + nguoiDung.getHoTen() +",\n";
				content += " Có công văn mới được cập nhật. Vui lòng vào hệ thống làm việc để kiểm tra.\n";
				content += host + siteMap.searchCongVan + "?congVan=" + cvId;
				mail.setContent(content);
				sendMail.send(mail);
			}
			
    	}
		String content = "";
		DonViDAO donViDAO = new DonViDAO();
		MucDichDAO mucDichDAO = new MucDichDAO();
		MucDich mucDich = mucDichDAO.getMucDich(mdMa);
		DonVi donVi = donViDAO.getDonVi(dvMa);
		mucDichDAO.disconnect();
		donViDAO.disconnect();
		content += "&nbsp;&nbsp;+ Đơn vị: " + donVi.getDvTen() +"<b>";
		content += "&nbsp;&nbsp;+ Ngày gởi " + cvNgayGoi + "<br>";
		content += "&nbsp;&nbsp;+ Mục đích: " + mucDich.getMdTen() + "<br>";
		content += "&nbsp;&nbsp;+ Ngày gởi: " + cvNgayNhan + "<br>";
		content += "&nbsp;&nbsp;+ Trích yếu: " + trichYeu + "<br>";
		content += "&nbsp;&nbsp;+ Bút phế: " + butPhe + "<br>";
		if (fileFullName != null) {
			content += "&nbsp;&nbsp;+ Tên tệp: " + fileFullName;
		}
		Date currentDate = DateUtil.convertToSqlDate(new java.util.Date ());
		NhatKyDAO nhatKyDAO = new NhatKyDAO();
		NhatKy nhatKy = new NhatKy(authentication.getMsnv(), cvId + "#Thêm công văn số " + soDen + " nhận ngày " + cvNgayNhan, currentDate, content);
		nhatKyDAO.addNhatKy(nhatKy);
		nhatKyDAO.disconnect();
		congVanDAO.close();
		fileDAO.close();
		return getCongvan(request);
    }
    
    @RequestMapping(value="/preEditCongVan", method=RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String changePass(@RequestParam("cvId") int cvId) {
    	CongVanDAO congVanDAO = new CongVanDAO();
		CongVan congVan = congVanDAO.getCongVan(cvId);
		congVanDAO.disconnect();
		return JSonUtil.toJson(congVan);
	}
    @RequestMapping("updateCongVan")
    public ModelAndView updateCongVan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	root =  "/home/quoioln/DATA/";
//    	request.getCharacterEncoding();
//		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
    	CongVanDAO congVanDAO = new CongVanDAO();
    	FileDAO fileDAO = new FileDAO();
    	// 
    	
    	MultipartRequest multipartRequest = new MultipartRequest(request, root + tempPath, maxSize);
		String action = multipartRequest.getParameter("action");
		String soDen = new String(multipartRequest.getParameter("soDen").getBytes("Windows-1252"), "UTF-8");
		String cvSo =  new String(multipartRequest.getParameter("cvSo").getBytes("Windows-1252"), "UTF-8");
		Date cvNgayGoi = DateUtil.parseDate(multipartRequest.getParameter("ngayGoiUpdate"));
		Date cvNgayNhan = DateUtil.parseDate(multipartRequest.getParameter("ngayNhanUpdate"));
		String mdMa =  new String(multipartRequest.getParameter("mucDichUpdate").getBytes("Windows-1252"), "UTF-8");
		String dvMa =  new String(multipartRequest.getParameter("donViUpdate").getBytes("Windows-1252"), "UTF-8");
		String trichYeu = new String(multipartRequest.getParameter("trichYeuUpdate").getBytes("Windows-1252"), "UTF-8");
		
		String butPhe =  new String(multipartRequest.getParameter("butPheUpdate").getBytes("Windows-1252"), "UTF-8");
		String fileFullName = "";
		CongVan congVan = congVanDAO.getByCvSo(cvSo);
		int cvId = congVan.getCvId();
		Enumeration<String> listFileName = multipartRequest.getFileNames();		
		String fileName = "";
		while(listFileName.hasMoreElements()) {
			fileFullName = multipartRequest.getFilesystemName(listFileName.nextElement().toString());
			java.io.File file = new java.io.File(root + tempPath + fileFullName);
			fileName = FileUtil.getNameFile(file);
			String fileExtension = FileUtil.getExtension(file);
			if(fileExtension.length() > 0) {
				 fileName = fileName + "-" + cvId + "." + fileExtension;
			 } else {
				 fileName = fileName + "-" + cvId;
			 }
				
			file.renameTo(new java.io.File(root + pathFile + fileName));
			
		}
		
		congVan.setButPhe(butPhe);
		congVan.setCvNgayDi(cvNgayGoi);
		congVan.setCvNgayNhan(cvNgayNhan);
		congVan.setDonVi(new DonVi(dvMa));
		congVan.setMucDich(new MucDich(mdMa));
		congVan.setTrichYeu(trichYeu);
		congVanDAO.updateCongVan(congVan);
//    	File
		if (fileFullName != null) {
			File file = fileDAO.getByCongVanId(cvId);
			file.setDiaChi(root + pathFile + fileName);
			fileDAO.updateFile(file);
			fileDAO.close();
		}
		congVanDAO.close();
		HttpSession session = request.getSession(false);
    	NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
		NhatKyDAO nhatKyDAO = new NhatKyDAO();
		Date currentDate = DateUtil.convertToSqlDate(new java.util.Date ());
		String content = "";
		DonViDAO donViDAO = new DonViDAO();
		MucDichDAO mucDichDAO = new MucDichDAO();
		MucDich mucDich = mucDichDAO.getMucDich(mdMa);
		DonVi donVi = donViDAO.getDonVi(dvMa);
		mucDichDAO.disconnect();
		donViDAO.disconnect();
		content += "&nbsp;&nbsp;+ Đơn vị: " + donVi.getDvTen() +"<br>";
		content += "&nbsp;&nbsp;+ Ngày gởi " + cvNgayGoi + "<br>";
		content += "&nbsp;&nbsp;+ Mục đích: " + mucDich.getMdTen() + "<br>";
		content += "&nbsp;&nbsp;+ Ngày gởi: " + cvNgayNhan + "<br>";
		content += "&nbsp;&nbsp;+ Trích yếu: " + trichYeu + "<br>";
		content += "&nbsp;&nbsp;+ Bút phế: " + butPhe;
		if (fileFullName != null)
			content += "<br>&nbsp;&nbsp;+ Tên tệp: " + fileFullName;

		NhatKy nhatKy = new NhatKy(authentication.getMsnv(), cvId + "#Thay đổi công văn số " + soDen + " nhận ngày " + cvNgayNhan, currentDate, content);
		nhatKyDAO.addNhatKy(nhatKy);
		nhatKyDAO.disconnect();
		System.out.println(trichYeu);
		return getCongvan(request);
    }
	@RequestMapping(value="/deleteCv", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteNsx(@RequestParam("cvId") String cvId, HttpServletRequest request,
	            HttpServletResponse response) {
		String[] congVanList = cvId.split("\\, ");
		CongVanDAO congVanDAO = new CongVanDAO();
		StringBuilder content = new StringBuilder("");
		for (String s : congVanList) {
			int id = Integer.parseInt(s);
			CongVan congVan = congVanDAO.getCongVan(id);
			content.append("&nbsp;&nbsp;+ Số đến " + congVan.getSoDen() + " nhận ngày " + congVan.getCvNgayNhan() + "<br>");
			congVanDAO.deleteCongVan(id);
		}
		content.delete(content.length() - 4, content.length());
		congVanDAO.disconnect();
		HttpSession session = request.getSession(false);
    	NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
		NhatKyDAO nhatKyDAO = new NhatKyDAO();
		Date currentDate = DateUtil.convertToSqlDate(new java.util.Date ());
		NhatKy nhatKy = new NhatKy(authentication.getMsnv(), "Xóa công văn", currentDate, content.toString());
		nhatKyDAO.addNhatKy(nhatKy);
		nhatKyDAO.disconnect();
		return JSonUtil.toJson(cvId);
	}
	
	@RequestMapping(value="/preUpdateCv", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preUpdateCv(@RequestParam("congVan") String congVan, HttpServletRequest request,
	            HttpServletResponse responses) {
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		int id = Integer.parseInt(congVan);
		CongVan cv = congVanDAO.getCongVan(id);
		File file = fileDAO.getByCongVanId(id);
		
		
		fileDAO.disconnect();
		congVanDAO.close();
		ArrayList<Object> objectList = new ArrayList<Object>();
		objectList.add(cv);
		objectList.add(file);
		
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/loadByYear", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadByYear(@RequestParam("year") String yearRequest, HttpServletRequest request,
	            HttpServletResponse response) {
		truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
    	adminMa = context.getInitParameter("adminMa");
		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    	String msnv = nguoiDung.getMsnv();
		
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		year = Integer.parseInt(yearRequest);
		
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		month = 0;
		date = 0;
		String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa))
			msnvTemp = null;
		
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && ((String) columnValue).length() > 0) {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt((String) columnValue));
			else
				conditions.put(column, columnValue);
		}
		if (this.cvId != 0) {
			conditions.put("cvId", cvId);
		}	
		ArrayList<Integer> monthList = new ArrayList<Integer>();
		
		monthList = congVanDAO.groupByMonth(msnvTemp, conditions, year);
		orderBy.put("cvId", true);
		if (year != 0)
			conditions.put("year", year);
		
		
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
		
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>> ();
		ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>> ();
		VTCongVanDAO vtcvDAO = new VTCongVanDAO();
		if (msnvTemp != null) {
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				int cvId = congVan.getCvId();
				ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnvTemp);
				ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
				vaiTroList.add(vaiTro);
				vtCongVanList.add(vtcvList);
			}
			vaiTroDAO.disconnect();
		} else {
			for(CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				
				int cvId = congVan.getCvId();
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				nguoiXlCongVan.add(nguoiXl);
			}
		}
		long size = 0;
		if (this.cvId != 0) 
			size  = 1;
		else
			size = congVanDAO.size(msnvTemp, conditions);
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		objectList.add(monthList);
		long page = (size % 3 == 0 ? size/3 : (size/3) +1 );
		objectList.add(page);
		if (msnvTemp == null) {
			objectList.add(nguoiXlCongVan);
		} else {
			objectList.add(vaiTroList);
			objectList.add(vtCongVanList);
		}
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/loadByMonth", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadByMonth(@RequestParam("year") String yearRequest, @RequestParam("month") String monthRequest, HttpServletRequest request,
	            HttpServletResponse response) {
		truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
    	adminMa = context.getInitParameter("adminMa");
    	
		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    	String msnv = nguoiDung.getMsnv();
		year = Integer.parseInt(yearRequest);
		month = Integer.parseInt(monthRequest);
		date = 0;
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && ((String) columnValue).length() > 0) {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt((String) columnValue));
			else
				conditions.put(column, columnValue);
		}
		if (this.cvId != 0) {
			conditions.put("cvId", cvId);
		}
		String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || cdMa.equals(adminMa))
			msnvTemp = null;
		ArrayList<Integer> dateList = new ArrayList<Integer>();
			dateList = congVanDAO.groupByDate(msnvTemp, conditions, year, month);
		if (year != 0)
			conditions.put("year", year);
		if (month != 0)
			conditions.put("month", month);
		orderBy.put("cvNgayNhan", true);
		
		
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		
		
		ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>> ();
		ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>> ();
		VTCongVanDAO vtcvDAO = new VTCongVanDAO();
		if (msnvTemp != null) {
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				int cvId = congVan.getCvId();
				ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnvTemp);
				ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
				vaiTroList.add(vaiTro);
				vtCongVanList.add(vtcvList);
			}
			vaiTroDAO.disconnect();
		} else {
			for(CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				
				int cvId = congVan.getCvId();
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				nguoiXlCongVan.add(nguoiXl);
			}
		}
		long size = 0;
		if (this.cvId != 0) 
			size  = 1;
		else
			size = congVanDAO.size(msnvTemp, conditions);
		
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		objectList.add(dateList);
		long page = (size % 3 == 0 ? size/3 : (size/3) +1 );
		objectList.add(page);
		if (msnvTemp == null) {
			objectList.add(nguoiXlCongVan);
		} else {
			objectList.add(vaiTroList);
			objectList.add(vtCongVanList);
		}
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/loadByDate", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadByDate(@RequestParam("year") String yearRequest, @RequestParam("month") String monthRequest, @RequestParam("date") String dateRequest, HttpServletRequest request,
	            HttpServletResponse response) {
		truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
    	adminMa = context.getInitParameter("adminMa");
    	
		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    	String msnv = nguoiDung.getMsnv();
    	year = Integer.parseInt(yearRequest);
		month = Integer.parseInt(monthRequest);
		date = Integer.parseInt(dateRequest);
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		if (year != 0)
			conditions.put("year", year);
		if (month != 0)
			conditions.put("month", month);
		if (date != 0)
			conditions.put("day", date);
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && ((String) columnValue).length() > 0) {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt((String) columnValue));
			else
				conditions.put(column, columnValue);
		}
		if (this.cvId != 0) {
			conditions.put("cvId", cvId);
		}
		orderBy.put("cvNgayNhan", true);
		String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || cdMa.equals(adminMa))
			msnvTemp = null;
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>> ();
		ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>> ();
		VTCongVanDAO vtcvDAO = new VTCongVanDAO();
		if (msnvTemp != null) {
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				int cvId = congVan.getCvId();
				ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnvTemp);
				ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
				vaiTroList.add(vaiTro);
				vtCongVanList.add(vtcvList);
			}
			vaiTroDAO.disconnect();
		} else {
			for(CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				
				int cvId = congVan.getCvId();
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				nguoiXlCongVan.add(nguoiXl);
			}
		}
		long size = 0;
		if (this.cvId != 0) 
			size  = 1;
		else
			size = congVanDAO.size(msnvTemp, conditions);
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		long page = (size % 3 == 0 ? size/3 : (size/3) +1 ); 
		objectList.add(page);
		if (msnvTemp == null) {
			objectList.add(nguoiXlCongVan);
		} else {
			objectList.add(vaiTroList);
			objectList.add(vtCongVanList);
		}
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/searchByTrangThai", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String searchByTrangThai(@RequestParam("trangThai") String trangThai, HttpServletRequest request,
	            HttpServletResponse response) {
		truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
    	adminMa = context.getInitParameter("adminMa");
		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
		
    	String msnv = nguoiDung.getMsnv();
    	String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || cdMa.equals(adminMa))
			msnvTemp = null;
		
		ttMa = trangThai;
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		
		
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && ((String) columnValue).length() > 0)  {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt((String) columnValue));
			else
				conditions.put(column, columnValue);
		}
		if (this.cvId != 0) {
			conditions.put("cvId", cvId);
		}	
		if (year != 0)
			conditions.put("year", year);
		if (month != 0)
			conditions.put("month", month);
		if (date != 0)
			conditions.put("day", date);
		orderBy.put("cvNgayNhan", true);
		orderBy.put("soDen", true);
		
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>> ();
		ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>> ();
		VTCongVanDAO vtcvDAO = new VTCongVanDAO();
		if (msnvTemp != null) {
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				int cvId = congVan.getCvId();
				ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnvTemp);
				ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
				vaiTroList.add(vaiTro);
				vtCongVanList.add(vtcvList);
			}
			vaiTroDAO.disconnect();
		} else {
			for(CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				
				int cvId = congVan.getCvId();
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				nguoiXlCongVan.add(nguoiXl);
			}
		}
		long size = 0;
		if (this.cvId != 0) 
			size  = 1;
		else
			size = congVanDAO.size(msnvTemp, conditions);
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		long page = (size % 3 == 0 ? size/3 : (size/3) +1 ); 
		objectList.add(page);
		if (msnvTemp == null) {
			objectList.add(nguoiXlCongVan);
		} else {
			objectList.add(vaiTroList);
			objectList.add(vtCongVanList);
		}
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/filter", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String filter(@RequestParam("filter") String filter, @RequestParam("filterValue") String filterValue, HttpServletRequest request,
	            HttpServletResponse response) {
		truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
    	adminMa = context.getInitParameter("adminMa");
		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    	String msnv = nguoiDung.getMsnv();
    	String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || cdMa.equals(adminMa))
			msnvTemp = null;
		
		if (filter.equals("mdMa"))
			column = "mucDich."+"mdMa";
		else if (filter.equals("dvMa"))
			column = "donVi."+"dvMa";
		else
			column = filter;
		columnValue = filterValue;
		if (filter.equals("cvNgayNhan") || filter.equals("cvNgayDi"))
			columnValue = DateUtil.parseDate((String) columnValue);
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && columnValue.toString().length() > 0)  {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt((String) columnValue));
			else
				conditions.put(column, columnValue);
		}
		if (this.cvId != 0) {
			conditions.put("cvId", cvId);
		}
//		ArrayList<Integer> yearList = congVanDAO.groupByYearLimit(msnvTemp, conditions, 5);
		
		if (year != 0)
			conditions.put("year", year);
		if (month != 0)
			conditions.put("month", month);
		if (date != 0)
			conditions.put("day", date);
		orderBy.put("cvNgayNhan", true);
		
		
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>> ();
		ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>> ();
		VTCongVanDAO vtcvDAO = new VTCongVanDAO();
		if (msnvTemp != null) {
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				int cvId = congVan.getCvId();
				ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnvTemp);
				ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
				vaiTroList.add(vaiTro);
				vtCongVanList.add(vtcvList);
			}
			vaiTroDAO.disconnect();
		} else {
			for(CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				
				int cvId = congVan.getCvId();
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				nguoiXlCongVan.add(nguoiXl);
			}
		}
		
		long size = 0;
		for (String key : conditions.keySet()) {
			System.out.println("condition = " + key + "\tvalue= " + conditions.get(key));
		}
		if (this.cvId != 0) 
			size  = 1;
		else
			size = congVanDAO.size(msnvTemp, conditions);
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		long page = (size % 3 == 0 ? size/3 : (size/3) +1 ); 
		objectList.add(page);
		if (msnvTemp == null) {
			objectList.add(nguoiXlCongVan);
		} else {
			objectList.add(vaiTroList);
			objectList.add(vtCongVanList);
		}
		System.out.println(congVanList.size());
		System.out.println(msnv);
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/loadPageCongVan", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageCongVan(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request,
	            HttpServletResponse response) {
		try {
			truongPhongMa = context.getInitParameter("truongPhongMa");
	    	vanThuMa = context.getInitParameter("vanThuMa");
	    	adminMa = context.getInitParameter("adminMa");
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
	    	String msnv = nguoiDung.getMsnv();
	    	String cdMa = nguoiDung.getChucDanh().getCdMa();
			String msnvTemp = msnv;
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || cdMa.equals(adminMa)) {
				msnvTemp = null;
			}
			page = Integer.parseInt(pageNumber);
			CongVanDAO congVanDAO = new CongVanDAO();
			FileDAO fileDAO = new FileDAO();
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			
			if (ttMa.length() > 0)
				conditions.put("trangThai.ttMa", ttMa);
			if (column.length() > 0 && ((String) columnValue).length() > 0)  {
				if (column.equals("soDen"))
					conditions.put(column, Integer.parseInt((String) columnValue));
				else
					conditions.put(column, columnValue);
			}
			if (this.cvId != 0) {
				conditions.put("cvId", cvId);
			}	
//			ArrayList<Integer> yearList = congVanDAO.groupByYearLimit(msnvTemp, conditions, 5);

			if (year != 0)
				conditions.put("year", year);
			if (month != 0)
				conditions.put("month", month);
			if (date != 0)
				conditions.put("day", date);
			orderBy.put("cvNgayNhan", true);
			orderBy.put("soDen", true);
			
			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, (page) *3, 3);
			ArrayList<File> fileList = new ArrayList<File>();
			ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
			// array list vai tro nguoi dung
			ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>> ();
			ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>> ();
 
			VTCongVanDAO vtcvDAO = new VTCongVanDAO();
			 
			if (msnvTemp != null) {
				VaiTroDAO vaiTroDAO = new VaiTroDAO();
				for (CongVan congVan : congVanList) {
					File file = fileDAO.getByCongVanId(congVan.getCvId());
					fileList.add(file);
					int cvId = congVan.getCvId();
					ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnvTemp);
					ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
					vaiTroList.add(vaiTro);
					vtCongVanList.add(vtcvList);
				}
				vaiTroDAO.disconnect();
			} else {
				for(CongVan congVan : congVanList) {
					File file = fileDAO.getByCongVanId(congVan.getCvId());
					fileList.add(file);
					int cvId = congVan.getCvId();
					ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
					nguoiXlCongVan.add(nguoiXl);
				}
			}
			long size = 0;
			if (this.cvId != 0) 
				size  = 1;
			else
				size = congVanDAO.size(msnvTemp, conditions);
			congVanDAO.disconnect();
			fileDAO.disconnect();
			ArrayList<Object> objectList = new ArrayList<Object>(); 
			objectList.add(congVanList);
			objectList.add(fileList);
			long page = (size % 3 == 0 ? size/3 : (size/3) +1 );
			objectList.add(page);
			if (msnvTemp == null) {
				objectList.add(nguoiXlCongVan);
			} else {
				objectList.add(vaiTroList);
				objectList.add(vtCongVanList);
			}
//			session.setMaxInactiveInterval(5000);
			return JSonUtil.toJson(objectList);
		} catch (NumberFormatException | NullPointerException e) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login);
			try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}
	@RequestMapping(value="/changeTrangThaiVt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String changeTrangThai(@RequestParam("trangThai") String trangThai, HttpServletRequest request,
	            HttpServletResponse response, HttpSession session) {
		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
		String cdMa = authentication.getChucDanh().getCdMa();
    	String nhanVienMa = context.getInitParameter("nhanVienMa");
		String[] temp = trangThai.split("\\#");
		try {
			if (cdMa.equals(nhanVienMa)) {
				VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
				String msnv = temp[0];
				int cvId = Integer.parseInt(temp[1]);
				int vtId = Integer.parseInt(temp[2]);
				String ttMa = temp[3];
				VTCongVan vtCongVan = vtCongVanDAO.getVTCongVan(msnv, cvId, vtId);
				vtCongVan.setTrangThai(new TrangThai(ttMa));
				vtCongVanDAO.updateVTCongVan(vtCongVan);
				int check = vtCongVanDAO.checkTtCongVan(cvId);
				if (check == 1) {
					CongVanDAO congVanDAO = new CongVanDAO();
					CongVan congVan = congVanDAO.getCongVan(cvId);
					congVan.setTrangThai(new TrangThai("DaGQ"));
					congVanDAO.updateCongVan(congVan);
					congVanDAO.disconnect();
					return JSonUtil.toJson("changTtCongVan");
				}
				vtCongVanDAO.disconnect();
			}
			return JSonUtil.toJson("success");
		}
		catch (NumberFormatException e1) {
			return JSonUtil.toJson("fail");
		}
		catch (HibernateException e) {
			return JSonUtil.toJson("fail");
		}
	}
	@RequestMapping(value="/changeTrangThaiCv", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String changeTrangThaiCv(@RequestParam("trangThai") String trangThai, HttpServletRequest request,
	            HttpServletResponse response, HttpSession session) {
		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
		String cdMa = authentication.getChucDanh().getCdMa();
		truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
    	adminMa = context.getInitParameter("adminMa");
		String[] temp = trangThai.split("\\#");
		int cvId = Integer.parseInt(temp[0]);
		String ttMa = temp[1];
		try {
			if (cdMa.equals(truongPhongMa) || cdMa.equals(vanThuMa) || cdMa.equals(adminMa)) {
				CongVanDAO congVanDAO = new CongVanDAO();
				
				CongVan congVan = congVanDAO.getCongVan(cvId);
				
				congVan.setTrangThai(new TrangThai(ttMa));
				congVanDAO.updateCongVan(congVan);
				congVanDAO.disconnect();
				return JSonUtil.toJson("success");
			}
			return JSonUtil.toJson("fail");
		}
		catch (HibernateException e) {
			return JSonUtil.toJson("fail");
		}
	}
	@RequestMapping(value="/getDonVi", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String getDonVi(HttpServletRequest request,
	            HttpServletResponse response, HttpSession session) {
		DonViDAO donViDAO = new DonViDAO();
		ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
		donViDAO.disconnect();
		return JSonUtil.toJson(donViList);
	}
	@RequestMapping(value="/getMucDich", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String getMucDich(HttpServletRequest request,
	            HttpServletResponse response, HttpSession session) {
		MucDichDAO donViDAO = new MucDichDAO();
		ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) donViDAO.getAllMucDich();
		donViDAO.disconnect();
		return JSonUtil.toJson(mucDichList);
	}
}
