package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import dao.TrangThaiDAO;
import dao.VTCongVanDAO;
import dao.VaiTroDAO;
import map.siteMap;
import model.CongVan;
import model.DonVi;
import model.File;
import model.MucDich;
import model.NguoiDung;
import model.TrangThai;
import model.VaiTro;
import util.DateUtil;
import util.FileUtil;
import util.JSonUtil;
import util.Mail;
import util.SendMail;


@Controller
public class CvController extends HttpServlet {
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
    private String columnValue = "";
    
    private String truongPhongMa = "";
    private String vanThuMa = "";
    private int vtCapVt = 0;
    
    public ModelAndView getCongvan( HttpServletRequest request) {
    	truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
    	vtCapVt = Integer.parseInt(context.getInitParameter("capPhatId"));
    	session = request.getSession(false);
    	NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    	String msnv = nguoiDung.getMsnv();
    	
    	VTCongVanDAO vtcvDAO =  new VTCongVanDAO();
    	MucDichDAO mucDichDAO =  new MucDichDAO();
    	DonViDAO donViDAO =  new DonViDAO();
    	TrangThaiDAO trangThaiDAO =  new TrangThaiDAO();
    	CongVanDAO congVanDAO =  new CongVanDAO();
    	FileDAO fileDAO =  new FileDAO();
    	String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa)) {
			msnvTemp = null;
			// danh sach vai tro nguoi dung doi voi cong van
		}
    	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(msnvTemp, null, null, 0, 3);
    	
    	
    	//chung
		HashMap<Integer, File> fileHash = new HashMap<Integer, File>();
		ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
		ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) mucDichDAO.getAllMucDich();
		ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) trangThaiDAO.getAllTrangThai();
		long size = congVanDAO.size(msnvTemp, null);
		// danh sach nguoi xu ly cong van
    	ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vtCongVanList = new ArrayList<ArrayList<VaiTro>> (); 
		if (msnvTemp != null) {
			for(CongVan congVan : congVanList) {
				int cvId = congVan.getCvId();
				fileHash.put(cvId, fileDAO.getByCongVanId(cvId));
				
				ArrayList<VaiTro> vtCongVan = vtcvDAO.getVaiTro(cvId, msnv);
				vtCongVanList.add(vtCongVan);
				
			}
			request.setAttribute("vtCongVanList", vtCongVanList);
		}
		else if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa)) {
			for(CongVan congVan : congVanList) {
				int cvId = congVan.getCvId();
				fileHash.put(cvId, fileDAO.getByCongVanId(cvId));
				
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				
				nguoiXlCongVan.add(nguoiXl);
			}
			request.setAttribute("nguoiXlCongVan", nguoiXlCongVan);
		}
		ArrayList<Integer> yearList = congVanDAO.groupByYearLimit(msnvTemp,5);
		request.setAttribute("congVanList", congVanList);
		request.setAttribute("fileHash", fileHash);
		request.setAttribute("mucDichList", mucDichList);
		request.setAttribute("donViList", donViList);
		request.setAttribute("trangThaiList", trangThaiList);
		request.setAttribute("yearList", yearList);
		request.setAttribute("size", size);
		congVanDAO.disconnect();
		donViDAO.disconnect();
		trangThaiDAO.disconnect();
		fileDAO.disconnect();
		mucDichDAO.disconnect();
		vtcvDAO.disconnect();
		return new ModelAndView(siteMap.congVan);
    }
    
    @RequestMapping("/cvManage")
	public ModelAndView manageCV(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
    	
    	request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
		if (nguoiDung == null)
			response.sendRedirect("login.jsp");
		
		String action = request.getParameter("action");
		
		if("manageCv".equalsIgnoreCase(action)) {
			return getCongvan(request);
//			FileDAO fileDAO = new FileDAO();
//	    	CongVanDAO congVanDAO = new CongVanDAO();
//	    	String cdMa = nguoiDung.getChucDanh().getCdMa();
//	    	String msnv = nguoiDung.getMsnv();
//			String msnvTemp = msnv;
//			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa))
//				msnvTemp = null;
//			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(msnvTemp ,null, null, 0, 3);
//			HashMap<Integer, File> fileHash = new HashMap<Integer, File>();
//			for(CongVan congVan : congVanList) {
//				int cvId = congVan.getCvId();
//				File file = fileDAO.getByCongVanId(cvId);
//				fileHash.put(cvId, file);
//			}
//			congVanDAO.disconnect();
//			fileDAO.disconnect();
			
//			return getCongvan(request);
		}
	
		if("download".equals(action)) {
			try {
				FileDAO fileDAO = new FileDAO();
				int cvId = Integer.parseInt(request.getParameter("file"));
				model.File f = fileDAO.getByCongVanId(cvId);
				java.io.File file = new java.io.File(f.getDiaChi());
				fileDAO.close();
				return new ModelAndView(siteMap.fileDownload, "file", file);
				
			} catch (NumberFormatException e){
				System.out.println("Cannot convert to int");
			}
		}
		return new ModelAndView("login");
	}
   
    @RequestMapping("addCongVan")
    public ModelAndView addCV(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	request.ge
    	root =  request.getRealPath("/");
    	request.getCharacterEncoding();
		response.getCharacterEncoding();
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
				 fileName = fileName + "-" + soDen + "." + fileExtension;
			 } else {
				 fileName = fileName + "-" + soDen;
			 }
				
			file.renameTo(new java.io.File(root + pathFile + fileName));
			
		}
		System.out.println(root + pathFile + fileName);
		
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
			File file = fileDAO.getByCongVanId(congVanCheck.getCvId());
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
				content += host + siteMap.cscvManage + "?searchCongVan=chiaSeCv&congVan=" + cvId;
				mail.setContent(content);
				sendMail.send(mail);
			}
    	}
		congVanDAO.close();
		fileDAO.close();
		return getCongvan(request);
    }
    
    @RequestMapping(value="/preEditCongVan", method=RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String changePass(@RequestParam("cvId") int cvId) {
    	CongVanDAO congVanDAO = new CongVanDAO();
		CongVan congVan = congVanDAO.getCongVan(cvId);
		return JSonUtil.toJson(congVan);
	}
    @RequestMapping("updateCongVan")
    public ModelAndView updateCongVan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	request.ge
    	root =  request.getRealPath("/");
    	request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
    	CongVanDAO congVanDAO = new CongVanDAO();
    	FileDAO fileDAO = new FileDAO();
    	// 
    	MultipartRequest multipartRequest = new MultipartRequest(request, root + tempPath, maxSize);
		String action = multipartRequest.getParameter("action");
		String soDen = multipartRequest.getParameter("soDen");
		String cvSo = multipartRequest.getParameter("cvSo");
		
		Date cvNgayGoi = DateUtil.parseDate(multipartRequest.getParameter("ngayGoiUpdate"));
		Date cvNgayNhan = DateUtil.parseDate(multipartRequest.getParameter("ngayNhanUpdate"));
		String mdMa = multipartRequest.getParameter("mucDichUpdate");
		String dvMa = multipartRequest.getParameter("donViUpdate");
		String trichYeu = multipartRequest.getParameter("trichYeuUpdate");
		String butPhe = multipartRequest.getParameter("butPheUpdate");
		String ttMa = multipartRequest.getParameter("ttMaUpdate");
		String fileFullName = "";
		Enumeration<String> listFileName = multipartRequest.getFileNames();		
		String fileName = "";
		while(listFileName.hasMoreElements()) {
			fileFullName = multipartRequest.getFilesystemName(listFileName.nextElement().toString());
			java.io.File file = new java.io.File(root + tempPath + fileFullName);
			fileName = FileUtil.getNameFile(file);
			String fileExtension = FileUtil.getExtension(file);
			if(fileExtension.length() > 0) {
				 fileName = fileName + "-" + soDen + "." + fileExtension;
			 } else {
				 fileName = fileName + "-" + soDen;
			 }
				
			file.renameTo(new java.io.File(root + pathFile + fileName));
			
		}
		CongVan congVan = congVanDAO.getByCvSo(cvSo);
		int cvId = congVan.getCvId();
		congVan.setButPhe(butPhe);
		congVan.setCvNgayDi(cvNgayGoi);
		congVan.setCvNgayNhan(cvNgayNhan);
		congVan.setDonVi(new DonVi(dvMa));
		congVan.setMucDich(new MucDich(mdMa));
		congVan.setTrangThai(new TrangThai(ttMa));
		congVan.setTrichYeu(trichYeu);
		congVanDAO.updateCongVan(congVan);
//    	File
		if (fileName.length() !=0) {
			File file = fileDAO.getByCongVanId(cvId);
			file.setDiaChi(root + pathFile + fileName);
			fileDAO.updateFile(file);
			fileDAO.close();
		}
		congVanDAO.close();
		
		return getCongvan(request);
    }
	@RequestMapping(value="/deleteCv", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteNsx(@RequestParam("cvId") String cvId) {
		String[] congVanList = cvId.split("\\, ");
		for (String s : congVanList) {
			int id = Integer.parseInt(s);
			new CongVanDAO().deleteCongVan(id);
		}
		
		return JSonUtil.toJson(cvId);
	}
	
	@RequestMapping(value="/preUpdateCv", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preUpdateCv(@RequestParam("congVan") String congVan) {
		CongVanDAO congVanDAO = new CongVanDAO();
		int id = Integer.parseInt(congVan);
		CongVan cv = congVanDAO.getCongVan(id);
		congVanDAO.close();
		return JSonUtil.toJson(cv);
	}
	@RequestMapping(value="/loadByYear", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadByYear(@RequestParam("year") String yearRequest) {
		truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    	String msnv = nguoiDung.getMsnv();
		
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		year = Integer.parseInt(yearRequest);
		
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		month = 0;
		date = 0;
		if (year != 0)
			conditions.put("year", year);
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && columnValue.length() > 0) {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt(columnValue));
			else
				conditions.put(column, columnValue);
		}
		orderBy.put("cvId", true);
		String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa))
			msnvTemp = null;
		ArrayList<Integer> monthList = congVanDAO.groupByMonth(msnvTemp, year);
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vtCongVanList = new ArrayList<ArrayList<VaiTro>> (); 
		VTCongVanDAO vtcvDAO = new VTCongVanDAO();
		if (msnvTemp != null) {
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				int cvId = congVan.getCvId();
				ArrayList<VaiTro> vtCongVan = vtcvDAO.getVaiTro(cvId, msnv);
				vtCongVanList.add(vtCongVan);
			}
		} else {
			for(CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				
				int cvId = congVan.getCvId();
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				nguoiXlCongVan.add(nguoiXl);
			}
		}
		long size = congVanDAO.size(msnvTemp, conditions);
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		objectList.add(monthList);
		long page = (size % 3 == 0 ? size/3 : (size/3) +1 );
		System.out.println("size = " + size + "******* page = " + page);
		objectList.add(page);
		if (msnvTemp == null) {
			objectList.add(nguoiXlCongVan);
		} else {
			objectList.add(vtCongVanList);
		}
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/loadByMonth", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadByMonth(@RequestParam("year") String yearRequest, @RequestParam("month") String monthRequest) {
		truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
		
		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    	String msnv = nguoiDung.getMsnv();
		year = Integer.parseInt(yearRequest);
		month = Integer.parseInt(monthRequest);
		date = 0;
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		if (year != 0)
			conditions.put("year", year);
		if (month != 0)
			conditions.put("month", month);
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && columnValue.length() > 0) {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt(columnValue));
			else
				conditions.put(column, columnValue);
		}
		orderBy.put("cvNgayNhan", true);
		String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa))
			msnvTemp = null;
		ArrayList<Integer> dateList = congVanDAO.groupByDate(msnvTemp, year, month);
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vtCongVanList = new ArrayList<ArrayList<VaiTro>> (); 
		VTCongVanDAO vtcvDAO = new VTCongVanDAO();
		if (msnvTemp != null) {
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				int cvId = congVan.getCvId();
				ArrayList<VaiTro> vtCongVan = vtcvDAO.getVaiTro(cvId, msnv);
				vtCongVanList.add(vtCongVan);
			}
		} else {
			for(CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				
				int cvId = congVan.getCvId();
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				nguoiXlCongVan.add(nguoiXl);
			}
		}
		long size = congVanDAO.size(msnvTemp, conditions);
		
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		objectList.add(dateList);
		long page = (size % 3 == 0 ? size/3 : (size/3) +1 );
		System.out.println(page);
		objectList.add(page);
		if (msnvTemp == null) {
			objectList.add(nguoiXlCongVan);
		} else {
			objectList.add(vtCongVanList);
		}
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/loadByDate", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadByDate(@RequestParam("year") String yearRequest, @RequestParam("month") String monthRequest, @RequestParam("date") String dateRequest) {
		truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
		
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
		if (column.length() > 0 && columnValue.length() > 0) {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt(columnValue));
			else
				conditions.put(column, columnValue);
		}
		orderBy.put("cvNgayNhan", true);
		String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa))
			msnvTemp = null;
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vtCongVanList = new ArrayList<ArrayList<VaiTro>> (); 
		VTCongVanDAO vtcvDAO = new VTCongVanDAO();
		if (msnvTemp != null) {
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				int cvId = congVan.getCvId();
				ArrayList<VaiTro> vtCongVan = vtcvDAO.getVaiTro(cvId, msnv);
				vtCongVanList.add(vtCongVan);
			}
		} else {
			for(CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				
				int cvId = congVan.getCvId();
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				nguoiXlCongVan.add(nguoiXl);
			}
		}
		long size = congVanDAO.size(msnvTemp, conditions);
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
			objectList.add(vtCongVanList);
		}
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/searchByTrangThai", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String searchByTrangThai(@RequestParam("trangThai") String trangThai) {
		truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
		
		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    	String msnv = nguoiDung.getMsnv();
		
		ttMa = trangThai;
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
		if (column.length() > 0 && columnValue.length() > 0)  {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt(columnValue));
			else
				conditions.put(column, columnValue);
		}
			
		orderBy.put("cvNgayNhan", true);
		orderBy.put("soDen", true);
		String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa))
			msnvTemp = null;
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vtCongVanList = new ArrayList<ArrayList<VaiTro>> (); 
		VTCongVanDAO vtcvDAO = new VTCongVanDAO();
		if (msnvTemp != null) {
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				int cvId = congVan.getCvId();
				ArrayList<VaiTro> vtCongVan = vtcvDAO.getVaiTro(cvId, msnv);
				vtCongVanList.add(vtCongVan);
			}
		} else {
			for(CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				
				int cvId = congVan.getCvId();
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				nguoiXlCongVan.add(nguoiXl);
			}
		}
		long size = congVanDAO.size(msnvTemp, conditions);
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
			objectList.add(vtCongVanList);
		}
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/filter", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String filter(@RequestParam("filter") String filter, @RequestParam("filterValue") String filterValue) {
		truongPhongMa = context.getInitParameter("truongPhongMa");
    	vanThuMa = context.getInitParameter("vanThuMa");
		
		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    	String msnv = nguoiDung.getMsnv();
		
		column = filter;
		columnValue = filterValue;
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
		if (column.length() > 0 && columnValue.length() > 0)  {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt(columnValue));
			else
				conditions.put(column, columnValue);
		}
			
		orderBy.put("cvNgayNhan", true);
		
		String cdMa = nguoiDung.getChucDanh().getCdMa();
		String msnvTemp = msnv;
		if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa))
			msnvTemp = null;
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnv, conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
		// array list vai tro nguoi dung
		ArrayList<ArrayList<VaiTro>> vtCongVanList = new ArrayList<ArrayList<VaiTro>> (); 
		VTCongVanDAO vtcvDAO = new VTCongVanDAO();
		if (msnvTemp != null) {
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				int cvId = congVan.getCvId();
				ArrayList<VaiTro> vtCongVan = vtcvDAO.getVaiTro(cvId, msnv);
				vtCongVanList.add(vtCongVan);
			}
		} else {
			for(CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
				
				int cvId = congVan.getCvId();
				ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
				nguoiXlCongVan.add(nguoiXl);
			}
		}
		long size = congVanDAO.size(msnvTemp, conditions);
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
			objectList.add(vtCongVanList);
		}
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/loadPageCongVan", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageCongVan(@RequestParam("pageNumber") String pageNumber) {
		try { 
			truongPhongMa = context.getInitParameter("truongPhongMa");
	    	vanThuMa = context.getInitParameter("vanThuMa");
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
	    	String msnv = nguoiDung.getMsnv();
			
			page = Integer.parseInt(pageNumber);
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
			if (column.length() > 0 && columnValue.length() > 0)  {
				if (column.equals("soDen"))
					conditions.put(column, Integer.parseInt(columnValue));
				else
					conditions.put(column, columnValue);
			}
				
			orderBy.put("cvNgayNhan", true);
			orderBy.put("soDen", true);
			String cdMa = nguoiDung.getChucDanh().getCdMa();
			String msnvTemp = msnv;
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa)) {
				msnvTemp = null;
			}
			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, (page) *3, 3);
			ArrayList<File> fileList = new ArrayList<File>();
			ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
			// array list vai tro nguoi dung
			ArrayList<ArrayList<VaiTro>> vtCongVanList = new ArrayList<ArrayList<VaiTro>> (); 
			VTCongVanDAO vtcvDAO = new VTCongVanDAO();
			if (msnvTemp != null) {
				for (CongVan congVan : congVanList) {
					File file = fileDAO.getByCongVanId(congVan.getCvId());
					fileList.add(file);
					int cvId = congVan.getCvId();
					ArrayList<VaiTro> vtCongVan = vtcvDAO.getVaiTro(cvId, msnv);
					vtCongVanList.add(vtCongVan);
				}
			} else {
				for(CongVan congVan : congVanList) {
					File file = fileDAO.getByCongVanId(congVan.getCvId());
					fileList.add(file);
					
					int cvId = congVan.getCvId();
					ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
					nguoiXlCongVan.add(nguoiXl);
				}
			}
			long size = congVanDAO.size(msnvTemp, conditions);
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
				objectList.add(vtCongVanList);
			}
			return JSonUtil.toJson(objectList);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
