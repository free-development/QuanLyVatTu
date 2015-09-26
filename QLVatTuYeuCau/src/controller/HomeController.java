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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import dao.CongVanDAO;
import map.siteMap;
import model.CongVan;
import model.NguoiDung;


@Controller("/HomeController")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 @Autowired
	 private   ServletContext context; 
       
   @RequestMapping("/home")
	public ModelAndView manageLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   	String truongPhongMa = context.getInitParameter("truongPhongMa");
	   	String vanThuMa = context.getInitParameter("vanThuMa");
	   	int vtCapVt = Integer.parseInt(context.getInitParameter("capPhatId"));
	   
		HttpSession session = request.getSession();
		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
		
		String cdMa = authentication.getChucDanh().getCdMa();
		if (cdMa.equals(truongPhongMa)) {
			CongVanDAO congVanDAO = new CongVanDAO();
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(null, conditions, orderBy, 0, 5);
			System.out.println("size of cong van = " +  congVanList.size());
//			return new ModelAndView("index" , "a", congVanList);
			request.setAttribute("congVanList", congVanList);
			return new ModelAndView("home");
		}
		return new ModelAndView(siteMap.login);
	}

}
