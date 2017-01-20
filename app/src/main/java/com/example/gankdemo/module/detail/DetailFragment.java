package com.example.gankdemo.module.detail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gankdemo.R;
import com.example.gankdemo.base.fragment.BaseFragment;
import com.example.gankdemo.constants.BundleConstant;
import com.example.gankdemo.module.home.MainFragment;
import com.example.gankdemo.util.SnackbarUtil;

import butterknife.BindView;

/**详情页fragment
 * Created by clement on 17/1/20.
 */

public class DetailFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String titleString;
    private String url;
    @Override
    public int onBindLayoutID() {
        return R.layout.fragment_detail;
    }

    @Override
    public void initBehavior(View rootView) {
        initData();
        initToolbar();
        initWebView();
    }
    private void initData(){
        titleString = getArguments().getString(BundleConstant.TITLE);
        url = getArguments().getString(BundleConstant.URL);
    }
    private void initToolbar(){
        //将toolbar作为独立的控件使用，不和ActionBar进行关联
        tv_title.setText(titleString);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回上一层
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.menu_detail);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_share:
                        shareText(titleString+" "+url);
                        break;
                    case R.id.menu_copy:
                        copyText(url);
                        SnackbarUtil.show(toolbar,getString(R.string.copied));
                        break;
                    case R.id.menu_open:
                        openWeb(url);
                        break;
                }
                return true;
            }
        });
    }

    private void initWebView(){
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        //处理加载进度
        webView.setWebChromeClient(new CustomWebChromeClient());
        webView.setWebViewClient(new CustomWebViewClient());
        //加载网页
        webView.loadUrl(url);
    }

    @Override
    public boolean onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else{
            remove2ShowFragment(DetailFragment.class.getSimpleName(), MainFragment.class.getSimpleName());
        }
        return true;
    }

    /**
     * 辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
     */
    class CustomWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress==100){
                progressBar.setVisibility(View.GONE);
            }
            else{
                progressBar.setVisibility(View.VISIBLE);
            }
            progressBar.setProgress(newProgress);
        }
    }

    /**
     * 帮助WebView处理各种通知、请求事件的
     */
    class CustomWebViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
//            progressBar.setVisibility(View.GONE);
        }
    }

    /**分享文字
     * @param text
     */
    private void shareText(String text){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        intent.setType("text/plain");
        //检查是否有满足的activity
        if(intent.resolveActivity(getContext().getPackageManager())!=null){
            //设置分享的标题
            startActivity(Intent.createChooser(intent,getString(R.string.share_to)));
        }
        else{
            SnackbarUtil.show(tv_title,"没有合适的第三方应用");
        }
    }

    /**复制文本到系统剪切板
     * @param text
     */
    private void copyText(String text){
        // 得到剪贴板管理器
        ClipboardManager clipboardManager = (ClipboardManager)getActivity().getSystemService(
                getContext().CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
    }

    /**用浏览器打开
     * @param url
     */
    private void openWeb(String url){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri contentUri = Uri.parse(url);
        intent.setData(contentUri);
        //检查是否有满足的activity
        if(intent.resolveActivity(getContext().getPackageManager())!=null){
            //设置分享的标题
            startActivity(Intent.createChooser(intent,getString(R.string.web_select)));
        }
        else{
            SnackbarUtil.show(tv_title,"没有浏览器");
        }
    }
}
