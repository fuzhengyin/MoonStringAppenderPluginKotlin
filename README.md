# MoonStringAppenderPluginKotlin

在指定的xml 文件中生成Android 的string

比如你的string 为

```
"hello"
```

会被替换为

```
getString(R.string.xxxx)
```

可以通过在设置中指定xml 文件位置和feature id
路径：Android Studio / Preferences / Tools / Extract To Special Xml

todo：

1. 通过python 或者javascript 提供自定义字符串键的功能

## build

使用Jetbrains  Idea 编译

打包： Tasks/intellij/buildPlugin

## install

Android Studio / Preferences / Plugin / Install from Disk

