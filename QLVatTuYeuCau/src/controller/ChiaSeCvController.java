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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.CongVanDAO;
import dao.NguoiDungDAO;
import dao.VTCongVanDAO;
import dao.VaiTroDAO;
import map.siteMap;
import model.CongVan;
//import model.Mailer;
import model.NguoiDung;
import model.VTCongVan;
import model.VaiTro;
import util.JSonUtil;
import util.Mail;
import util.SendMail;

@Controller
// @WebServlet("/ChiaSeCvController")
public class ChiaSeCvController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ServletContext context;
	// private int pageCscv = 1;
	// HttpSession session = null;
	HttpSession session;
	HttpServletResponse res = null;

	@RequestMapping("/cscvManage")
	protected ModelAndView cscvManage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("chiaSeCv".equalsIgnoreCase(action)) {
			session = request.getSession(false);
			res = response;
			String id = request.getParameter("congVan");
			int cvId = Integer.parseInt(id);
			CongVanDAO congVanDAO = new CongVanDAO();
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();

			CongVan congVan = congVanDAO.getCongVan(cvId);
			ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) vaiTroDAO.getAllVaiTro();
			ArrayList<String> ignoreList = new ArrayList<String>();
			ignoreList.add("TP");
			ignoreList.add("AD");
			ArrayList<NguoiDung> nguoiDungList = (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(ignoreList);
			VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();

			HashMap<String, NguoiDung> vtNguoiDungHash = vtCongVanDAO.getNguoiXuLy(cvId);
			HashMap<String, HashMap<Integer, VaiTro>> vaiTroHash = new HashMap<String, HashMap<Integer, VaiTro>>();
			for (String msnv : vtNguoiDungHash.keySet()) {
				ArrayList<VTCongVan> vtcvList = vtCongVanDAO.getVTCongVan(cvId, msnv);
				HashMap<Integer, VaiTro> vtHash = vtCongVanDAO.toVaiTro(vtcvList);
				vaiTroHash.put(msnv, vtHash);
			}
			request.setAttribute("vaiTroHash", vaiTroHash);
			request.setAttribute("vtNguoiDungHash", vtNguoiDungHash);

			session.setAttribute("vaiTroList", vaiTroList);
			session.setAttribute("nguoiDungList", nguoiDungList);
			session.setAttribute("congVan", congVan);

			congVanDAO.disconnect();
			vaiTroDAO.disconnect();
			nguoiDungDAO.disconnect();
			return new ModelAndView(siteMap.chiaSeCv);
		}
		return new ModelAndView("login");
	}

	@RequestMapping("/chiaSeCv")
	protected ModelAndView chiaSeCv(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		if ("save".equalsIgnoreCase(action)) {
			// session = request.getSession(false);
			session = request.getSession(false);
			CongVan congVan = (CongVan) session.getAttribute("congVan");
			String[] vaiTro = request.getParameterValues("vaiTro");
			System.out.println(vaiTro.length);
			VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			int cvId = congVan.getCvId();
			vtCongVanDAO.deleteByCvId(cvId);
			for (String vtMa : vaiTro) {
				String[] str = vtMa.split("\\#");
				NguoiDung nguoiDung = nguoiDungDAO.getNguoiDung(str[0]);
				//
				// final String username = "evnCanTho@gmail.com";
				// final String password = "evnCanTho2015";

				//
				// InternetAddress.parse("camtien.le1994@gmail.com"));
				// message.setSubject("Công việc được chia sẻ");
				// message.setText("Bạn đã được chia sẻ công việc. Vui lòng vào
				// hệ thống làm việc để kiểm tra.");
				//
				// Transport.send(message);

				VTCongVan vtCongVan = new VTCongVan();
				vtCongVan.setCvId(cvId);
				vtCongVan.setMsnv(str[0]);
				vtCongVan.setVtId(Integer.parseInt(str[1]));
				vtCongVanDAO.addOrUpdateVTCongVan(vtCongVan);
			}
			HashMap<String, NguoiDung> vtNguoiDungHash = vtCongVanDAO.getNguoiXuLy(cvId);
			HashMap<String, HashMap<Integer, VaiTro>> vaiTroHash = new HashMap<String, HashMap<Integer, VaiTro>>();
			for (String msnv : vtNguoiDungHash.keySet()) {
				ArrayList<VTCongVan> vtcvList = vtCongVanDAO.getVTCongVan(cvId, msnv);
				HashMap<Integer, VaiTro> vtHash = vtCongVanDAO.toVaiTro(vtcvList);
				vaiTroHash.put(msnv, vtHash);
				String str1 = "";
				VaiTro vt = new VaiTro();
				System.out.println(str1);
				String account = context.getInitParameter("account");
				String password = context.getInitParameter("password");
				String host = context.getInitParameter("hosting");

				SendMail sendMail = new SendMail(account, password);
				vtHash = vaiTroHash.get(msnv);
				NguoiDung nguoiDung = vtNguoiDungHash.get(msnv);
				for(Integer vtId : vtHash.keySet()) {
					vt = vtHash.get(vtId);
					str1 += "\t\t , " + vt.getVtTen() + "\n ";
				}
				Mail mail = new Mail();
				mail.setFrom(account);
				mail.setTo(nguoiDung.getEmail());
				mail.setSubject("Công việc được chia sẻ");
				String content = "Bạn đã được chia sẻ công văn. Vui lòng vào hệ thống làm việc để kiểm tra.\n";
				content += "\t *Công việc được chia sẻ là: \n" + str1 + ".\n" + "Thân mến!";
				//content += host + siteMap.cscvManage + "?action=chiaSeCv&congVan=" + cvId;
				mail.setContent(content);
				sendMail.send(mail);
			}
			request.setAttribute("vaiTroHash", vaiTroHash);
			request.setAttribute("vtNguoiDungHash", vtNguoiDungHash);
			vtCongVanDAO.disconnect();
			nguoiDungDAO.disconnect();
			vaiTroDAO.disconnect();
			return new ModelAndView(siteMap.chiaSeCv);
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/preUpdateYeuCau", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String preUpdateYeuCau(@RequestParam("msnv") String msnv) {
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
		VaiTroDAO vaiTroDAO = new VaiTroDAO();

		CongVan congVan = (CongVan) session.getAttribute("congVan");
		session.setAttribute("msnvUpdate", msnv);
		// NguoiDung nguoiDung = nguoiDungDAO.getNguoiDung(msnv);
		ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) vaiTroDAO.getAllVaiTro();
		ArrayList<VTCongVan> vtCongVanList = vtCongVanDAO.getVTCongVan(congVan.getCvId(), msnv);
		ArrayList<Object> objectList = new ArrayList<Object>();
		objectList.add(msnv);
		objectList.add(vaiTroList);
		objectList.add(vtCongVanList);
		nguoiDungDAO.disconnect();
		vtCongVanDAO.disconnect();
		vaiTroDAO.disconnect();
		return JSonUtil.toJson(objectList);
	}

	@RequestMapping(value = "/updateYeuCau", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateYeuCau(@RequestParam("vaiTroList") String vaiTroList) throws IOException {
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
		VaiTroDAO vaiTroDAO = new VaiTroDAO();

		CongVan congVan = (CongVan) session.getAttribute("congVan");
		String msnvUpdate = (String) session.getAttribute("msnvUpdate");
		int cvId = congVan.getCvId();
		vtCongVanDAO.delete(cvId, msnvUpdate);
		System.out.println(msnvUpdate);
		if (msnvUpdate == null || congVan == null)
			res.sendRedirect(siteMap.cvManage + "?action=manageCv");
		// return JSonUtil.toJson("delete");
		String[] vtList = vaiTroList.split("\\, ");
		// StringBuilder str = new StringBuilder("");
		ArrayList<Object> objectList = new ArrayList<Object>();
		ArrayList<VaiTro> list = new ArrayList<VaiTro>();
		if (vaiTroList.length() != 0) {
			for (String s : vtList) {
				int vtId = Integer.parseInt(s);
				vtCongVanDAO.addVTCongVan(new VTCongVan(cvId, vtId, msnvUpdate));
				// String vt = vtCongVanDAO.getVaiTro(vtId);
				// str.append(vt + "<br>");
				VaiTro vt = vaiTroDAO.getVaiTro(vtId);
				// JOptionPane.showMessageDialog(null, vt.getVtTen());
				list.add(vt);
			}
			// str.delete(str.length()-4, 4);
		}
		objectList.add(list);
		objectList.add(msnvUpdate);
		// vtCongVanDAO.close();
		nguoiDungDAO.close();
		vaiTroDAO.close();
		nguoiDungDAO.disconnect();
		vtCongVanDAO.disconnect();
		vaiTroDAO.disconnect();
		return JSonUtil.toJson(objectList);
	}

	// @RequestMapping(value="/sendMail", method=RequestMethod.GET,
	// produces = MediaType.APPLICATION_JSON_VALUE, consumes =
	// MediaType.APPLICATION_JSON_VALUE)
	// public @ResponseBody String sendMail(@RequestParam("email") String
	// email,@RequestParam("chude") String chude,@RequestParam("noidung") String
	// noidung) {
	// Mailer mailer = new Mailer();
	// mailer.send(email, chude, noidung);
	// ArrayList<Object> objectList = new ArrayList<Object>();
	// objectList.add(mailer);
	// return JSonUtil.toJson(objectList);
	// }

	@RequestMapping(value = "/loadPageCscv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadPageCscv(@RequestParam("pageNumber") String pageNumber) {
		NguoiDungDAO ndDAO = new NguoiDungDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<Object> objectList = new ArrayList<Object>();
		long sizeNd = ndDAO.size();
		ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) ndDAO.limit((page - 1) * 10, 10);
		objectList.add(ndList);
		objectList.add((sizeNd - 1) / 10);
		// return JSonUtil.toJson(objectList);
		ndDAO.disconnect();
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/timKiemNguoidungCs", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String timKiemNguoidungCs(@RequestParam("msnv") String msnv, @RequestParam("hoTen") String hoTen) {
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		VaiTroDAO vaiTroDAO = new VaiTroDAO();
		ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>)vaiTroDAO.getAllVaiTro();
		
		ArrayList<Object> objectList = new ArrayList<Object>();
		//ArrayList<VaiTro> list = new ArrayList<VaiTro>();
		if(msnv != ""){
			ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) nguoiDungDAO.searchMsnv(msnv);
			
			objectList.add(vaiTroList);
			objectList.add(ndList);
			nguoiDungDAO.disconnect();
			return JSonUtil.toJson(objectList);
		}
		else
		{
			ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) nguoiDungDAO.searchHoten(hoTen);
			//System.out.println("Ten: "+vtTen);
//			nguoiDungDAO.disconnect();
			objectList.add(vaiTroList);
			objectList.add(ndList);
			nguoiDungDAO.disconnect();
			return JSonUtil.toJson(objectList);
		}
	}
}
