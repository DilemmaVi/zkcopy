# zkcopy
## 简述

zkcopy是一款简单的zookeeper间数据复制工具，可以快速的从来源zk中的数据复制到目标zk中

## 使用方法

1. 源码启动
- 下载源码 git clone https://github.com/DilemmaVi/zkcopy.git
- 使用maven或者IDE工具运行main方法
- 需要注意有三个启动参数，来源zk地址，目标zk地址和复制路径

> 启动命令示例
```
java -jar zkcoy.jar 127.0.0.1:2181 127.0.0.1:2182 /path
```

2. 下载release进行启动

- release地址：[点击下载](https://github.com/DilemmaVi/zkcopy/releases/download/V1.0/zkcopy.zip)
- 下载后解压，双击start.bat启动，按照指示操作即可