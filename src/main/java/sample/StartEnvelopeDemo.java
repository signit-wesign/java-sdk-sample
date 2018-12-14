package sample;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.pojo.BaseFileData;
import cn.signit.sdk.pojo.CertData;
import cn.signit.sdk.pojo.Contact;
import cn.signit.sdk.pojo.EnvelopeBasicInfo;
import cn.signit.sdk.pojo.EnvelopeContentInfo;
import cn.signit.sdk.pojo.EnvelopeFile;
import cn.signit.sdk.pojo.EnvelopeParticipantInfo;
import cn.signit.sdk.pojo.InitialValue;
import cn.signit.sdk.pojo.PresetForm;
import cn.signit.sdk.pojo.Receiver;
import cn.signit.sdk.pojo.SealData;
import cn.signit.sdk.pojo.Sender;
import cn.signit.sdk.pojo.Signer.Position;
import cn.signit.sdk.pojo.Signer.Position.KeywordPosition;
import cn.signit.sdk.pojo.Signer.Position.RectanglePosition;
import cn.signit.sdk.pojo.WriteData;
import cn.signit.sdk.pojo.request.StartEnvelopeRequest;
import cn.signit.sdk.pojo.response.StartEnvelopeResponse;
import cn.signit.sdk.pojo.webhook.response.AbstractWebhookResponseData;
import cn.signit.sdk.pojo.webhook.response.WebhookResponse;
import cn.signit.sdk.type.AcceptDataType;
import cn.signit.sdk.type.AuthLevel;
import cn.signit.sdk.type.CertType;
import cn.signit.sdk.type.Direction;
import cn.signit.sdk.type.EnvelopeRoleType;
import cn.signit.sdk.type.EnvelopeType;
import cn.signit.sdk.type.FileType;
import cn.signit.sdk.type.FormType;
import cn.signit.sdk.type.ReceiverType;
import cn.signit.sdk.type.RenderMode;
import cn.signit.sdk.type.SecureLevel;
import cn.signit.sdk.util.HmacSignatureBuilder;

/**
 * 易企签 Java SDK 启动信封调用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行启动信封，示例代码中的姓名、手机、邮箱、签名文件、手写签名数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，url均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class StartEnvelopeDemo {
    public static void main(String[] args) throws SignitException {
        String appSecretKey = "sk9120dcdab8b05d08f8c53815dc953756";
        String appId = "1678bc2091000d861138f74aa51";// 794182811@qq.com
        // 国信易企签科技有限公司

        // String appUrl =
        // "http://10.10.9.70:2576/v1/open/verifications/enterprise";
        String appUrl = "http://10.10.9.148:7830/envelopes/start";

        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        client.setOauthUrl("http://10.10.9.70:2576/v1/oauth/oauth/token");

        StartEnvelopeRequest request = stastartEnvelopeLeastParams();
        System.out.println("request is :\n" + JSON.toJSONString(request, true));
        StartEnvelopeResponse response = client.execute(request);
        System.out.println("response is: \n" + JSON.toJSONString(response, true));

    }

    // 启动信封可使用的参数示例
    public static StartEnvelopeRequest stastartEnvelopeWithAllParams() {
        return StartEnvelopeRequest.builder()
                .basicinfo(EnvelopeBasicInfo.builder()
                        .title("title")
                        .subject("subject")
                        .type(EnvelopeType.ANY)
                        .authLevel(AuthLevel.LOW))
                .contentInfo(EnvelopeContentInfo.builder()
                        .files(EnvelopeFile.builder()
                                .id("cutomId1")
                                .sequence(1)
                                .data(BaseFileData.builder()
                                        .url("https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/quick_signature_with_fieldname.pdf"))
                                .fileName("file1")
                                .contentType(FileType.PDF)
                                .isAttached(false)))
                .participantInfo(EnvelopeParticipantInfo.builder()
                        .sender(Sender.builder()
                                .name("李XX")
                                .contact(Contact.builder()
                                        .email("1416694870@qq.com")))
                        .receivers(Receiver.builder()// 参与者可以设置多个，此处仅设置了一个
                                .name("王五")
                                .contact(Contact.builder()
                                        .phone("13795956607"))
                                .secureLevel(SecureLevel.DISPOSABLE_CERT)
                                .type(ReceiverType.SIGNER)
                                .roleType(EnvelopeRoleType.ENTERPRISE_MEMBER)
                                .needForm(false)
                                .assignedSequence(1)
                                .authLevel(AuthLevel.LOW)
                                .enterpriseName("enterprise1")
                                .presetForms(PresetForm.builder()
                                        .formType(FormType.SEAL_SIGN)
                                        .fileId("customefieldID1")
                                        .position(Position.builder()
                                                .withRectanglePosition(RectanglePosition.builder()
                                                        .withLrx(500.0f)
                                                        .withLry(50.0f)
                                                        .withUlx(400.0f)
                                                        .withUly(100.0f)
                                                        .withPage(1))
                                                .withKeywordPosition(KeywordPosition.builder()
                                                        .withWidth(100.0f)
                                                        .withRelativeWidthRatio(1.0f)
                                                        .withHeight(50.0f)
                                                        .withRelativeHeightRatio(1.0f)
                                                        .withDirection(Direction.RIGHT)
                                                        .withOffset(1.0f)
                                                        .withRelativeOffsetRatio(0.1f)
                                                        .withKeyword("甲方盖章处")
                                                        .withPages("all"))
                                                .withFieldName("field1"))
                                        .required(true)
                                        .scale(1.0f)
                                        .initialValue(InitialValue.builder()
                                                .sealData(SealData.builder()
                                                        .withUrl("https://app.signit.cn/seal/sealId001.png")
                                                        .withName("XX印章"))
                                                .certData(CertData.builder()
                                                        .url("https://app.signit.cn/cert/certId001.png")
                                                        .wsid("certId001"))
                                                .certType(CertType.DISPOSABLE_CERT)
                                                .renderingMode(RenderMode.GRAPHIC)
                                                .location("四川省绵阳市XX区XX路XX号")
                                                .contact("18600000000")
                                                .locked(false)
                                                .reason("XX原因"))
                                        .revisable(false)
                                        .tagId("调用方定义标识当前表单（json字符串）")
                                        .corrected(false),
                                        PresetForm.builder()
                                                .formType(FormType.WRITE_SIGN)
                                                // .name()
                                                .fileId("customefieldID2")
                                                .position(Position.builder()
                                                        .withRectanglePosition(RectanglePosition.builder()
                                                                .withLrx(500.0f)
                                                                .withLry(50.0f)
                                                                .withUlx(400.0f)
                                                                .withUly(100.0f)
                                                                .withPage(1))
                                                        .withKeywordPosition(KeywordPosition.builder()
                                                                .withWidth(100.0f)
                                                                .withRelativeWidthRatio(1.0f)
                                                                .withHeight(50.0f)
                                                                .withRelativeHeightRatio(1.0f)
                                                                .withDirection(Direction.RIGHT)
                                                                .withOffset(1.0f)
                                                                .withRelativeOffsetRatio(0.1f)
                                                                .withKeyword("甲代表人（签字）:")
                                                                .withPages("all"))
                                                        .withFieldName("field2"))
                                                .required(true)
                                                .scale(1.0f)
                                                .initialValue(InitialValue.builder()
                                                        .writeData(WriteData.builder()
                                                                .url("https://app.signit.cn/seal/sealId001.png")
                                                                .name("XX印章"))
                                                        .certData(CertData.builder()
                                                                .url("https://app.signit.cn/cert/certId001.png")
                                                                .wsid("certId001"))
                                                        .certType(CertType.DISPOSABLE_CERT)
                                                        .renderingMode(RenderMode.GRAPHIC)
                                                        .location("四川省绵阳市XX区XX路XX号")
                                                        .contact("18600000000")
                                                        .locked(false)
                                                        .reason("XX原因"))
                                                .revisable(false)
                                                .tagId("调用方定义标识当前表单（json字符串）")
                                                .corrected(false))

                        ))
                .returnUrl("https://return.qq.com/XXXX")
                .acceptDataType(AcceptDataType.URL)
                .customTag("cutomtag")
                .build();
    }

    // 使用最少参数启动一个信封 1个参与者，一个发送者，使用关键字定位
    public static StartEnvelopeRequest stastartEnvelopeLeastParams() {
        return StartEnvelopeRequest.builder()
                .basicinfo(EnvelopeBasicInfo.builder()
                        .title("title")
                        .subject("subject")
                        .type(EnvelopeType.ANY)
                        .authLevel(AuthLevel.LOW))
                .contentInfo(EnvelopeContentInfo.builder()
                        .files(EnvelopeFile.builder()
                                .id("cutomId1")
                                .sequence(1)
                                .data(BaseFileData.builder()
                                        .url("https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/quick_signature_with_fieldname.pdf"))
                                .fileName("sign_file_test_field_name")
                                .contentType(FileType.PDF)
                                .isAttached(false)))
                .participantInfo(EnvelopeParticipantInfo.builder()
                        .sender(Sender.builder()
                                .name("周雪梅")
                                .contact(Contact.builder()
                                        .email("794182811@qq.com")))
                        .receivers(Receiver.builder()
                                .name("王五")
                                .contact(Contact.builder()
                                        .phone("13795956607"))
                                .secureLevel(SecureLevel.DISPOSABLE_CERT)
                                .type(ReceiverType.SIGNER)
                                .roleType(EnvelopeRoleType.ENTERPRISE_MEMBER)
                                .needForm(false)
                                .assignedSequence(1)
                                .authLevel(AuthLevel.LOW)
                                .enterpriseName("王五的企业")
                                .presetForms(PresetForm.builder()
                                        .formType(FormType.WRITE_SIGN)
                                        .fileId("customefieldID1")
                                        .position(Position.builder()
                                                .withKeywordPosition(KeywordPosition.builder()
                                                        .withKeyword("甲代表人（签字）:")
                                                        .withDirection(Direction.RIGHT)
                                                        .withScale(1.5f)
                                                        .withRelativeOffsetRatio(0.3f)
                                                        .withRelativeHeightRatio(1.2f)
                                                        .withRelativeWidthRatio(0.4f)
                                                        .withPages("last")))
                                        .required(false)
                                        .scale(1.0f)
                                        // .initialValue(InitialValue.builder()
                                        // .writeData(WriteData.builder()
                                        // .url("https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/wangwu.png")
                                        // .name("王五手写签名"))
                                        // .certData(CertData.builder()
                                        // .url("https://app.signit.cn/cert/certId001.png")
                                        // .wsid("certId001"))
                                        // .certType(CertType.DISPOSABLE_CERT)
                                        // .renderingMode(RenderMode.GRAPHIC)
                                        // .location("四川省绵阳市XX区XX路XX号")
                                        // .contact("18600000000")
                                        // .locked(false)
                                        // .reason("XX原因"))
                                        .revisable(false)
                                        .tagId("调用方定义标识当前表单（json字符串）")
                                        .corrected(false))

                        ))
                .returnUrl("https://return.qq.com/XXXX")
                .acceptDataType(AcceptDataType.URL)
                .customTag("cutomtag")
                .build();
    }

    // 使用关键字定位手写签名位置
    // 测试文件
    // https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/quick_signature_with_fieldname.pdf
    private static Receiver setReceiverForWriteSignWithKeyword() {
        // 使用关键字定位手写签名的位置（印章签名是同理的）--- 这是最简配置
        return Receiver.builder()
                .name("王五")
                .contact(Contact.builder()
                        .phone("13795956607"))
                .type(ReceiverType.SIGNER)
                .roleType(EnvelopeRoleType.PERSON)
                .needForm(true)
                .presetForms(PresetForm.builder()
                        // .formType(FormType.WRITE_SIGN) 印章签名
                        .formType(FormType.WRITE_SIGN)
                        .fileId("customKeywordLocationWithWriteSignFieldId1")
                        .position(Position.builder()
                                .withKeywordPosition(/*
                                                      * new
                                                      * KeywordPosition(null,
                                                      * 0.3f, null, 1.2f,
                                                      * Direction.RIGHT, null,
                                                      * 0.12f, "甲代表人（签字）:",
                                                      * 1.5f, "last")//通过构造函数初始化
                                                      */
                                        KeywordPosition.builder()
                                                .withRelativeWidthRatio(0.3f)
                                                .withRelativeHeightRatio(1.2f)
                                                .withDirection(Direction.RIGHT)
                                                .withRelativeOffsetRatio(0.12f)
                                                .withKeyword("甲代表人（签字）:")
                                                .withScale(1.5f)
                                                .withPages("last")))
                        .scale(1.0f)
                        .tagId("customKeywordLocationWithWriteSignTagId1"))
                .assignedSequence(1)
                .build();
        // https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/zhangsan.png

    }

    // 直接设置手写签名位置
    // 测试文件https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/quick_signature_with_fieldname.pdf
    private static Receiver setReceiverForWriteSignWithRectangle() {
        // 确定手写签名数据位置（印章签名是同理的）--- 这是最简配置
        return Receiver.builder()
                .name("王五")
                .contact(Contact.builder()
                        .phone("13795956607"))
                .type(ReceiverType.SIGNER)
                .roleType(EnvelopeRoleType.PERSON)
                .needForm(true)
                .presetForms(PresetForm.builder()
                        // .formType(FormType.WRITE_SIGN) 印章签名
                        .formType(FormType.WRITE_SIGN)
                        .fileId("customKeywordLocationWithWriteSignFieldId1")
                        // 确定签名位置
                        // 设置小技巧：针对pdf的文档中ulx,uly：使用截图工具从当前页文档左上角顶点拉到签名框右上角，得到截图大小，然后*0.7；对lrx和lry同理
                        .position(Position.builder()
                                .withRectanglePosition(/*
                                                        * new
                                                        * RectanglePosition(400
                                                        * * 0.75f, 630 * 0.75f,
                                                        * 320 * 0.75f, 600 *
                                                        * 0.75f, 1.0f,
                                                        * 1)//通过构造函数初始化
                                                        */
                                        RectanglePosition.builder()// 乙代表人（签字）：
                                                .withLrx(623 * 0.75f)
                                                .withLry(715 * 0.75f)
                                                .withUlx(552 * 0.75f)
                                                .withUly(670 * 0.75f)
                                                .withPage(2)))
                        .scale(1.0f)
                        .tagId("customKeywordLocationWithWriteSignTagId1"))
                .assignedSequence(1)
                .build();

    }

    // 使用表单域域名定位手写签名位置--请确保签名文件格式为pdf且拥有对应域名的签名域
    // 利用签名域的测试文件：https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/quick_signature_with_fieldname.pdf
    private static Receiver setReceiverForWriteSignWithFieldname() {

        // 确定手写签名数据位置（印章签名是同理的）--- 这是最简配置
        return Receiver.builder()
                .name("王五")
                .contact(Contact.builder()
                        .phone("13795956607"))
                .type(ReceiverType.SIGNER)
                .roleType(EnvelopeRoleType.PERSON)
                .needForm(true)
                .presetForms(PresetForm.builder()
                        // .formType(FormType.WRITE_SIGN) 印章签名
                        .formType(FormType.WRITE_SIGN)
                        .fileId("customKeywordLocationWithWriteSignFieldId1")
                        .position(Position.builder()
                                .withFieldName("王五签名"))// pdf文件中的签名域名称
                        .scale(1.0f)
                        .tagId("customKeywordLocationWithWriteSignTagId1"))
                .assignedSequence(1)
                .build();
    }

}
