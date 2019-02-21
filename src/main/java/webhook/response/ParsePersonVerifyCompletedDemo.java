package webhook.response;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.PersonVerificationCompleted;
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
public class ParsePersonVerifyCompletedDemo {
    public static void main(String[] args) {

        String personVerifyWebhookRespStr = "{\"event\":\"personVerificationCompleted\",\"target\":{\"webhookWsid\":\"WSID_HOOK_000001678bc29d510242ac1400030001\",\"destination\":\"https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\"},\"rawData\":\"{\\\"code\\\":\\\"100550000\\\",\\\"message\\\":\\\"\\\\u8BF7\\\\u6C42\\\\u6210\\\\u529F\\\",\\\"customTag\\\":\\\"hello world legal People:https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\\\",\\\"invokeNo\\\":\\\"201812201444089293130520594001\\\",\\\"status\\\":\\\"DENY\\\",\\\"description\\\":\\\"\\\\u8EAB\\\\u4EFD\\\\u8BC1\\\\u53F7\\\\u7801\\\\u6216\\\\u59D3\\\\u540D\\\\u4E0D\\\\u6B63\\\\u786E\\\\uFF0C\\\\u8BF7\\\\u786E\\\\u8BA4\\\\u540E\\\\u91CD\\\\u65B0\\\\u63D0\\\\u4EA4\\\",\\\"submitDatetime\\\":1545288343,\\\"handleDatetime\\\":1545288411,\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";
        String appId = "1678bc2091000d861138f74aa51";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 1678bc2091000d861138f74aa51:cexuoQx/N+Cp29qzrYTLR6osTWZRZF3Q7g5jMbY35NbagnxrzOvWkrI4Z25sAwtni9vtCyIedT6shFnfBiSOrA==";
        // webhook响应header中x-signit-nonce
        String nonce = "Sb09WuHKSBaa0Gwx10A1w2ke";
        // webhook响应header中x-signit-date
        String dataString = "Thu Dec 20 14:46:51 CST 2018";
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
            // ps:rawData的命名方式为事件名称转换的大驼峰命名，数据所在包为cn.signit.sdk.pojo.webhook.response，其获取的2种方式如下：
            PersonVerificationCompleted rawData3 = (PersonVerificationCompleted) ente.rawDataAsBean();
            boolean s3 = rawData3.isSuccess();
            // 法2：
            PersonVerificationCompleted rawDat4 = ente.rawDataAsBean(PersonVerificationCompleted.class);
            System.out.println("\nwebhookResponse rawData is :\n" + JSON.toJSONString(rawData3, true));
            break;
        default:
            break;
        }

    }
}
