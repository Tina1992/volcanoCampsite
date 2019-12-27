package campsite;

import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.volcano.campsite.entities.CampsiteRequest;
import org.volcano.campsite.entities.UserData;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookingTask implements Callable<String> {

	private Date dateFrom;
	private Date dateTo;

	@Override
	public String call() throws Exception {
		HttpPost post = new HttpPost("https://volcanocampsite.herokuapp.com/reservations");
		post.setHeader("Content-type", "application/json");

		CampsiteRequest campsiteReq = new CampsiteRequest();
		UserData userData = new UserData();
		userData.setMail("test@heroku.com");
		userData.setFullName("test");
		campsiteReq.setUserData(userData);
		campsiteReq.setDateFrom(dateFrom);
		campsiteReq.setDateTo(dateTo);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(campsiteReq);
		System.out.println(jsonString);
		StringEntity entity = new StringEntity(jsonString);

		post.setEntity(entity);

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			System.out.println(EntityUtils.toString(response.getEntity()));
			return response.getEntity().toString();
		}
	}

}
