package ygateway;

import java.io.IOException;

import java.sql.Date;

import org.apache.http.client.ClientProtocolException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@SpringBootApplication
@RestController
@RequestMapping("/payment_api/training")
public class App {
	private static final String RESPONSE_CODE_INVALID = "1";
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@RequestMapping(value = "apply", method = RequestMethod.POST)
	String doPost(Integer amount) throws ClientProtocolException, IOException {

		if(RequestValidator.isInvalid(amount)){
			KPspResponse errorModel = new KPspResponse();
			errorModel.setResponse_code(RESPONSE_CODE_INVALID);
			errorModel.setMessage("金額が無効な数字です。");
			return new Gson().toJson(errorModel);
		}
		
		
		PaymentInfoDto infoDto = new PaymentInfoDto();
		infoDto.setRequest_status((byte)0);
		infoDto.setAmount(amount);
		infoDto.setCreate_time(new Date(System.currentTimeMillis()));
		infoDto.setUpdate_time(new Date(System.currentTimeMillis()));
		infoDto = PaymentInfoDao.insert(infoDto);

		KPspRequest reqModel = new KPspRequest(infoDto);
		KPspResponse resModel = KPspClient.send(reqModel);
		if(null != resModel && "0".equals(resModel.getResponse_code())){
			infoDto.setRequest_status((byte)1);			
		} else {
			infoDto.setRequest_status((byte)9);
		}
		infoDto.setUpdate_time(new Date(System.currentTimeMillis()));
		PaymentInfoDao.update(infoDto);
		return new Gson().toJson(resModel);
	}
}