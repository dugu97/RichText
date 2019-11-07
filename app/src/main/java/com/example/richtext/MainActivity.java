package com.example.richtext;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button btnTable;

    @RequiresApi(api = VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        btnTable = findViewById(R.id.btn_table);

        btnTable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextView(textView);
            }
        });
    }

    @RequiresApi(api = VERSION_CODES.N)
    public void setTextView(TextView tv) {
        String html = "<html>" +
                "<body>" +
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
        Spanned htmlstr = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY,
                null,
                new TableTagHandler(this));
        SpannableStringBuilder richText = setSpan(htmlstr);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(richText);
    }

    private SpannableStringBuilder setSpan(Spanned htmlstr) {
        ImageSpan[] imgs = htmlstr.getSpans(0, htmlstr.length(), ImageSpan.class);
        RectSpan[] rectSpans = htmlstr.getSpans(0, htmlstr.length(), RectSpan.class);
        SpannableStringBuilder style = new SpannableStringBuilder(htmlstr);
        for (ImageSpan url : imgs) {
            Drawable drawable = getResources().getDrawable(R.drawable.default_icon);
            drawable.setBounds(0, 0, 200, 200);
            ImageSpan span = new ImageSpan(drawable);
            style.setSpan(span, htmlstr.getSpanStart(url), htmlstr.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        for (RectSpan rect : rectSpans) {
            int start = htmlstr.getSpanStart(rect);
            int end = htmlstr.getSpanEnd(rect);
            Log.d("123", start + " " + end);
            final String item = htmlstr.subSequence(start, end).toString();
            style.setSpan(new CustomClickableSpan(this,item), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return style;
    }

//    public void setImageText(TextView tv){
//
//        String  html ="<p dir=\"ltr\"><img src=\"http://p1.duyao001.com/image/article/a838e283f2b5d7cc45487c5fd79f84cb.gif\"><img src=\"http://statics.zhid58.com/Fqr9YXHd20fDOqil4nLAbBhNBw0A\"><br><img src=\"http://statics.zhid58.com/FufBg05KGCLypIvrYgjaXnTWySUS\"><br>OK咯木木克人咯咯JOJO图谋木木木<a href=\"http://www.taobao.com\">http://www.taobao.com</a> jvjvjvjv jgjvvjjvjce<br><br><img src=\"https://b-ssl.duitang.com/uploads/blog/201312/04/20131204184148_hhXUT.jpeg\"><br></p><br>";
//        Spanned htmlStr = Html.fromHtml(html);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//            tv.setTextIsSelectable(true);
//        }
//        tv.setText(htmlStr);
//        tv.setMovementMethod(LinkMovementMethod.getInstance());
//        CharSequence text = tv.getText();
//        if(text instanceof Spannable){
//            int end = text.length();
//            Spannable sp = (Spannable)tv.getText();
//            URLSpan[] urls=sp.getSpans(0, end, URLSpan.class);
//            ImageSpan[] imgs = sp.getSpans(0,end,ImageSpan.class);
//            StyleSpan[] styleSpens = sp.getSpans(0,end,StyleSpan.class);
//            ForegroundColorSpan[] colorSpans = sp.getSpans(0,end,ForegroundColorSpan.class);
//            SpannableStringBuilder style=new SpannableStringBuilder(text);
//            style.clearSpans();
//            for(URLSpan url : urls){
//                style.setSpan(url,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF12ADFA"));
//                style.setSpan(colorSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            }
//            for(ImageSpan url : imgs){
//                Drawable drawable = getResources().getDrawable(R.drawable.default_icon);
//                drawable.setBounds(0, 0, 200, 200);
//                ImageSpan span = new ImageSpan(drawable);
//                style.setSpan(span,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//            for(StyleSpan styleSpan : styleSpens){
//                style.setSpan(styleSpan,sp.getSpanStart(styleSpan),sp.getSpanEnd(styleSpan),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//            for(ForegroundColorSpan colorSpan : colorSpans){
//                style.setSpan(colorSpan,sp.getSpanStart(colorSpan),sp.getSpanEnd(colorSpan),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//
//            tv.setText(style);
//        }
//    }

}
