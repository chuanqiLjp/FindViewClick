> 一个简单的找控件并可以设置OnClickListener的库

# 使用
1. 在根gradle文件中
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
2. 在Modle的gradle文件中
```
implementation 'com.github.chuanqiLjp:FindViewClick:v1.0.0'
```