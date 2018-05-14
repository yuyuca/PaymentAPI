package ygateway.factory;

import java.sql.Date;

import ygateway.model.PaymentInfo;

public class PaymentDataFactory {
	public static PaymentInfo build(Integer amount) {
		PaymentInfo infoDto = new PaymentInfo();
		infoDto.setRequest_status((byte) 0);
		infoDto.setAmount(amount);
		infoDto.setCreate_time(new Date(System.currentTimeMillis()));
		infoDto.setUpdate_time(new Date(System.currentTimeMillis()));
		return infoDto;
	}
}
