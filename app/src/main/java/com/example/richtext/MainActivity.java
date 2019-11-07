package com.example.richtext;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button btnTable;
    private Button btnImg;

    @RequiresApi(api = VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        btnTable = findViewById(R.id.btn_table);
        btnImg = findViewById(R.id.btn_img);

        btnTable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextView(textView);
            }
        });
        btnImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageText(textView);
            }
        });
    }

    @RequiresApi(api = VERSION_CODES.N)
    public void  setTextView(TextView tv){
        String html = "<html>" +
                "<body>" +
                "<h4>两行三列：</h4>" +
                "<table>" +
                "<tr>" +
                "<td>100</td>" +
                "<td>200</td>" +
                "<td>300</td>" +
                "</tr>" +
                "<tr>" +
                "<td>400</td>" +
                "<td>500</td>" +
                "<td>600</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";
//        String html = "<table>123</table>";
//        html = "<h2>Hello wold</h2><ul><li>cats</li><li>dogs</li></ul><img src=\"cat_pic\"/>";
        Spanned htmlstr =  Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY,
                null,
                new MyTagHandler());
        textView.setText(htmlstr);
    }

    public void setImageText(TextView tv){

        String  html ="<p dir=\"ltr\"><img src=\"http://p1.duyao001.com/image/article/a838e283f2b5d7cc45487c5fd79f84cb.gif\"><img src=\"http://statics.zhid58.com/Fqr9YXHd20fDOqil4nLAbBhNBw0A\"><br><img src=\"http://statics.zhid58.com/FufBg05KGCLypIvrYgjaXnTWySUS\"><br>OK咯木木克人咯咯JOJO图谋木木木扣女<a href=\"http://www.taobao.com\">http://www.taobao.com</a> jvjvjvjv jgjvvjjvjce<br><br><img src=\"https://b-ssl.duitang.com/uploads/blog/201312/04/20131204184148_hhXUT.jpeg\"><br></p><br>";
        Spanned htmlStr = Html.fromHtml(html);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            tv.setTextIsSelectable(true);
        }
        tv.setText(htmlStr);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if(text instanceof Spannable){
            int end = text.length();
            Spannable sp = (Spannable)tv.getText();
            URLSpan[] urls=sp.getSpans(0, end, URLSpan.class);
            ImageSpan[] imgs = sp.getSpans(0,end,ImageSpan.class);
            StyleSpan[] styleSpens = sp.getSpans(0,end,StyleSpan.class);
            ForegroundColorSpan[] colorSpans = sp.getSpans(0,end,ForegroundColorSpan.class);
            SpannableStringBuilder style=new SpannableStringBuilder(text);
            style.clearSpans();
            for(URLSpan url : urls){
                style.setSpan(url,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF12ADFA"));
                style.setSpan(colorSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            for(ImageSpan url : imgs){
                Drawable drawable = getResources().getDrawable(R.drawable.default_icon);
                drawable.setBounds(0, 0, 200, 200);
                ImageSpan span = new ImageSpan(drawable);
                style.setSpan(span,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            for(StyleSpan styleSpan : styleSpens){
                style.setSpan(styleSpan,sp.getSpanStart(styleSpan),sp.getSpanEnd(styleSpan),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            for(ForegroundColorSpan colorSpan : colorSpans){
                style.setSpan(colorSpan,sp.getSpanStart(colorSpan),sp.getSpanEnd(colorSpan),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            tv.setText(style);
        }
    }

//    public static Drawable getUrlDrawable(String source, TextView mTextView) {
//        GlideImageGetter imageGetter = new GlideImageGetter(mTextView.getContext(),mTextView);
//        return imageGetter.getDrawable(source);
//
//    }
}
