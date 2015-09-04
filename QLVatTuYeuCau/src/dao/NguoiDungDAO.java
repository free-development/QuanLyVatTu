package dao;

import java.util.ArrayList;
import java.util.List;

import model.CTVatTu;
import model.CongVan;
import model.NguoiDung;
import model.YeuCau;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

public class NguoiDungDAO {
	
	private SessionFactory template;  
	private Session session;
	
	private String truongPhongMa = "TP";
	private String adminMa = "AD";
	public NguoiDungDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	
	public NguoiDung getNguoiDung(final String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NguoiDung.class, "nguoiDung");
		
		cr.add(Restrictions.eq("nguoiDung.msnv", msnv));
		NguoiDung nguoiDung = null;
		
		ArrayList<NguoiDung> nguoiDungList = (ArrayList<NguoiDung>) cr.list();
		if (nguoiDungList.size() > 0) 
			nguoiDung = nguoiDungList.get(0);
		session.getTransaction().commit();
		return nguoiDung;
	}
	public List<NguoiDung> getAllNguoiDung(ArrayList<String> ignoreList) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NguoiDung.class, "nguoiDung");
		cr.createAlias("nguoiDung.chucDanh", "chucDanh");
		cr.add(Restrictions.not(Restrictions.in("chucDanh.cdMa", ignoreList)));
		List<NguoiDung> nguoiDungList = (List<NguoiDung>) cr.list();
		session.getTransaction().commit();
		return nguoiDungList;
	}
	public List<NguoiDung> getTruongPhong(String truongPhongMa) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NguoiDung.class, "nguoiDung");
		cr.createAlias("nguoiDung.chucDanh", "chucDanh");
		cr.add(Restrictions.eq("chucDanh.cdMa", truongPhongMa));
		List<NguoiDung> nguoiDungList = (List<NguoiDung>) cr.list();
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
	
	
	
	public ArrayList<NguoiDung> limit(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NguoiDung.class, "nd");
		cr.createAlias("nd.msnv", "msnv");
		cr.createAlias("nd.hoTen", "hoTen");
		cr.createAlias("nd.vaiTro", "vaiTro");
		cr.createAlias("vaiTro.vtMa", "vtMa");
		cr.addOrder(Order.asc("nd.msnv"));
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<NguoiDung> list = (ArrayList<NguoiDung>) cr.list();
		session.getTransaction().commit();
		return list;
	}
	
	public long size() {
		session.beginTransaction();
		String sql = "select count(msnv) from NguoiDung";
		Query query =  session.createQuery(sql);
		long size = (long) query.list().get(0);
		session.getTransaction().commit();
		return size;
		
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
	public void close() {
		if(session.isOpen())
			session.close();
	}
	public void disconnect() {
		if (session.isConnected())
			session.disconnect();
	}
	public static void main(String[] args) {
		System.out.println(new NguoiDungDAO().getTruongPhong("TP"));
	}
}
