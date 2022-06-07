import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static final String NASA_SERVICE_URI =
            "https://api.nasa.gov/planetary/apod?api_key=TebbIZkXinqIo8UOFHbaXDYps1GSIPIYeNZVMQ67";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request1 = new HttpGet(NASA_SERVICE_URI);
        CloseableHttpResponse response1 = httpClient.execute(request1);
NasaApi nasaApi= mapper.readValue(response1.getEntity().getContent(),NasaApi.class);
        System.out.println(nasaApi);
HttpGet request2= new HttpGet(nasaApi.getUrl());
        CloseableHttpResponse response2 = httpClient.execute(request2);
        String[] array = nasaApi.getUrl().split("/");
String file = array[6];
        HttpEntity entity=response2.getEntity();
        if(entity !=null){
            FileOutputStream fostream= new FileOutputStream(file);
            entity.writeTo(fostream);
            fostream.close();
        }
    }
}
