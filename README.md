# Agile
a agile android framework for MVP.
一个Android高效框架，提供公共框架、方法，进行敏捷开发。

    最新v0.4.10:
    	1.优化代码</br>
    	2.优化SoftInputKeyBoardHelper</br>
	
    v0.4.8:1.新增BatteryHelper</br>
    	2.优化代码</br>
    v0.4.2:完善功能</br>
    v0.4:1.增加圆角图形、圆角WebView、无限循环ViewPager等自定义控件
	 2.调整优化状态视图模块
	 3.优化框架代码
    v0.3.48:重构部分框架代码，统一方法调用，提升安全性</br>
    v0.3.39:重构框架部分内容，优化代码</br>
    v0.3.33:</br>
    v0.3.32:</br>
    v0.3.31:</br>
	1.优化调整</br>
    v0.3.30:</br>
    	1.新增FTP和Socket功能模块</br>
    	2.调整api对外提供依赖包</br>

已重构架构，规范命名，引入Kotlin
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
	        implementation 'com.github.MingYueChunQiu:Agile:最新版本'
	}
```

3.现在自已应用的application中添加
```
//框架配置信息，可以配置，也可以不配置使用库的默认值
AgileFrameConfigure configure = new AgileFrameConfigure.Builder()
                .setNetworkConfig(new AgileNetworkConfig.Builder()
                        .setConnectNetTimeout(20)
                        .setReadNetTimeout(20)
                        .setWriteNetTimeout(20)
                        .build())
                .build();
//这句一定要有，否则有些功能会无法实现
Agile.init(this);
Agile.setConfigure(configure);
Agile.debug(true);//设置是否启动调试模式
```
4.使用MVP
所有MVP相关的类都在base包下，可以选择具体的M、V、P层进行继承，跟界面有关的类都在ui包下，可以选择对应的activity、fragment、dialogFragment进行继承。

(1).base包下:

```
view:		
		IBaseView<P extends IBasePresenter>
	  	----IBaseStatusView<P extends BaseAbstractStatusViewPresenter>
	     	    ----IBaseDataView<P extends BaseAbstractDataPresenter>
		    
		IViewAttachPresenter<P>
	
presenter:	
		IBasePresenter<V extends IBaseView<?>, M extends IBaseModel<?>>
		----BaseAbstractPresenter<V extends IBaseView<?>, M extends IBaseModel<?>>
		    ----BaseAbstractStatusViewPresenter<V extends IBaseStatusView<?>, M extends BaseAbstractModel<?>>
		        ----BaseAbstractDataPresenter<V extends IBaseDataView<?>, M extends BaseAbstractDataModel<?>>
			    ----BaseAbstractCountDownPresenter（具体业务Presenter）
	        ----engine
		    ----IBasePresenterEngine
		    
model:		
		IBaseModel<I extends IBaseListener>
		----BaseAbstractModel<I extends IBaseListener>
		    ----BaseAbstractDataModel<I extends IBaseListener>
		
		part:
		----IBaseModelPart
		    ----BaseAbstractModelPart
		    
		dao:
		----IBaseDao<C extends DaoCallback<?>>
		
		----local:
		    ----IBaseLocalDao<C extends DaoLocalCallback<?>>
		        ----BaseAbstractLocalDao<C extends DaoLocalCallback<?>>
			
		----remote:
		    ----IBaseRemoteDao<C extends DaoRemoteCallback<?>>
		        ----BaseAbstractRemoteDao<C extends DaoRemoteCallback<?>>
			    ----BaseAbstractNetworkDao<C extends DaoNetworkCallback<?>>
			        ----BaseAbstractRetrofitDao<C extends DaoRetrofitCallback<?>>
			
			DaoCallback:
			----DaoCallback<I extends IBaseListener>
			    local:
			    ----DaoLocalCallback<I extends IBaseListener>
			    remote:
			    ----DaoRemoteCallback<I extends IBaseListener>
				----DaoNetworkCallback<I extends IBaseListener>
				----DaoRetrofitCallback<I extends IBaseListener>
			
		operation:
		    ----IBaseDaoOperation<T>
		    ----local
		        ----IBaseLocalDaoOperation<T>
			    ----BaseAbstractLocalDaoOperation<T>
		    ----remote
		        ----IBaseRemoteDaoOperation<T>
			    ----BaseAbstractRemoteDaoOperation<T>
			        ----BaseAbstractNetworkDaoOperation<T>
			        ----RetrofitCallDaoOperation<T>
				
Listener:
		IBaseListener
		----ICountDownListener（具体业务接口）
```

(2).ui包下:

```
activity:	
		BaseActivity
		----BaseFullImmerseScreenActivity
		    ----BaseStatusViewPresenterActivity<V extends IBaseStatusView<P>, P extends BaseAbstractStatusViewPresenter>
		        ----BaseDataPresenterActivity<V extends IBaseDataView<P>, P extends BaseAbstractDataPresenter<?,?>>
		            ----BaseToolbarPresenterActivity<V extends IBaseDataView<P>, P extends BaseAbstractDataPresenter<?,?>>
	        	        ----WebViewActivity（具体业务实现）
					
fragment:	
		BaseFragment
		----BaseStatusViewPresenterFragment<V extends IBaseStatusView<P>, P extends BaseAbstractStatusViewPresenter>
		    ----BaseDataPresenterFragment<V extends IBaseDataView<P>, P extends BaseAbstractDataPresenter<?,?>>
		        ----BaseToolbarPresenterFragment<V extends IBaseDataView<P>, P extends BaseAbstractDataPresenter<?,?>>
			
dialogFragment:	
		BaseDialogFragment
		----BaseStatusViewPresenterDialogFragment<V extends IBaseStatusView<P>, P extends BaseAbstractStatusViewPresenter>
		    ----BaseDataPresenterDialogFragment<V extends IBaseDataView<P>, P extends BaseAbstractDataPresenter<?,?>>
		
bottomSheetDialogFragment:	
		BaseBSDialogFragment
		----BaseStatusViewPresenterBSDialogFragment<V extends IBaseStatusView<P>, P extends BaseAbstractStatusViewPresenter>
		    ----BaseDataPresenterBSDialogFragment<V extends IBaseDataView<P>, P extends BaseAbstractDataPresenter<?,?>>
```

(3).feature包下:

目前暂时提供如下功能：&nbsp;</br>
        remote远程包下：1.FTP处理</br>
                       2.Socket处理</br>
	json包：提供了对json的相关处理</br>
	loading：提供了加载Fragment的功能</br>
	logmanager：提供了日志功能</br>
	playermanager：提供了播放视频的相关功能</br>
	
5.具体内容后续会进行补充
