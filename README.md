### 基于SpringBoot + Vue的产品售后服务系统

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios
报表：Spread.js

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok 支付的话用支付宝的沙箱

#### 管理员
1、用户管理
2、员工管理
3、评价管理
4、服务类型
5、预约管理
6、维修信息
7、产品信息
8、缴费记录
9、工单管理
10、公告信息

#### 客户
1.我的工单
2.个人信息
3.缴费记录
4.维修信息

#### 维修员
1.我的任务
产品售后服务系统

我国信息产业、企业售后服务发展迅速,电商的发展超出了所有人都想象，与此同时也带来了诸多的问题。飞速发展的交易额，带来了大量的GDP增长，然而光鲜的背后是每天无数客服手忙脚乱的为顾客服务。由于顾客对自身生活质量要求越来越高，售后管理业必须随着人们生活水平的提高而改变。伴随着购物规模的不断扩大，售后的各项反馈、投诉，对售后的要求等都将越来越复杂，售后管理系统的自身的管理工作量也将越来越大。人工服务不能做到及时有效的反映，以及做出正确的应对以及保存，迫切需要一个系统来解决这些问题。本项目的出发点力在解决这一困难！

#### 前台启动方式

安装所需文件 yarn install 
运行 yarn run dev

#### 后端启动方式

1.首先启动redis，进入redis目录终端。输入redis-server回车
2.导入sql文件，修改数据库与redis连接配置
3.idea中启动后端项目

#### 默认后台账户密码

[管理员]
admin
1234qwer

[客户]
fank
1234qwer

#### 项目截图

|  |  |
|---------------------|---------------------|
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357817058.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357975247.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357799210.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357961409.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357774358.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357946190.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357759608.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357926218.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357743535.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357909448.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357718811.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357890366.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357692588.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357874138.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357859761.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1673357829777.jpg) |


#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解`

#### 其它资源

[2024年答辩顺利通过](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年答辩顺利通过](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年答辩通过率100%](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)


#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

More info: [悲伤的橘子树](https://berserker287.github.io/)


关键词： Java技术；产品售后；管理系统

#### 系统管理模块
该模块主要用于对系统进行管理，包括系统权限的设置，系统管理员的增加删除，密码的修改，用户的管理等。

系统权限是系统操作的先决条件。设计系统必须对使用系统的人作出分类，不同的人能够使用的权限是不同的，管理员权限不能随意授予，只能交给公司高层使用。普通用户使用的是普通权限。他们只能对自己的信息进行修改查询，不能对其他人进行查询修改，否则信息泄露，公司会变成一团糟。

### 服务信息管理模块
该模块主要是对消费者的信息进行管理维护，对客户信息进行数据建档,包括售后服务信息的添加、删除和修改。客户在购买之后，可以对客户信息进行登记录入系统，然后对客户进行跟踪服务，客户可对服务进行点评，提出建议，公司可不定时对客户发放问卷，进行满意度调查。


### 系统详细设计
#### 后台数据库设计
在数据库被推出的十多年来，数据库管理系统得到了迅猛的发展，从以前简单的应用程序发展成为了拥有自己专属的语言，专门的理论，专业的研究人员的系统软件。通过数据库管理系统，将数据库中的数据变得结构清晰，关系明确，冗余度低，拥有较高的程序独立性，容易增减，条理清楚，易于被相关的系统或者程序调用，因此，不论是大型的系统软件还是小型的应用软件都建立在数据库的基础之上，大大降低了数据保存的难度，使得计算技术得以广泛的推广。在近几年来逐渐流行的大数据分析，也是建立在数据库的基础上，对海量的数据进行相关的分析。随着计算机软件的广泛推广，对于数据库的性能要求越来越高，反过来也促进了数据库技术的发展。通过不同的设计方式设计出来的数据库，在各个方面的优劣也不相同。比如：在大型数据处理系统中，不仅对数据库的稳定性有极高的要求，性能方面也不能差的太多，这就需要使稳定性和高性能达到一个平衡；然而在微型计算机系统中，数据规模不大，需要的是能够及时的响应用户的相关操作，因此对性能就有极高的要求。随着对数据库的研究越来越正式化，科学化，投入不断地增加，已经使得现在的数据库系统从以前的单纯处理加工数据为中心转变为围绕共享的数据库为中心，这种数据库的改变，更加的方便了数据的集中管理，有效的挖掘出数据中蕴含的更加丰富的信息，有利于应用程序对数据的调用，也方便了程序本身的开发和维护，通过对相同的数据整合，不仅节省了数据库空间，更使得数据本身之间的联系更加明确清晰。

同时，数据库中的相关数据，都是从现实生活中抽象得来的。通过对现实世界的实体模型的抽象化，将实体模型的各种属性都量化为一个个具体的数字，不同的数据，表明了实体模型的差异性，相似升值相同的数据，表明了实体模型之间的相似性。所以，通过设计不同的数据库，不同的表，视图，行，列就可以把实体模型转换为数据库模型。所以，数据库设计的好坏，也直接影响了能否完整的将一个实体模型所具有的特点直接彰显出来，突出实体模型之间的差异性。

综上所述，数据库设计是数据库的核心，是数据库管理系统的核心任务，是评价一个系统好坏优劣的主要标准，是能否直截了当地将数据之间的关系展现出来，是能否和现实世界完美的结合起来的主要手段。数据库设计的不好，小则影响了系统中某一个功能模块的流畅运行，大则是的整个系统都不能达到要求，造成巨大的损失。数据库设计的不好，其他一切用来提升数据库性能的方法和手段都会收效甚微。在数据库设计不断发展的进程中，人们逐渐提出了一系列标准和方法来规范数据库的设计。在现在流行的关系型数据中，这些标准和方法叫做范式，换句话说，范式就是某一种关系的集合。现在人们遵循的大多为第三范式，即：在某个数据库表中不能包含其他表中的非主关键字。这种范式的采用，降低了数据冗余，明确了表和表之间的关系，提升了数据库相关操作的性能。

