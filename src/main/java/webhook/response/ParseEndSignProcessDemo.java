package webhook.response;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.EndSignProcess;
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
public class ParseEndSignProcessDemo {

    public static void main(String[] args) {
        String personVerifyWebhookRespStr = "{\r\n" + "  \"event\": \"envelopeCompleted\",\r\n" + "  \"target\": {\r\n"
                + "    \"webhookWsid\": \"WSID_HOOK_00000171bbbae14a00ff1aa995d10001\",\r\n"
                + "    \"destination\": \"https://webhook.site/2750a3f1-76e4-433f-8aef-ada4ad34c943\"\r\n" + "  },\r\n"
                + "  \"rawData\": \"{\\\"code\\\":\\\"100600000\\\",\\\"message\\\":\\\"操作成功\\\",\\\"actionUrl\\\":null,\\\"actions\\\":null,\\\"account\\\":null,\\\"customTag\\\":\\\"test123\\\",\\\"invokeNo\\\":\\\"202004281631261095219683741001\\\",\\\"basicEnvelope\\\":{\\\"wsid\\\":\\\"WSID_ENVE_00000171bfe9959f00ff1aa995d10001\\\",\\\"senderWsid\\\":\\\"WSID_EMEM_00000171bf6136550e6686044b270001\\\",\\\"senderName\\\":\\\"测试用企业账号,郑复俊\\\",\\\"type\\\":\\\"ANY\\\",\\\"title\\\":\\\"This is test Envelope\\\",\\\"subject\\\":\\\"subject\\\",\\\"status\\\":\\\"ED_FAIL_REVOKE\\\",\\\"statusDatetime\\\":1588075287000,\\\"statusReason\\\":\\\"这是撤销流程原因\\\",\\\"createdDatetime\\\":1588062688000,\\\"modifiedDatetime\\\":1588075287000,\\\"sendDatetime\\\":1588062737000,\\\"expireDatetime\\\":1619598688000,\\\"finishedDatetime\\\":null,\\\"currentSequence\\\":2,\\\"templateWsid\\\":null,\\\"multisendNum\\\":null,\\\"authLevel\\\":\\\"LOW\\\",\\\"mode\\\":\\\"MANUAL\\\",\\\"invokeNo\\\":\\\"202004281631261095219683741001\\\",\\\"fromTag\\\":\\\"WSID_ENTE_00000171aaf6bd384281d802bf3e0001\\\",\\\"metadata\\\":\\\"{\\\\\\\"acceptDataType\\\\\\\":null}\\\",\\\"tagId\\\":{\\\"value\\\":null},\\\"acceptDataType\\\":null,\\\"openMetadata\\\":null},\\\"senderParticipant\\\":null,\\\"receiverParticipant\\\":null,\\\"signData\\\":null,\\\"returnUrl\\\":\\\"http://www.signit.com\\\",\\\"freeLoginSign\\\":null,\\\"links\\\":[]}\",\r\n"
                + "  \"needCallBack\": false\r\n" + "}";
        String appSecretKey = "sk3847e93a9e3835e6e42a4944ae979308";
        String appId = "171ba7cc03600ff1aa995d134a1";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 171ba7cc03600ff1aa995d134a1:aOblWnSgXX958I6ZKHgeA8FQ0y3E9UMMqN7lq7t5slUQz3P0N0BewCzxjunitc1SdNBBH2oLINKMMa5MTEWiCg";
        // webhook响应header中x-signit-nonce
        String nonce = "93LoistZ1T4ebbUFMa687AS0";
        // webhook响应header中x-signit-date
        String dataString = "Tue Apr 28 20:20:37 CST 2020";
        // webhook响应header中host
        String host = "webhook.site";

        // step1: 客户端验证服务端
        Boolean verifyResult = SignitClient.verify(signitSignature, appId,
                new HmacSignatureBuilder().scheme("https").apiKey(appId).apiSecret(appSecretKey.getBytes()).method(
                        "POST").payload(personVerifyWebhookRespStr.getBytes()).contentType("application/json").host(
                                host).resource("").nonce(nonce).date(dataString));
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
            // EndSignProcess rawData1 = (EndSignProcess) ente.rawDataAsBean();
            // boolean s = rawData1.isSuccess();
            // 法2：
            EndSignProcess rawDat2 = ente.rawDataAsBean(EndSignProcess.class);
            System.out.println("\nwebhookResponse rawData is :\n" + JSON.toJSONString(rawDat2, true));
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
        // 个人实名认证提交
        case PERSON_VERIFICATION_SUBMITTED:
            break;
        // 个人实名认证完成
        case PERSON_VERIFICATION_COMPLETED:
            break;
        // 参与者正在处理信封
        case PARTICIPANT_HANDLING:
            break;
        // 快捷签署完成事件
        case QUICK_SIGN_COMPLETED:
            break;
        default:
            break;
        }
    }
}