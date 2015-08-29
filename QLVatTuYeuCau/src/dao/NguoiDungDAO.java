package dao;

import java.util.ArrayList;
import java.util.List;

import model.NguoiDung;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

public class NguoiDungDAO {
	
	private SessionFactory template;  
	private Session session;
	
	public NguoiDungDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	
	public NguoiDung getNguoiDung(final String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NguoiDung.class, "nguoiDung");
		cr.createAlias("nguoiDung.chucDanh", "chucDanh");
		cr.add(Restrictions.eq("nguoiDung.msnv", msnv));
		NguoiDung nguoiDung = null;
		
		ArrayList<NguoiDung> nguoiDungList = (ArrayList<NguoiDung>) cr.list();
		if (nguoiDungList.size() > 0) 
			nguoiDung = nguoiDungList.get(0);
		session.getTransaction().commit();
		return nguoiDung;
	}
	public List<NguoiDung> getAllNguoiDung() {
		session.beginTransaction();
		List<NguoiDung> nguoiDungList = (List<NguoiDung>) session.createCriteria(NguoiDung.class).list();
		session.getTransaction().commit();
		return nguoiDungList;
	}
	public void addNguoiDung(NguoiDung nguoiDung){
		session.beginTransaction();
		session.save(nguoiDung);
		session.getTransaction().commit();
	}
	public void updateNguoiDung(NguoiDung nguoiDung){
		session.beginTransaction();
		session.update(nguoiDung);
		session.getTransaction().commit();
	}
	public void deleteNguoiDung(NguoiDung nguoiDung){
		session.beginTransaction();
		session.delete(nguoiDung);
		session.getTransaction().commit();
	}
	public ArrayList<String> startWith(String i) {
		session.beginTransaction();
//		session.createCriteria(NguoiDung.class, "hoTen");
		//Criteria likeHoten = Restrictions.ilike(propertyName, value)
		String sql = "select hoTen from NguoiDung where hoTen LIKE :hoTen";
		Query query = session.createQuery(sql);
		query.setParameter("hoTen", i+"%");
		ArrayList<String> list = (ArrayList<String>) query.list();
		session.getTransaction().commit();
		return list;
	}
	public static void main(String[] args) {
		ArrayList<String> list = new NguoiDungDAO().startWith("TRUONG TRUNG");
		if(list.size() > 0) {
			for(String hoTen : list)
				System.out.println(hoTen);
		}
	}
	public void close() {
		if(session.isOpen())
			session.close();
	}
	public void disconnect() {
		if (session.isConnected())
			session.disconnect();
	}

}
