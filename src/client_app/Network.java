package client_app;


import client_app.classes.dtoPlatform.*;
import client_app.classes.dtoGames.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.*;


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
        System.out.println("kod odpowiedzi ############################### "+ response.getStatusLine().getStatusCode());
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
        System.out.println(accountData.toString());
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
        System.out.println("kod odpowiedzi ############################# "+ response.getStatusLine().getStatusCode());

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

    public static Map<String,String> checkingUserPresence(String nick) {
        nick = nick.replace(" ","");
        String uri = "http://127.0.0.1:8080/platform-server/user/forgetPasswordIsUser/" + nick;
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpPut request = new HttpPut(uri);
        final Gson gson = new Gson();

        try {

            final CloseableHttpResponse response = client.execute(request);
            System.out.println("kod odpowiedzi ############################# "+ response.getStatusLine().getStatusCode());

            final HttpEntity entity =  response.getEntity();
            final String json = EntityUtils.toString(entity);

            final Type type = new TypeToken<Map<String,String>>() {}.getType();

            final Map<String ,String > messageBack =gson.fromJson(json,type);

            client.close();
            //if(messageBack!=null){
                return messageBack;
          //  }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Boolean FPChange(LogginData fpChangeData) {
        Boolean status=false;
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpPut request = new HttpPut("http://127.0.0.1:8080/platform-server/user/forgetPasswordChange");

        final Gson gson = new Gson();
        final String json = gson.toJson(fpChangeData);

        final StringEntity entity;
        try {
            entity = new StringEntity(json);

            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            final CloseableHttpResponse response = client.execute(request);
            System.out.println("kod odpowiedzi ############################# "+ response.getStatusLine().getStatusCode());

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

    public static List<String> getLogins() {
        String uri = "http://127.0.0.1:8080/platform-server/user/getLogins";
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet(uri);
        final Gson gson = new Gson();

        try {

            final CloseableHttpResponse response = client.execute(request);
            System.out.println("kod odpowiedzi ############################# "+ response.getStatusLine().getStatusCode());

            final HttpEntity entity =  response.getEntity();
            final String json = EntityUtils.toString(entity);

            final Type type = new TypeToken<List<String>>() {}.getType();

            final List<String> messageBack =gson.fromJson(json,type);

            client.close();
            System.out.println(messageBack.size());

            return messageBack;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deletePlayer(String nick){
        String uri = "http://127.0.0.1:8080/platform-server/user/deleteUser/" + nick;
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpDelete request = new HttpDelete(uri);

        try {
            final CloseableHttpResponse response = client.execute(request);
            System.out.println("kod odpowiedzi ############################# "+ response.getStatusLine().getStatusCode());
            client.close();

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Boolean passwordChange(ChangePasswordDto changePassworddto) {
        Boolean status=false;
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpPut request = new HttpPut("http://127.0.0.1:8080/platform-server/user/changePassword");

        final Gson gson = new Gson();
        final String json = gson.toJson(changePassworddto);

        final StringEntity entity;
        try {
            entity = new StringEntity(json);

            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            final CloseableHttpResponse response = client.execute(request);
            System.out.println("kod odpowiedzi ############################# "+ response.getStatusLine().getStatusCode());

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

    public static List<AccountScoresDto> getScoreTable() {
        String uri = "http://127.0.0.1:8080/platform-server/user/getScoreTable";
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet(uri);
        final Gson gson = new Gson();

        try {

            final CloseableHttpResponse response = client.execute(request);
            System.out.println("kod odpowiedzi ############################# "+ response.getStatusLine().getStatusCode());

            final HttpEntity entity =  response.getEntity();
            final String json = EntityUtils.toString(entity);

            final Type type = new TypeToken<List<AccountScoresDto>>() {}.getType();

            final List<AccountScoresDto> messageBack =gson.fromJson(json,type);

            client.close();
            //if(messageBack!=null){
            return messageBack;
            //  }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void resetScore(String nick){
        String uri = "http://127.0.0.1:8080/platform-server/user/resetScore/" + nick;
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpPut request = new HttpPut(uri);

        try {
            final CloseableHttpResponse response = client.execute(request);
            System.out.println("kod odpowiedzi ############################# "+ response.getStatusLine().getStatusCode());
            client.close();

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(ChatMessage chatMessage) {
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpPut request = new HttpPut("http://127.0.0.1:8080/platform-server/user/sendMessage");
        System.out.println("w network"+chatMessage.toString());

        final Gson gson = new Gson();
        final String json = gson.toJson(chatMessage);
        System.out.println("json"+json);

        final StringEntity entity;
        try {
            entity = new StringEntity(json);

            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            final CloseableHttpResponse response = client.execute(request);
            System.out.println("kod odpowiedzi ############################# "+ response.getStatusLine().getStatusCode());

            client.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<ChatMessage> getChatMessages() {
        String uri = "http://127.0.0.1:8080/platform-server/user/getChatMessages";
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet(uri);
        final Gson gson = new Gson();

        try {

            final CloseableHttpResponse response = client.execute(request);
            System.out.println("kod odpowiedzi ############################# "+ response.getStatusLine().getStatusCode());

            final HttpEntity entity =  response.getEntity();
            final String json = EntityUtils.toString(entity);

            final Type type = new TypeToken<LinkedList<ChatMessage>>() {}.getType();

            final LinkedList<ChatMessage> messageBack =gson.fromJson(json,type);
            Collections.reverse(messageBack);

            client.close();
            //if(messageBack!=null){
            return messageBack;
            //  }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void clearChat(){
        String uri = "http://127.0.0.1:8080/platform-server/user/clearChat";
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final HttpDelete request = new HttpDelete(uri);

        try {
            final CloseableHttpResponse response = client.execute(request);
            System.out.println("kod odpowiedzi ############################# "+ response.getStatusLine().getStatusCode());
            client.close();

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // obsluga api gier

    public static boolean sendMissle(int x, int y) throws Exception {

        try {
            final CloseableHttpClient client = HttpClients.createDefault();
            final HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/api/ship/user");

            Gson gson = new Gson();

            // Serializacja obiektu do JSONa
            final String json = gson.toJson(new Single2Int(x, y));

            final StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            final CloseableHttpResponse response = client.execute(httpPost);

            System.out.println("Kod odpowiedzi serwera sendMissle : " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200) {

                final HttpEntity httpEntity = response.getEntity();

                // Na tym etapie odczytujemy JSON'a, ale jako String.
                final String text = EntityUtils.toString(httpEntity);

                final Type type = new TypeToken<SingleBoolean>() {
                }.getType();
                final SingleBoolean singleBoolean = gson.fromJson(text, type);

                client.close();
                return singleBoolean.isValue();
            }
            else
            {
                throw new Exception("Bad reposnse in sendMissle");
            }

        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static void generateMap() throws Exception {

        try
        {
            final CloseableHttpClient client = HttpClients.createDefault();
            final HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/api/ship/reset");
            final CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("Kod odpowiedzi serwera generateMap: " + response.getStatusLine().getStatusCode());
            client.close();
        }
        catch(Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static Single2Int getMissle() throws Exception {

        try {
            final HttpClient client = HttpClientBuilder.create().build();
            final HttpGet request = new HttpGet("http://127.0.0.1:8080/api/ship/bot");
            final Gson gson = new Gson();

            final HttpResponse response = client.execute(request);
            final HttpEntity entity = response.getEntity();
            final String json = EntityUtils.toString(entity);
            final Type type = new TypeToken<Single2Int>() { }.getType();
            final Single2Int files = gson.fromJson(json, type);

            System.out.println("Kod odpowiedzi serwera getMissle : " + response.getStatusLine().getStatusCode());

            if(response.getStatusLine().getStatusCode() != 200)
            {
                throw new Exception("Bad reposnse in getMissle");
            }

            return files;

        } catch (IOException e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static void sendMap(Array2Int array2Int) throws Exception {

        try {
            final CloseableHttpClient client = HttpClients.createDefault();
            final HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/api/ship/array");
            Gson gson = new Gson();
            final String json = gson.toJson(array2Int);

            final StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            final CloseableHttpResponse response = client.execute(httpPost);

            System.out.println("Kod odpowiedzi serwera sendMap : " + response.getStatusLine().getStatusCode());

            if(response.getStatusLine().getStatusCode() != 200)
            {
                throw new Exception("Bad reposnse in sendMap");
            }

            client.close();
        }
        catch(Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static Array2Int result() throws Exception {

        //budujemy klineta jednorzaowego
        final HttpClient client = HttpClientBuilder.create().build();

        //podajemy link
        final HttpGet request = new HttpGet("http://127.0.0.1:8080/api/ttt/result");

        //obiekt do konwersacji json
        final Gson gson = new Gson();

        try
        {
            // Otrzymujemy odpowiedz od serwera.
            final HttpResponse response = client.execute(request);
            final HttpEntity entity = response.getEntity();

            // Na tym etapie odczytujemy JSON'a, ale jako String.
            final String json = EntityUtils.toString(entity);

            final Type type = new TypeToken<Array2Int>() {}.getType();

            final Array2Int files = gson.fromJson(json, type);

            System.out.println("Result - kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200)
            {
                System.out.println("Aktualizacja planszy");
            }

            return files;

        }
        catch (IOException e)
        {
            throw new Exception("Problem z zwróceniem JSONA");
        }
    }

    public static void reset() throws Exception{

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPut httpPut = new HttpPut("http://127.0.0.1:8080/api/ttt/reset");

        try {

            final CloseableHttpResponse response = client.execute(httpPut);

            System.out.println("Reset - kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("Gra zresetowana");
            }
            client.close();
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void bot(int symbol) throws Exception{

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPut httpPut = new HttpPut("http://127.0.0.1:8080/api/ttt/bot");

        Gson gson = new Gson();

        // Tworzymy obiekt uzytkownika
        final Single1Int userData = new Single1Int(symbol);

        // Serializacja obiektu do JSONa
        final String json = gson.toJson(userData);

        try {

            final StringEntity entity = new StringEntity(json);
            httpPut.setEntity(entity);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");

            final CloseableHttpResponse response = client.execute(httpPut);

            System.out.println("Bot - kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200)
            {
                System.out.println("Ruch bota");
            }

            client.close();
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void user(final Single3Int single3IntData) throws Exception{

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPut httpPut = new HttpPut("http://127.0.0.1:8080/api/ttt/user");

        Gson gson = new Gson();

        // Serializacja obiektu do JSONa
        final String json = gson.toJson(single3IntData);

        try {

            final StringEntity entity = new StringEntity(json);
            httpPut.setEntity(entity);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");

            final CloseableHttpResponse response = client.execute(httpPut);

            System.out.println("User - kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200)
            {
                System.out.println("Ruch usera");
            }

            client.close();
        } catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static void gameResult(String nick, String state) throws Exception {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPut httpPut = new HttpPut("http://127.0.0.1:8080/platform-server/user/updateStats");
        Map<String,String> date = new HashMap<>();
        date.put("nick",nick);
        date.put("operation",state);

        Gson gson = new Gson();

        // Serializacja obiektu do JSONa
        final String json = gson.toJson(date);
        System.err.println(json);

        try {
            final StringEntity entity = new StringEntity(json);
            httpPut.setEntity(entity);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");

            final CloseableHttpResponse response = client.execute(httpPut);

            System.out.println("Game result - kot odpowiedzi serwera: " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("Dodano resul do servera");
            }

            client.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
