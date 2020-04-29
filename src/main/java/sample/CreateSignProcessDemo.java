package sample;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.pojo.BaseFileData;
import cn.signit.sdk.pojo.Contact;
import cn.signit.sdk.pojo.EnvelopeBasicInfo;
import cn.signit.sdk.pojo.EnvelopeContentInfo;
import cn.signit.sdk.pojo.EnvelopeFile;
import cn.signit.sdk.pojo.EnvelopeParticipantInfo;
import cn.signit.sdk.pojo.ErrorResp;
import cn.signit.sdk.pojo.InitialValue;
import cn.signit.sdk.pojo.PresetForm;
import cn.signit.sdk.pojo.Receiver;
import cn.signit.sdk.pojo.SealData;
import cn.signit.sdk.pojo.Sender;
import cn.signit.sdk.pojo.Signer.Position;
import cn.signit.sdk.pojo.Signer.Position.KeywordPosition;
import cn.signit.sdk.pojo.Signer.Position.RectanglePosition;
import cn.signit.sdk.pojo.request.CreateSignProcessRequest;
import cn.signit.sdk.pojo.response.CreateSignProcessResponse;
import cn.signit.sdk.type.AcceptDataType;
import cn.signit.sdk.type.AuthType;
import cn.signit.sdk.type.Direction;
import cn.signit.sdk.type.EnvelopeRoleType;
import cn.signit.sdk.type.FormType;
import cn.signit.sdk.type.ParticipantHandleMode;
import cn.signit.sdk.type.ReceiverType;
import cn.signit.sdk.util.FastjsonDecoder;

/**
 * 易企签 Java SDK 创建签署流程调用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行启动信封，示例代码中的姓名、手机、邮箱、签名文件、手写签名数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，url均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class CreateSignProcessDemo {

    public static void main(String[] args) {
        String appSecretKey = "sk3847e93a9e3835e6e42a4944ae979308";
        String appId = "171ba7cc03600ff1aa995d134a1";

        // "https://open.signit.cn/v1/open/envelopes/manual-mode";
        String appUrl = "http://112.44.251.136:2576/v1/open/envelopes/manual-mode";// 测试环境使用的地址，生产环境时，应该使用上面一个appUrl

        // step1: 初始化易企签开放平台客户端
        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        client.setOauthUrl("http://112.44.251.136:2576/v1/oauth/oauth/token");// 测试环境需要手动设置oauthUrl，生产环境不用设置

        // step2: 使用SDK封装创建签署流程请求
        CreateSignProcessRequest request = getCreateSignProcessRequestParam();
        System.out.println("request is:\n" + JSON.toJSONString(request, true));

        // step3: 执行请求,获得响应
        CreateSignProcessResponse response = null;
        try {
            response = client.execute(request);
        } catch (SignitException e) {
            ErrorResp errorResp = null;
            if (FastjsonDecoder.isValidJson(e.getMessage())) {
                errorResp = FastjsonDecoder.decodeAsBean(e.getMessage(), ErrorResp.class);
            }
            System.out.println("\nerror response is:\n" + JSON.toJSONString(errorResp, true));
        }
        System.out.println("\nresponse is:\n" + JSON.toJSONString(response, true));
    }

    // 使用SDK封装创建签署流程请求
    private static CreateSignProcessRequest getCreateSignProcessRequestParam() {

        return CreateSignProcessRequest.builder()
                .basicInfo(EnvelopeBasicInfo.builder()
                        .title("This is test Envelope")
                        .subject("subject")
                        .expire(365))
                .contentInfo(EnvelopeContentInfo.builder()
                        .files(EnvelopeFile.builder()
                                .id("321")
                                .sequence(1)
                                .data(BaseFileData.builder()
                                        .name("signit-quicksign-demo.docx")
                                        .url("https://gitee.com/zhdzhd.oschina.net/test/raw/master/src/main/resources/quicksign/signit-quicksign-demo.docx")
                                        .base64("J"))))
                .participantInfo(EnvelopeParticipantInfo.builder()
                        .sender(Sender.builder()
                                .name("郑复俊")
                                .contact(Contact.builder()
                                        .email("775477093@qq.com") //邮件和电话二选一
                                        .phone("15885829860"))
                                .clientId("12345")
                                .enableEmbeddedMode(true))
                        .receivers(Receiver.builder()// 参与者可以设置多个，此处仅设置了一个
                                .name("邓文")
                                .type(ReceiverType.SIGNER)
                                .roleType(EnvelopeRoleType.ENTERPRISE_MEMBER)
                                .contact(Contact.builder()
                                        //.email("734206544@qq.com")  //邮件和电话二选一
                                        .phone("15998945918"))
                                .assignedSequence(1)
                                .isExternal(false)
                                .isEntrust(true)
                                .enterpriseName("测试用企业账号")
                                .allowRevoke(true)
                                .enableEmbeddedMode(false)
                                .selectedAuthTypes(AuthType.SMS_CODE)//意愿认证类型可以设置多个，此处仅设置了一个
                                .handleMode(ParticipantHandleMode.NORMAL)
                                .clientId("12345")
                                .presetForms(PresetForm.builder()
                                        .formType(FormType.SEAL_SIGN)
                                        .fileId("321")
                                        .scale(1.0f)
                                        .position(Position.builder()
                                                .withKeywordPosition(KeywordPosition.builder()
                                                        .withKeyword("甲方盖章：")
                                                        .withWidth(100.0f)
                                                        .withHeight(100.f)
                                                        .withDirection(Direction.RIGHT)
                                                        .withXOffset(10f)
                                                        .withYOffset(0f)
                                                        .withPages("ALL")
                                                        .withIndex(0))
                                                .withRectanglePosition(RectanglePosition.builder()
                                                        .withLrx(100.0f)
                                                        .withLry(100.0f)
                                                        .withUlx(0.0f)
                                                        .withUly(0.0f)
                                                        .withScale(1.0f)
                                                        .withPage(1)))
                                        .initialValue(InitialValue.builder()
                                                .sealData(SealData.builder()
                                                        .withName("TIM截图20200427142131"))))))//公司印章名称
               .customTag("test123")
               .returnUrl("http://www.signit.cn")
               .acceptDataType(AcceptDataType.URL)
               .build();
    }

}
