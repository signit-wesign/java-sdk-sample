
# 易企签 Java SDK 示例

易企签Java sdk调用示例，包括**快捷签署**，**提交企业实名认证（即：开通在线电子签约服务接口（企业））**，**提交个人实名认证（即：开通在线电子签约服务接口(个人)）**，**启动信封签署流程**,**快捷会签**,**信封资源-标准签手动模式（即：自由签）共 (5) 个:[创建签署流程,托管签署流程到易企签,签署流程中追加新的签署方,撤销指定签署流程,结束整个签署流程]**等功能

# 环境要求
Java 1.6 or later.

# 使用前准备
### 1、申请应用
申请应用需要已经**实名认证**过的**企业账号**，如尚未拥有账号，请前往[https://www.signit.cn](https://www.signit.cn) 注册申请并进行认证。

已经拥有账号的用户请[登录](https://app.signit.cn/login)后切换到企业账号下，从"企业"的"开放API"中进入开发者中心创建应用。

### 2、设置webhook地址
sdk中部分功能需要依托webhook传递数据，所以需要设置一个webhook地址。如果webhook地址测试不通过，服务端将无法返回处理后的数据给客户端。

为了方便开发者进行调试，可以访问[https://webhook.site/](https://webhook.site/)生成URL地址作为webhook地址，如[https://webhook.site/54972912-22b5-4894-a640-3069671404ed](https://webhook.site/54972912-22b5-4894-a640-3069671404ed)，调用应用功能（如：“快捷签署”）成功后，就会实时查看接收到的webhook信息

# 安装方式


### Maven

    <dependency>
		<groupId>cn.signit.sdk</groupId>
		<artifactId>signit-java-sdk</artifactId>
		<version>2.6.0</version>
	</dependency>

### 源码
如需使用源码，请前往[易企签官方github](https://github.com/signit-wesign/java-sdk)下载



# 如何使用
### 1、快捷签署

无需将文件上传到易企签平台，只需要在sdk中设置待签名的文件以及签名数据，调用快捷签署接口，即可在开放平台设置的webhook地址中的接收到签名后的文件

[快捷签署调用示例](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/QuickSignatureDemo.java)

### 2、提交企业实名认证（即：开通在线电子签约服务接口(企业)）
企业实名认证完整流程：
- 调用方通过调用接口提交待认证企业信息到易企签平台
- 提交成功后将向提交的企业信息中的法人或者经办人手机号发送短信，法人或经办人需要点击短信中链接开始进行认证。
- 认证过程中，当易企签验证企业信息以及法人或经办人信息属实且有效后，会向企业对公银行账号打款0.01元，并在交易备注中附上4位或6位验证码，同时会向法人或经办人手机号中发送相应提示短信。企业法人或经办人获取到4位或6位验证码后，点击提示短信中的链接进行最后的银行验证码验证。验证通过后即为企业实名认证成功
- 完成上述操作后，即可开通电子签约服务

[提交企业实名认证调用示例](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/EnterpriseVerifyDemo.java)

### 3、提交个人实名认证（即：开通在线电子签约服务接口(个人)）
个人实名认证完整流程：
-调用方通过接口提交待认证个人信息到易企签平台
-提交成功后将向提交的待认证的个人联系方式发送消息，待认证的个人需要点消息中的链接开始认证

[提交个人实名认证调用示例](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/PersonVerifyDemo.java)


### 4、启动信封签署流程
无需进入易企签平台发起信封，调用方通过设置签署流程以及签署人信息，即可发起信封。

[启动信封签署流程调用示例](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/StartEnvelopeDemo.java)

### 5、解析webhook响应数据
在示例中，我们提供对webhook响应数据进行校验和解析的方法。并给出了一种简便的校验和解析方式。

### 6、快捷会签
多个人在一个文档上签字，开发人员只需指定签字区域，无需计算每个签署位置坐标即可实现签字自动排版

[快捷会签调用示例](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/m                                                                                                                                                                            ain/java/sample/CountersignatureSignDemo.java)

[校验并解析webhook响应数据](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/WebhookResponseParseDemo.java)

[通过HttpServletRequest校验并解析webhook响应数据](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/WebhookResponseParseByRequestDemo.java)

[校验和并解析各类型webhook响应数据](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/webhook/response)

### 7、信封资源-标准签手动模式（即：自由签）共 (5) 个
将创建签署流程，托管签署流程到易企签，签署流程中追加新的签署方，撤销指定签署流程，结束整个签署流程等接口解耦合，开发人员可以根据业务需求选择性的使用所提供接口。  

[创建签署流程调用示例](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/CreateSignProcessDemo.java)  

[托管签署流程到易企签调用示例](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/EntrustSignProcess2WeSignDemo.java)  
[托管签署流程到易企签webhook响应数据](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/webhook/response/ParseEntrustSignProcess2WeSignDemo.java)  

[签署流程中追加新的签署方调用示例](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/AppendEnvelopeParticipantsDemo.java)  

[撤销指定签署流程调用示例](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/RevokeSignProcessDemo.java)  
[撤销指定签署流程webhook响应数据](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/webhook/response/ParseRevokeSignProcessDemo.java)  

[结束整个签署流程调用示例](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/EndSignProcessDemo.java)  
[结束整个签署流程webhook响应数据](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/webhook/response/ParseEndSignProcessDemo.java)  



