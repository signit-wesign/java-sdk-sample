package webhook.response;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.ParticipantRejected;
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
public class ParseParticipantRejectedDemo {
    public static void main(String[] args) {
        String personVerifyWebhookRespStr = "{\"event\":\"participantRejected\",\"target\":{\"webhookWsid\":\"WSID_HOOK_000001678bc29d510242ac1400030001\",\"destination\":\"https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06\"},\"rawData\":\"{\\\"code\\\":\\\"100600000\\\",\\\"message\\\":\\\"\\\\u64CD\\\\u4F5C\\\\u6210\\\\u529F\\\",\\\"actionUrl\\\":\\\"http://10.10.9.67:61112/WSID_LINK_0000016878b6004f22c91cdc09520001/open-flow-envelope?token=03875e09d6b94c6098c4b3bf42e8e13b\\\",\\\"actions\\\":[\\\"CHECK\\\",\\\"SIGN\\\",\\\"VIEW\\\"],\\\"account\\\":\\\"18380581554\\\",\\\"customTag\\\":\\\"C130018122503922\\\",\\\"invokeNo\\\":\\\"201901231114463824001724586001\\\",\\\"basicEnvelope\\\":{\\\"wsid\\\":\\\"WSID_ENVE_0000016878b329d1e294b6c7f50d0001\\\",\\\"status\\\":\\\"ED_FAIL_REJECT\\\",\\\"createdDatetime\\\":1548213287000,\\\"expireDatetime\\\":1579749287000,\\\"statusDatetime\\\":1548213420000,\\\"statusReason\\\":\\\"\\\\u6D4B\\\\u8BD5\\\",\\\"currentSequence\\\":1},\\\"senderParticipant\\\":{\\\"name\\\":\\\"\\\\u5218\\\\u6E05\\\\u534E\\\",\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"email\\\":\\\"\\\",\\\"sms\\\":\\\"18681695956\\\"}]}},\\\"receiverParticipant\\\":{\\\"name\\\":\\\"\\\\u5F20\\\\u6CE2\\\",\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"sms\\\":\\\"18380581554\\\"}]},\\\"secureLevel\\\":\\\"DISPOSABLE_CERT\\\",\\\"type\\\":\\\"SIGNER\\\",\\\"roleType\\\":\\\"PERSON\\\",\\\"needForm\\\":false,\\\"assignedSequence\\\":1,\\\"authLevel\\\":\\\"0\\\",\\\"metadata\\\":\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"position\\\\\\\":0,\\\\\\\"ip\\\\\\\":\\\\\\\"::ffff:10.10.9.198\\\\\\\"}\\\",\\\"status\\\":\\\"ED_FAIL_REJECT\\\",\\\"wsid\\\":\\\"WSID_EPAR_0000016878b3f11200d8611392780001\\\",\\\"handleDatetime\\\":1548213420000,\\\"handleReason\\\":\\\"\\\\u6D4B\\\\u8BD5\\\"},\\\"returnUrl\\\":\\\"https://return.qq.com/XXXX\\\",\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";
        String appId = "1678bc2091000d861138f74aa51";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 1678bc2091000d861138f74aa51:vUSY5Qg5TPiknqXvdAek72SFIkmfr96zmkG7opWnCiT1d+4H87RlU0bUThZ6SDkmwtCr0vs+Rk57LAou5PAI/w==";
        // webhook响应header中x-signit-nonce
        String nonce = "9Im4NH96U68flPP29TjHeKsn";
        // webhook响应header中x-signit-date
        String dataString = "Wed Jan 23 11:17:00 CST 2019";
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
            // ps:rawData的命名方式为事件名称转换的大驼峰命名，数据所在包为cn.signit.sdk.pojo.webhook.response，其获取的2种方式如下：
            ParticipantRejected rawData3 = (ParticipantRejected) ente.rawDataAsBean();
            boolean s3 = rawData3.isSuccess();
            // 法2：
            ParticipantRejected rawDat4 = ente.rawDataAsBean(ParticipantRejected.class);
            System.out.println("\nwebhookResponse rawData is :\n" + JSON.toJSONString(rawData3, true));
            break;
        // 个人实名认证完成
        case PERSON_VERIFICATION_COMPLETED:
            break;
        default:
            break;
        }

    }
}
