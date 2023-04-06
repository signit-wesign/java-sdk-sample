package sample;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @SpringBootApplication(scanBasePackageClasses = sample.WebhookResponseParseByRequestDemo.Application.class)
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
        public void testVerifyAndParseWebhookResponse(HttpServletRequest request, @RequestBody String body)
                throws IOException {

            String appId = "1678bc2091000d861138f74aa51";
            String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";

            // step1 : 客户端验证服务端
            boolean verifyResult = SignitClient.verify(appId, appSecretKey, body.getBytes(), request);

            System.out.println("\n\nthe result of hmac verify is " + verifyResult);

            // step2: 解析webhook响应数据
            WebhookResponse ente = SignitClient.parseWebhookResponse(body);
            System.out.println("\nwebhookResponse is :\n" + JSON.toJSONString(ente, true));

        }
        
        // 由于request中body数据流只能打开一次，如果涉及多次需要取出使用的场景不合适，故而可以使用@RequestBody方式获取webhook响应数据流
        @PostMapping("test/parse-webhook-withbody")
        public void testVerifyAndParseWebhookResponse(HttpServletRequest request, @RequestBody byte[] body)
                throws IOException {
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
        
        @GetMapping("test-body")
        public String test2() throws IOException {
            sendHttpRequest2();
            return "send success";
        }

    }

    // 模拟易企签 发送给客户端 webhook响应数据
    public static void sendHttpRequest() throws IOException {

        String enteVerifyWebhookRespStr = "{\"event\":\"participantRejected\",\"target\":{\"webhookWsid\":\"WSID_HOOK_000001678bc29d510242ac1400030001\",\"destination\":\"https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\"},\"rawData\":\"{\\\"code\\\":\\\"100600000\\\",\\\"message\\\":\\\"\\\\u64CD\\\\u4F5C\\\\u6210\\\\u529F\\\",\\\"actionUrl\\\":\\\"http://10.10.9.67:61112/WSID_LINK_0000016878b6004f22c91cdc09520001/open-flow-envelope?token=03875e09d6b94c6098c4b3bf42e8e13b\\\",\\\"actions\\\":[\\\"CHECK\\\",\\\"SIGN\\\",\\\"VIEW\\\"],\\\"account\\\":\\\"18380581554\\\",\\\"customTag\\\":\\\"C130018122503922\\\",\\\"invokeNo\\\":\\\"201901231114463824001724586001\\\",\\\"basicEnvelope\\\":{\\\"wsid\\\":\\\"WSID_ENVE_0000016878b329d1e294b6c7f50d0001\\\",\\\"status\\\":\\\"ED_FAIL_REJECT\\\",\\\"createdDatetime\\\":1548213287000,\\\"expireDatetime\\\":1579749287000,\\\"statusDatetime\\\":1548213420000,\\\"statusReason\\\":\\\"\\\\u6D4B\\\\u8BD5\\\",\\\"currentSequence\\\":1},\\\"senderParticipant\\\":{\\\"name\\\":\\\"\\\\u5218\\\\u6E05\\\\u534E\\\",\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"email\\\":\\\"\\\",\\\"sms\\\":\\\"18681695956\\\"}]}},\\\"receiverParticipant\\\":{\\\"name\\\":\\\"\\\\u5F20\\\\u6CE2\\\",\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"sms\\\":\\\"18380581554\\\"}]},\\\"secureLevel\\\":\\\"DISPOSABLE_CERT\\\",\\\"type\\\":\\\"SIGNER\\\",\\\"roleType\\\":\\\"PERSON\\\",\\\"needForm\\\":false,\\\"assignedSequence\\\":1,\\\"authLevel\\\":\\\"0\\\",\\\"metadata\\\":\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"position\\\\\\\":0,\\\\\\\"ip\\\\\\\":\\\\\\\"::ffff:10.10.9.198\\\\\\\"}\\\",\\\"status\\\":\\\"ED_FAIL_REJECT\\\",\\\"wsid\\\":\\\"WSID_EPAR_0000016878b3f11200d8611392780001\\\",\\\"handleDatetime\\\":1548213420000,\\\"handleReason\\\":\\\"\\\\u6D4B\\\\u8BD5\\\"},\\\"returnUrl\\\":\\\"https://return.qq.com/XXXX\\\",\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";
        String appId = "1678bc2091000d861138f74aa51";
        String nonce = "BbYJtgX11vhu2oxQ3diaMgl2";
        String dateString = "Wed Jan 23 11:17:31 CST 2019";
        String host = "localhost:8080";
        String scheme = "http";

        String signitSignature = null;// "HmacSHA512
                                      // 1678bc2091000d861138f74aa51:sYurqlP8S2qCy8bvjE1hxAfi361qVZt5ZmaCreYqfy0FciVzNz8q/pPVQcrd1kPGqmB7beDh1f2NVzlwjfbDlw==";
        HmacSignatureBuilder builder = new HmacSignatureBuilder();
        builder.scheme(scheme)
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

        // 通过java发起请求时不允许重写标准头的，但是此处为了保证解析时动态获取的host是webhook.site，就使用此方法。
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        URL url = new URL("http://localhost:8080/test/parse-webhook");
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("X-Signit-Signature", signitSignature);
        conn.setRequestProperty("X-Signit-Nonce", nonce);
        conn.setRequestProperty("X-Signit-Date", dateString);
        conn.setRequestProperty("X-Signit-Event", "PARTICIPANT_REJECTED");
        conn.setRequestProperty("X-Signit-Resource", "");
        conn.setRequestProperty("User-Agent", "Signit HTTP");
        conn.setRequestProperty("X-Signit-Host", host);
        conn.setRequestProperty("X-Signit-Scheme", scheme);
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

    
    // 模拟易企签 发送给客户端 webhook响应数据
    public static void sendHttpRequest2() throws IOException {
        String enteVerifyWebhookRespStr = "{\"event\":\"participantRejected\",\"target\":{\"webhookWsid\":\"WSID_HOOK_000001678bc29d510242ac1400030001\",\"destination\":\"https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\"},\"rawData\":\"{\\\"code\\\":\\\"100600000\\\",\\\"message\\\":\\\"\\\\u64CD\\\\u4F5C\\\\u6210\\\\u529F\\\",\\\"actionUrl\\\":\\\"http://10.10.9.67:61112/WSID_LINK_0000016878b6004f22c91cdc09520001/open-flow-envelope?token=03875e09d6b94c6098c4b3bf42e8e13b\\\",\\\"actions\\\":[\\\"CHECK\\\",\\\"SIGN\\\",\\\"VIEW\\\"],\\\"account\\\":\\\"18380581554\\\",\\\"customTag\\\":\\\"C130018122503922\\\",\\\"invokeNo\\\":\\\"201901231114463824001724586001\\\",\\\"basicEnvelope\\\":{\\\"wsid\\\":\\\"WSID_ENVE_0000016878b329d1e294b6c7f50d0001\\\",\\\"status\\\":\\\"ED_FAIL_REJECT\\\",\\\"createdDatetime\\\":1548213287000,\\\"expireDatetime\\\":1579749287000,\\\"statusDatetime\\\":1548213420000,\\\"statusReason\\\":\\\"\\\\u6D4B\\\\u8BD5\\\",\\\"currentSequence\\\":1},\\\"senderParticipant\\\":{\\\"name\\\":\\\"\\\\u5218\\\\u6E05\\\\u534E\\\",\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"email\\\":\\\"\\\",\\\"sms\\\":\\\"18681695956\\\"}]}},\\\"receiverParticipant\\\":{\\\"name\\\":\\\"\\\\u5F20\\\\u6CE2\\\",\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"sms\\\":\\\"18380581554\\\"}]},\\\"secureLevel\\\":\\\"DISPOSABLE_CERT\\\",\\\"type\\\":\\\"SIGNER\\\",\\\"roleType\\\":\\\"PERSON\\\",\\\"needForm\\\":false,\\\"assignedSequence\\\":1,\\\"authLevel\\\":\\\"0\\\",\\\"metadata\\\":\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"position\\\\\\\":0,\\\\\\\"ip\\\\\\\":\\\\\\\"::ffff:10.10.9.198\\\\\\\"}\\\",\\\"status\\\":\\\"ED_FAIL_REJECT\\\",\\\"wsid\\\":\\\"WSID_EPAR_0000016878b3f11200d8611392780001\\\",\\\"handleDatetime\\\":1548213420000,\\\"handleReason\\\":\\\"\\\\u6D4B\\\\u8BD5\\\"},\\\"returnUrl\\\":\\\"https://return.qq.com/XXXX\\\",\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";
        String appId = "1678bc2091000d861138f74aa51";
        String nonce = "BbYJtgX11vhu2oxQ3diaMgl2";
        String dateString = "Wed Jan 23 11:17:31 CST 2019";
        String host = "localhost:8080";
        String scheme = "http";

        String signitSignature = null;// "HmacSHA512
                                      // 1678bc2091000d861138f74aa51:sYurqlP8S2qCy8bvjE1hxAfi361qVZt5ZmaCreYqfy0FciVzNz8q/pPVQcrd1kPGqmB7beDh1f2NVzlwjfbDlw==";
        HmacSignatureBuilder builder = new HmacSignatureBuilder();
        builder.scheme(scheme)
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

        URL url = new URL("http://localhost:8080/test/parse-webhook-withbody");
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("X-Signit-Signature", signitSignature);
        conn.setRequestProperty("X-Signit-Nonce", nonce);
        conn.setRequestProperty("X-Signit-Date", dateString);
        conn.setRequestProperty("X-Signit-Event", "PARTICIPANT_REJECTED");
        conn.setRequestProperty("X-Signit-Resource", "");
        conn.setRequestProperty("User-Agent", "Signit HTTP");
        conn.setRequestProperty("X-Signit-Host", host);
        conn.setRequestProperty("X-Signit-Scheme", scheme);
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
