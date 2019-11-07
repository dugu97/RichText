package com.example.richtext;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.EntityResolver2;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class MyTagHandler implements Html.TagHandler {

    private static String TAG = "MyTagHandler";
    private Context context;


    //    final HashMap<String, String> attributes = new HashMap<String, String>();

    private static class TdTag {}

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        String lowerTag = tag.toLowerCase();
        if (lowerTag.equals("table")) {
            if (opening) {
                startTableTag(tag, output, xmlReader);
            } else {
                endTableTag(tag, output, xmlReader);
            }
        }else if (lowerTag.equals("tr")){
            //此处只处理tr的结束标签
            if (!opening){
                output.append('\n');
            }
        }else if (lowerTag.equals("td")){
            if (opening){
                startTdTag(tag, output, xmlReader);
            }else {
                endTdTag(tag, output, xmlReader);
            }
        }
    }

    private void startTdTag(String tag, Editable output, XMLReader xmlReader) {
        int len = output.length();
        output.setSpan(new TdTag(), len, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private void endTdTag(String tag, Editable output, XMLReader xmlReader) {
        int len = output.length();
        output.append("|");
        //得到位于最后的TdTag
        Object obj = getLast(output, TdTag.class);
        if (obj != null) {
            setSpanFromMark(output, obj,
                    new RectSpan(),
                    new StyleSpan(Typeface.BOLD));
        }
    }

    private void startTableTag(String tag, Editable output, XMLReader xmlReader) {
//        int len = output.length();
//        output.setSpan(new TableTag(), len, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private void endTableTag(String tag, Editable output, XMLReader xmlReader) {
//        int len = output.length();
//        Object obj = getLast(output, ForegroundColorSpan);
//        if (obj != null) {
//            setSpanFromMark(text, obj, repl);
//        }
//        //这里可以将里面的内容修改成任意你想要的格式，大小，加粗，以及一系列操作
//        output.setSpan(new BackgroundColorSpan(Color.parseColor(color)), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        Log.d(TAG, "endIndex " + endIndex);
//        output.setSpan(new ForegroundColorSpan(Color.parseColor("#222222")), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private static <T> T getLast(Spanned text, Class<T> kind) {
        /*
         * This knows that the last returned object from getSpans()
         * will be the most recently added.
         */
        T[] objs = text.getSpans(0, text.length(), kind);

        if (objs.length == 0) {
            return null;
        } else {
            return objs[objs.length - 1];
        }
    }

    private static void setSpanFromMark(Spannable text, Object mark, Object... spans) {
        int where = text.getSpanStart(mark);
        text.removeSpan(mark);
        int len = text.length();
        if (where != len) {
            for (Object span : spans) {
                Log.d("dugu", text.toString() + " " + where + " " + len);
                text.setSpan(span, where, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

//    /**
//     * 利用反射获取html标签的属性值
//     *
//     * @param xmlReader
//     * @param property
//     * @return
//     */
//    private String getProperty(XMLReader xmlReader, String property) {
//        try {
//            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
//            elementField.setAccessible(true);
//            Object element = elementField.get(xmlReader);
//            Field attsField = element.getClass().getDeclaredField("theAtts");
//            attsField.setAccessible(true);
//            Object atts = attsField.get(element);
//            Field dataField = atts.getClass().getDeclaredField("data");
//            dataField.setAccessible(true);
//            String[] data = (String[]) dataField.get(atts);
//            Field lengthField = atts.getClass().getDeclaredField("length");
//            lengthField.setAccessible(true);
//            int len = (Integer) lengthField.get(atts);
//
//            for (int i = 0; i < len; i++) {
//                // 这边的property换成你自己的属性名就可以了
//                if (property.equals(data[i * 5 + 1])) {
//                    return data[i * 5 + 4];
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
