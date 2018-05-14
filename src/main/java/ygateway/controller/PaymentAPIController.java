package ygateway.controller;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ygateway.constants.YGatewayConstants;
import ygateway.dao.PaymentInfoDao;
import ygateway.factory.PaymentDataFactory;
import ygateway.model.PaymentInfo;
import ygateway.model.YGateWayResponse;
import ygateway.psp.KPspClient;
import ygateway.psp.model.KPspRequest;
import ygateway.psp.model.KPspResponse;
import ygateway.validator.RequestValidator;

@SpringBootApplication
@RestController
@RequestMapping("/payment_api/training")
public class PaymentAPIController {
	public static void main(String[] args) {
		SpringApplication.run(PaymentAPIController.class, args);
	}

	@RequestMapping(value = "apply", method = RequestMethod.POST)
	String doPost(Integer amount) throws Exception {
		if (RequestValidator.isInvalid(amount)) {
			return buildInvalidResponse("金額が無効な数字です").toJson();
		}

		PaymentInfo infoDto = PaymentDataFactory.build(amount);
		PaymentInfoDao.insert(infoDto);

		KPspResponse pspResModel = null;
		try {
			pspResModel = KPspClient.send(new KPspRequest(infoDto));
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		if (null == pspResModel) {
			return buildInvalidResponse("PSPの接続に失敗しました。").toJson();
		}

		List<PaymentInfo> info = PaymentInfoDao.findAll();
		PaymentInfo currentRecord = info.get(info.size() - 1);

		switch (pspResModel.getResponse_code()) {
		case YGatewayConstants.RES_CODE_SUCCESS:
			currentRecord.setRequest_status(YGatewayConstants.STATUS_CODE_OK);
			currentRecord.setUpdate_time(new Date(System.currentTimeMillis()));
			break;
		case YGatewayConstants.RES_CODE_FAILED:
			currentRecord.setRequest_status(YGatewayConstants.STATUS_CODE_NG);
			currentRecord.setUpdate_time(new Date(System.currentTimeMillis()));
			break;
		default:
			return buildInvalidResponse("想定外のレスポンスを受け付けました。").toJson();
		}
		PaymentInfoDao.update(currentRecord);
		return new YGateWayResponse(pspResModel).toJson();
	}

	public static YGateWayResponse buildInvalidResponse(String errorMessage) {
		YGateWayResponse errorModel = new YGateWayResponse();
		errorModel.setResponse_code(YGatewayConstants.RES_CODE_FAILED);
		errorModel.setMessage(errorMessage);
		return errorModel;
	}
}