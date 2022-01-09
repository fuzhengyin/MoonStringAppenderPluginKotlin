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

可以通过在设置中指定xml 文件位置和prefix。（suffix 为可选项）

路径：Android Studio / Preferences / Tools / Extract To Special Xml

可以通过指定python 的方式，动态获取feature id。标准：
>可以接受一个参数，参数的内容是项目的地址\
结果需要通过打印到控制台的方式返回\
返回的格式为
> prefix/suffix

比如：

```python
# coding=utf-8
import sys

from git import Repo

path = "."
if len(sys.argv) > 1:
    path = sys.argv[1]
repo = Repo(path)
print(repo.active_branch.name)
```

## build

使用Jetbrains  Idea 编译

打包： Tasks/intellij/buildPlugin

## install

Android Studio / Preferences / Plugin / Install from Disk

