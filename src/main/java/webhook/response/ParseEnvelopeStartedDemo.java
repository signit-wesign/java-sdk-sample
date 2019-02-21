package webhook.response;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.EnvelopeStarted;
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
public class ParseEnvelopeStartedDemo {
    public static void main(String[] args) {
        String personVerifyWebhookRespStr = "{\"event\":\"envelopeStarted\",\"target\":{\"webhookWsid\":\"WSID_HOOK_000001678bc29d510242ac1400030001\",\"destination\":\"https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\"},\"rawData\":\"{\\\"code\\\":\\\"100600000\\\",\\\"message\\\":\\\"\\\\u64CD\\\\u4F5C\\\\u6210\\\\u529F\\\",\\\"actionUrl\\\":\\\"http://10.10.9.67:61112/WSID_LINK_00000168731df5c822c91cdc09520001/open-flow-envelope?token=7d81984467ea4c62b0183607aacf4e98\\\",\\\"actions\\\":[\\\"CHECK\\\",\\\"SIGN\\\",\\\"VIEW\\\"],\\\"account\\\":\\\"18681695956\\\",\\\"customTag\\\":\\\"C130018122503922\\\",\\\"invokeNo\\\":\\\"201901220912463824001721298001\\\",\\\"basicEnvelope\\\":{\\\"wsid\\\":\\\"WSID_ENVE_00000168731d1c87e294b6c7f50d0001\\\",\\\"status\\\":\\\"ING_SENT_DILIVER\\\",\\\"createdDatetime\\\":1548119567000,\\\"expireDatetime\\\":1579655567000,\\\"statusDatetime\\\":1548119570000,\\\"currentSequence\\\":1},\\\"senderParticipant\\\":{\\\"name\\\":\\\"\\\\u5218\\\\u6E05\\\\u534E\\\",\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"email\\\":\\\"\\\",\\\"sms\\\":\\\"18681695956\\\"}]}},\\\"receiverParticipant\\\":{\\\"name\\\":\\\"\\\\u5218\\\\u6E05\\\\u534E\\\",\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"sms\\\":\\\"18681695956\\\"}]},\\\"secureLevel\\\":\\\"DISPOSABLE_CERT\\\",\\\"type\\\":\\\"SIGNER\\\",\\\"roleType\\\":\\\"PERSON\\\",\\\"needForm\\\":false,\\\"assignedSequence\\\":1,\\\"authLevel\\\":\\\"0\\\",\\\"metadata\\\":\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"position\\\\\\\":0}\\\",\\\"status\\\":\\\"ING_WAIT\\\",\\\"wsid\\\":\\\"WSID_EPAR_00000168731de45300d8611392780001\\\"},\\\"returnUrl\\\":\\\"https://return.qq.com/XXXX\\\",\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";
        String appId = "1678bc2091000d861138f74aa51";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 1678bc2091000d861138f74aa51:6m9GEhWkzrHiuU+PUsg39+DRgntpel5rPd1erFp5WzaeKXx7S5bgv93AT/EBFuwByx+JjPjF3iCabLyz3utwuw==";
        // webhook响应header中x-signit-nonce
        String nonce = "95V7bqCg48T0XJ9jK373CDHL";
        // webhook响应header中x-signit-date
        String dataString = "Tue Jan 22 09:12:49 CST 2019";
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
            // ps:rawData的命名方式为事件名称转换的大驼峰命名，数据所在包为cn.signit.sdk.pojo.webhook.response，其获取的2种方式如下：
            EnvelopeStarted rawData3 = (EnvelopeStarted) ente.rawDataAsBean();
            boolean s3 = rawData3.isSuccess();
            // 法2：
            EnvelopeStarted rawDat4 = ente.rawDataAsBean(EnvelopeStarted.class);
            System.out.println("\nwebhookResponse rawData is :\n" + JSON.toJSONString(rawData3, true));
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
        default:
            break;
        }

    }
}
