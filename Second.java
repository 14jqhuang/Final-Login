package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JTextArea;
public class Second extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private static String protocol = "jdbc:derby:";
	String dbName = "C://Login";
	Connection con = null;
	Statement sql = null;
	ResultSet res =null;

	JTextArea jta;
	public Second()
	{
		loadDriver();
		doIt();
	}

	public void loadDriver() {
		try {
			Class.forName(driver).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void doIt() {

		try {
			con = DriverManager.getConnection(protocol + dbName
					);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 查询
		public ResultSet executeQuery(String sql_s) {
			try {
				sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				res = sql.executeQuery(sql_s);
			} catch (SQLException e) {
				System.out.println(e);
			}
			return res;
		}

		// update操作
		public int executeUpdate(String sql_s) {

			int rs = 0;
			try {
				sql = con.createStatement();
				rs = sql.executeUpdate(sql_s);
			} catch (SQLException e) {
				System.out.println(e);
			}

			return rs;
		}

		// 关闭数据库
		public void close() {
			if (res != null) {
				try {
					res.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}


}