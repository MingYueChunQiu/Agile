# AgileMVPFrame
a agile android framework for MVP.
一个自用的Android的MVP框架，提供公共框架、方法，进行快速开发。
1.Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2.Add the dependency
```
dependencies {
	        implementation 'com.github.MingYueChunQiu:AgileMVPFrame:最新版本'
	}
```

3.现在自已应用的application中添加
```
//框架配置信息，可以配置，也可以不配置使用库的默认值
AgileMVPFrameConfigure configure = new AgileMVPFrameConfigure.Builder()
                .setNetTimeout(20)
                .build();
//这句一定要有，否则有些功能会无法实现
AgileMVPFrame.init(this);
AgileMVPFrame.setConfigure(configure);
```
4.使用MVP
所有MVP相关的类都在base包下，可以选择具体的M、V、P层进行继承，跟界面有关的类都在ui包下，可以选择对应的activity、fragment、dialogFragment进行继承。

5.具体内容后续会进行补充
