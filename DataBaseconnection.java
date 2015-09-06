package login;
//���ݿ������
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseconnection {
	
	Connection con = null;
	Statement sql = null;
	ResultSet rs = null;

	public DataBaseconnection() {

		// �����ܳ����쳣��������try����.
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); // ��������
		} catch (ClassNotFoundException e) {
			System.out.println(e);//catch�鴦���쳣.
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/abcd","root","578111"); // �������ݿ�

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	// ��ѯ
	public ResultSet executeQuery(String sql_s) {
		try {
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = sql.executeQuery(sql_s);
		} catch (SQLException e) {
			System.out.println(e);
		}
		return rs;
	}

	// update����
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

	// �ر����ݿ�
	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
//���кܶ๦��û��ʵ�֣�������ʱ���ٲ��ϣ���Ps������ѧϰ��~~~