package webhook.response;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.ParticipantHandling;
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
public class ParseParticipantHandlingDemo {
    public static void main(String[] args) {

        String personVerifyWebhookRespStr = "{\"event\":\"participantHandling\",\"target\":{\"webhookWsid\":\"WSID_HOOK_0000016903bfc15402428f6168060001\",\"destination\":\"https://webhook.site/eda20c6f-119b-4f3a-93d9-b2c71dd9e254\"},\"rawData\":\"{\\\"code\\\":\\\"100600000\\\",\\\"message\\\":\\\"\\u64CD\\u4F5C\\u6210\\u529F\\\",\\\"actionUrl\\\":\\\"http://10.10.9.67:61112/WSID_LINK_000001692d93acf97248cd3800560001/open-flow-envelope?token=d25ef303970a4c86a6704b082482b0e9\\\",\\\"actions\\\":[\\\"CHECK\\\",\\\"SIGN\\\",\\\"VIEW\\\"],\\\"account\\\":\\\"18681695956\\\",\\\"customTag\\\":\\\"C1300181225039241551247829568\\\",\\\"invokeNo\\\":\\\"201902271410291256001691188001\\\",\\\"basicEnvelope\\\":{\\\"wsid\\\":\\\"WSID_ENVE_000001692d9275d1463f72514a040001\\\",\\\"senderWsid\\\":\\\"WSID_EMEM_00000169034335a26e87cee8ed000002\\\",\\\"senderName\\\":\\\"\\u5218\\u6E05\\u534E\\u7684\\u6D4B\\u8BD5\\u4F01\\u4E1A,\\u5218\\u6E05\\u534E\\\",\\\"type\\\":\\\"ANY\\\",\\\"title\\\":\\\"\\u542F\\u52A8\\u4FE1\\u5C01\\u6D4B\\u8BD5\\uFF1A\\u4F7F\\u7528\\u9A91\\u7F1D\\u7AE0\\u8868\\u5355\\\",\\\"subject\\\":\\\"subject\\\",\\\"status\\\":\\\"ING_SENT_WAIT_FILLOUT\\\",\\\"statusDatetime\\\":1551247845000,\\\"statusReason\\\":null,\\\"createdDatetime\\\":1551247830000,\\\"modifiedDatetime\\\":1551247845000,\\\"sendDatetime\\\":1551247834000,\\\"expireDatetime\\\":1582783830000,\\\"finishedDatetime\\\":null,\\\"currentSequence\\\":2,\\\"templateWsid\\\":null,\\\"multisendNum\\\":null,\\\"authLevel\\\":\\\"LOW\\\"},\\\"senderParticipant\\\":{\\\"wsid\\\":\\\"WSID_EPAR_000001692d9275d4463f72514a040001\\\",\\\"envelopeWsid\\\":\\\"WSID_ENVE_000001692d9275d1463f72514a040001\\\",\\\"authorWsid\\\":\\\"WSID_EMEM_00000169034335a26e87cee8ed000002\\\",\\\"name\\\":\\\"\\u5218\\u6E05\\u534E\\\",\\\"type\\\":\\\"SENDER\\\",\\\"secureLevel\\\":null,\\\"status\\\":null,\\\"receiveNotificationStatus\\\":null,\\\"assignedMeessage\\\":null,\\\"assignedSequence\\\":0,\\\"viewDatetime\\\":null,\\\"handleDatetime\\\":1551247830000,\\\"handleReason\\\":null,\\\"authorizerWsid\\\":null,\\\"envelopeDisplay\\\":\\\"VISIBLE\\\",\\\"sequenceSensitive\\\":true,\\\"enterpriseWsid\\\":\\\"WSID_ENTE_000001690343359c6e87cee8ed000001\\\",\\\"enterpriseName\\\":\\\"\\u5218\\u6E05\\u534E\\u7684\\u6D4B\\u8BD5\\u4F01\\u4E1A\\\",\\\"alias\\\":null,\\\"corrected\\\":false,\\\"needForm\\\":false,\\\"roleType\\\":\\\"ENTERPRISE_MEMBER\\\",\\\"createdDatetime\\\":1551247830000,\\\"modifiedDatetime\\\":1551247767000,\\\"multisendNum\\\":null,\\\"authLevel\\\":null,\\\"authType\\\":null,\\\"handleMode\\\":null,\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"authorWsid\\\":null,\\\"email\\\":\\\"\\\",\\\"sms\\\":\\\"18681695956\\\"}]},\\\"secureMetadata\\\":null,\\\"metadata\\\":\\\"{\\\\\\\"groups\\\\\\\":[{\\\\\\\"id\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"position\\\\\\\":1,\\\\\\\"groupName\\\\\\\":\\\\\\\"\\u4F01\\u4E1A\\u5185\\u90E8\\u6D41\\u7A0B:\\u5218\\u6E05\\u534E\\u7684\\u6D4B\\u8BD5\\u4F01\\u4E1A\\\\\\\",\\\\\\\"type\\\\\\\":\\\\\\\"ENTERPRISE_INSIDE\\\\\\\",\\\\\\\"enterpriseName\\\\\\\":\\\\\\\"\\u5218\\u6E05\\u534E\\u7684\\u6D4B\\u8BD5\\u4F01\\u4E1A\\\\\\\",\\\\\\\"enterpriseWsid\\\\\\\":\\\\\\\"WSID_ENTE_000001690343359c6e87cee8ed000001\\\\\\\"}],\\\\\\\"order\\\\\\\":true,\\\\\\\"ip\\\\\\\":\\\\\\\"10.10.9.198\\\\\\\"}\\\"},\\\"receiverParticipant\\\":{\\\"wsid\\\":\\\"WSID_EPAR_000001692d928414463f72514a040001\\\",\\\"envelopeWsid\\\":\\\"WSID_ENVE_000001692d9275d1463f72514a040001\\\",\\\"authorWsid\\\":\\\"WSID_EMEM_00000169034335a26e87cee8ed000002\\\",\\\"name\\\":\\\"\\u5218\\u6E05\\u534E\\\",\\\"type\\\":\\\"SIGNER\\\",\\\"secureLevel\\\":\\\"DISPOSABLE_CERT\\\",\\\"status\\\":\\\"ING_WAIT\\\",\\\"receiveNotificationStatus\\\":null,\\\"assignedMeessage\\\":null,\\\"assignedSequence\\\":2,\\\"viewDatetime\\\":null,\\\"handleDatetime\\\":null,\\\"handleReason\\\":null,\\\"authorizerWsid\\\":null,\\\"envelopeDisplay\\\":\\\"VISIBLE\\\",\\\"sequenceSensitive\\\":null,\\\"enterpriseWsid\\\":\\\"WSID_ENTE_000001690343359c6e87cee8ed000001\\\",\\\"enterpriseName\\\":\\\"\\u5218\\u6E05\\u534E\\u7684\\u6D4B\\u8BD5\\u4F01\\u4E1A\\\",\\\"alias\\\":null,\\\"corrected\\\":false,\\\"needForm\\\":null,\\\"roleType\\\":\\\"ENTERPRISE_MEMBER\\\",\\\"createdDatetime\\\":1551247771000,\\\"modifiedDatetime\\\":1551247771000,\\\"multisendNum\\\":null,\\\"authLevel\\\":\\\"LOW\\\",\\\"authType\\\":null,\\\"handleMode\\\":\\\"NORMAL\\\",\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"authorWsid\\\":null,\\\"email\\\":null,\\\"sms\\\":\\\"18681695956\\\"}]},\\\"secureMetadata\\\":null,\\\"metadata\\\":\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"groupId\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"position\\\\\\\":0}\\\"},\\\"signData\\\":null,\\\"returnUrl\\\":\\\"https://www.baidu.com\\\",\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk9d79a9dc5bb91e02a320b50ab78dd43b";
        String appId = "16903be96b002428f616806f5c1";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 16903be96b002428f616806f5c1:rAE24tO38NAFV5QsuFNq4VK66cSYInmC0hmiy/MmBGJjJlDu0QffIXMD/02N+oXM+M78y7drLFKovPA9QRUhWg==";
        // webhook响应header中x-signit-nonce
        String nonce = "36kBh31WsgXjF5AOy35aFHlW";
        // webhook响应header中x-signit-date
        String dataString = "Wed Feb 27 14:10:46 CST 2019";
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
        //个人实名认证提交
        case PERSON_VERIFICATION_SUBMITTED:
            break;
        // 个人实名认证完成
        case PERSON_VERIFICATION_COMPLETED:
            break;
        // 参与者正在处理信封
        case PARTICIPANT_HANDLING:
            // ps:rawData的命名方式为事件名称转换的大驼峰命名，数据所在包为cn.signit.sdk.pojo.webhook.response，其获取的2种方式如下：
            ParticipantHandling rawData1 = (ParticipantHandling) ente.rawDataAsBean();
            boolean s = rawData1.isSuccess();
            // 法2：
            ParticipantHandling rawDat2 = ente.rawDataAsBean(ParticipantHandling.class);
            System.out.println("\nwebhookResponse rawData is :\n" + JSON.toJSONString(rawDat2, true));
            break;
        //快捷签署完成事件
        case QUICK_SIGN_COMPLETED:
            break;
        default:
            break;
        }

    }
}
