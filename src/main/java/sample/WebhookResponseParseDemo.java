package sample;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.AbstractWebhookResponseData;
import cn.signit.sdk.pojo.webhook.response.EnterpriseVerificationSubmitted;
import cn.signit.sdk.pojo.webhook.response.WebhookResponse;
import cn.signit.sdk.util.HmacSignatureBuilder;

/**
 * 易企签 Java SDK 解析Webhook响应的示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK解析Webhook响应，示例代码中的数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，appUrl均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class WebhookResponseParseDemo {
    public static void main(String[] args) {

        String enteVerifyWebhookRespStr = "{\"event\":\"enterpriseVerificationSubmitted\",\"target\":{\"webhookWsid\":\"WSID_HOOK_000001678bc29d510242ac1400030001\",\"destination\":\"https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\"},\"rawData\":\"{\\\"code\\\":\\\"100550000\\\",\\\"message\\\":\\\"\\\\u8BF7\\\\u6C42\\\\u6210\\\\u529F\\\",\\\"customTag\\\":\\\"hello world agent:https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\\\",\\\"invokeNo\\\":\\\"201812111620019293398044121001\\\",\\\"actionUrl\\\":\\\"http://10.10.9.67:61112/WSID_LINK_000001679c59fbd4aafd2531a35d0001/open-flow-enterprise-identity?token=43528582a1bb4659b33c8eee57c717cb\\\",\\\"status\\\":\\\"INCOMPLETE\\\",\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";
        String appId = "1678bc2091000d861138f74aa51";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 1678bc2091000d861138f74aa51:Xkx6X35mqC8kG16NOPtV7IX+1h1KsEdbbvTgYtXOUXojjjGd+1gP7tu4n/zD7AqiD4KoCFD4yEzoOwrPORj9zg==";
        // webhook响应header中x-signit-nonce
        String nonce = "yZIAsVFMzl1GTVoe0suis77p";
        // webhook响应header中x-signit-date
        String dataString = "Tue Dec 11 16:20:02 CST 2018";
        // webhook响应header中host
        String host = "webhook.site";

        // step1: 客户端验证服务端
        Boolean verifyResult = SignitClient.verify(signitSignature, appId, new HmacSignatureBuilder().scheme("https")
                .apiKey(appId)
                .apiSecret(appSecretKey.getBytes())
                .method("POST")
                .payload(enteVerifyWebhookRespStr.getBytes())
                .contentType("application/json")
                .host(host)
                .resource("")
                .nonce(nonce)
                .date(dataString));
        System.out.println("\n\nthe result of hmac verify is " + verifyResult);

        // step2: 解析webhook响应数据
        WebhookResponse ente = SignitClient.parseWebhookResponse(enteVerifyWebhookRespStr);

        // ps:rawData 获取的2种方式

        // 法1：
        AbstractWebhookResponseData rawData1 = ente.rawDataAsBean();

        // 法2：
        if (ente.getEvent()
                .equals("enterpriseVerificationSubmitted")) {
            EnterpriseVerificationSubmitted rawData = ente.rawDataAsBean(EnterpriseVerificationSubmitted.class);
        }

        System.out.println("\nwebhookResponse is :\n" + JSON.toJSONString(ente, true));
    }

}
