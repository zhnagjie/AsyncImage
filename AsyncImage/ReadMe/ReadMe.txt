使用说明

一. 说明
	异步加载图片.
	可以通过实现LoadMethod接口自定义图片的加载方法, 所以理论上可以加载任意来源的图片.
	如果不指定图片的加载方法, 则用默认的加载方法. 默认可以加载3种来源的图片:
		Asset: "file:///android_asset";
		File: "file:///"或"/";
		网络图片.
	如果不自定义图片加载方法, 可以调用AsyncImageView的setOptions()方法设置BitmapFactory.Options用于decode图片, 但对于不同尺寸的图片Options要设置的属性值可能不一样, 无法确切设置好Options的值, 所以最好自定义加载方法.

	图片异步加载(通过AsyncImageView)最主要地是用于ListVIew或GridView, 可以设置当ListView或GridView Scrolling或Flinging的时候停止加载图片, 只需在OnScollListener中调用setPause(true), 当scrolling/flinging结束的时候调用setPause(false)重新开始加载.

	通过实现CacheCallback接口可以控制对缓存的读写.

	通过实现ImapeProcessor接口实现在图片加载完成后使用之前对图片进行一些预先处理, ImageProcessor的实现类ChainProcessor可以串连多个ImageProcessor.

二. 使用步骤
	使用AsyncImageView异步地加载图片,使用步骤如下:
	1.通过在XML中或用代码生成AsyncImageView类实例.
	2.调用setDefaultImageResource(int resId)/setDefaultImageBitmap(Bitmap)/setDefaultDrawable(Drawable)设置默认图片(可不设置默认图片, 那么在图片加载完之前显示为空白).
	3.调用setImageProcessor(ImageProcessor)当图片加载完后进行进一步处理(可不设置).
	4.调用setCacheCallback(CacheCallback)设置缓存方法用于读取或写入缓存(可不设置,无缓存).
	5.调用setPath(String path)/setPath(String Path, LoadMethod loadMethod)进行图片加载, url为图片的地址, loadMethod为自定义图片加载方法, 若不设置则用默认的加载方法.

三. 主要类说明(使用时显式用到的类, 后台类像ImageRequest,ImageLoader虽说是主要类但不直接用到,不说明)
	AsyncImageView
		描述:
			继承自ImageView,所以可以在任意使用ImageView的地方使用AsyncImageView.使用方法见"使用步骤".
		主要方法:
			public boolean isLoaded(): 图片是否已加载完成.
			public boolean isLoading(): 是否正在加载图片.
			public void setPaused(boolean paused): 暂停/开始加载图片.
			public void setOptions(BitmapFactory.Options options): 设置图片解析Options.
			public void reload(boolean force): 重新加载图片, force表示是否强制刷新不读缓存.
			setDefault...()系列函数: 设置默认图片.
			public void setPath(String path, LoadMethod loadMethod): 设置要加载的图片的地址及自定义的加载方法, 加载方法可不指定, 重载方法setPath(path)==setPath(path, null);
			public void setOnImageViewLoadListener(OnImageViewLoadListener listener): 设置图片加载时的状态监听接口

	AsyncImageView.OnImageViewLoadListener
		描述:
			图片加载状态监听
		主要方法:
			void onImageLoadingStarted(ImageLoader loader): 图片加载之前回调
			void onImageLoadingEnded(ImageLoader loader, Bitmap bitmap): 图片加载完成回调
			void onImageLoadingFailed(ImageLoader loader, Throwable exception): 图片加载失败回调
	
	CacheCallback
		描述:
			缓存接口
		主要方法:
			Bitmap readCache(String key): 读取缓存, 一般在调用此方法的线程执行, 即主线程
			boolean writeCache(String key, Bitmap bitmap): 写缓存, 一般异步执行.
	
	LoadMethod
		描述:
			自定义图片加载接口
		主要方法:
			Bitmap load(String path): 自定义加载方法

	ImageProcessor
		描述:
			图片加载完成后使用之前对图片进行处理的接口
		主要方法:
			Bitmap processImage(Bitmap bitmap): 预先处理图片

	ChainImageProcessor
		描述:
			ImageProcessor实现类, 用于串连一组ImageProcessor, 通过构造函数传入多个ImageProcessor
