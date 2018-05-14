package ygateway.psp.model;

import ygateway.constants.YGatewayConstants;
import ygateway.model.PaymentInfo;

public class KPspRequest {
	private String payment_id;
	private String amount;
	private String access_key;

	public KPspRequest(PaymentInfo infoDto) {
		payment_id = String.valueOf(infoDto.getId());
		amount = String.valueOf(infoDto.getAmount());
		access_key = YGatewayConstants.PSP_ACCESS_KEY;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public String getAmount() {
		return amount;
	}

	public String getAccess_key() {
		return access_key;
	}
}
