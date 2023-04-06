package sample;

import cn.signit.sdk.pojo.webhook.response.EnvelopeCompletedSucceed;
import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.EnterpriseVerificationSubmitted;
import cn.signit.sdk.pojo.webhook.response.WebhookResponse;
import cn.signit.sdk.type.WebhookEventType;
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

        String enteVerifyWebhookRespStr = "{\"event\":\"enterpriseVerificationSubmitted\",\"target\":{\"webhookWsid\":\"WSID_HOOK_000001678bc29d510242ac1400030001\",\"destination\":\"https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\"},\"rawData\":\"{\\\"code\\\":\\\"100550000\\\",\\\"message\\\":\\\"\\\\u8BF7\\\\u6C42\\\\u6210\\\\u529F\\\",\\\"customTag\\\":\\\"hello world legal People:https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\\\",\\\"invokeNo\\\":\\\"201812170944268608001716639001\\\",\\\"actionUrl\\\":\\\"http://10.10.9.67:61112/WSID_LINK_00000167b9d5fdbb4e4a988d68c80001/open-flow-enterprise-identity?token=1363dc721e5e47f4ab150880e146b9d1\\\",\\\"status\\\":\\\"INCOMPLETE\\\",\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";
        String appId = "1678bc2091000d861138f74aa51";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 1678bc2091000d861138f74aa51:le3SIT/QgHje/SZ047DIavKP8sP9weEQy58UFovYa5jzVupJzn9jgYU1nO93AYuyZSojdXiDb3cmltGxXN8S9Q==";
        // webhook响应header中x-signit-nonce
        String nonce = "2QzvVs0lP2oJ80SWjSi0VYK0";
        // webhook响应header中x-signit-date
        String dataString = "Mon Dec 17 09:44:28 CST 2018";
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
        // step3: 解析获取rawData
        switch (WebhookEventType.parse(ente.getEvent())) {
        // 企业实名认证提交
        case ENTERPRISE_VERIFICATION_SUBMITTED:
            // ps:rawData的命名方式为事件名称转换的大驼峰命名，数据所在包为cn.signit.sdk.pojo.webhook.response，其获取的2种方式如下：
            // 法1：
            EnterpriseVerificationSubmitted rawData1 = (EnterpriseVerificationSubmitted) ente.rawDataAsBean();
            boolean s = rawData1.isSuccess();
            // 法2：
            EnterpriseVerificationSubmitted rawDat2 = ente.rawDataAsBean(EnterpriseVerificationSubmitted.class);
            break;
        // 企业实名认证初级完成
        case ENTERPRISE_VERIFICATION_PRIMARY_COMPLETED:
            break;
            // 企业实名认证已打款
        case ENTERPRISE_VERIFICATION_PAID:
            break;
            // 企业实名认证完成
        case ENTERPRISE_VERIFICATION_COMPLETED:
            break;
            // 信封流程完成
        case ENVELOPE_COMPLETED:
            break;
            // 信封流程完成
        case ENVELOPE_STARTED:
            break;
            // 参与者确认
        case PARTICIPANT_CONFIRMED:
            break;
            // 参与者拒绝
        case PARTICIPANT_REJECTED:
            break;
            // 个人实名认证完成
        case PERSON_VERIFICATION_COMPLETED:
            break;
            case ENVELOPE_COMPLETED_SUCCEED:
                // ps:rawData的命名方式为事件名称转换的大驼峰命名，数据所在包为cn.signit.sdk.pojo.webhook.response，其获取的2种方式如下：
                // 法1：
                EnvelopeCompletedSucceed rawData12 = (EnvelopeCompletedSucceed) ente.rawDataAsBean();
                boolean isok = rawData12.isSuccess();
                System.out.println(isok);
                // 法2：
                EnvelopeCompletedSucceed rawDat13 = ente.rawDataAsBean(EnvelopeCompletedSucceed.class);
                String s1 = JSON.toJSONString(rawDat13.getThirdPartFieldsMetadata());
                System.out.println(s1);
        default:
            break;
        }

        System.out.println("\nwebhookResponse is :\n" + JSON.toJSONString(ente, true));
    }
}
