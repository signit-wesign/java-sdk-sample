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

        String enteVerifyWebhookRespStr = "{\"event\":\"envelopeCompletedSucceed\",\"target\":{\"webhookWsid\":\"WSID_HOOK_00000186fe8e4e18ac9e174a79478d01\",\"destination\":\"http://www.baidu.com\"},\"rawData\":\"{\\\"code\\\":\\\"100600000\\\",\\\"message\\\":\\\"\\u64CD\\u4F5C\\u6210\\u529F\\\",\\\"basicEnvelope\\\":{\\\"wsid\\\":\\\"WSID_ENVE_000001875529edabac9e174a79470452\\\",\\\"senderWsid\\\":\\\"WSID_EMEM_0000018529571229be29e652dd280001\\\",\\\"senderName\\\":\\\"\\u6D4B\\u8BD5\\u4F01\\u4E1A\\u4E00\\u4E8C\\u4E09,\\u5434\\u73A8\\\",\\\"type\\\":\\\"ANY\\\",\\\"title\\\":\\\"\\u81EA\\u7531\\u7B7E\\u6D4B\\u8BD520230328\\\",\\\"subject\\\":\\\"\\u81EA\\u7531\\u7B7E\\\",\\\"status\\\":\\\"ED_SUCCESS\\\",\\\"statusDatetime\\\":1680761260000,\\\"statusReason\\\":\\\"\\u6210\\u529F\\u5904\\u7406\\u5B8C\\u6210\\\",\\\"createdDatetime\\\":1680761022000,\\\"modifiedDatetime\\\":1680761260000,\\\"sendDatetime\\\":1680761038000,\\\"expireDatetime\\\":1680847436000,\\\"finishedDatetime\\\":1680761260000,\\\"currentSequence\\\":2,\\\"authLevel\\\":\\\"LOW\\\",\\\"envelopeFlowType\\\":\\\"SIGN_PROCESS\\\",\\\"mode\\\":\\\"MANUAL\\\",\\\"invokeNo\\\":\\\"202304061403431897040126807600\\\",\\\"fromTag\\\":\\\"WSID_ENTE_000001852957118ebe29e652dd280001\\\",\\\"metadata\\\":\\\"{\\\\\\\"thirdPartFields\\\\\\\":[{\\\\\\\"tpfCode\\\\\\\":\\\\\\\"contractId\\\\\\\",\\\\\\\"tpfName\\\\\\\":\\\\\\\"\\\\\\\\u9879\\\\\\\\u76EE\\\\\\\\u540D\\\\\\\\u79F0\\\\\\\",\\\\\\\"tpfValue\\\\\\\":\\\\\\\"\\\\\\\\u6D4B\\\\\\\\u8BD5\\\\\\\\u9879\\\\\\\\u76EE\\\\\\\"},{\\\\\\\"tpfCode\\\\\\\":\\\\\\\"contractName\\\\\\\",\\\\\\\"tpfName\\\\\\\":\\\\\\\"\\\\\\\\u9879\\\\\\\\u76EE\\\\\\\\u7F16\\\\\\\\u53F7\\\\\\\",\\\\\\\"tpfValue\\\\\\\":\\\\\\\"456\\\\\\\"}],\\\\\\\"ip\\\\\\\":\\\\\\\"112.44.251.136\\\\\\\",\\\\\\\"acceptDataType\\\\\\\":null}\\\",\\\"renewalDatetime\\\":1712383437000},\\\"senderParticipant\\\":{\\\"wsid\\\":\\\"WSID_EPAR_000001875529ede0ac9e174a79470452\\\",\\\"envelopeWsid\\\":\\\"WSID_ENVE_000001875529edabac9e174a79470452\\\",\\\"authorWsid\\\":\\\"WSID_EMEM_0000018529571229be29e652dd280001\\\",\\\"name\\\":\\\"\\u5434\\u73A8\\\",\\\"type\\\":\\\"SENDER\\\",\\\"assignedSequence\\\":0,\\\"viewDatetime\\\":1680761022000,\\\"handleDatetime\\\":1680761022000,\\\"envelopeDisplay\\\":\\\"VISIBLE\\\",\\\"sequenceSensitive\\\":true,\\\"enterpriseWsid\\\":\\\"WSID_ENTE_000001852957118ebe29e652dd280001\\\",\\\"enterpriseName\\\":\\\"\\u6D4B\\u8BD5\\u4F01\\u4E1A\\u4E00\\u4E8C\\u4E09\\\",\\\"corrected\\\":false,\\\"needForm\\\":false,\\\"roleType\\\":\\\"ENTERPRISE_MEMBER\\\",\\\"createdDatetime\\\":1680761022000,\\\"modifiedDatetime\\\":1680761024000,\\\"isExternal\\\":false,\\\"isEntrust\\\":true,\\\"allowRevoke\\\":true,\\\"allowAddForms\\\":false,\\\"contactMetadata\\\":{\\\"contacts\\\":[{\\\"sms\\\":\\\"13440130046\\\",\\\"phone\\\":\\\"13440130046\\\",\\\"email\\\":\\\"\\\",\\\"wechat\\\":\\\"\\\",\\\"idNumber\\\":\\\"\\\"}]},\\\"contact\\\":{\\\"sms\\\":\\\"13440130046\\\",\\\"phone\\\":\\\"13440130046\\\",\\\"email\\\":\\\"\\\",\\\"wechat\\\":\\\"\\\",\\\"idNumber\\\":\\\"\\\"},\\\"metadata\\\":\\\"{\\\\\\\"groups\\\\\\\":[],\\\\\\\"order\\\\\\\":true,\\\\\\\"ip\\\\\\\":\\\\\\\"{X-Http-Client-IP}\\\\\\\"}\\\",\\\"allowRejectRehandle\\\":false,\\\"resetContactCount\\\":0,\\\"openMetadata\\\":{\\\"clientId\\\":\\\"show-client-id\\\",\\\"enableEmbeddedMode\\\":true,\\\"enableNoAppearance\\\":false,\\\"enableSimpleDisplay\\\":false,\\\"enableEntepriseReceive\\\":false}},\\\"signData\\\":{\\\"base64\\\":null,\\\"fileWsid\\\":null,\\\"acceptDataByUrlAliveMinutes\\\":21600,\\\"url\\\":\\\"http://10.10.9.149:61112/WSID_LINK_00000187552fb551fa1831ec7b4d67de/download-file?token=2fc90ab47d6048258df6c2fb3e7def1d\\\"},\\\"appId\\\":\\\"186ddf6a284ac9e174a79473140\\\",\\\"thirdPartFieldsMetadata\\\":{\\\"thirdPartFields\\\":[{\\\"tpfCode\\\":\\\"contractId\\\",\\\"tpfName\\\":\\\"\\u9879\\u76EE\\u540D\\u79F0\\\",\\\"tpfValue\\\":\\\"\\u6D4B\\u8BD5\\u9879\\u76EE\\\"},{\\\"tpfCode\\\":\\\"contractName\\\",\\\"tpfName\\\":\\\"\\u9879\\u76EE\\u7F16\\u53F7\\\",\\\"tpfValue\\\":\\\"456\\\"}]},\\\"links\\\":[],\\\"customTag\\\":\\\"42\\\",\\\"invokeNo\\\":\\\"202304061403431897040126807600\\\",\\\"returnUrl\\\":\\\"http://www.bilibili.com\\\",\\\"actions\\\":null}\",\"needCallBack\":false}";
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
