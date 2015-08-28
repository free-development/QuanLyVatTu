package dao;

import java.util.List;

import model.CTNguoiDung;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import util.HibernateUtil;

public class CTNguoiDungDAO {
	
	private SessionFactory template;  
	private Session session;
	
	public CTNguoiDungDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	
	public CTNguoiDung getCTNguoiDung(final String msnv) {
		session.beginTransaction();
		CTNguoiDung ctNguoiDung = (CTNguoiDung) session.get(CTNguoiDung.class, msnv);
		session.getTransaction().commit();
		return ctNguoiDung;
	}
	public boolean login(final String msnv, final String matkhau) {
		CTNguoiDung ctNguoiDung = getCTNguoiDung(msnv);
		if (ctNguoiDung == null || !matkhau.equals(ctNguoiDung.getMatKhau()))
			return false;
		return true;
	}
	public List<CTNguoiDung> getAllCTNguoiDung() {
		session.beginTransaction();
		List<CTNguoiDung> ctNguoiDungList = (List<CTNguoiDung>) session.createCriteria(CTNguoiDung.class).list();
		session.getTransaction().commit();
		return ctNguoiDungList;
	}
	public void addCTNguoiDung(CTNguoiDung ctNguoiDung){
		session.beginTransaction();
		session.save(ctNguoiDung);
		session.getTransaction().commit();
	}
	public void updateCTNguoiDung(CTNguoiDung ctNguoiDung){
		session.beginTransaction();
		session.update(ctNguoiDung);
		session.getTransaction().commit();
	}
	public void deleteCTNguoiDung(CTNguoiDung ctNguoiDung){
		session.beginTransaction();
		session.delete(ctNguoiDung);
		session.getTransaction().commit();
	}
	public void close() {
		session.close();
	}
	
}
