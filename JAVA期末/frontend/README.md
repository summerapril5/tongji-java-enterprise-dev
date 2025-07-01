# 项目前端
## 技术栈介绍
前端采用vue3框架，用户身份验证的token使用cookie来储存
## 项目文件介绍
apis文件夹中存放与后端连接的api，assents文件夹中存放图片资源，router中存放vue的页面路由配置，stores存放前端的持久化储存内容，utils存放一些工具函数，views中存放所有的页面代码。
## 页面介绍
AdminCenter：
管理员中心页面，可以进行独属于管理员的功能
AdminLogin：
管理员登录页面
Category：   
特定板块页面
Home：
主页面（搜索后也为此页面）
Layout：
在主页面、特定板块页面、特定帖子页面的顶端布局，包含搜索框和一些功能
Post：
特定帖子页面
UserCenter：
用户中心页面
UserHomepage：
用户主页页面（他人点击看到的页面）
UserLogin：
用户登录页面（包含登录与注册功能）