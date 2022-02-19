项目介绍

## 1开发环境和工具表
![image](https://github.com/yuanhang110/ZheyeProject/blob/main/%E5%9B%BE%E7%89%87/%E5%9B%BE%E7%89%870.jpg)

## 2系统用户角色分析

如图，问答社区系统的用户分为两类，普通用户和管理员用户，他们在后台管理系统中起到的作用不同，在系统中的角色也不同。

​                                     图3-1 系统相关角色

![image](https://github.com/yuanhang110/ZheyeProject/blob/main/%E5%9B%BE%E7%89%87/%E5%9B%BE%E7%89%871.jpg)

用户在哲也问答社区中可以对问题进行回答、关注[13]，对回答进行点赞和收藏，也可以查看自己回答的情感数据，是整个系统的主要使用者。

管理员用户是具有管理员账号的用户，从系统入口登陆后可以查看用户的回答并且进行修改和删除，删除错误还可以从回收站还原。相比于普通用户，管理员用户有更高的权限，可以实现批量删除修改回答。

## 3 系统需求分析

如图所示，本系统分为两大子系统，包括问答社区子系统和后台管理子系统。问答社区子系统主要由用户使用，是哲也问答社区系统的核心部分。

问答社区用例图

![图片2](C:\Users\xyh\Desktop\图片\图片2.jpg)后台管理社区用例图

![图片3](C:\Users\xyh\Desktop\图片\图片3.jpg)

#### 4系统架构设计

![图片4](C:\Users\xyh\Desktop\图片\图片4.jpg)

客户端：移动端设备或PC端浏览器可以通过应用APP可以通过内置的WebView等插件渲染H5页面或原生页面向用户展示界面信息。

前端：使用Vue 2.6和Element UI 2.0.8编写前端页面，Vue为MVVM架构。使用前端框架编写完页面后，在前端界面通过Axios调用后端的RESTFUL API接口，并使用JSON格式数据进行交互。

后端：后端使用Spring Boot框架，引入Jwt、E-Charts、Lombok、Swagger、Junit、Druid等组件。Jwt用于权限验证，Lombok用来简化代码编写，E-Charts用于可视化图标，Junit用于单元测试，Swagger用于生成接口文档。Druid为阿里巴巴开源的数据库连接池，集合了C3P0、DBCP、Proxool等连接池的优点，还加入了日志监控，有效的监控DB池连接和SQL的执行情况。

持久层框架使用MyBatis，它简化了编写操作数据库代码的开发过程。它包括Mapper类、POJO实体类和mapper.xml，主要完成数据持久化的工作，可以实现对数据库的CRUD操作

#### 5功能模块设计

![图片5](C:\Users\xyh\Desktop\图片\图片5.jpg)

#### 6系统实现效果

如视频
