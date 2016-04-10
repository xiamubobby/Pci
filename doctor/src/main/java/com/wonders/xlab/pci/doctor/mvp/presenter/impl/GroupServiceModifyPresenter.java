package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.groupmanage.servicemanage.bean.PackageInfoBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupServiceModifyPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/4/10.
 */
public class GroupServiceModifyPresenter extends BasePresenter implements IGroupServiceModifyPresenter {
    private GroupServiceModifyPresenterListener mListener;

    public GroupServiceModifyPresenter(GroupServiceModifyPresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getServicePackageInfo(String packageId) {
        List<HashMap<String,String>> defaultValues = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("value", i * 100 + "元/月");
            map.put("tag", String.valueOf(i * 100));
            defaultValues.add(map);
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("value", "自定义");
        map.put("tag", "-1");
        defaultValues.add(map);

        PackageInfoBean packageInfoBean = new PackageInfoBean("价格","元/月",defaultValues,"套餐说明","<div>\n" +
                "\n" +
                "      \n" +
                "      \n" +
                "\n" +
                "      \n" +
                "        <p>RxJava是响应式编程, 在异步处理网络数据时, 使用广泛. 使用一些Rx的特性, 缓存网络数据, 并同步显示, 可以增强用户的网络加载体验.</p>\n" +
                "<a></a>\n" +
                "<blockquote>\n" +
                "<p>更多: <a href=\"http://www.wangchenlong.org/\">http://www.wangchenlong.org/</a></p>\n" +
                "</blockquote>\n" +
                "<p>缓存模式: 读取数据库, 显示, 请求数据, 存储到数据库, 再更新页面.</p>\n" +
                "\n" +
                "<p>使用Dagger2+Retrofit+Rx的标准组合, 我来讲解一下如何使用.</p>\n" +
                "<p>本文源码的GitHub<a href=\"https://github.com/SpikeKing/wcl-rx-cache-demo\" target=\"_blank\" rel=\"external\">下载地址</a></p>\n" +
                "\n" +
                "\n" +
                "<h2><a href=\"http://www.wangchenlong.org/2016/03/19/rx-network-cache/#框架\" title=\"框架\"></a>框架</h2><p>常规项目, 包含跳转缓存和非缓存页面, 为了模拟慢速环境, 延迟3秒加载数据.<br></p><pre><code class=\"hljs scala\">public <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">MainActivity</span> <span class=\"hljs-keyword\">extends</span> <span class=\"hljs-title\">AppCompatActivity</span> </span>{\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Override</span>\n" +
                "    <span class=\"hljs-keyword\">protected</span> void onCreate(<span class=\"hljs-type\">Bundle</span> savedInstanceState) {\n" +
                "        <span class=\"hljs-keyword\">super</span>.onCreate(savedInstanceState);\n" +
                "        setContentView(<span class=\"hljs-type\">R</span>.layout.activity_main);\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-comment\">// 跳转无缓存</span>\n" +
                "    public void gotoNoCache(<span class=\"hljs-type\">View</span> view) {\n" +
                "        startActivity(<span class=\"hljs-keyword\">new</span> <span class=\"hljs-type\">Intent</span>(<span class=\"hljs-keyword\">this</span>, <span class=\"hljs-type\">NocacheActivity</span>.<span class=\"hljs-keyword\">class</span>));\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-comment\">// 跳转有缓存</span>\n" +
                "    public void gotoCache(<span class=\"hljs-type\">View</span> view) {\n" +
                "        startActivity(<span class=\"hljs-keyword\">new</span> <span class=\"hljs-type\">Intent</span>(<span class=\"hljs-keyword\">this</span>, <span class=\"hljs-type\">CacheActivity</span>.<span class=\"hljs-keyword\">class</span>));\n" +
                "    }\n" +
                "}</code></pre><p></p>\n" +
                "\n" +
                "<h2><a href=\"http://www.wangchenlong.org/2016/03/19/rx-network-cache/#无缓存\" title=\"无缓存\"></a>无缓存</h2><p>依赖注入三个关键部分, Application/Component/Module.<br></p><pre><code class=\"hljs scala\">public <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">RcApplication</span> <span class=\"hljs-keyword\">extends</span> <span class=\"hljs-title\">Application</span> </span>{\n" +
                "    <span class=\"hljs-keyword\">private</span> <span class=\"hljs-type\">ApiComponent</span> mApiComponent;\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Override</span> public void onCreate() {\n" +
                "        <span class=\"hljs-keyword\">super</span>.onCreate();\n" +
                "        mApiComponent = <span class=\"hljs-type\">DaggerApiComponent</span>.builder()\n" +
                "                .apiModule(<span class=\"hljs-keyword\">new</span> <span class=\"hljs-type\">ApiModule</span>(<span class=\"hljs-keyword\">this</span>)).build();\n" +
                "    }\n" +
                "\n" +
                "    public <span class=\"hljs-type\">ApiComponent</span> getApiComponent() {\n" +
                "        <span class=\"hljs-keyword\">return</span> mApiComponent;\n" +
                "    }\n" +
                "}</code></pre><p></p>\n" +
                "<pre><code class=\"hljs java\"><span class=\"hljs-meta\">@Singleton</span>\n" +
                "<span class=\"hljs-meta\">@Component</span>(modules = ApiModule.class)\n" +
                "<span class=\"hljs-keyword\">public</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">interface</span> <span class=\"hljs-title\">ApiComponent</span> </span>{\n" +
                "    <span class=\"hljs-function\"><span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">inject</span><span class=\"hljs-params\">(NocacheActivity activity)</span></span>;\n" +
                "\n" +
                "    <span class=\"hljs-function\"><span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">inject</span><span class=\"hljs-params\">(CacheActivity activity)</span></span>;\n" +
                "}</code></pre>\n" +
                "<pre><code class=\"hljs java\"><span class=\"hljs-meta\">@Module</span>\n" +
                "<span class=\"hljs-keyword\">public</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">ApiModule</span> </span>{\n" +
                "    <span class=\"hljs-keyword\">private</span> Application mApplication;\n" +
                "\n" +
                "    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-title\">ApiModule</span><span class=\"hljs-params\">(Application application)</span> </span>{\n" +
                "        mApplication = application;\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Provides</span>\n" +
                "    <span class=\"hljs-meta\">@Singleton</span>\n" +
                "    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> Application <span class=\"hljs-title\">provideApplication</span><span class=\"hljs-params\">()</span> </span>{\n" +
                "        <span class=\"hljs-keyword\">return</span> mApplication;\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Provides</span>\n" +
                "    <span class=\"hljs-meta\">@Singleton</span> <span class=\"hljs-function\">GitHubClient <span class=\"hljs-title\">provideGitHubClient</span><span class=\"hljs-params\">()</span> </span>{\n" +
                "        <span class=\"hljs-keyword\">return</span> <span class=\"hljs-keyword\">new</span> GitHubClient();\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Provides</span> <span class=\"hljs-function\">ObservableRepoDb <span class=\"hljs-title\">provideObservableRepoDb</span><span class=\"hljs-params\">()</span> </span>{\n" +
                "        <span class=\"hljs-keyword\">return</span> <span class=\"hljs-keyword\">new</span> ObservableRepoDb(mApplication);\n" +
                "    }\n" +
                "}</code></pre>\n" +
                "<blockquote>\n" +
                "<p>模块提供应用信息, GitHub的网络请求, 数据库.<br>@Singleton表示单例模式, 全部注入拥有一个实例.</p>\n" +
                "</blockquote>\n" +
                "<p>页面, 使用RecyclerView显示列表信息, 在加载时显示ProgressBar.<br></p><pre><code class=\"hljs scala\"><span class=\"hljs-comment\">/**\n" +
                " * 无缓存Activity\n" +
                " * &lt;p&gt;\n" +
                " * Created by wangchenlong on 16/1/18.\n" +
                " */</span>\n" +
                "public <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">NocacheActivity</span> <span class=\"hljs-keyword\">extends</span> <span class=\"hljs-title\">Activity</span> </span>{\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Bind</span>(<span class=\"hljs-type\">R</span>.id.nocache_rv_list) <span class=\"hljs-type\">RecyclerView</span> mRvList;\n" +
                "    <span class=\"hljs-meta\">@Bind</span>(<span class=\"hljs-type\">R</span>.id.nocache_pb_progress) <span class=\"hljs-type\">ProgressBar</span> mPbProgress;\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Inject</span> <span class=\"hljs-type\">Application</span> mApplication;\n" +
                "    <span class=\"hljs-meta\">@Inject</span> <span class=\"hljs-type\">GitHubClient</span> mGitHubClient;\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> <span class=\"hljs-type\">ListAdapter</span> mListAdapter;\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Override</span> <span class=\"hljs-keyword\">protected</span> void onCreate(<span class=\"hljs-type\">Bundle</span> savedInstanceState) {\n" +
                "        <span class=\"hljs-keyword\">super</span>.onCreate(savedInstanceState);\n" +
                "        setContentView(<span class=\"hljs-type\">R</span>.layout.activity_nocache);\n" +
                "        <span class=\"hljs-type\">ButterKnife</span>.bind(<span class=\"hljs-keyword\">this</span>);\n" +
                "\n" +
                "        ((<span class=\"hljs-type\">RcApplication</span>) getApplication()).getApiComponent().inject(<span class=\"hljs-keyword\">this</span>);\n" +
                "\n" +
                "        <span class=\"hljs-type\">LinearLayoutManager</span> layoutManager = <span class=\"hljs-keyword\">new</span> <span class=\"hljs-type\">LinearLayoutManager</span>(mApplication);\n" +
                "        mRvList.setLayoutManager(layoutManager);\n" +
                "\n" +
                "        mListAdapter = <span class=\"hljs-keyword\">new</span> <span class=\"hljs-type\">ListAdapter</span>();\n" +
                "        mRvList.setAdapter(mListAdapter);\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Override</span> <span class=\"hljs-keyword\">protected</span> void onResume() {\n" +
                "        <span class=\"hljs-keyword\">super</span>.onResume();\n" +
                "\n" +
                "        <span class=\"hljs-comment\">// 延迟3秒, 模拟效果</span>\n" +
                "        mGitHubClient.getRepos(<span class=\"hljs-string\">\"SpikeKing\"</span>)\n" +
                "                .delay(<span class=\"hljs-number\">3</span>, <span class=\"hljs-type\">TimeUnit</span>.<span class=\"hljs-type\">SECONDS</span>)\n" +
                "                .subscribeOn(<span class=\"hljs-type\">Schedulers</span>.io())\n" +
                "                .observeOn(<span class=\"hljs-type\">AndroidSchedulers</span>.mainThread())\n" +
                "                .subscribe(<span class=\"hljs-keyword\">this</span>::onSuccess, <span class=\"hljs-keyword\">this</span>::onError);\n" +
                "\n" +
                "        mPbProgress.setVisibility(<span class=\"hljs-type\">View</span>.<span class=\"hljs-type\">VISIBLE</span>);\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> void onSuccess(<span class=\"hljs-type\">ArrayList</span>&lt;repo&gt; repos) {\n" +
                "        mListAdapter.setRepos(repos);\n" +
                "        mPbProgress.setVisibility(<span class=\"hljs-type\">View</span>.<span class=\"hljs-type\">INVISIBLE</span>);\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> void onError(<span class=\"hljs-type\">Throwable</span> throwable) {\n" +
                "        mPbProgress.setVisibility(<span class=\"hljs-type\">View</span>.<span class=\"hljs-type\">INVISIBLE</span>);\n" +
                "    }\n" +
                "}&lt;/repo&gt;&lt;/p&gt;</code></pre><p></p>\n" +
                "<p>通过观察可以发现, 长时间显示白屏会降低用户体验. 我来看看缓存模式.</p>\n" +
                "\n" +
                "<h2><a href=\"http://www.wangchenlong.org/2016/03/19/rx-network-cache/#缓存\" title=\"缓存\"></a>缓存</h2><p>缓存模式: 读取数据库, 显示, 请求数据, 存储到数据库, 再更新页面.<br>推荐使用脚本生成数据库处理类, 使用方式<a href=\"http://www.jianshu.com/p/012d793e4b5f\" target=\"_blank\" rel=\"external\">参考</a>, <strong>自动生成DbHelper的脚本</strong>.</p>\n" +
                "<p>主页逻辑.<br></p><pre><code class=\"hljs scala\">public <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">CacheActivity</span> <span class=\"hljs-keyword\">extends</span> <span class=\"hljs-title\">Activity</span> </span>{\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Bind</span>(<span class=\"hljs-type\">R</span>.id.cache_rv_list) <span class=\"hljs-type\">RecyclerView</span> mRvList; <span class=\"hljs-comment\">// 列表</span>\n" +
                "    <span class=\"hljs-meta\">@Bind</span>(<span class=\"hljs-type\">R</span>.id.cache_srl_swipe) <span class=\"hljs-type\">SwipeRefreshLayout</span> mSrlSwipe; <span class=\"hljs-comment\">// 刷新</span>\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Inject</span> <span class=\"hljs-type\">Application</span> mApplication;\n" +
                "    <span class=\"hljs-meta\">@Inject</span> <span class=\"hljs-type\">ObservableRepoDb</span> mRepoDb;\n" +
                "    <span class=\"hljs-meta\">@Inject</span> <span class=\"hljs-type\">GitHubClient</span> mGitHubClient;\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> <span class=\"hljs-type\">ListAdapter</span> mListAdapter; <span class=\"hljs-comment\">// RecyclerView适配器</span>\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Override</span> <span class=\"hljs-keyword\">protected</span> void onCreate(<span class=\"hljs-type\">Bundle</span> savedInstanceState) {\n" +
                "        <span class=\"hljs-keyword\">super</span>.onCreate(savedInstanceState);\n" +
                "        setContentView(<span class=\"hljs-type\">R</span>.layout.activity_cache);\n" +
                "        <span class=\"hljs-type\">ButterKnife</span>.bind(<span class=\"hljs-keyword\">this</span>);\n" +
                "\n" +
                "        <span class=\"hljs-comment\">// 注入类</span>\n" +
                "        ((<span class=\"hljs-type\">RcApplication</span>) getApplication()).getApiComponent().inject(<span class=\"hljs-keyword\">this</span>);\n" +
                "\n" +
                "        <span class=\"hljs-type\">LinearLayoutManager</span> layoutManager = <span class=\"hljs-keyword\">new</span> <span class=\"hljs-type\">LinearLayoutManager</span>(mApplication);\n" +
                "        mRvList.setLayoutManager(layoutManager);\n" +
                "\n" +
                "        mListAdapter = <span class=\"hljs-keyword\">new</span> <span class=\"hljs-type\">ListAdapter</span>();\n" +
                "        mRvList.setAdapter(mListAdapter);\n" +
                "\n" +
                "        mSrlSwipe.setOnRefreshListener(<span class=\"hljs-keyword\">this</span>::fetchUpdates);\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@Override</span> <span class=\"hljs-keyword\">protected</span> void onResume() {\n" +
                "        <span class=\"hljs-keyword\">super</span>.onResume();\n" +
                "        mRepoDb.getObservable()\n" +
                "                .subscribeOn(<span class=\"hljs-type\">Schedulers</span>.io())\n" +
                "                .observeOn(<span class=\"hljs-type\">AndroidSchedulers</span>.mainThread())\n" +
                "                .subscribe(<span class=\"hljs-keyword\">this</span>::setData);\n" +
                "\n" +
                "        fetchUpdates();\n" +
                "        <span class=\"hljs-type\">Toast</span>.makeText(mApplication, <span class=\"hljs-string\">\"正在更新\"</span>, <span class=\"hljs-type\">Toast</span>.<span class=\"hljs-type\">LENGTH_SHORT</span>).show();\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-comment\">// 设置数据, 更新完成会调用</span>\n" +
                "    <span class=\"hljs-keyword\">private</span> void setData(<span class=\"hljs-type\">ArrayList</span>&lt;repo&gt; repos) {\n" +
                "        mListAdapter.setRepos(repos);\n" +
                "        <span class=\"hljs-type\">Toast</span>.makeText(mApplication, <span class=\"hljs-string\">\"更新完成\"</span>, <span class=\"hljs-type\">Toast</span>.<span class=\"hljs-type\">LENGTH_SHORT</span>).show();\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> void fetchUpdates() {\n" +
                "        <span class=\"hljs-comment\">// 延迟3秒, 模拟效果</span>\n" +
                "        mGitHubClient.getRepos(<span class=\"hljs-string\">\"SpikeKing\"</span>)\n" +
                "                .delay(<span class=\"hljs-number\">3</span>, <span class=\"hljs-type\">TimeUnit</span>.<span class=\"hljs-type\">SECONDS</span>)\n" +
                "                .subscribeOn(<span class=\"hljs-type\">Schedulers</span>.io())\n" +
                "                .observeOn(<span class=\"hljs-type\">AndroidSchedulers</span>.mainThread())\n" +
                "                .subscribe(mRepoDb::insertRepoList, <span class=\"hljs-keyword\">this</span>::fetchError, <span class=\"hljs-keyword\">this</span>::fetchComplete);\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> void fetchError(<span class=\"hljs-type\">Throwable</span> throwable) {\n" +
                "        mSrlSwipe.setRefreshing(<span class=\"hljs-literal\">false</span>);\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> void fetchComplete() {\n" +
                "        mSrlSwipe.setRefreshing(<span class=\"hljs-literal\">false</span>);\n" +
                "    }\n" +
                "}&lt;/repo&gt;</code></pre><p></p>\n" +
                "<p>数据库的观察者<br></p><pre><code class=\"hljs gradle\"><span class=\"hljs-comment\">/**\n" +
                " * Redo的观察者\n" +
                " * &lt;p&gt;\n" +
                " * Created by wangchenlong on 16/1/18.\n" +
                " */</span>\n" +
                "<span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">class</span> ObservableRepoDb {\n" +
                "    <span class=\"hljs-keyword\">private</span> PublishSubject&lt;arraylist&lt;repo&gt;&gt; mPublishSubject; <span class=\"hljs-comment\">// 发表主题</span>\n" +
                "    <span class=\"hljs-keyword\">private</span> RepoDbHelper mDbHelper; <span class=\"hljs-comment\">// 数据库</span>\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">public</span> ObservableRepoDb(Context context) {\n" +
                "        mDbHelper = <span class=\"hljs-keyword\">new</span> RepoDbHelper(context);\n" +
                "        mPublishSubject = PublishSubject.create();\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-comment\">// 返回观察者</span>\n" +
                "    <span class=\"hljs-keyword\">public</span> Observable&lt;arraylist&lt;repo&gt;&gt; getObservable() {\n" +
                "        Observable&lt;arraylist&lt;repo&gt;&gt; firstObservable = Observable.fromCallable(<span class=\"hljs-keyword\">this</span>::getRepoList);\n" +
                "        <span class=\"hljs-keyword\">return</span> firstObservable.concatWith(mPublishSubject); <span class=\"hljs-comment\">// 连接发表主题</span>\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-comment\">// 从数据库获得数据</span>\n" +
                "    <span class=\"hljs-keyword\">private</span> ArrayList&lt;repo&gt; getRepoList() {\n" +
                "        mDbHelper.openForRead();\n" +
                "        ArrayList&lt;repo&gt; repos = <span class=\"hljs-keyword\">new</span> ArrayList&lt;&gt;();\n" +
                "        Cursor c = mDbHelper.getAllRepo();\n" +
                "        <span class=\"hljs-keyword\">if</span> (!c.moveToFirst()) {\n" +
                "            <span class=\"hljs-keyword\">return</span> repos; <span class=\"hljs-comment\">// 返回空</span>\n" +
                "        }\n" +
                "\n" +
                "        <span class=\"hljs-keyword\">do</span> {\n" +
                "            <span class=\"hljs-comment\">// 添加数据</span>\n" +
                "            repos.add(<span class=\"hljs-keyword\">new</span> Repo(\n" +
                "                    c.getString(RepoDbHelper.REPO_ID_COLUMN_POSITION),\n" +
                "                    c.getString(RepoDbHelper.REPO_NAME_COLUMN_POSITION),\n" +
                "                    c.getString(RepoDbHelper.REPO_DESCRIPTION_COLUMN_POSITION),\n" +
                "                    <span class=\"hljs-keyword\">new</span> Repo.Owner(c.getString(RepoDbHelper.REPO_OWNER_COLUMN_POSITION), <span class=\"hljs-string\">\"\"</span>, <span class=\"hljs-string\">\"\"</span>, <span class=\"hljs-string\">\"\"</span>)));\n" +
                "        } <span class=\"hljs-keyword\">while</span> (c.moveToNext());\n" +
                "        c.close();\n" +
                "        mDbHelper.close();\n" +
                "        <span class=\"hljs-keyword\">return</span> repos;\n" +
                "    }\n" +
                "\n" +
                "    <span class=\"hljs-comment\">// 插入Repo列表</span>\n" +
                "    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> insertRepoList(ArrayList&lt;repo&gt; repos) {\n" +
                "        mDbHelper.open();\n" +
                "        mDbHelper.removeAllRepo();\n" +
                "        <span class=\"hljs-keyword\">for</span> (Repo repo : repos) {\n" +
                "            mDbHelper.addRepo(\n" +
                "                    repo.getId(),\n" +
                "                    repo.getName(),\n" +
                "                    repo.getDescription(),\n" +
                "                    repo.getOwner().getLogin()\n" +
                "            );\n" +
                "        }\n" +
                "        mDbHelper.close();\n" +
                "        mPublishSubject.onNext(repos); <span class=\"hljs-comment\">// 会调用更新数据</span>\n" +
                "    }\n" +
                "}\n" +
                "&lt;<span class=\"hljs-regexp\">/repo&gt;&lt;/</span>repo&gt;&lt;<span class=\"hljs-regexp\">/repo&gt;&lt;/</span>arraylist&lt;repo&gt;&lt;<span class=\"hljs-regexp\">/arraylist&lt;repo&gt;&lt;/</span>arraylist&lt;repo&gt;&lt;<span class=\"hljs-regexp\">/p&gt;</span></code></pre><p></p>\n" +
                "<blockquote>\n" +
                "<p>这一部分是关键, 实现网络请求同步插入数据库和更新页面.<br>关联PublishSubject, 在插入数据完成后, 调用绑定观察者, 更新页面.<br>即<strong>.concatWith(mPublishSubject)</strong>和<strong>mPublishSubject.onNext(repos)</strong>.</p>\n" +
                "</blockquote>\n" +
                "\n" +
                "<p>Rx在处理网络请求方面, 确实非常优雅, 值得喜欢完美的人使用.</p>\n" +
                "<p>OK, that’s all! Enjoy it.</p>\n" +
                "\n" +
                "<blockquote>\n" +
                "<p>原始地址:<br><a href=\"http://www.wangchenlong.org/2016/03/19/rx-network-cache/\">http://www.wangchenlong.org/2016/03/19/rx-network-cache/</a><br>欢迎Follow我的<a href=\"https://github.com/SpikeKing\" target=\"_blank\" rel=\"external\">GitHub</a>, 关注我的<a href=\"http://www.jianshu.com/users/e2b4dd6d3eb4/latest_articles\" target=\"_blank\" rel=\"external\">简书</a>, <a href=\"http://weibo.com/u/2852941392\" target=\"_blank\" rel=\"external\">微博</a>, <a href=\"http://blog.csdn.net/caroline_wendy\" target=\"_blank\" rel=\"external\">CSDN</a>, <a href=\"http://gold.xitu.io/#/user/56de98c2f3609a005442ec58\" target=\"_blank\" rel=\"external\">掘金</a>, <a href=\"https://slides.com/spikeking\" target=\"_blank\" rel=\"external\">Slides</a>.<br>我已委托“维权骑士”为我的文章进行维权行动. 未经授权, 禁止转载, 授权或合作请留言.</p>\n" +
                "</blockquote>\n" +
                "      \n" +
                "    </div>");
        mListener.hideLoading();
        mListener.showServicePackageInfo(packageInfoBean);
    }

    public interface GroupServiceModifyPresenterListener extends BasePresenterListener{
        void showServicePackageInfo(PackageInfoBean packageInfoBean);
    }
}
