package dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import model.CongVan;
import model.DonVi;
import model.File;
import model.TrangThai;
import model.VTCongVan;
import util.DateUtil;
import util.HibernateUtil;
import util.SqlUtil;

public class CongVanDAO {
	
	private SessionFactory template;  
	private Session session;
	public CongVanDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	public CongVan getCongVan(final int cvId) {
		session.beginTransaction();
		CongVan congVan = (CongVan) session.get(CongVan.class, cvId);
		session.getTransaction().commit();
		return congVan;
	}
	public List<CongVan> getAllCongVan() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		cr.add(Restrictions.eq("daXoa", 0));
		List<CongVan> congVanList = (List<CongVan>) cr.list();
		session.getTransaction().commit();
		return congVanList;
	}
	
	public List<CongVan> limit(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
//		Criterion limitRow = Restrictions.
		cr.add(xoaCd);
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list(); 
		session.getTransaction().commit();
		return congVanList;
	}
	public Criteria getCriteria(String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		
		if (msnv != null) {
			Criteria crVtCv = session.createCriteria(VTCongVan.class);
			crVtCv.add(Restrictions.eq("msnv", msnv));
			crVtCv.setProjection(Projections.countDistinct("cvId"));
			
			crVtCv.setProjection(Projections.property("cvId"));
			
			ArrayList<Integer> cvIdList = (ArrayList<Integer>) crVtCv.list();
			
			if (cvIdList.size() > 0)
				cr.add(Restrictions.in("cvId", cvIdList));
			else {
				session.getTransaction().commit();
				return null;
			}
		}
//		
		session.getTransaction().commit();
		return cr;
	}
	public long size(String msnv) {
		
		Criteria cr = getCriteria(msnv);
		long size = 0;
		if (cr != null) {
//			session.beginTransaction();
			cr.setProjection(Projections.countDistinct("cvId"));
			size = (long) cr.list().get(0);
//			session.getTransaction().commit();
		}
		
		return size;
	}
	
	public void addCongVan(CongVan congVan){
		session.beginTransaction();
		session.save(congVan);
		session.getTransaction().commit();
	}
	public void addOrUpdateCongVan(CongVan congVan){
		session.beginTransaction();
		session.saveOrUpdate(congVan);
		session.getTransaction().commit();
	}
	public void updateCongVan(CongVan congVan){
		session.beginTransaction();
		session.update(congVan);
		session.getTransaction().commit();
	}
	public void deleteCongVan(int cvId){
		session.beginTransaction();
//		session.delete(congVan);
		String hql = "UPDATE CongVan set daXoa = 1 "  + 
	             "WHERE cvId = :cvId";
		Query query = session.createQuery(hql);
		query.setParameter("cvId", cvId);
		int result = query.executeUpdate();
		session.getTransaction().commit();
	}
	public int getLastInsert() {
		session.beginTransaction();
		Criteria cr =  session.createCriteria(File.class).setProjection(Projections.max("cvId"));// max("ctvtId"));
		Integer idOld =  (Integer) cr.list().get(0);
		int id = 0;
		if (idOld != null)
			id += idOld + 1;
		else
			id++;
		
		session.getTransaction().commit();
		return id;
	}
	public int getSoDenMax() {
		session.beginTransaction();
		Criteria cr =  session.createCriteria(CongVan.class).setProjection(Projections.max("soDen"));// max("ctvtId"));
		Integer idOld =  (Integer) cr.list().get(0);
		int soDen = 0;
		if (idOld != null)
			soDen += idOld + 1;
		else
			soDen++;
		
		session.getTransaction().commit();
		return soDen;
	}
	public ArrayList<CongVan> getByDate(Date ngaybd, Date ngaykt){
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		cr.add(Restrictions.between("cvNgayNhan", ngaybd, ngaykt));
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list();
		session.getTransaction().commit();
		return congVanList;
	}
	public ArrayList<CongVan> getTrangThai(Date ngaybd, Date ngaykt){
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		Criterion tt1 = Restrictions.eq("trangThai",new TrangThai("DGQ", ""));
		Criterion tt2 = Restrictions.eq("trangThai",new TrangThai("CGQ", ""));
		LogicalExpression andExp = Restrictions.or(tt1, tt2);
		Criterion ngay = Restrictions.between("cvNgayNhan", ngaybd, ngaykt);
		LogicalExpression andNgay = Restrictions.and(andExp, ngay);
		cr.add(andNgay);
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list();
		session.getTransaction().commit();
		return congVanList;
	}
	public ArrayList<CongVan> getTrangThai(String ngaybd, String ngaykt,String dvMa, String ttMa){
		session.beginTransaction();
		Date ngayht = DateUtil.convertToSqlDate(new java.util.Date());
		Criteria cr = session.createCriteria(CongVan.class);
		
		if (ngaybd != "" || ngaykt != "") 
		{
			if (ngaybd =="") 
			{
			ngaybd = ngaykt;
			}
			if (ngaykt == "")
			{
			ngaykt = ngaybd;
			}
			Criterion ngay = Restrictions.between("cvNgayNhan", DateUtil.parseDate(ngaybd), DateUtil.parseDate(ngaykt));
		cr.add(ngay);
		}
	
			if (ttMa != null && !"all".equalsIgnoreCase(ttMa)) {
				Criterion crtt = Restrictions.eq("trangThai",new TrangThai(ttMa));
				cr.add(crtt);
			}
			if (dvMa != null) {
				Criterion crdv =  Restrictions.eq("donVi",new DonVi(dvMa));
				cr.add(crdv);
			}
		
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list();
		session.getTransaction().commit();
		return congVanList;
	}
	public CongVan getByCvSo(String cvSo){
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		cr.add(Restrictions.eq("cvSo", cvSo));
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list();
		CongVan congVan = null;
		if (congVanList.size() > 0)
			congVan = congVanList.get(0);
		session.getTransaction().commit();
		return congVan;
	}
	public ArrayList<Integer> groupByYearLimit(String msnv, int limit){
		session.beginTransaction();
		ArrayList<Integer> cvIdList = (ArrayList<Integer>) session.createQuery("select distinct(b.cvId) from VTCongVan b where msnv = '" + msnv + "'").list();
		if (cvIdList.size() == 0) {
			session.getTransaction().commit();
			return new ArrayList<Integer>();
			
		}
		String sql = "select distinct YEAR(a.cvNgayNhan) from CongVan a where a.daXoa = 0 and a.cvId in"
					+ " (select distinct(b.cvId) from VTCongVan b where msnv = :msnv) order by cvNgayNhan DESC";
		Query query = session.createQuery(sql);
		query.setParameter("msnv", msnv);
		query.setMaxResults(limit);
		ArrayList<Integer> yearList = (ArrayList<Integer>) query.list();
		session.getTransaction().commit();
		return yearList;
	}
	public ArrayList<Integer> groupByMonth(final String msnv, final int year){
		session.beginTransaction();
		ArrayList<Integer> cvIdList = (ArrayList<Integer>) session.createQuery("select distinct(b.cvId) from VTCongVan b where msnv = '" + msnv + "'").list();
		if (cvIdList.size() == 0)
			return new ArrayList<Integer>();
		String sql = "select distinct MONTH(a.cvNgayNhan) from CongVan a where a.daXoa = 0 and YEAR(cvNgayNhan) = :year and a.cvId in"
				+ " (select distinct(b.cvId) from VTCongVan b where msnv = :msnv) order by cvNgayNhan DESC";
		Query query = session.createQuery(sql);
		query.setParameter("msnv", msnv);
		query.setParameter("year", year);
		
		ArrayList<Integer> monthList = (ArrayList<Integer>) query.list();
		session.getTransaction().commit();
		return monthList;
	}
	public ArrayList<Integer> groupByDate(final String msnv, final int year, final int month){
		session.beginTransaction();
		ArrayList<Integer> cvIdList = (ArrayList<Integer>) session.createQuery("select distinct(b.cvId) from VTCongVan b where msnv = '" + msnv + "'").list();
		if (cvIdList.size() == 0)
			return new ArrayList<Integer>();
		String sql = "select distinct DAY(a.cvNgayNhan) from CongVan a where a.daXoa = 0 and YEAR(cvNgayNhan) = :year and MONTH(cvNgayNhan) = :month and a.cvId in"
				+ " (select distinct(b.cvId) from VTCongVan b where msnv = :msnv) order by cvNgayNhan DESC";
		Query query = session.createQuery(sql);
		
		query.setParameter("msnv", msnv);
		query.setParameter("year", year);
		query.setParameter("month", month);
		ArrayList<Integer> dateList = (ArrayList<Integer>) query.list();
		session.getTransaction().commit();
		return dateList;
	}
	public ArrayList<CongVan> searchByYear(int year) {
		session.beginTransaction();
		String sql = "from CongVan where daXoa = 0 and year(cvNgayNhan) = :year";
		Query query = session.createQuery(sql);
		query.setParameter("year", year);
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) query.list();
		session.getTransaction().commit();
		return congVanList;
	}
	public ArrayList<CongVan> search(HashMap<String, Object> conditions,  HashMap<String, Boolean> orderBy) {
		session.beginTransaction();
		SqlUtil sqlUtil = new SqlUtil();
		sqlUtil.createQuery("CongVan");
		if (conditions != null && conditions.size() > 0) {
			for (String key : conditions.keySet()) {
				Object object = conditions.get(key);
				if (object instanceof Integer)
					sqlUtil.addCondition(key + " = "+ object);
				else
					sqlUtil.addCondition(key + " = '" + object +"'");
			}
		}
		if (orderBy.size() > 0 && orderBy != null)
			sqlUtil.orderBy(orderBy);
		Query query = session.createQuery(sqlUtil.getQuery());
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) query.list(); 
		session.getTransaction().commit();
		return congVanList;
	}
	
	
	public ArrayList<CongVan> searchLimit(String msnv, HashMap<String, Object> conditions,  HashMap<String, Boolean> orderBy, int first, int limit) {
		
		Criteria cr = getCriteria(msnv);
		if (cr == null) {
			return new ArrayList<CongVan>();
		}
		session.beginTransaction();
		if (conditions != null) {
			for (String key : conditions.keySet()) {
				Object object = conditions.get(key);
				if (object instanceof Integer && !key.equals("soDen")) {
					cr.add(Restrictions.sqlRestriction(key.toUpperCase() + "(cvNgayNhan) = " + conditions.get(key)));
				} else { 
					cr.add(Restrictions.eq(key, conditions.get(key)));
				}
			}
		}
		cr.add(Restrictions.eq("daXoa", 0));
		ArrayList<Integer> cvIdList = (ArrayList<Integer>) session.createQuery("select cvId from VTCongVan where msnv = '" + msnv + "'").list();
		if (orderBy != null) {
			for (String key : orderBy.keySet()) {
				if (orderBy.get(key))
					cr.addOrder(Order.desc(key));
				else 
					cr.addOrder(Order.asc(key));
			}
		}
		if (cvIdList.size() > 0)
			cr.add(Restrictions.in("cvId", cvIdList));
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list(); 
		session.getTransaction().commit();
		return congVanList;
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
		System.out.println(new CongVanDAO().size("b1203959"));
		ArrayList<Integer> yearList = new CongVanDAO().groupByYearLimit("b1203959", 5);
		for(Integer year : yearList)
			System.out.println(year);
		
	}
}
