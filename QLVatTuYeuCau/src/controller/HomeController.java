package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import dao.CTVatTuDAO;
import dao.CongVanDAO;
import dao.NhatKyDAO;
import dao.VTCongVanDAO;
import map.siteMap;
import model.CTVatTu;
import model.CongVan;
import model.NguoiDung;
import model.NhatKy;
import model.VaiTro;


@Controller("/HomeController")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 @Autowired
	 private   ServletContext context; 
       
   @RequestMapping("/home")
	public ModelAndView manageLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   	String truongPhongMa = context.getInitParameter("truongPhongMa");
	   	String vanThuMa = context.getInitParameter("vanThuMa");
	   	String nhanVienMa = context.getInitParameter("nhanVienMa");
	   	int vtCapVt = Integer.parseInt(context.getInitParameter("capPhatId"));
	   	NhatKyDAO nhatKyDAO = new NhatKyDAO();
		HttpSession session = request.getSession();
//		NhatKy nhatKy = (NhatKy) session.getAttribute("nhatKy");
//		if (nhatKy != null) {
//			nhatKyDAO.addNhatKy(nhatKy);
//			session.removeAttribute("nhatKy");
//			nhatKyDAO.refresh(nhatKy);
//		}
		
		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
		
		String cdMa = authentication.getChucDanh().getCdMa();
		if (cdMa.equals(truongPhongMa) || cdMa.equals(vanThuMa)) {
			CongVanDAO congVanDAO = new CongVanDAO();
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("trangThai.ttMa", "CGQ");
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(null, conditions, orderBy, 0, 10);
			request.setAttribute("congVanList", congVanList);
			
			ArrayList<NhatKy> nhatKyList = nhatKyDAO.getLimitByMsnv(authentication.getMsnv(), 0, 10);
			nhatKyDAO.disconnect();
			congVanDAO.disconnect();
			request.setAttribute("nhatKyList", nhatKyList);
			if (cdMa.equals(truongPhongMa)) {
				CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
				ArrayList<CTVatTu> ctVatTuListAlert = ctVatTuDAO.getCtVatTuListAlert();
				request.setAttribute("ctVatTuListAlert", ctVatTuListAlert);
				ctVatTuDAO.disconnect();
			}
			return new ModelAndView("home");
		}
//		else if (cdMa.equals(vanThuMa)) {
//			NhatKyDAO nhatKyDAO = new NhatKyDAO();
//			ArrayList<NhatKy> nhatKyList = nhatKyDAO.getLimitByMsnv(authentication.getMsnv(), 0, 10);
//			nhatKyDAO.disconnect();
//			request.setAttribute("nhatKyList", nhatKyList);
//			return new ModelAndView("home");
//		}
		else if (cdMa.equals(nhanVienMa)) {
			VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
			CongVanDAO congVanDAO = new CongVanDAO();
			String msnv = authentication.getMsnv();
			ArrayList<Integer> cvIdList = vtCongVanDAO.groupByMsnvLimit(msnv, 0, 15);
			ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>>();
			ArrayList<CongVan> congVanList = new ArrayList<CongVan>();
			ArrayList<ArrayList<String>> trangThaiList = new ArrayList<ArrayList<String>>(); 
			if (cvIdList.size() > 0) { 
				vaiTroList = vtCongVanDAO.getVaiTroList(msnv, cvIdList);
				trangThaiList = vtCongVanDAO.getTrangThai(msnv, cvIdList, vaiTroList);
				for (Integer cvId : cvIdList) {
					CongVan congVan = congVanDAO.getCongVan(cvId);
					congVanList.add(congVan);
				}
			}
			
			ArrayList<NhatKy> nhatKyList = nhatKyDAO.getByMsnv(msnv);
			vtCongVanDAO.disconnect();
			congVanDAO.disconnect();
			nhatKyDAO.disconnect();
//			for (Integer cvId : congVanIdList) {
//				ArrayList<VaiTro> vaiTroList = vtCongVanDAO.getVaiTro(cvId, msnv);
//				vtCongVanList.add(vaiTroList);
//			}
			
//			HashMap<String, Object> conditions = new HashMap<String, Object>();
//			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
//			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(null, conditions, orderBy, 0, 5);
			request.setAttribute("congVanList", congVanList);
			request.setAttribute("trangThaiList", trangThaiList);
			request.setAttribute("vaiTroList", vaiTroList);
			request.setAttribute("nhatKyList", nhatKyList);
//			request.setAttribute("vaiTroList", vaiTroList);
//			JOptionPane.showMessageDialog(null, "size of cvIdList = " + cvIdList.size());
//			JOptionPane.showMessageDialog(null, "size of vaiTroList = " + vaiTroList.size());
//			JOptionPane.showMessageDialog(null, "size of congvanList = " + congVanList.size());
//			JOptionPane.showMessageDialog(null, "size of nhatKyList = " + nhatKyList.size());
			return new ModelAndView("home");
		}
		return new ModelAndView(siteMap.login);
	}

}
