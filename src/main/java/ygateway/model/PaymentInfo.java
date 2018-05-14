package ygateway.model;

import java.sql.Date;

/**
 * payment_infoテーブル用DTOクラス
 *
 */
public class PaymentInfo {
	/**
	 * フィールド変数
	 */
	private Long id;
	private Byte request_status;
	private Integer amount;
	private Date create_time;
	private Date update_time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getRequest_status() {
		return request_status;
	}

	public void setRequest_status(Byte request_status) {
		this.request_status = request_status;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}