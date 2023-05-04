package cc.mrbird.febs.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 集成swagger2  解决前后端分离 弊端：不能及时协商+尽早解决的问题
 *      使用swagger总结:
 *          通过swagger 给一些比基奥难理解的接口或属性，增加注释信息
 *          接口文档实时更新
 *          可以在线测试
 *      安全问题:
 *          正式上线的时候  记得关闭swagger
 */
@Configuration//加载到springboot配置里面
@EnableSwagger2//开启swagger2
public class SwaggerConfig {
    /**
     * 配置swagger2
     * 注册一个bean属性
     * swagger2其实就是重新写一个构造器，因为他没有get set方法\
     * enable() 是否启用swagger false swagger不能再浏览器中访问
     * groupName()配置api文档的分组  那就注册多个Docket实例 相当于多个分组
     * @return
     */
    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("qlh")//组名称
                .enable(true)
                .select()
                /**
                 * RequestHandlerSelectors配置扫描接口的方式
                 *      basePackage 配置要扫描的包
                 *      any 扫描全部
                 *      none 不扫描
                 *      withClassAnnotation 扫描类上的注解
                 *      withMethodAnnotation 扫描方法上的注解
                 */
                .apis(RequestHandlerSelectors.basePackage("com.tinygray.madison.controller"))
                /**
                 * paths() 扫描过滤方式
                 *      any过滤全部
                 *      none不过滤
                 *      regex正则过滤
                 *      ant过滤指定路径
                 */
                //                .paths(PathSelectors.ant("/login/**"))
                .build();
    }

    /**
     * 配置swagger2信息 =apiInfo
     * @return
     */
    public ApiInfo apiInfo(){
        /*作者信息*/
        //        Contact contact = new Contact("XXX", "http://baidu.com", "email");
        Contact contact = new Contact("", "", "");
        return new ApiInfo(
                "XXX的API接口",
                "company接口",
                "V1.0",
                "urn:toVs",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

}