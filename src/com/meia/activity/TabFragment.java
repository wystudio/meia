package com.meia.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.meia.R;

public class TabFragment extends Fragment {
	private String weburl = "index.html";

	public TabFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (getArguments() != null) {
			weburl = getArguments().getString("weburl");
		}
		
		  /*TextView textView = new TextView(getActivity());
		  textView.setTextSize(20);
		  textView.setBackgroundColor(Color.parseColor("#ffffffff"));
		  textView.setGravity(Gravity.CENTER); textView.setText(weburl); return
		  textView;*/
		
		WebView webView = new WebView(getActivity());
		// 支持JS
		webView.getSettings().setJavaScriptEnabled(true);
		// 支持缩放
		webView.getSettings().setSupportZoom(true);
		// 启用内置缩放装置
		webView.getSettings().setBuiltInZoomControls(true);
		// 设置进度条
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				getActivity().setTitle("正在努力加载中...");
				getActivity().setProgress(progress * 100);
				if (progress == 100)
					getActivity().setTitle(R.string.app_name);
			}
		});
		webView.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// Handle the error
				// System.out.println(description);
			}

			//
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// for testing
				if (Uri.parse(url).getHost().equals("www.baidu.com")) {
					// Load the site into the default browser
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(url));
					startActivity(intent);
					return true;
				}
				// Load url into the webview
				return false;
			}
		});
		webView.loadUrl(weburl);
		return webView;
	}
}
