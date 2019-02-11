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
                .setConnectNetTimeout(20)
                .setReadNetTimeout(20)
                .setWriteNetTimeout(20)
                .build();
//这句一定要有，否则有些功能会无法实现
AgileMVPFrame.init(this);
AgileMVPFrame.setConfigure(configure);
AgileMVPFrame.debug(true);//设置是否启动调试模式
```
4.使用MVP
所有MVP相关的类都在base包下，可以选择具体的M、V、P层进行继承，跟界面有关的类都在ui包下，可以选择对应的activity、fragment、dialogFragment进行继承。

(1).base包下:
view:		
		BaseView
	  	---BaseDialogView
	     	   ---BaseNetView
presenter:	
		BasePresenter
		---BaseAbstractPresenter
		   ---BaseDialogPresenter
		      ---BaseNetPresenter
			 ---BaseCountDownPresenter（具体业务Presenter）
model:		
		BaseModel
		---BaseAbstractModel
		   ---BaseNetModel
		      ---BaseTokenNetModel
listener:	
		IBaseListener
		---ICountDownListener（具体业务接口）
		
(2).ui包下:
activity:	
		BaseActivity
		---BaseFullImmerseScreenActivity
		   ---BasePresenterActivity
		      ---BaseToolbarPresenterActivity
			 ---VideoViewActivity（具体业务实现）
	        	 ---WebViewActivity（具体业务实现）
					
fragment:	
		BaseFragment
		---BasePresenterFragment
		   ---BaseToolbarPresenterFragment
			
dialogFragment:	
		BaseDialogFragment
		---BasePresenterDialogFragment
		
bottomSheetDialogFragment:	
		BaseBSDialogFragment
		---BasePresenterBSDialogFragment
				
(3).feature包下:
目前暂时提供了3个功能：
	json包：提供了对json的相关处理
	loadingDialogFragment：提供了加载Fragment的功能
	videoViewManager：提供了播放视频的相关功能
	
5.具体内容后续会进行补充
