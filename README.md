# Agile
a agile android framework for MVP.
一个Android高效框架，提供公共框架、方法，进行敏捷开发。

已重构架构，规范命名，引入Kotlin
1.Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { videoSource 'https://jitpack.io' }
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
	  	----IBaseDialogView
	     	    ----IBaseNetView
		    
		IViewAttachPresenter<P>
	
presenter:	
		IBasePresenter
		----BaseAbstractPresenter
		    ----BaseDialogPresenter
		        ----BaseNetPresenter
			    ----BaseCountDownPresenter（具体业务Presenter）
	        ----engine
		    ----IBasePresenterEngine
		    
model:		
		IBaseModel
		----BaseAbstractModel
		    ----BaseNetModel
		        ----BaseTokenNetModel
		
		part 
		----IBaseModelPart
		    ----BaseAbstractModelPart
		    
		dao
		----IBaseDao<C extends IBaseDao.ModelDaoCallback>
		
		----local
		    ----IBaseLocalDao<C extends IBaseDao.ModelDaoCallback>
		        ----BaseAbstractLocalDao<C extends IBaseDao.ModelDaoCallback>
			
		----remote
		    ----IBaseRemoteDao<C extends IBaseDao.ModelDaoCallback>
		        ----BaseAbstractRemoteDao<C extends IBaseDao.ModelDaoCallback>
			    ----BaseAbstractNetworkDao<C extends IBaseDao.ModelDaoCallback>
			        ----BaseAbstractRetrofitDao<C extends IBaseDao.ModelDaoCallback>
			
		----operation
		    ----IBaseDaoOperation
		    ----local
		        ----IBaseLocalDaoOperation
			    ----BaseAbstractLocalDaoOperation<T>
		    ----remote
		        ----IBaseRemoteDaoOperation
			    ----BaseAbstractRemoteDaoOperation<T>
			        ----RetrofitCallDaoOperation
				
listener:	
		IBaseListener
		----ICountDownListener（具体业务接口）
```

(2).ui包下:

```
activity:	
		BaseActivity
		----BaseFullImmerseScreenActivity
		    ----BasePresenterActivity
		        ----BaseNetPresenterActivity
		            ----BaseToolbarPresenterActivity
	        	        ----WebViewActivity（具体业务实现）
					
fragment:	
		BaseFragment
		----BasePresenterFragment
		    ----BaseNetPresenterFragment
		        ----BaseToolbarPresenterFragment
			
dialogFragment:	
		BaseDialogFragment
		----BasePresenterDialogFragment
		    ----BaseNetPresenterDialogFragment
		
bottomSheetDialogFragment:	
		BaseBSDialogFragment
		----BasePresenterBSDialogFragment
		    ----BaseNetPresenterBSDialogFragment
```

(3).feature包下:

目前暂时提供如下功能：&nbsp;
	json包：提供了对json的相关处理</br>
	loading：提供了加载Fragment的功能</br>
	logmanager：提供了日志功能</br>
	playermanager：提供了播放视频的相关功能</br>
	
5.具体内容后续会进行补充
