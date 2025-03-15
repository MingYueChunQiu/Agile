# Agile
a agile android framework for MVP and MVVM.
一个Android高效框架，提供公共框架、方法，进行敏捷开发。

    最新v0.7.6</br>
    1.AGP现已升级至8.5.2，SDK升至34</br>
    2.新增通用样式对话框，优化对话框显示判断</br>
    3.增加ExtraHelper用于Activity/Fragment参数传递辅助类
    
    v0.7.4</br>
    AGP现已升级至8.0，SDK升至34，更新已有代码</br>
    v0.5.2:1.升级SDK</br>
    	2.更新API</br>
	3.重构框架</br>
	
    	v0.4.19:add CircleProgressView</br>
        v0.4.17:add PopPageHelper</br>
    	v0.4.15:
    		1.优化框架填充布局</br>
		2.升级SDK</br>
	
	v0.4.13:
    		1.框架增加对Activity/Fragment/DialogFragment/BottomSheetDialogFragment/Dialog生命周期全局注册监听</br>
    	v0.4.12:
    		1.优化BitmapUtils,JsonManager</br>
    	v0.4.10:
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
		IBaseView
	  	----IBaseDataView extends IBaseView
		    
		IViewAttachPresenter<P extends IBasePresenter<? extends IBaseView, ? extends IBaseModel>>
	
presenter:	
		IBasePresenter<V extends IBaseView, M extends IBaseModel> extends LifecycleObserver, ICallExecutor, IPopHintOwner, IStatusViewable
		----BaseAbstractPresenter<V extends IBaseView, M extends IBaseModel> implements IBasePresenter<V, M>
		    ----BaseAbstractDataPresenter<V extends IBaseDataView, M extends BaseAbstractDataModel> extends BaseAbstractPresenter<V, M>
		        ----BaseAbstractCountDownPresenter<V extends IBaseDataView, M extends BaseAbstractDataModel> extends BaseAbstractDataPresenter<V, M>
	        ----engine
		    ----IBasePresenterEngine
		    
model:		
		IBaseModel extends IModelPartOwner, IDaoOwner, ICallExecutor
		----BaseAbstractModel implements IBaseModel
		    ----BaseAbstractDataModel extends BaseAbstractModel implements IModelLocalDataProcessor, IModelNetworkDataProcessor, DaoLocalCallback, 				DaoRetrofitCallback
		
		part:
		----IBaseModelPart extends IDaoOwner
		    ----BaseAbstractModelPart implements IBaseModelPart
		    
		repository:
		----IBaseDao<C extends DaoCallback> extends ICallExecutor
		    ----BaseAbstractDao<C : DaoCallback> : IBaseDao<C>
		
		----local:
		    ----IBaseLocalDao
		        ----BaseAbstractLocalDao<C extends DaoLocalCallback> extends BaseAbstractDao<C> implements IBaseLocalDao
			
		----remote:
		    ----IBaseRemoteDao
		        ----BaseAbstractRemoteDao<C extends DaoRemoteCallback> extends BaseAbstractDao<C> implements IBaseRemoteDao
			    ----BaseAbstractNetworkDao<C extends DaoNetworkCallback> extends BaseAbstractRemoteDao<C>
			        ----BaseAbstractRetrofitDao<C extends DaoRetrofitCallback> extends BaseAbstractNetworkDao<C>
			
			DaoCallback:
			----DaoCallback
			    local:
			    ----DaoLocalCallback extends DaoCallback
			    remote:
			    ----DaoRemoteCallback extends DaoCallback
				----DaoNetworkCallback extends DaoRemoteCallback
				----DaoRetrofitCallback extends DaoNetworkCallback
				
			data:
			----IModelDataProcessor:
			    local:
			    ----IModelLocalDataProcessor extends IModelDataProcessor
			    remote:
			    ----IModelRemoteDataProcessor extends IModelDataProcessor
			        ----IModelNetworkDataProcessor extends IModelRemoteDataProcessor
				    ----IModelRetrofitDataProcessor extends IModelNetworkDataProcessor
			
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
				
bridge:
		call:
		    ----ICallExecutor
		    ----Call<T>
		   	----RequestCall<T> constructor(private val mRequest: Request, private val mCallback: Request.Callback<T>) : Call<T>
```

(2).ui包下:

```
activity:	
		BaseActivity
		----BaseFullImmerseScreenActivity
		    ----BaseAbstractPresenterActivity
		        ----BaseDataPresenterActivity
		            ----BaseToolbarPresenterActivity
	        	        ----WebViewActivity（具体业务实现）
					
fragment:	
		BaseFragment
		----BaseAbstractPresenterFragment
		    ----BaseDataPresenterFragment
		        ----BaseToolbarPresenterFragment
			
dialogFragment:	
		BaseDialogFragment
		----BaseAbstractPresenterDialogFragment
		    ----BaseDataPresenterDialogFragment
		
bottomSheetDialogFragment:	
		BaseBSDialogFragment
		----BaseAbstractPresenterBSDialogFragment
		    ----BaseDataPresenterBSDialogFragment

dialog:
		BaseDialog
		----BaseAbstractPresenterDialog
		    ----BaseDataPresenterDialog
```

(3).feature包下:

目前暂时提供如下功能：&nbsp;</br>
        remote远程包下：1.FTP处理</br>
                       2.Socket处理</br>
	helper包：辅助工具
	json包：提供了对json的相关处理</br>
	loading：提供了加载Fragment的功能</br>
	logmanager：提供了日志功能</br>
	playermanager：提供了播放视频的相关功能</br>
	
5.具体内容后续会进行补充
