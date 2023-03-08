package sample.relative;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.pojo.ErrorResp;
import cn.signit.sdk.pojo.request.relative.UserEnterpriseRelativeResquest;
import cn.signit.sdk.pojo.response.relative.UserEnterpriseRelativeResponse;
import cn.signit.sdk.util.FastjsonDecoder;
import com.alibaba.fastjson.JSON;

/**
 * 易企签 Java SDK 添加相对方企业账户用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行添加相对方企业账户，示例代码中的姓名、手机、邮箱、证件号等数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，url均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class AddUserEnterpriseRelativeDemo {
    public static void main(String[] args) throws SignitException {
        String appSecretKey = "skc8959ea29ec6fefdc3d7e3e158dc6e77";
        String appId = "186a6bbd10500ff63263cdd5781";

        String appUrl = "http://10.10.1.39:2576/v1/open/organization-relatives/create-by-third-party-user-id";// 测试环境使用的地址，生产环境时，应该使用上面一个appUrl

        // step1: 初始化易企签开放平台客户端
        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        client.setOauthUrl("http://10.10.1.39:2576/v1/oauth/oauth/token");// 测试环境需要手动设置oauthUrl，生产环境不用设置

        // step2: 使用SDK封装请求数据
        UserEnterpriseRelativeResquest request = addUserEnterpriseRelative();
        System.out.println("request is :\n" + JSON.toJSONString(request, true));

        // step3: 执行请求,获得响应
        UserEnterpriseRelativeResponse response = null;
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

    // 添加一个相对方企业账户
    public static UserEnterpriseRelativeResquest addUserEnterpriseRelative() {
        return UserEnterpriseRelativeResquest.builder()
                .uerThirdPartyUserId("E1002")//第三方平台机构（企业）账户唯一ID
                .uerName("测试企业二呀")//姓名
                .uerIdCode("12345678900000000M")//统一信用编号
                .uerIdType("UNIFIED_SOCIAL_CREDIT_CODE")//证件类型，具体见UserEnterpriseRelativeInfoIdType枚举类
                .uerOrgLegalIdCode("510781197210242226")//法人证件号
                .uerOrgLegalName("浦月")//法人姓名
                .customTag("E130018100000002")//调用方自定义标识，易企签会原封不动返回
                .build();
    }

}
