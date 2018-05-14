package ygateway;

public class KPspRequest {
	private String payment_id;
	private String amount;
	private String access_key;

	public KPspRequest(PaymentInfoDto infoDto) {
		payment_id = String.valueOf(infoDto.getId());
		amount = String.valueOf(infoDto.getAmount());
		access_key = "yssotrk";

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
