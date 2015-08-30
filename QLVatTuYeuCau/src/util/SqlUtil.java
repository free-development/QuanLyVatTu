/**
 * 
 */
package util;

/**
 * @author quoioln
 *
 */
public class SqlUtil {
	private String query;
	private String condition;
	public SqlUtil() {
		this.query = "";
		this.condition = "";
	}
	public String createQuery(String table) {
		this.query = "from " +table;
		return this.query;
	}
	public String addCondition(String columnValue) {
		if (this.condition.length() == 0)
			this.condition += " where " + columnValue;
		else 
			this.condition += " and " + columnValue;
		this.query += this.condition;
		return this.query;
	}
	public String orderBy(String column, boolean desc) {
		
		this.query += " order by " + column;
		if (desc)
			query += " desc";
		return this.query;
	}
}
