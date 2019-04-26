package webhook.response;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.EnvelopeCompleted;
import cn.signit.sdk.pojo.webhook.response.EnvelopeStarted;
import cn.signit.sdk.pojo.webhook.response.WebhookResponse;
import cn.signit.sdk.type.WebhookEventType;
import cn.signit.sdk.util.HmacSignatureBuilder;

public class ParseEnvelopeCompletedDemo {
    public static void main(String[] args) {
        String personVerifyWebhookRespStr = "{\"event\":\"envelopeCompleted\",\"target\":{\"webhookWsid\":\"WSID_HOOK_000001678bc29d510242ac1400030001\",\"destination\":\"https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\"},\"rawData\":\"{\\\"code\\\":\\\"100600000\\\",\\\"message\\\":\\\"\\\\u64CD\\\\u4F5C\\\\u6210\\\\u529F\\\",\\\"actionUrl\\\":\\\"http://10.10.9.67:61112/WSID_LINK_00000168731f82d622c91cdc09520001/open-flow-envelope?token=ce2bc47daef5470d8b0bb9ff6e0f7a54\\\",\\\"customTag\\\":\\\"C130018122503922\\\",\\\"invokeNo\\\":\\\"201901220912463824001721298001\\\",\\\"basicEnvelope\\\":{\\\"wsid\\\":\\\"WSID_ENVE_00000168731d1c87e294b6c7f50d0001\\\",\\\"status\\\":\\\"ED_SUCCESS\\\",\\\"createdDatetime\\\":1548119567000,\\\"expireDatetime\\\":1579655567000,\\\"statusDatetime\\\":1548119610000,\\\"statusReason\\\":\\\"\\\\u4FE1\\\\u5C01\\\\u4E2D\\\\u6240\\\\u6709\\\\u53C2\\\\u4E0E\\\\u8005\\\\u5747\\\\u5DF2\\\\u6210\\\\u529F\\\\u5B8C\\\\u6210,\\\\u7B49\\\\u5F85\\\\u786E\\\\u8BA4\\\\u5B8C\\\\u6210\\\",\\\"currentSequence\\\":2},\\\"signData\\\":{\\\"url\\\":\\\"http://10.10.9.67:61112/WSID_LINK_00000168731f82a222c91cdc09520001/download-file?token=2145c1217ad64160a14fa274ad933e64\\\"},\\\"returnUrl\\\":\\\"https://return.qq.com/XXXX\\\",\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";
        String appId = "1678bc2091000d861138f74aa51";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 1678bc2091000d861138f74aa51:5p2aQxrHhS9HYCuIotr3qBHerQhnBjAFduLlLENyNenbDaeesTUGMxqjyQ+yLzMlWPludXurSMbHJVHDZLRboQ==";
        // webhook响应header中x-signit-nonce
        String nonce = "0R2mpA5f06UepFCw2XYGv4h2";
        // webhook响应header中x-signit-date
        String dataString = "Tue Jan 22 09:14:31 CST 2019";
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
            // ps:rawData的命名方式为事件名称转换的大驼峰命名，数据所在包为cn.signit.sdk.pojo.webhook.response，其获取的2种方式如下：
            EnvelopeCompleted rawData3 = (EnvelopeCompleted) ente.rawDataAsBean();
            boolean s3 = rawData3.isSuccess();
            // 法2：
            EnvelopeCompleted rawDat4 = ente.rawDataAsBean(EnvelopeCompleted.class);
            System.out.println("\nwebhookResponse rawData is :\n" + JSON.toJSONString(rawData3, true));
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
        //个人实名认证提交
        case PERSON_VERIFICATION_SUBMITTED:
            break;
        // 个人实名认证完成
        case PERSON_VERIFICATION_COMPLETED:
            break;
        // 参与者正在处理信封
        case PARTICIPANT_HANDLING:
            break;
        default:
            break;
        }

    }
}
