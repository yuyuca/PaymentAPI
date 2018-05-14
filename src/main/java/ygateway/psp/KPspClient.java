package ygateway.psp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import ygateway.psp.model.KPspRequest;
import ygateway.psp.model.KPspResponse;

public class KPspClient {

	public static KPspResponse send(KPspRequest reqModel) throws ClientProtocolException, IOException {
		String url = "https://kawano-psp-api.herokuapp.com/kawanogw/capture";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		
		EntityBuilder builder = EntityBuilder.create();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("amount", String.valueOf(reqModel.getAmount())));
		params.add(new BasicNameValuePair("payment_id", String.valueOf(reqModel.getPayment_id())));
		params.add(new BasicNameValuePair("access_key", String.valueOf(reqModel.getAccess_key())));
		builder.setParameters(params);
		post.setEntity(builder.build());

		CloseableHttpResponse res = client.execute(post);
		return new Gson().fromJson(EntityUtils.toString(res.getEntity(), "UTF-8" ), KPspResponse.class);
	}

}
