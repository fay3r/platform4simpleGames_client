package client_app;


import client_app.classes.AccountData;
import client_app.classes.LogginData;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Network {

    public static Boolean loginUser(LogginData logginData){

        Boolean status=false;
        final String uri = "http://127.0.0.1:8080/platform-server/user/loginUser";
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpPut request = new HttpPut(uri);

        final Gson gson = new Gson();

        final String json = gson.toJson(logginData);

        final StringEntity entity;
        try {
        entity = new StringEntity(json);

        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        final CloseableHttpResponse response = client.execute(request);
        System.out.println("kod odpowiedzi #############login################## "+ response.getStatusLine().getStatusCode());
        if(response.getStatusLine().getStatusCode() == 200){
            status = true;
        }

        client.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return status;
        }
}

    public static Boolean registerUser(AccountData accountData) {
        Boolean status=false;
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpPost request = new HttpPost("http://127.0.0.1:8080/platform-server/user/registerUser");

        final Gson gson = new Gson();
        final String json = gson.toJson(accountData);

        final StringEntity entity;
        try {
            entity = new StringEntity(json);

        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        final CloseableHttpResponse response = client.execute(request);
        System.out.println("kod odpowiedzi ##################register########### "+ response.getStatusLine().getStatusCode());

        if(response.getStatusLine().getStatusCode() == 200){
            status = true;
        }
        client.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return status;
        }
    }
}
