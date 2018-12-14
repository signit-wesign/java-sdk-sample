package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.http.Authentication;
import cn.signit.sdk.pojo.FileData;
import cn.signit.sdk.pojo.SealData;
import cn.signit.sdk.pojo.SignatureRequest;
import cn.signit.sdk.pojo.SignatureResponse;
import cn.signit.sdk.pojo.Signer;
import cn.signit.sdk.pojo.Signer.Data;
import cn.signit.sdk.pojo.Signer.Position;
import cn.signit.sdk.pojo.Signer.Position.KeywordPosition;
import cn.signit.sdk.pojo.Signer.Position.RectanglePosition;
import cn.signit.sdk.pojo.Signer.SignerInfo;
import cn.signit.sdk.type.AcceptDataType;
import cn.signit.sdk.type.Direction;

/**
 * 易企签 Java SDK 快捷签署调用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行快捷签署，示例代码中的姓名、手机、邮箱、签名文件、手写签名数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，url均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 
 */
public class QuickSignatureDemo {

    public static void main(String[] args) {
        String appId = "164ea4a9c800242ac130007de41";
        String appSecretKey = "sk216a33d789f165aa26d01526023d903a";
        // 测试环境，生产环境不用设置url
        String url = "http://10.10.9.222:2576";

        // 签名文件
        FileData fileData = new FileData(
                "https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/quickSignature.txt");
        // 签名的文件生成一页的文档
        fileData.setEnableSinglePage(true);

        // 详细的签名数据
        List<Signer> signDetails = new ArrayList<Signer>();
        // 使用关键字定位手写签名位置
        signDetails.add(setSignerForWriteSignWithKeyword());
        // 直接设置手写签名位置
        signDetails.add(setSignerForWriteSignWithRectangle());
        // 使用关键字定位签章位置
        signDetails.add(setSignerForSealSignWithKeyword());
        // 在签名域中签名
        // signDetails.add(setSignerForWriteSignWithFieldname());

        // 设置请求参数
        SignatureRequest request = SignatureRequest.builder()
                .withAcceptDataType(AcceptDataType.URL)
                .withFileData(fileData)
                .withSignDetails(signDetails)
                .build();

        // 发送请求
        try {
            Authentication auth = new Authentication(appId, appSecretKey);
            SignitClient client = new SignitClient(auth, url);
            // SignitClient client = new SignitClient(auth);生产环境可以使用这个
            SignatureResponse response = client.sendSignatureRequest(request);
            System.out.println("Sending request is success:" + response.isSuccess());
        } catch (SignitException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 使用关键字定位手写签名位置
    private static Signer setSignerForWriteSignWithKeyword() {
        // 确定签名数据--这里使用的是手写签名数据
        SealData writeData = SealData.builder()
                .withUrl("https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/zhangsan.png")
                .build();
        Data data = Data.build()
                .withWriteData(writeData)
                .build();
        // 确定签名位置
        Position position = Position.builder()
                .withKeywordPosition(new KeywordPosition(null, 0.3f, null, 1.2f, Direction.RIGHT, null, 0.12f,
                        "甲代表人（签字）:", 1.5f, "last"))
                .build();
        // 设置签名人信息--用于颁发证书
        SignerInfo signerInfo = SignerInfo.builder()
                .withContact("12345678@qq.com")
                .withName("张三")
                .build();
        // 完整的一个签署信息
        Signer signer = Signer.builder()
                .withSequence(1)
                .withData(data)
                .withPosition(position)
                .withSignerInfo(signerInfo)
                .build();

        return signer;
    }

    // 直接设置手写签名位置
    private static Signer setSignerForWriteSignWithRectangle() {
        // 确定签名数据--这里使用的是手写签名数据
        SealData writeData = SealData.builder()
                .withUrl("https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/lisi.png")
                .build();
        Data data = Data.build()
                .withWriteData(writeData)
                .build();
        // 确定签名位置
        // 设置小技巧：针对pdf的文档：ulx,uly：使用截图工具从文档左上角顶点拉到签名框右上角，得到截图大小，然后*0.75，对lrx和lry同理
        Position position = Position.builder()
                .withRectanglePosition(
                        new RectanglePosition(400 * 0.75f, 630 * 0.75f, 320 * 0.75f, 600 * 0.75f, 1.0f, 1))
                .build();
        // 设置签名人信息--用于颁发证书
        SignerInfo signerInfo = SignerInfo.builder()
                .withContact("12345678@qq.com")
                .withName("李四")
                .build();
        // 完整的一个签署信息
        Signer signer = Signer.builder()
                .withSequence(1)
                .withData(data)
                .withPosition(position)
                .withSignerInfo(signerInfo)
                .build();

        return signer;

    }

    // 使用表单域域名定位手写签名位置--请确保签名文件格式为pdf且拥有对应域名的签名域
    // 利用签名域的测试文件：https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/quick_signature_with_fieldname.pdf
    private static Signer setSignerForWriteSignWithFieldname() {
        // 确定签名数据--这里使用的是手写签名数据
        SealData writeData = SealData.builder()
                .withUrl("https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/wangwu.png")
                .build();
        Data data = Data.build()
                .withWriteData(writeData)
                .build();
        // 确定签名位置
        Position position = Position.builder()
                .withFieldName("王五签名")
                .build();
        // 设置签名人信息--用于颁发证书
        SignerInfo signerInfo = SignerInfo.builder()
                .withContact("12345678@qq.com")
                .withName("王五")
                .build();
        // 完整的一个签署信息
        Signer signer = Signer.builder()
                .withSequence(1)
                .withData(data)
                .withPosition(position)
                .withSignerInfo(signerInfo)
                .build();

        return signer;
    }

    // 使用关键字定位签章位置
    private static Signer setSignerForSealSignWithKeyword() {
        // 确定签名数据--这里使用的是签章数据
        SealData sealData = SealData.builder()
                .withUrl("https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/testSeal.png")
                .build();
        Data data = Data.build()
                .withSealData(sealData)
                .build();
        // 确定签名位置
        Position position = Position.builder()
                .withKeywordPosition(
                        new KeywordPosition(75f, null, 75f, null, Direction.CENTER, null, null, "甲方盖章处", 1.0f, "last"))
                .build();
        // 设置签名人信息--用于颁发证书
        SignerInfo signerInfo = SignerInfo.builder()
                .withContact("12345678@qq.com")
                .withName("赵六")
                .build();
        // 完整的一个签署信息
        Signer signer = Signer.builder()
                .withSequence(3)
                .withData(data)
                .withPosition(position)
                .withSignerInfo(signerInfo)
                .build();

        return signer;
    }

}
