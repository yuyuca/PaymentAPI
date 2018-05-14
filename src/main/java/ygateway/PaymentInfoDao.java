package ygateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * payment_infoテーブル用のDAOクラス
 *
 */
public class PaymentInfoDao {

	public static PaymentInfoDto insert(PaymentInfoDto dto) {
		
		// JDBCドライバ読み込み
		try {
			// PostgreSQLドライバの読み込み
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// データベースへの接続
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "yokota.yuko",
				"");) {
			// sql文を実行するためのオブジェクト生成
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO payment_information(request_status, amount, create_time, update_time) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setByte(1, dto.getRequest_status());
			ps.setInt(2, dto.getAmount());
			ps.setDate(3, dto.getCreate_time());
			ps.setDate(4, dto.getUpdate_time());
			System.out.println(ps.toString());
			ps.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<PaymentInfoDto> list = findAll();		
		return list.get(list.size() -1);
	}

	public static PaymentInfoDto update(PaymentInfoDto dto) {
		
		// JDBCドライバ読み込み
		try {
			// PostgreSQLドライバの読み込み
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// データベースへの接続
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "yokota.yuko",
				"");) {
			// sql文を実行するためのオブジェクト生成
			Statement stmt = conn.createStatement();
			String sql = "UPDATE payment_information SET request_status = ?, update_time = ? WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setByte(1, dto.getRequest_status());
			ps.setDate(2, dto.getUpdate_time());
			ps.setLong(3, dto.getId());
			System.out.println(ps.toString());
			ps.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<PaymentInfoDto> list = findAll();		
		return list.get(list.size() -1);
	}
	
	
	public static List<PaymentInfoDto> findAll() {
		// DTOクラスのインスタンス格納用
		List<PaymentInfoDto> PaymentInfoDto = new ArrayList<>();

		// JDBCドライバ読み込み
		try {
			// PostgreSQLドライバの読み込み
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			
		}

		// データベースへの接続
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "yokota.yuko",
				"");) {
			// sql文を実行するためのオブジェクト生成
			Statement stmt = conn.createStatement();
			// SELECT文の発行
			String sql = "SELECT * FROM payment_information";
			// sql文の実行結果を取得（データベースからの値）
			ResultSet rset = stmt.executeQuery(sql);

			// データベースから取得した値がある間、
			while (rset.next()) {
				// PaymentInfoDtoクラスのインスタンスを生成
				PaymentInfoDto dto = new PaymentInfoDto();
				// カラムidの値をフィールドidにセット
				dto.setId(rset.getLong("id"));
				// カラムrequest_statusの値をフィールドrequest_statusにセット
				dto.setRequest_status(rset.getByte("request_status"));
				// カラムamountの値をフィールドamountにセット
				dto.setAmount(rset.getInt("amount"));
				// カラムcreate_timeの値をフィールドcreate_timeにセット
				dto.setCreate_time(rset.getDate("create_time"));
				// カラムupdate_timeの値をフィールドupdate_timeにセット
				dto.setUpdate_time(rset.getDate("update_time"));
				// インスタンスをListに格納
				PaymentInfoDto.add(dto);
				// while文で次のレコードの処理へ?
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// DTOクラスのインスタンスのListを返す
		return PaymentInfoDto;
	}
}
