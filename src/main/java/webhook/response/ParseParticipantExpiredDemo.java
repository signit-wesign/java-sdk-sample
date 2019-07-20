package webhook.response;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.ParticipantExpired;
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
public class ParseParticipantExpiredDemo {
    public static void main(String[] args) {

        String personVerifyWebhookRespStr = "{\"event\":\"participantExpired\",\"target\":{\"webhookWsid\":\"WSID_HOOK_0000016a543d37463eebbcfde3dd0001\",\"destination\":\"http://112.44.251.136:8084/52d1bd58-a6f2-4cf6-93aa-52bdccb8da7c\"},\"rawData\":\"{\\\"code\\\":\\\"100600000\\\",\\\"message\\\":\\\"\\u64CD\\u4F5C\\u6210\\u529F\\\",\\\"actionUrl\\\":null,\\\"actions\\\":null,\\\"account\\\":\\\"18380581554\\\",\\\"customTag\\\":\\\"C130018122503922\\\",\\\"invokeNo\\\":\\\"201907201553375554001775334001\\\",\\\"basicEnvelope\\\":{\\\"wsid\\\":\\\"WSID_ENVE_0000016c0e5f3d4b32852745e6770001\\\",\\\"senderWsid\\\":\\\"WSID_EMEM_0000016bfe98426b32852745e6770002\\\",\\\"senderName\\\":\\\"\\u6D4B\\u8BD5\\u53F7\\u6709\\u9650\\u516C\\u53F8,\\u5F20\\u6CE2\\\",\\\"type\\\":\\\"ANY\\\",\\\"title\\\":\\\"title\\\",\\\"subject\\\":\\\"subject\\\",\\\"status\\\":\\\"ED_FAIL_EXPIRED\\\",\\\"statusDatetime\\\":1563609360000,\\\"statusReason\\\":\\\"\\u4FE1\\u5C01\\u5DF2\\u8FC7\\u6709\\u6548\\u671F\\\",\\\"createdDatetime\\\":1563609218000,\\\"modifiedDatetime\\\":1563609230000,\\\"sendDatetime\\\":1563609230000,\\\"expireDatetime\\\":1563609230000,\\\"finishedDatetime\\\":null,\\\"currentSequence\\\":1,\\\"templateWsid\\\":null,\\\"multisendNum\\\":null,\\\"authLevel\\\":\\\"LOW\\\"},\\\"senderParticipant\\\":{\\\"wsid\\\":\\\"WSID_EPAR_0000016c0e5f3d5032852745e6770001\\\",\\\"envelopeWsid\\\":\\\"WSID_ENVE_0000016c0e5f3d4b32852745e6770001\\\",\\\"authorWsid\\\":\\\"WSID_EMEM_0000016bfe98426b32852745e6770002\\\",\\\"name\\\":\\\"\\u5F20\\u6CE2\\\",\\\"type\\\":\\\"SENDER\\\",\\\"secureLevel\\\":null,\\\"status\\\":null,\\\"receiveNotificationStatus\\\":null,\\\"assignedMeessage\\\":null,\\\"assignedSequence\\\":0,\\\"viewDatetime\\\":null,\\\"handleDatetime\\\":1563609218000,\\\"handleReason\\\":null,\\\"authorizerWsid\\\":null,\\\"envelopeDisplay\\\":\\\"VISIBLE\\\",\\\"sequenceSensitive\\\":true,\\\"enterpriseWsid\\\":\\\"WSID_ENTE_00000167ad24473b9ec1b3833f460001\\\",\\\"enterpriseName\\\":\\\"\\u6D4B\\u8BD5\\u53F7\\u6709\\u9650\\u516C\\u53F8\\\",\\\"alias\\\":null,\\\"corrected\\\":false,\\\"needForm\\\":false,\\\"roleType\\\":\\\"ENTERPRISE_MEMBER\\\",\\\"createdDatetime\\\":1563609218000,\\\"modifiedDatetime\\\":1563609218000,\\\"multisendNum\\\":null,\\\"authLevel\\\":null,\\\"authType\\\":null,\\\"handleMode\\\":null,\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"authorWsid\\\":null,\\\"email\\\":\\\"\\\",\\\"sms\\\":\\\"18380581554\\\",\\\"wechat\\\":\\\"\\\",\\\"wechatPublicAccount\\\":null}]},\\\"secureMetadata\\\":null,\\\"metadata\\\":\\\"{\\\\\\\"groups\\\\\\\":[{\\\\\\\"id\\\\\\\":2,\\\\\\\"position\\\\\\\":1,\\\\\\\"groupName\\\\\\\":\\\\\\\"\\u7C7B\\u578B\\uFF1A\\u4F01\\u4E1A\\\\\\\",\\\\\\\"type\\\\\\\":\\\\\\\"ENTERPRISE_OUTSIDE\\\\\\\",\\\\\\\"enterpriseName\\\\\\\":\\\\\\\"\\u6587\\u672C\\u4F1A\\\\\\\",\\\\\\\"enterpriseWsid\\\\\\\":\\\\\\\"\\\\\\\"}],\\\\\\\"order\\\\\\\":true,\\\\\\\"ip\\\\\\\":\\\\\\\"112.44.251.136\\\\\\\"}\\\",\\\"openMetadata\\\":{\\\"selectedAuthTypes\\\":null,\\\"clientId\\\":null,\\\"enableEmbeddedMode\\\":false}},\\\"receiverParticipant\\\":{\\\"wsid\\\":\\\"WSID_EPAR_0000016c0e5f662932852745e6770001\\\",\\\"envelopeWsid\\\":\\\"WSID_ENVE_0000016c0e5f3d4b32852745e6770001\\\",\\\"authorWsid\\\":\\\"WSID_PUSR_00000167ad2633c29ec1b3833f460001\\\",\\\"name\\\":\\\"\\u5F20\\u6CE2\\\",\\\"type\\\":\\\"SIGNER\\\",\\\"secureLevel\\\":\\\"DISPOSABLE_CERT\\\",\\\"status\\\":\\\"ED_FAIL_EXPIRED\\\",\\\"receiveNotificationStatus\\\":null,\\\"assignedMeessage\\\":null,\\\"assignedSequence\\\":1,\\\"viewDatetime\\\":null,\\\"handleDatetime\\\":1563609360000,\\\"handleReason\\\":\\\"\\u53C2\\u4E0E\\u8005\\u672A\\u5728\\u4FE1\\u5C01\\u6709\\u6548\\u671F\\u5185\\u5904\\u7406\\u4FE1\\u5C01\\\",\\\"authorizerWsid\\\":null,\\\"envelopeDisplay\\\":\\\"VISIBLE\\\",\\\"sequenceSensitive\\\":null,\\\"enterpriseWsid\\\":null,\\\"enterpriseName\\\":null,\\\"alias\\\":null,\\\"corrected\\\":false,\\\"needForm\\\":null,\\\"roleType\\\":\\\"PERSON\\\",\\\"createdDatetime\\\":1563609229000,\\\"modifiedDatetime\\\":1563609229000,\\\"multisendNum\\\":null,\\\"authLevel\\\":\\\"LOW\\\",\\\"authType\\\":null,\\\"handleMode\\\":\\\"NORMAL\\\",\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"authorWsid\\\":null,\\\"email\\\":null,\\\"sms\\\":\\\"18380581554\\\",\\\"wechat\\\":\\\"\\\",\\\"wechatPublicAccount\\\":null}]},\\\"secureMetadata\\\":null,\\\"metadata\\\":\\\"{\\\\\\\"id\\\\\\\":1,\\\\\\\"position\\\\\\\":0}\\\",\\\"openMetadata\\\":{\\\"selectedAuthTypes\\\":[\\\"SMS_CODE\\\",\\\"EMAIL_CODE\\\",\\\"SIGN_PIN\\\"],\\\"clientId\\\":null,\\\"enableEmbeddedMode\\\":false}},\\\"signData\\\":null,\\\"returnUrl\\\":\\\"https://return.qq.com/XXXX\\\",\\\"freeLoginSign\\\":null,\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk34acd7f913696b965288f1aabbcf19ad";
        String appId = "16a543b48463eebbcfde3dd32d1";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 16a543b48463eebbcfde3dd32d1:j/RJEQnBl60XMfOFf4qu71qnVbR2MLzpBMrMZPMIp4IckqcMb/xohXXkSoD9Keu63Mjw/7CFVzXTRVkfe92g5Q==";
        // webhook响应header中x-signit-nonce
        String nonce = "0ZSt67cMn2299tUQZ52xH05Z";
        // webhook响应header中x-signit-date
        String dataString = "Sat Jul 20 15:56:00 CST 2019";
        // webhook响应header中host
        String host = "112.44.251.136:8084";

        // step1: 客户端验证服务端
        Boolean verifyResult = SignitClient.verify(signitSignature, appId, new HmacSignatureBuilder().scheme("http")
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
            break;
        //快捷签署完成事件
        case QUICK_SIGN_COMPLETED:
            break;
        //参与者逾期未签署信封事件
        case PARTICIPANT_EXPIRED:
            // ps:rawData的命名方式为事件名称转换的大驼峰命名，数据所在包为cn.signit.sdk.pojo.webhook.response，其获取的2种方式如下：
            ParticipantExpired rawData1 = (ParticipantExpired) ente.rawDataAsBean();
            boolean s = rawData1.isSuccess();
            // 法2：
            ParticipantExpired rawDat2 = ente.rawDataAsBean(ParticipantExpired.class);
            System.out.println("\nwebhookResponse rawData is :\n" + JSON.toJSONString(rawDat2, true));
        default:
            break;
        }

    }
}
