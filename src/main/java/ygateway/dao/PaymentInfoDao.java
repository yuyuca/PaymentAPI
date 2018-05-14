package ygateway.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ygateway.model.PaymentInfo;

/**
 * payment_infoテーブル用のDAOクラス
 */
public class PaymentInfoDao {

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void insert(PaymentInfo dto) throws SQLException {
		final String SQL = "INSERT INTO payment_information(request_status, amount, create_time, update_time) VALUES (?, ?, ?, ?)";
		try (Connection conn =
					DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "yokota.yuko","")) {
			PreparedStatement ps = conn.prepareStatement(SQL);
			ps.setByte(1, dto.getRequest_status());
			ps.setInt(2, dto.getAmount());
			ps.setDate(3, dto.getCreate_time());
			ps.setDate(4, dto.getUpdate_time());
			ps.executeUpdate();
		}
	}

	public static void update(PaymentInfo dto) throws SQLException {
		final String SQL = "UPDATE payment_information SET request_status = ?, update_time = ? WHERE id = ?";
		try (Connection conn =
					DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "yokota.yuko", "")) {
			PreparedStatement ps = conn.prepareStatement(SQL);
			ps.setByte(1, dto.getRequest_status());
			ps.setDate(2, dto.getUpdate_time());
			ps.setLong(3, dto.getId());
			ps.executeUpdate();
		}
	}

	public static List<PaymentInfo> findAll() throws SQLException {
		try (Connection conn =
					DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "yokota.yuko", "");) {
			ResultSet rset = conn.createStatement().executeQuery("SELECT * FROM payment_information");
			List<PaymentInfo> PaymentInfoDto = new ArrayList<>();
			while (rset.next()) {
				PaymentInfo dto = new PaymentInfo();
				dto.setId(rset.getLong("id"));
				dto.setRequest_status(rset.getByte("request_status"));
				dto.setAmount(rset.getInt("amount"));
				dto.setCreate_time(rset.getDate("create_time"));
				dto.setUpdate_time(rset.getDate("update_time"));
				PaymentInfoDto.add(dto);
			}
			return PaymentInfoDto;
		}
	}
}
