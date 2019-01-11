package com.cgy.chengy.demofactory.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cgy.chengy.demofactory.R;
import com.cgy.chengy.demofactory.app.BaseActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class ParseUrlActivity extends BaseActivity {
    private EditText edt;
    private Button btn;
    private ImageView iv;
    private TextView tv_title;
    private TextView tv_content;
    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_url);
        initView();
    }

    private void initView() {
        edt = findViewById(R.id.edt);
        btn = findViewById(R.id.btn);
        iv = findViewById(R.id.iv);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        ll_container = findViewById(R.id.ll_container);

        btn.setOnClickListener(v -> {
            String urlStr = edt.getText().toString().trim();
            if (TextUtils.isEmpty(urlStr)) {
                Toast.makeText(mActivity, "请输入网址！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!urlStr.startsWith("http")) {
                Toast.makeText(mActivity, "请检查网址是否输入正确！", Toast.LENGTH_SHORT).show();
                return;
            }
            new ParseUrlAsyncTask().execute(urlStr);
        });
    }

    private HashMap<String, String> parseUrl(String urlStr) {
        HashMap<String, String> result = new HashMap<>();
        try {
            //这里开始是做一个解析，需要在非UI线程进行，超时设为5000毫秒
            Document doc = Jsoup.parse(new URL(urlStr), 5000);
            //获取title
            String title = doc.title();
            //获取description
            String description = "";
            Elements meta_elements = doc.head().select("meta");
            for (Element meta_e : meta_elements) {
                if ("description".equalsIgnoreCase(meta_e.attr("name"))) {
                    description = meta_e.attr("content");
                    break;
                } else if ("description".equalsIgnoreCase(meta_e.attr("itemprop"))) {
                    description = meta_e.attr("content");
                    break;
                }
            }
            //获取图片
            String imgStr = "";
            Elements png_eles;
            String reg = ".*\\.(jpg|png|ico)"; //取href标签以png/ico/jpg结尾的链接;
            if ((png_eles = doc.getElementsByAttributeValueMatching("href", reg)) != null && !png_eles.isEmpty()) {
                for (Element png_ele : png_eles) {
                    if ("shortcut icon".equalsIgnoreCase(png_ele.attr("rel"))) {
                        imgStr = png_ele.attr("href");
                        break;
                    }
                }
            } else if ((png_eles = doc.getElementsByAttributeValueMatching("content", reg)) != null && !png_eles.isEmpty()) {
                Element png_ele = png_eles.first();
                imgStr = png_ele.attr("content");
            } else if ((png_eles = doc.getElementsByAttributeValueMatching("src", reg)) != null && !png_eles.isEmpty()) {
                Element png_ele = png_eles.first();
                imgStr = png_ele.attr("src");
            }
            //放入map
            result.put("code", "0");
            result.put("title", title);
            result.put("description", description);
            result.put("url", urlStr);
            result.put("img", imgStr);
        } catch (IOException e) {
            result.put("code", "1");
            e.printStackTrace();
        }
        return result;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ParseUrlActivity.class);
        context.startActivity(starter);
    }

    private class ParseUrlAsyncTask extends AsyncTask<String, Void, HashMap<String, String>> {

        @Override
        protected HashMap<String, String> doInBackground(String... strings) {
            return parseUrl(strings[0]);
        }

        @Override
        protected void onPostExecute(HashMap<String, String> map) {
            if (map.get("code").equals("0")) {
                String title = map.get("title");
                String description = map.get("description");
                String url = map.get("url");
                String img = map.get("img");
                if (!TextUtils.isEmpty(img)) {
                    if (!img.startsWith("http")) {
                        img = "http:" + img;
                    }
                }

                tv_title.setText(title);
                tv_content.setText(description);
                Glide.with(mActivity).load(img).placeholder(R.mipmap.ic_launcher).into(iv);
                ll_container.setOnClickListener(v -> {
                    SimpleWebViewActivity.Companion.start(mActivity, url);
                });
            } else {
                Toast.makeText(mActivity, "解析错误", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
