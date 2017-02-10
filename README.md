# 「有干货」Gank.io 安卓客户端
干货集中营(gank.io)的非官方安卓客户端, 数据来源于[干货集中营](http://gank.io/)

## 介绍
「Gank」是 [Gank.io](http://gank.io) 第三方安卓客户端，UI设计来自于Github上其他Gank app.每天提供一张精选的妹纸图片，若干精选的Android，ios，web等相关的技术干货。

## 截图
![ugank1.gif](https://github.com/Developmc/Demo_Gank/blob/master/app/src/main/assets/Gank_1.gif)
![ugank2.gif](https://github.com/Developmc/Demo_Gank/blob/master/app/src/main/assets/Gank_2.gif)
![ugank3.gif](https://github.com/Developmc/Demo_Gank/blob/master/app/src/main/assets/Gank_3.gif)
![ugank4.gif](https://github.com/Developmc/Demo_Gank/blob/master/app/src/main/assets/Gank_4.gif)

## APP设计
###App包含下面几个页面:
* 主页显示今日的干货内容，分为All，Android,IOS,前端，瞎推荐，拓展资源六个模块
* 点击查看干货详情页面
* 搜索页面可以搜索干货页面
* 设置页面

###特征
* 单activity+多fragment；
* Retrofit+RxJava获取网络请求
* 观察者模式通知fragment刷新
* ToolBar、TabLayout+ViewPager、SwipeRefreshLayout、RecyclerView
* ViewPager下，fragment懒加载
* 日间夜间模式切换
* 清理图片缓存
* 默认不显示列表缩略图（可设置）
* Material Design 风格（未完全遵守）
* 沉浸式状态栏
* Glide-图片加载
* ButterKnife-View-注入
* MVP(完善中)

## 特别鸣谢
 [代码家](https://github.com/daimajia)  [干货集中营](http://gank.io/) 提供 API
