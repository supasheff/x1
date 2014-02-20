package blog.rostova.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebActivity extends Activity {

	/** Called when the activity is first created. */
	WebView mWebView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.web);
	    mWebView = (WebView) findViewById(R.id.webView1);
	   
	   mWebView.setWebViewClient(new HelloWebViewClient());
		// включаем поддержку JavaScript
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    WebSettings webSettings = mWebView.getSettings();
	   String ua = "Mozilla/5.0";
	    webSettings.setUserAgentString(ua); 
	    webSettings.setSavePassword(true);
	    webSettings.setSaveFormData(true);
	    webSettings.setJavaScriptEnabled(true);
		// указываем страницу загрузки
	    mWebView.loadUrl("http://www.rostov-gorod.ru/?ID=4514"); 
	   
		
        final Button button1 = (Button)findViewById(R.id.button1);	
		button1.setOnClickListener(new View.OnClickListener() {
				 
				 
		

				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
					
				}
				// }
    });
	   
	}

	private class HelloWebViewClient extends WebViewClient  
	{
		@Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) 
	    {
	        view.loadUrl(url);
	        return true;
	    }
	}
		

				
}

