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
                .basicinfo(EnvelopeBasicInfo.builder()
                        .title("创建签署流程")
                        .subject("subject")
                        .expire(365))
                .contentInfo(EnvelopeContentInfo.builder()
                        .files(EnvelopeFile.builder()
                                .id("200427")
                                .sequence(1)
                                .data(BaseFileData.builder()
                                        .name("文件数据")
                                        .url("https://raw.githubusercontent.com/enHb7/Yiqiqian/master/%E5%81%87%E6%9C%9F%E7%95%99%E6%A0%A1%E4%BD%8F%E5%AE%BF%E5%AD%A6%E7%94%9F%E5%AE%89%E5%85%A8%E8%B4%A3%E4%BB%BB%E4%B9%A6%EF%BC%88%E5%AE%9E%E9%AA%8C%E5%AE%A4%E5%AD%A6%E4%B9%A0%EF%BC%89.docx")
                                        .base64("UEsDBAoAAAAAAIdO4kAAAAAAAAAAAAAAAAAJA"))))
                .participantInfo(EnvelopeParticipantInfo.builder()
                        .sender(Sender.builder()
                                .name("刘文萱")
                                .contact(Contact.builder()
                                        .email("734206544@qq.com")
                                        .phone("15998945918"))
                                .clientId("100")
                                .enableEmbeddedMode(false))
                        .receivers(Receiver.builder()// 参与者可以设置多个，此处仅设置了一个
                                .name("李四")//TODO
                                .type(ReceiverType.SIGNER)
                                .roleType(EnvelopeRoleType.PERSON)
                                .contact(Contact.builder()
                                        .email("734206544@qq.com")
                                        .phone("15998945918"))
                                .assignedSequence(1)
                                .isExternal(false)
                                .isEntrust(true)
                                .enterpriseName("xxx公司")
                                .allowRevoke(false)
                                .enableEmbeddedMode(false)
                                .selectedAuthTypes(AuthType.SMS_CODE)//意愿认证类型可以设置多个，此处仅设置了一个
                                .handleMode(ParticipantHandleMode.NORMAL)
                                .clientId("111")
                                .presetForms(PresetForm.builder()
                                        .formType(FormType.TEXT)
                                        .fileId("字段")
                                        .scale(1.0f)
                                        .position(Position.builder()
                                                .withKeywordPosition(KeywordPosition.builder()
                                                        .withKeyword("学生签名：")
                                                        .withWidth(100.0f)
                                                        .withHeight(50.0f)
                                                        .withDirection(Direction.RIGHT)
                                                        .withXOffset(0f)
                                                        .withYOffset(20f)
                                                        .withPages("all")
                                                        .withIndex(0))
                                                .withRectanglePosition(RectanglePosition.builder()
                                                        .withLrx(251.0f)
                                                        .withLry(270.0f)
                                                        .withUlx(151.0f)
                                                        .withUly(220.0f)
                                                        .withScale(1.0f)
                                                        .withPage(2)))
                                        .initialValue(InitialValue.builder()
                                                .sealData(SealData.builder()
                                                        .withName("易企签测试企业"))))))
               .customTag("C130018122503922")
                .returnUrl("https://return.qq.com/XXXX")
               .acceptDataType(AcceptDataType.URL)
               .build();
    }

}
