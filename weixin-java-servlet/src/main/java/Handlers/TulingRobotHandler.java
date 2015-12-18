package Handlers;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joway on 15/12/17.
 */
public class TulingRobotHandler {

    public static String getReplyFromTulingRobot(String info){

        String apiUrl = "http://www.tuling123.com/openapi/api?key=a33208bb10bba61e9a05e7cec9037cc4&info=";
        String requestUrl = null;
        try {
            requestUrl = apiUrl + URLEncoder.encode(info,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String responseText = "";
        /** 发送httpget请求 */
        HttpGet request = new HttpGet(requestUrl);
        try {
            HttpResponse response = HttpClients.createDefault().execute(request);
            if(response.getStatusLine().getStatusCode()==200){
                responseText = EntityUtils.toString(response.getEntity());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** 请求失败处理 */
        if(null==responseText){
            return "对不起，你说的话真是太高深了……";
        }

        try {
            JSONObject json = new JSONObject(responseText);
            //以code=100000为例，参考图灵机器人api文档
            if(100000==json.getInt("code")){
                responseText = json.getString("text");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(responseText);
        return responseText;
    }


    public static void main(String[] args) {
        System.out.println(TulingRobotHandler.getReplyFromTulingRobot("你好"));
    }
}
