package ygateway.model;

import com.google.gson.Gson;

import ygateway.psp.model.KPspResponse;

public class YGateWayResponse {
	private String response_code;
	private String message;

	public YGateWayResponse() {
	}

	public YGateWayResponse(KPspResponse resModel) {
		this.setResponse_code(resModel.getResponse_code());
		this.setMessage(resModel.getMessage());
	}
	public String getResponse_code() {
		return response_code;
	}
	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
}
