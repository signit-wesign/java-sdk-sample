package sample.relative;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.pojo.*;
import cn.signit.sdk.pojo.request.relative.UserPersonRelativeResquest;
import cn.signit.sdk.pojo.response.relative.UserPersonRelativeResponse;
import cn.signit.sdk.util.FastjsonDecoder;
import com.alibaba.fastjson.JSON;

/**
 * 易企签 Java SDK 添加相对方个人账户用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行添加相对方个人账户，示例代码中的姓名、手机、邮箱、证件号等数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，url均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class AddUserPersonRelativeDemo {
    public static void main(String[] args) throws SignitException {
        String appSecretKey = "skc8959ea29ec6fefdc3d7e3e158dc6e77";
        String appId = "186a6bbd10500ff63263cdd5781";

        String appUrl = "http://10.10.1.39:2576/v1/open/person-relatives/create-by-third-party-user-id";// 测试环境使用的地址，生产环境时，应该使用上面一个appUrl

        // step1: 初始化易企签开放平台客户端
        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        client.setOauthUrl("http://10.10.1.39:2576/v1/oauth/oauth/token");// 测试环境需要手动设置oauthUrl，生产环境不用设置

        // step2: 使用SDK封装请求数据
        UserPersonRelativeResquest request = addUserPersonRelative();
        System.out.println("request is :\n" + JSON.toJSONString(request, true));

        // step3: 执行请求,获得响应
        UserPersonRelativeResponse response = null;
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

    // 添加一个相对方个人账户
    public static UserPersonRelativeResquest addUserPersonRelative() {
        return UserPersonRelativeResquest.builder()
                .uprThirdPartyUserId("U1004")//第三方平台个人账户唯一ID
                .uprName("章晴伯")//姓名
                .uprIdCode("445322196710089213")//证件号
                .uprIdType("SECOND_GENERATION_IDCARD")//证件类型，具体见UserPersonRelativeInfoIdType枚举类
                .uprPhone("19900000003")//手机号
                .uprEmail("1000@qq.com")//邮箱
                .customTag("C130018100000005")//调用方自定义标识，易企签会原封不动返回
                .build();
    }

}
