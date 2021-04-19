package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.StrictMode;
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

public class PdfActivity extends AppCompatActivity {
    String url = null;
    String pdfName = null;
    WebView view;
    TextView pdfNameView;
    ImageView shareButton, downloadButton;
    ProgressBar progressBar;
    public static final int PERMISSION_WRITE = 0;
    File pdfFolder, myFile;
    String fileName;
    Context context;
    int f = 0;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        initui();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pdfFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        } else {
            pdfFolder = Environment.getExternalStorageDirectory();
        }

        Log.e("Folder", String.valueOf(pdfFolder));
        fileName = pdfName + ".pdf";
        myFile = new File(pdfFolder.getAbsolutePath() + File.separator + fileName);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                if (myFile.exists()) {
                    intentShareFile.setType("application/pdf");
                    intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(myFile));

                    intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                            "pdf file");
                    intentShareFile.putExtra(Intent.EXTRA_TEXT, pdfName);

                }
                else {
                    f = 1;
                    new downloading().execute(url);
                    intentShareFile.setType("application/pdf");
                    intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(myFile));

                    intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                            "pdf file");
                    intentShareFile.putExtra(Intent.EXTRA_TEXT, pdfName);
                    myFile.delete();

                }
                context.startActivity(Intent.createChooser(intentShareFile, pdfName));
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.getSettings().setJavaScriptEnabled(true);
        }
        view.clearCache(false);
        view.clearView();
        view.reload();
        view.zoomIn();
        view.zoomOut();
        view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        view.setWebChromeClient(new WebChromeClient());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                new downloading().execute(url);
            }
        });

        view.postDelayed(new Runnable() {

            @Override
            public void run() {
                view.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);
            }
        }, 500);
    }

    private void initui() {
        context = this;
        Intent intent = getIntent();

        url = intent.getStringExtra("url");
        pdfName = intent.getStringExtra("pdfName");
        view = findViewById(R.id.webView);
        pdfNameView = findViewById(R.id.pdfName);
        pdfNameView.setText(pdfName);
        view.setBackgroundColor(Color.TRANSPARENT);
        view.loadUrl("about:blank");
        view.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);
        Log.e("Loading URL", "https://docs.google.com/gview?embedded=true&url=" + url);
        downloadButton = findViewById(R.id.downloadButton);
        shareButton = findViewById(R.id.shareButton);
        progressBar = findViewById(R.id.progressBar);

    }

    public class downloading extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            File mydir = new File(String.valueOf(Environment.DIRECTORY_DOWNLOADS));
            if (!mydir.exists()) {
                mydir.mkdirs();
            }
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                pdfFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            } else {
                pdfFolder = Environment.getExternalStorageDirectory();
            }
            myFile = new File(pdfFolder.getAbsolutePath() + File.separator + fileName);

            SimpleDateFormat dateFormat = new SimpleDateFormat("mmddyyyyhhmmss");
            String date = dateFormat.format(new Date());
            if (myFile.exists()){
                myFile.delete();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(pdfName)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, pdfName + ".pdf")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            }
            else {
                if (f==1){
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(false)
                            .setTitle(pdfName)
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, pdfName + ".pdf")
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
                }
                else {
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(false)
                            .setTitle(pdfName)
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, pdfName + ".pdf")
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                }
                downloadManager.enqueue(request);
            if (f==1)
                myFile.delete();

            }
            return mydir.getAbsolutePath() + File.separator + date + ".pdf";
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

}
