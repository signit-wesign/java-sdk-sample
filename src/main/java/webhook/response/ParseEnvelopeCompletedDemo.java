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
        String personVerifyWebhookRespStr = "{\"event\":\"envelopeCompleted\",\"target\":{\"webhookWsid\":\"WSID_HOOK_0000016b2bbb68240242d7e5be900001\",\"destination\":\"http://xx.xxx.com:12311/api/v1/SignWebHook\"},\"rawData\":\"{\\\"code\\\":\\\"100600000\\\",\\\"message\\\":\\\"操作成功\\\",\\\"actionUrl\\\":null,\\\"actions\\\":null,\\\"account\\\":null,\\\"customTag\\\":\\\"0|1015|0|1_1|1015|1|1_2|87732|1|0_3|541|2|1\\\",\\\"invokeNo\\\":\\\"202001161717542486001766941001\\\",\\\"basicEnvelope\\\":{\\\"wsid\\\":\\\"WSID_ENVE_0000016fada5162902425b39c1cf0001\\\",\\\"senderWsid\\\":\\\"WSID_EMEM_0000016e8bb1ab020242b2da76cb0002\\\",\\\"senderName\\\":\\\"XXX有限公司,张三\\\",\\\"type\\\":\\\"ANY\\\",\\\"title\\\":\\\"签约合同\\\",\\\"subject\\\":\\\"签约合同\\\",\\\"status\\\":\\\"ED_SUCCESS\\\",\\\"statusDatetime\\\":1579166748000,\\\"statusReason\\\":\\\"信封中所有参与者均已成功完成,等待确认完成\\\",\\\"createdDatetime\\\":1579166275000,\\\"modifiedDatetime\\\":1579166748000,\\\"sendDatetime\\\":1579166278000,\\\"expireDatetime\\\":1579771078000,\\\"finishedDatetime\\\":null,\\\"currentSequence\\\":3,\\\"templateWsid\\\":null,\\\"multisendNum\\\":null,\\\"authLevel\\\":\\\"LOW\\\"},\\\"senderParticipant\\\":null,\\\"receiverParticipant\\\":null,\\\"signData\\\":{\\\"base64\\\":null,\\\"fileWsid\\\":null,\\\"url\\\":\\\"http://r.signit.cn/WSID_LINK_0000016fadac5ac702425b39c1cf0001/download-file?token=b945f19ef13941d8b6bb6f6099d79582\\\"},\\\"returnUrl\\\":\\\"\\\",\\\"freeLoginSign\\\":null,\\\"links\\\":[]}\",\"needCallBack\":false}";
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
        //快捷签署完成事件
        case QUICK_SIGN_COMPLETED:
            break;
        default:
            break;
        }

    }
}
