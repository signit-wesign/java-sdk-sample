package sample;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.WebhookResponse;
import cn.signit.sdk.util.HmacSignatureBuilder;

/**
 * 易企签 Java SDK 通过HttpServletRequest解析Webhook响应的示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK通过HttpServletRequest解析Webhook响应，示例代码中的数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，appUrl均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class WebhookResponseParseByRequestDemo {
    @SpringBootApplication
    public static class Application {
        public static void main(String[] args) throws Exception {
            Object[] reso = { Application.class, CatchWebhookResponse.class };
            SpringApplication.run(reso, args);
        }
    }

    @RestController
    public static class CatchWebhookResponse {
        // 使用 request 验证并解析响应数据
        @PostMapping("test/parse-webhook")
        public void testVerifyAndParseWebhookResponse(HttpServletRequest request) throws IOException {
            request.setCharacterEncoding("UTF-8");
            int len = request.getContentLength();
            ServletInputStream iii = request.getInputStream();
            byte[] body = new byte[len];
            iii.read(body, 0, len);

            String appId = "1678bc2091000d861138f74aa51";
            String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";

            // step1 : 客户端验证服务端
            boolean verifyResult = SignitClient.verify(appId, appSecretKey, body, request);

            System.out.println("\n\nthe result of hmac verify is " + verifyResult);

            // step2: 解析webhook响应数据
            WebhookResponse ente = SignitClient.parseWebhookResponse(new String(body));
            System.out.println("\nwebhookResponse is :\n" + JSON.toJSONString(ente, true));

        }

        @GetMapping("test")
        public String test() throws IOException {
            sendHttpRequest();
            return "send success";
        }

    }

    // 模拟易企签 发送给客户端 webhook响应数据
    public static void sendHttpRequest() throws IOException {

        String enteVerifyWebhookRespStr = "{\"event\":\"enterpriseVerificationSubmitted\",\"target\":{\"webhookWsid\":\"WSID_HOOK_000001678bc29d510242ac1400030001\",\"destination\":\"https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\"},\"rawData\":\"{\\\"code\\\":\\\"100550000\\\",\\\"message\\\":\\\"\\\\u8BF7\\\\u6C42\\\\u6210\\\\u529F\\\",\\\"customTag\\\":\\\"hello world agent:https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\\\",\\\"invokeNo\\\":\\\"201812111620019293398044121001\\\",\\\"actionUrl\\\":\\\"http://10.10.9.67:61112/WSID_LINK_000001679c59fbd4aafd2531a35d0001/open-flow-enterprise-identity?token=43528582a1bb4659b33c8eee57c717cb\\\",\\\"status\\\":\\\"INCOMPLETE\\\",\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";
        String appId = "1678bc2091000d861138f74aa51";
        String nonce = "yZIAsVFMzl1GTVoe0suis77p";
        String dateString = "Tue Dec 11 16:20:02 CST 2018";
        String host = "localhost:8080";
        String protocol = "HTTP/1.1";

        String signitSignature = null;// "HmacSHA512
                                      // 1678bc2091000d861138f74aa51:sYurqlP8S2qCy8bvjE1hxAfi361qVZt5ZmaCreYqfy0FciVzNz8q/pPVQcrd1kPGqmB7beDh1f2NVzlwjfbDlw==";
        HmacSignatureBuilder builder = new HmacSignatureBuilder();
        builder.scheme(protocol)
                .apiKey(appId)
                .apiSecret(appSecretKey.getBytes())
                .method("POST")
                .payload(enteVerifyWebhookRespStr.getBytes())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .host(host)
                .resource("")
                .nonce(nonce)
                .date(dateString);
        signitSignature = builder.getDefaultAlgorithm() + " " + appId + ":" + builder.buildAsBase64();

        URL url = new URL("http://localhost:8080/test/parse-webhook");
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("X-Signit-Signature", signitSignature);
        conn.setRequestProperty("X-Signit-Nonce", nonce);
        conn.setRequestProperty("X-Signit-Date", dateString);
        conn.setRequestProperty("X-Signit-Event", "enterpriseVerificationSubmitted");
        conn.setRequestProperty("X-Signit-User_Agent", "Signit HTTP");
        conn.setRequestProperty("Host", host);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(30000);
        conn.setRequestMethod("POST");

        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);

        conn.connect();
        // 获取URLConnection对象对应的输出流
        byte[] buffer = enteVerifyWebhookRespStr.getBytes();
        OutputStream out = conn.getOutputStream();

        PrintWriter pw = new PrintWriter(out);
        pw.print(out.toString());

        out.write(buffer, 0, buffer.length);
        out.flush();

        Thread thread = new Thread(new Runnable() {

            public void run() {
                try {
                    conn.getInputStream();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

}
