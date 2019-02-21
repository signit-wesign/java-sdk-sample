package webhook.response;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.EnterpriseVerificationPrimaryCompleted;
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
public class ParseEnterpriseVerificationPrimaryCompletedDemo {
    public static void main(String[] args) {

        String personVerifyWebhookRespStr = "{\"event\":\"enterpriseVerificationPrimaryCompleted\",\"target\":{\"webhookWsid\":\"WSID_HOOK_000001678bc29d510242ac1400030001\",\"destination\":\"https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\"},\"rawData\":\"{\\\"code\\\":\\\"100550000\\\",\\\"message\\\":\\\"\\\\u8BF7\\\\u6C42\\\\u6210\\\\u529F\\\",\\\"customTag\\\":\\\"hello world legal People:https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\\\",\\\"invokeNo\\\":\\\"201812231139168608001765203001\\\",\\\"status\\\":\\\"WAITING_SUM\\\",\\\"description\\\":\\\"\\\\u65E0\\\",\\\"submitDatetime\\\":1545536383,\\\"handleDatetime\\\":1545746821,\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";
        String appId = "1678bc2091000d861138f74aa51";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 1678bc2091000d861138f74aa51:jCH3RI/HyDFBkdQbMz0KACd79dX3Gwkf7WSqFg+8TYV93eQ0k7Dnwqy95wDVrqfQbrIqe3MEwTUvWX/14WAb1w==";
        // webhook响应header中x-signit-nonce
        String nonce = "EEenEhg70ZkvPx7l5896rMb1";
        // webhook响应header中x-signit-date
        String dataString = "Tue Dec 25 22:07:02 CST 2018";
        // webhook响应header中host
        String host = "webhook.site";

        // step1: 客户端验证服务端
        Boolean verifyResult = SignitClient.verify(signitSignature, appId, new HmacSignatureBuilder().scheme("https")
                .apiKey(appId)
                .apiSecret(appSecretKey.getBytes())
                .method("POST")
                .payload(personVerifyWebhookRespStr.getBytes())
                .contentType("application/json")
                .host(host)
                .resource("")
                .nonce(nonce)
                .date(dataString));
        System.out.println("\n\nthe result of hmac verify is " + verifyResult);

        // step2: 解析webhook响应数据
        WebhookResponse ente = SignitClient.parseWebhookResponse(personVerifyWebhookRespStr);
        System.out.println("\nwebhookResponse is :\n" + JSON.toJSONString(ente, true));

        // step3: 解析获取rawData
        switch (WebhookEventType.parse(ente.getEvent())) {
        // 企业实名认证提交
        case ENTERPRISE_VERIFICATION_SUBMITTED:
            break;
        // 企业实名认证初级完成
        case ENTERPRISE_VERIFICATION_PRIMARY_COMPLETED:
            // ps:rawData的命名方式为事件名称转换的大驼峰命名，数据所在包为cn.signit.sdk.pojo.webhook.response，其获取的2种方式如下：
            // 法1：
            EnterpriseVerificationPrimaryCompleted rawData1 = (EnterpriseVerificationPrimaryCompleted) ente
                    .rawDataAsBean();
            boolean s = rawData1.isSuccess();
            // 法2：
            EnterpriseVerificationPrimaryCompleted rawDat2 = ente
                    .rawDataAsBean(EnterpriseVerificationPrimaryCompleted.class);
            System.out.println("\nwebhookResponse rawData is :\n" + JSON.toJSONString(rawData1, true));
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
        // 信封流程启动
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
        default:
            break;
        }

    }
}
