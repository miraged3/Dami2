package cn.maoookai.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HttpPostUtil {

    public static String send(String url, JSONObject jsonObject) throws IOException {
        String body = "";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(s);
        System.out.println("Request address：" + url);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            body = EntityUtils.toString(entity, Charset.defaultCharset());
        }
        StringBuilder stringBuilder = new StringBuilder(body);
        stringBuilder.replace(0, 1, "");
        stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
        EntityUtils.consume(entity);
        response.close();
        return stringBuilder.toString();
    }

    public static List<String> getArray(JSONObject secretCode) {

        if (secretCode.containsKey("trans")) {
            JSONArray array = secretCode.getJSONArray("trans");
            return IntStream.range(0, array.size()).mapToObj(array :: getString).collect(Collectors.toList());
        } else return null;
    }

}
