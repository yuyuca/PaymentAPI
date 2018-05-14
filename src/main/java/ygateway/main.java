package ygateway;

import java.io.IOException;
import java.sql.Date;

import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class main {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		PaymentInfoDto infoDto = new PaymentInfoDto();
		infoDto.setRequest_status((byte) 0);
		infoDto.setAmount(1000);
		infoDto.setCreate_time(new Date(System.currentTimeMillis()));
		infoDto.setUpdate_time(new Date(System.currentTimeMillis()));
		infoDto = PaymentInfoDao.insert(infoDto);
		
		KPspRequest reqModel = new KPspRequest(infoDto);
		KPspResponse resModel = KPspClient.send(reqModel);
		System.out.println(resModel.getMessage());
		
		ObjectMapper mapper = new ObjectMapper();
	}
}
