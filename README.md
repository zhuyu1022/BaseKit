# BaseKit
集成一些常用的小工具、扩展函数等，脱离基础架构
（纯个人测试、学习使用）

## 安装
在项目根目录的 build.gradle 添加仓库
```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```
然后在 module 的 build.gradle 添加依赖框架

```groovy
implementation 'com.github.zhuyu1022:BaseKit:0.1.1'
```

## 后续再考虑集成点 [Engine](https://github.com/liangjingkanji/Engine) 的内容

