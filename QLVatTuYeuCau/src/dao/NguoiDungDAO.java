package dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import util.StringUtil;
import model.CTVatTu;
import model.CongVan;
import model.NguoiDung;
import model.NoiSanXuat;
import model.VatTu;
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
	private   ServletContext context;
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
	
	public void lockNguoiDung(String msnv){
		session.beginTransaction();
		String sql = "update CTNguoiDung set khoa = 1 where msnv = '" + msnv +"'";		
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	public void resetNguoiDung(String msnv){
		session.beginTransaction();
		String sql = "update CTNguoiDung set khoa = 0 where msnv = '" + msnv +"'";		
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	public ArrayList<String> startWithMa(String i) {
		session.beginTransaction();
		String sql = "select msnv from NguoiDung where msnv LIKE :msnv";
		Query query = session.createQuery(sql);
		query.setParameter("msnv", i+"%");
		ArrayList<String> list = (ArrayList<String>) query.list();
		session.getTransaction().commit();
		return list;
	}
	public ArrayList<String> startWithTen(String i) {
		session.beginTransaction();
		String sql = "select hoTen from NguoiDung where hoTen LIKE :hoTen";
		Query query = session.createQuery(sql);
		query.setParameter("hoTen", i+"%");
		ArrayList<String> list = (ArrayList<String>) query.list();
		
		session.getTransaction().commit();
		return list;
	}
	public ArrayList<NguoiDung> searchHoten(String i) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NguoiDung.class);
		cr.add(Restrictions.like("hoTen", i+"%"));
		ArrayList<NguoiDung> list = (ArrayList<NguoiDung>) cr.list();
		session.getTransaction().commit();
		return list;
	}
	
	public ArrayList<NguoiDung> searchMsnv(String i) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NguoiDung.class);
		cr.add(Restrictions.like("msnv", i+"%"));
		ArrayList<NguoiDung> list = (ArrayList<NguoiDung>) cr.list();
		session.getTransaction().commit();
		return list;
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
	public ArrayList<NguoiDung> limit(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NguoiDung.class, "nguoiDung");
//		cr.createAlias("nguoiDung.msnv", "msnv");
//		cr.createAlias("nguoiDung.hoTen", "hoTen");
		cr.createAlias("nguoiDung.chucDanh", "chucDanh");
//		cr.createAlias("chucDanh.cdTen", "cdTen");
		cr.addOrder(Order.asc("chucDanh.cdMa"));
//		cr.createAlias("nguoiDung.email", "email");
//		cr.createAlias("nguoiDung.diaChi", "diaChi");
//		cr.createAlias("nguoiDung.sdt", "sdt");
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<NguoiDung> list = (ArrayList<NguoiDung>) cr.list();
		
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
		System.out.println(new NguoiDungDAO().searchHoten("V"));
	}
}
