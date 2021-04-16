package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.security.AccessController.getContext;

public class PdfActivity extends AppCompatActivity  {
    String url = null;
    String pdfName = null;
    WebView view;
    TextView pdfNameView;
    ImageView shareButton, downloadButton;
    ProgressBar progressBar;
    public static final int PERMISSION_WRITE = 0;
    WebSettings webSettings;
    File pdfFolder, myFile, file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            view.getSettings().setSafeBrowsingEnabled(false);
//        }
//
//        WebSettings settings = view.getSettings();
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);
//        settings.setJavaScriptEnabled(true);
//
//        settings.setAppCacheEnabled(false);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        settings.setDatabaseEnabled(false);
//        settings.setDomStorageEnabled(false);
//        settings.setGeolocationEnabled(false);
//        settings.setSaveFormData(false);
//        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // chromium, enable hardware acceleration
//            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else {
//            // older android version, disable hardware acceleration
//            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
////        settings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
//
//        view.setWebViewClient(new CustowebViewClient());
//        view.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                Log.e("Progress: ", "Progreess: " + newProgress);
//                super.onProgressChanged(view, newProgress);
//            }
//        });
////        webView.loadDataWithBaseURL(url, null, "text/html", "UTF-8", null);
//        view.loadUrl(url);
//    }
//
//    private class CustowebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//            if (url.contains("jackpotjoy.xyz")) {
//                view.loadUrl(url);
//            } else {
//                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(i);
//            }
//            return true;
//        }
//
//    }

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        pdfName = intent.getStringExtra("pdfName");
        view = findViewById(R.id.webView);
        pdfNameView = findViewById(R.id.pdfName);
        pdfNameView.setText(pdfName);
        view.setBackgroundColor(Color.TRANSPARENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.getSettings().setJavaScriptEnabled(true);
        }
//        wv.clearCache(true);
//        wv.clearView();
//        wv.reload();
//        wv.loadUrl("about:blank");
//        wv.loadData(Constants.BLOG_URL);
        view.clearCache(false);
        view.clearView();
        view.reload();
        view.zoomIn();
        view.zoomOut();
        view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        view.setWebChromeClient(new WebChromeClient() );

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        view.loadUrl("about:blank");
        view.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
        Log.e("Loading URL", "https://docs.google.com/gview?embedded=true&url="+url);
        downloadButton = findViewById(R.id.downloadButton);
        shareButton = findViewById(R.id.shareButton);
        progressBar = findViewById(R.id.progressBar);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            pdfFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//        } else{
//            pdfFolder = Environment.getExternalStorageDirectory();
//        }
//        String fileName = "Invoice_detail" + ".pdf";
//        myFile = new File(pdfFolder.getAbsolutePath() + File.separator + fileName);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                new downloading().execute(url);
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), pdfName);
//                Uri path = Uri.fromFile(filelocation);
                String path = PdfActivity.this.getFilesDir().getAbsolutePath();
                File file = new File(path + "/"+pdfName+".pdf");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/pdf");
                String shareBody = "...........";
                String shareSub = "PDF Share";
                intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                intent.putExtra(Intent.EXTRA_STREAM, file);
                startActivity(intent.createChooser(intent, "Share using"));
            }
        });
        view.postDelayed(new Runnable() {

            @Override
            public void run() {
                view.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
            }
        }, 500);
    }

    public class downloading extends AsyncTask{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            super .onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            File mydir = new File(Environment.getExternalStorageDirectory() + "/Download");
            if (!mydir.exists()) {
                mydir.mkdirs();
            }
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

            SimpleDateFormat dateFormat = new SimpleDateFormat("mmddyyyyhhmmss");
            String date = dateFormat.format(new Date());

            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(pdfName)
                    .setDestinationInExternalPublicDir("/Download", pdfName + ".pdf")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            downloadManager.enqueue(request);
            return mydir.getAbsolutePath() + File.separator + date + ".pdf";
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "PDF Saved", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }
    BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            // your code
        }
    };

}
