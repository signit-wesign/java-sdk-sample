package sample;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.http.Authentication;
import cn.signit.sdk.pojo.BaseFileData;
import cn.signit.sdk.pojo.Contact;
import cn.signit.sdk.pojo.EnvelopeBasicInfo;
import cn.signit.sdk.pojo.EnvelopeContentInfo;
import cn.signit.sdk.pojo.EnvelopeFile;
import cn.signit.sdk.pojo.EnvelopeParticipantInfo;
import cn.signit.sdk.pojo.ErrorResp;
import cn.signit.sdk.pojo.InitialValue;
import cn.signit.sdk.pojo.OauthData;
import cn.signit.sdk.pojo.PresetForm;
import cn.signit.sdk.pojo.Receiver;
import cn.signit.sdk.pojo.SealData;
import cn.signit.sdk.pojo.Sender;
import cn.signit.sdk.pojo.Signer.Position;
import cn.signit.sdk.pojo.Signer.Position.KeywordPosition;
import cn.signit.sdk.pojo.Signer.Position.RectanglePosition;
import cn.signit.sdk.pojo.request.StartEnvelopeRequest;
import cn.signit.sdk.pojo.response.StartEnvelopeResponse;
import cn.signit.sdk.type.AcceptDataType;
import cn.signit.sdk.type.Direction;
import cn.signit.sdk.type.EnvelopeRoleType;
import cn.signit.sdk.type.FormType;
import cn.signit.sdk.type.ParticipantHandleMode;
import cn.signit.sdk.type.ReceiverType;
import cn.signit.sdk.type.RenderMode;
import cn.signit.sdk.type.SecureLevel;
import cn.signit.sdk.util.FastjsonDecoder;

/**
 * 易企签 Java SDK 启动信封调用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行启动信封，示例代码中的姓名、手机、邮箱、签名文件、手写签名数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，url均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class StartEnvelopeDemo {
    public static void main(String[] args) throws SignitException {
        // https://webhook.site 可以在该网站上申请webhook地址用于测试
        // 本次测试webhook地址
        // https://webhook.site/eda20c6f-119b-4f3a-93d9-b2c71dd9e254
        String appSecretKey = "sk9d79a9dc5bb91e02a320b50ab78dd43b";
        String appId = "16903be96b002428f616806f5c1";

        // String appUrl = "https://open.signit.cn/v1/open/envelopes";
        String appUrl = "http://10.10.9.70:2576/v1/open/envelopes/start";// 测试环境使用的地址，生产环境时，应该使用上面一个appUrl

        // step1: 初始化易企签开放平台客户端
        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        client.setOauthUrl("http://10.10.9.70:2576/v1/oauth/oauth/token");// 测试环境需要手动设置oauthUrl，生产环境不用设置

        // step2: 使用SDK封装实名认证请求
        StartEnvelopeRequest request = startEnvelopeWithQrcode();
        System.out.println("request is :\n" + JSON.toJSONString(request, true));

        // step3: 执行请求,获得响应
        StartEnvelopeResponse response = null;
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

    // 启动信封:一个发送者，一个个人接收者（2个手写签名），一个企业接受者（一个公章签名）
    public static StartEnvelopeRequest startEnvelope() {
        return StartEnvelopeRequest.builder()
                .basicinfo(EnvelopeBasicInfo.builder()
                        .title("title")
                        .subject("subject"))
                .contentInfo(EnvelopeContentInfo.builder()
                        .files(EnvelopeFile.builder()
                                .id("127090")
                                .sequence(1)
                                .data(BaseFileData.builder()
                                        .url("https://raw.githubusercontent.com/enHb7/Yiqiqian/master/%E5%81%87%E6%9C%9F%E7%95%99%E6%A0%A1%E4%BD%8F%E5%AE%BF%E5%AD%A6%E7%94%9F%E5%AE%89%E5%85%A8%E8%B4%A3%E4%BB%BB%E4%B9%A6%EF%BC%88%E5%AE%9E%E9%AA%8C%E5%AE%A4%E5%AD%A6%E4%B9%A0%EF%BC%89.docx"))
                                .isAttached(false)))
                .participantInfo(EnvelopeParticipantInfo.builder()
                        .sender(Sender.builder()
                                .name("刘清华")
                                .contact(Contact.builder()
                                        .phone("18681695956"))
                                .deleteCompletedEnvelope(true))
                        .receivers(Receiver.builder()// 参与者可以设置多个，此处仅设置了一个
                                .name("张波")
                                .assignedSequence(1)
                                .contact(Contact.builder()
                                        .phone("18380581554"))
                                .secureLevel(SecureLevel.DISPOSABLE_CERT)
                                .type(ReceiverType.SIGNER)
                                .roleType(EnvelopeRoleType.PERSON)
                                .deleteCompletedEnvelope(true)
                                .presetForms(PresetForm.builder()
                                        .fileId("127090")
                                        .formType(FormType.WRITE_SIGN)
                                        .position(Position.builder()
                                                .withRectanglePosition(RectanglePosition.builder()
                                                        .withLrx(251.0f)
                                                        .withLry(270.0f)
                                                        .withUlx(151.0f)
                                                        .withUly(220.0f)
                                                        .withPage(2)))
                                        .revisable(true)
                                        .scale(1.0f),
                                        PresetForm.builder()
                                                .formType(FormType.WRITE_SIGN)
                                                .fileId("127090")
                                                .position(Position.builder()
                                                        .withKeywordPosition(KeywordPosition.builder()
                                                                .withWidth(100.0f)
                                                                .withRelativeWidthRatio(1.0f)
                                                                .withHeight(50.0f)
                                                                .withRelativeHeightRatio(1.0f)
                                                                .withDirection(Direction.RIGHT)
                                                                .withOffset(1.0f)
                                                                .withRelativeOffsetRatio(0.1f)
                                                                .withXOffset(0f)
                                                                .withYOffset(20f)
                                                                .withKeyword("学生签名：")
                                                                .withPages("all")
                                                                .withIndex(0)))
                                                .scale(1.0f)
                                                .revisable(true)),
                                Receiver.builder()
                                        .assignedSequence(2)
                                        .contact(Contact.builder()
                                                .phone("18681695956"))
                                        .enterpriseName("刘清华的测试企业")
                                        .name("刘清华")
                                        .needForm(false)
                                        .roleType(EnvelopeRoleType.ENTERPRISE_MEMBER)
                                        .secureLevel(SecureLevel.DISPOSABLE_CERT)
                                        .type(ReceiverType.SIGNER)
                                        .presetForms(PresetForm.builder()
                                                .fileId("127090")
                                                .formType(FormType.SEAL_SIGN)
                                                .revisable(false)
                                                .scale(1.0f)
                                                .tagId("customTagId1")
                                                .position(Position.builder()
                                                        .withKeywordPosition(KeywordPosition.builder()
                                                                .withWidth(70f)
                                                                .withRelativeWidthRatio(1.0f)
                                                                .withHeight(70f)
                                                                .withRelativeHeightRatio(1.0f)
                                                                .withDirection(Direction.RIGHT)
                                                                .withOffset(1.0f)
                                                                .withRelativeOffsetRatio(0.1f)
                                                                .withXOffset(0f)
                                                                .withYOffset(20f)
                                                                .withKeyword("老师签名：")
                                                                .withPages("all")
                                                                .withIndex(0)))
                                                .initialValue(InitialValue.builder()
                                                        .renderingMode(RenderMode.GRAPHIC)
                                                        .sealData(SealData.builder()
                                                                .withName("刘清华的测试企业"))))

                        ))
                .returnUrl("https://return.qq.com/XXXX")
                .acceptDataType(AcceptDataType.URL)
                .customTag("C130018122503922")
                .build();
    }

    // 启动信封：一个发送者，一个个人接收者（一个手写签名），一个企业接收者（一个公章签名，一个文本框表单）
    public static StartEnvelopeRequest startEnvelopeWithTextForm() {
        return StartEnvelopeRequest.builder()
                .basicinfo(EnvelopeBasicInfo.builder()
                        .title("启动信封测试：使用文本框")
                        .subject("subject"))
                .contentInfo(EnvelopeContentInfo.builder()
                        .files(EnvelopeFile.builder()
                                .id("127091")
                                .sequence(1)
                                .data(BaseFileData.builder()
                                        .url("https://raw.githubusercontent.com/enHb7/Yiqiqian/master/%E5%81%87%E6%9C%9F%E7%95%99%E6%A0%A1%E4%BD%8F%E5%AE%BF%E5%AD%A6%E7%94%9F%E5%AE%89%E5%85%A8%E8%B4%A3%E4%BB%BB%E4%B9%A6%EF%BC%88%E5%AE%9E%E9%AA%8C%E5%AE%A4%E5%AD%A6%E4%B9%A0%EF%BC%89.docx"))
                                .isAttached(false)))
                .participantInfo(EnvelopeParticipantInfo.builder()
                        .sender(Sender.builder()
                                .name("刘清华")
                                .contact(Contact.builder()
                                        .phone("18681695956"))
                                .deleteCompletedEnvelope(true))
                        .receivers(Receiver.builder()// 参与者可以设置多个，此处仅设置了一个
                                .name("张波")
                                .assignedSequence(1)
                                .contact(Contact.builder()
                                        .phone("18380581554"))
                                .secureLevel(SecureLevel.DISPOSABLE_CERT)
                                .type(ReceiverType.SIGNER)
                                .roleType(EnvelopeRoleType.PERSON)
                                .deleteCompletedEnvelope(true)
                                .presetForms(PresetForm.builder()
                                        .fileId("127091")
                                        .formType(FormType.WRITE_SIGN)
                                        .position(Position.builder()
                                                .withKeywordPosition(KeywordPosition.builder()
                                                        .withWidth(100.0f)
                                                        .withRelativeWidthRatio(1.0f)
                                                        .withHeight(50.0f)
                                                        .withRelativeHeightRatio(1.0f)
                                                        .withDirection(Direction.RIGHT)
                                                        .withOffset(1.0f)
                                                        .withRelativeOffsetRatio(0.1f)
                                                        .withXOffset(0f)
                                                        .withYOffset(20f)
                                                        .withKeyword("学生签名：")
                                                        .withPages("all")
                                                        .withIndex(0)))
                                        .revisable(true)
                                        .scale(1.0f)),
                                Receiver.builder()
                                        .assignedSequence(2)
                                        .contact(Contact.builder()
                                                .phone("18681695956"))
                                        .enterpriseName("刘清华的测试企业")
                                        .name("刘清华")
                                        .needForm(false)
                                        .roleType(EnvelopeRoleType.ENTERPRISE_MEMBER)
                                        .secureLevel(SecureLevel.DISPOSABLE_CERT)
                                        .type(ReceiverType.SIGNER)
                                        .handleMode(ParticipantHandleMode.SILENCE)
                                        .presetForms(PresetForm.builder()
                                                .fileId("127091")
                                                .formType(FormType.SEAL_SIGN)
                                                .revisable(false)
                                                .scale(1.0f)
                                                .tagId("customtagId2")
                                                .position(Position.builder()
                                                        .withKeywordPosition(KeywordPosition.builder()
                                                                .withWidth(70f)
                                                                .withRelativeWidthRatio(1.0f)
                                                                .withHeight(70f)
                                                                .withRelativeHeightRatio(1.0f)
                                                                .withDirection(Direction.RIGHT)
                                                                .withOffset(1.0f)
                                                                .withRelativeOffsetRatio(0.1f)
                                                                .withXOffset(0f)
                                                                .withYOffset(20f)
                                                                .withKeyword("老师签名：")
                                                                .withPages("all")
                                                                .withIndex(0)))
                                                .initialValue(InitialValue.builder()
                                                        .renderingMode(RenderMode.GRAPHIC)
                                                        .sealData(SealData.builder()
                                                                .withName("刘清华的测试企业"))),
                                                PresetForm.builder()
                                                        .formType(FormType.TEXT)
                                                        .fileId("127091")
                                                        .position(Position.builder()
                                                                .withRectanglePosition(RectanglePosition.builder()
                                                                        .withLrx(251.0f)
                                                                        .withLry(270.0f)
                                                                        .withUlx(151.0f)
                                                                        .withUly(220.0f)
                                                                        .withPage(2)))
                                                        .scale(1.0f)
                                                        .initialValue(InitialValue.builder()
                                                                .textContent("假期注意安全"))
                                                        .revisable(true))

                        ))
                .returnUrl("https://return.qq.com/XXXX")
                .acceptDataType(AcceptDataType.URL)
                .customTag("C130018122503924" + System.currentTimeMillis())
                .build();
    }

    // 启动信封：一个发送者，一个个人接收者（一个手写签名），一个企业接收者（一个公章签名，一个骑缝章表单）
    public static StartEnvelopeRequest startEnvelopeWithMultiCheckMark() {
        return StartEnvelopeRequest.builder()
                .basicinfo(EnvelopeBasicInfo.builder()
                        .title("启动信封测试：使用骑缝章表单")
                        .subject("subject"))
                .contentInfo(EnvelopeContentInfo.builder()
                        .files(EnvelopeFile.builder()
                                .id("127091")
                                .sequence(1)
                                .data(BaseFileData.builder()
                                        .url("https://raw.githubusercontent.com/enHb7/Yiqiqian/master/%E5%81%87%E6%9C%9F%E7%95%99%E6%A0%A1%E4%BD%8F%E5%AE%BF%E5%AD%A6%E7%94%9F%E5%AE%89%E5%85%A8%E8%B4%A3%E4%BB%BB%E4%B9%A6%EF%BC%88%E5%AE%9E%E9%AA%8C%E5%AE%A4%E5%AD%A6%E4%B9%A0%EF%BC%89.docx"))
                                .isAttached(false)))
                .participantInfo(EnvelopeParticipantInfo.builder()
                        .sender(Sender.builder()
                                .name("刘清华")
                                .contact(Contact.builder()
                                        .phone("18681695956"))
                                .deleteCompletedEnvelope(true))
                        .receivers(Receiver.builder()// 参与者可以设置多个，此处仅设置了一个
                                .name("张波")
                                .assignedSequence(1)
                                .contact(Contact.builder()
                                        .phone("18380581554"))
                                .secureLevel(SecureLevel.DISPOSABLE_CERT)
                                .type(ReceiverType.SIGNER)
                                .roleType(EnvelopeRoleType.PERSON)
                                .deleteCompletedEnvelope(true)
                                .presetForms(PresetForm.builder()
                                        .fileId("127091")
                                        .formType(FormType.WRITE_SIGN)
                                        .position(Position.builder()
                                                .withKeywordPosition(KeywordPosition.builder()
                                                        .withWidth(100.0f)
                                                        .withRelativeWidthRatio(1.0f)
                                                        .withHeight(50.0f)
                                                        .withRelativeHeightRatio(1.0f)
                                                        .withDirection(Direction.RIGHT)
                                                        .withOffset(1.0f)
                                                        .withRelativeOffsetRatio(0.1f)
                                                        .withXOffset(0f)
                                                        .withYOffset(20f)
                                                        .withKeyword("学生签名：")
                                                        .withPages("all")
                                                        .withIndex(0)))
                                        .revisable(true)
                                        .scale(1.0f)),
                                Receiver.builder()
                                        .assignedSequence(2)
                                        .contact(Contact.builder()
                                                .phone("18681695956"))
                                        .enterpriseName("刘清华的测试企业")
                                        .name("刘清华")
                                        .needForm(false)
                                        .roleType(EnvelopeRoleType.ENTERPRISE_MEMBER)
                                        .secureLevel(SecureLevel.DISPOSABLE_CERT)
                                        .type(ReceiverType.SIGNER)
                                        .handleMode(ParticipantHandleMode.SILENCE)
                                        .presetForms(PresetForm.builder()
                                                .fileId("127091")
                                                .formType(FormType.SEAL_SIGN)
                                                .revisable(false)
                                                .scale(1.0f)
                                                .tagId("customtagId2")
                                                .position(Position.builder()
                                                        .withKeywordPosition(KeywordPosition.builder()
                                                                .withWidth(70f)
                                                                .withRelativeWidthRatio(1.0f)
                                                                .withHeight(70f)
                                                                .withRelativeHeightRatio(1.0f)
                                                                .withDirection(Direction.RIGHT)
                                                                .withOffset(1.0f)
                                                                .withRelativeOffsetRatio(0.1f)
                                                                .withXOffset(0f)
                                                                .withYOffset(20f)
                                                                .withKeyword("老师签名：")
                                                                .withPages("all")
                                                                .withIndex(0)))
                                                .initialValue(InitialValue.builder()
                                                        .renderingMode(RenderMode.GRAPHIC)
                                                        .sealData(SealData.builder()
                                                                .withName("刘清华的测试企业"))),
                                                PresetForm.builder()
                                                        .formType(FormType.MULTI_CHECK_MARK)
                                                        .fileId("127091")
                                                        .margin(0f)
                                                        .offset(100f)
                                                        .certPages("last")
                                                        .pixel(10)
                                                        .singlePageMark(true)
                                                        .scale(1.0f)
                                                        .resizable(true)
                                                        .resizeWidth(40f)
                                                        .resizeHeight(40f)
                                                        .initialValue(InitialValue.builder()
                                                                .renderingMode(RenderMode.GRAPHIC)
                                                                .sealData(SealData.builder()
                                                                        .withName("刘清华的测试企业")))
                                                        .revisable(true))

                        ))
                .returnUrl("https://www.baidu.com")
                .acceptDataType(AcceptDataType.URL)
                .customTag("C130018122503924" + System.currentTimeMillis())
                .build();
    }

    // 启动信封：一个发送者，一个个人接收者（一个手写签名），一个企业接收者（一个公章签名，一个二维码骑缝章表单域）
    public static StartEnvelopeRequest startEnvelopeWithQrcode() {
        return StartEnvelopeRequest.builder()
                .basicinfo(EnvelopeBasicInfo.builder()
                        .title("启动信封测试：使用二维码表单")
                        .subject("subject"))
                .contentInfo(EnvelopeContentInfo.builder()
                        .files(EnvelopeFile.builder()
                                .id("127091")
                                .sequence(1)
                                .data(BaseFileData.builder()
                                        .url("https://raw.githubusercontent.com/enHb7/Yiqiqian/master/%E5%81%87%E6%9C%9F%E7%95%99%E6%A0%A1%E4%BD%8F%E5%AE%BF%E5%AD%A6%E7%94%9F%E5%AE%89%E5%85%A8%E8%B4%A3%E4%BB%BB%E4%B9%A6%EF%BC%88%E5%AE%9E%E9%AA%8C%E5%AE%A4%E5%AD%A6%E4%B9%A0%EF%BC%89.docx"))
                                .isAttached(false)))
                .participantInfo(EnvelopeParticipantInfo.builder()
                        .sender(Sender.builder()
                                .name("刘清华")
                                .contact(Contact.builder()
                                        .phone("18681695956"))
                                .deleteCompletedEnvelope(true))
                        .receivers(Receiver.builder()// 参与者可以设置多个，此处仅设置了一个
                                .name("张波")
                                .assignedSequence(1)
                                .contact(Contact.builder()
                                        .phone("18380581554"))
                                .secureLevel(SecureLevel.DISPOSABLE_CERT)
                                .type(ReceiverType.SIGNER)
                                .roleType(EnvelopeRoleType.PERSON)
                                .deleteCompletedEnvelope(false)
                                .presetForms(PresetForm.builder()
                                        .fileId("127091")
                                        .formType(FormType.WRITE_SIGN)
                                        .position(Position.builder()
                                                .withKeywordPosition(KeywordPosition.builder()
                                                        .withWidth(100.0f)
                                                        .withRelativeWidthRatio(1.0f)
                                                        .withHeight(50.0f)
                                                        .withRelativeHeightRatio(1.0f)
                                                        .withDirection(Direction.RIGHT)
                                                        .withOffset(1.0f)
                                                        .withRelativeOffsetRatio(0.1f)
                                                        .withXOffset(0f)
                                                        .withYOffset(20f)
                                                        .withKeyword("学生签名：")
                                                        .withPages("all")
                                                        .withIndex(0)))
                                        .revisable(true)
                                        .scale(1.0f)),
                                Receiver.builder()
                                        .assignedSequence(2)
                                        .contact(Contact.builder()
                                                .phone("18681695956"))
                                        .enterpriseName("刘清华的测试企业")
                                        .name("刘清华")
                                        .needForm(false)
                                        .roleType(EnvelopeRoleType.ENTERPRISE_MEMBER)
                                        .secureLevel(SecureLevel.DISPOSABLE_CERT)
                                        .type(ReceiverType.SIGNER)
                                        .handleMode(ParticipantHandleMode.SILENCE)
                                        .presetForms(PresetForm.builder()
                                                .fileId("127091")
                                                .formType(FormType.SEAL_SIGN)
                                                .revisable(false)
                                                .scale(1.0f)
                                                .tagId("customtagId2")
                                                .position(Position.builder()
                                                        .withKeywordPosition(KeywordPosition.builder()
                                                                .withWidth(70f)
                                                                .withRelativeWidthRatio(1.0f)
                                                                .withHeight(70f)
                                                                .withRelativeHeightRatio(1.0f)
                                                                .withDirection(Direction.RIGHT)
                                                                .withOffset(1.0f)
                                                                .withRelativeOffsetRatio(0.1f)
                                                                .withXOffset(0f)
                                                                .withYOffset(20f)
                                                                .withKeyword("老师签名：")
                                                                .withPages("all")
                                                                .withIndex(0)))
                                                .initialValue(InitialValue.builder()
                                                        .renderingMode(RenderMode.GRAPHIC)
                                                        .sealData(SealData.builder()
                                                                .withName("刘清华的测试企业"))),
                                                PresetForm.builder()
                                                        .formType(FormType.MULTI_QRCODE_MARK)
                                                        .fileId("127091")
                                                        .scale(1.0f)
                                                        .height(100f)
                                                        .width(100f)
                                                        .initialValue(InitialValue.builder()
                                                                .renderingMode(RenderMode.GRAPHIC)
                                                                .qrcodeContent("this is a test"))
                                                        .revisable(true))

                        ))
                .returnUrl("https://www.baidu.com")
                .acceptDataType(AcceptDataType.URL)
                .customTag("C130018122503924" + System.currentTimeMillis())
                .build();
    }
}
