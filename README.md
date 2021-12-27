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

todo：

1. 通过python 或者javascript 提供自定义字符串键的功能
