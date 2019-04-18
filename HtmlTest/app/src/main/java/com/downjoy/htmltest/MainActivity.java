package com.downjoy.htmltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.*;
import android.text.method.LinkMovementMethod;
import android.text.style.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    List<String> imgSource = new ArrayList<>();
    ImageSpan[] imags;
    ViewPager viewPager;
    VpAdapter vpAdapter;

    private String getTest() {
        String s1 = "<strong>强调</strong><br>";
        s1 += "<em>斜体</em><br>";
        s1 += "<b>加粗</b><br>";
        s1 += "<h1>标题1</h1><br>";
        s1 += "<h2>标题2</h2><br>";
        s1 += "<h3>标题3</h3><br>";
        s1 += "<h4>标题4</h4><br>";
        s1 += "<h5>标题5</h5><br>";
        s1 += "<font color='#FF6A6A'>设置红色</font><br>";
        s1 += "<a href=\"https://www.baidu.com\">百度链接</a><br>";
        s1 += "<img src=\"https://www.laoyuyu.me/assets/img/linux/openvpn.jpg\"/><br>";
        s1 += "\"<p>友友您好，有两种办法可以帮到您：</p><p>（1）如果由于手机重装/恢复出厂设置等原因导致帐号丢失，建议通过帐号绑定的QQ/微信/微博/手机/邮箱点击<a href=\\\"http://i.d.cn/\\\"><span style=\\\"color:#ff0000;\\\">这里登陆</span></a>，即可找回您的帐号；</p><p>（2）如果没有绑定以上信息，您也可以点击这里<a href=\\\"https://kf.d.cn/#/findAccount/byIMEI?id=1&parentId=1\\\"><span style=\\\"color:#ff0000;\\\">找回帐号</span></a>，提交帐号相关信息，通过系统为您进行帐号自助找回，感谢您对当乐的支持。</p><p>　　<br/></p><br>";
        s1 += "<img src=\"https://www.laoyuyu.me/assets/img/docker/what-is-nginx.png\"/><br>";
        s1 += "<h5>标题5</h5><br>";
        s1 += "<img src=\"https://www.laoyuyu.me/assets/img/%E4%B8%8D%E6%94%AF%E6%8C%81windows.png\"/><br>";
        s1 += "<img src=\"https://upload-images.jianshu.io/upload_images/1925324-276ca84ef89aa269.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/349/format/webp\"/><br>";
        s1 += "<h5>标题5</h5><br>";
        s1 += "<h5>标题5</h5><br>";
        s1 += "<h1>标题1</h1><br>";
        return s1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String source = getTest();
        tv = findViewById(R.id.text);
        Spanned spanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT, new IamegGetter(), null);
        } else {
            spanned = Html.fromHtml(source, null, new HtmlTagHandler("gg"));
        }

        SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(spanned);
        URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spanned.length(), URLSpan.class);
        for (URLSpan span : urls) {
            setLinkClickAction(clickableHtmlBuilder, span);
        }
        imags = clickableHtmlBuilder.getSpans(0, spanned.length(), ImageSpan.class);
        List<Drawable> drawables = new ArrayList<>();
        for (ImageSpan span : imags) {
            imgSource.add(span.getSource());
            setImageClickAction(clickableHtmlBuilder, span);
            drawables.add(span.getDrawable());
        }

        tv.setText(clickableHtmlBuilder);
        tv.setMovementMethod(LinkMovementMethod.getInstance());//设置可点击

        viewPager = findViewById(R.id.vp);
        vpAdapter = new VpAdapter(imgSource);
        viewPager.setAdapter(vpAdapter);
    }

    /**
     * 设置图片点击事件
     */
    private void setImageClickAction(final SpannableStringBuilder clickableHtmlBuilder, final ImageSpan imageSpan) {
        int start = clickableHtmlBuilder.getSpanStart(imageSpan);
        int end = clickableHtmlBuilder.getSpanEnd(imageSpan);
        int flags = clickableHtmlBuilder.getSpanFlags(imageSpan);
        Log.d("TAG", "imgSpan:" + start + ", " + end);
        ClickableSpan clickableSpan = new ClickableSpan() {

            @Override
            public void onClick(View view) {
                int postion = imgSource.indexOf(imageSpan.getSource());
//                Log.d("TAG", "postion = " + postion + ", id = " + view.getId());
                viewPager.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(postion, false);
                int[] xy = new int[2];
                view.getLocationOnScreen(xy);
//                Log.d("TAG", Arrays.toString(xy));
//                Log.d("TAG", "rect = " + imageSpan.getDrawable().getBounds());
//                Log.d("TAG", "rect = " + ((DrawableWrapper)imageSpan.getDrawable()).canvas.getClipBounds().toString());
            }

        };
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getVisibility() == View.VISIBLE) {
            viewPager.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
            for (ImageSpan span : imags){
            }
        }
    }

    /**
     * 设置超链接点击事件
     */
    private void setLinkClickAction(final SpannableStringBuilder clickableHtmlBuilder, final URLSpan urlSpan) {
        int start = clickableHtmlBuilder.getSpanStart(urlSpan);
        int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
        int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);
        ClickableSpan clickableSpan = new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置是否要下划线
                ds.setUnderlineText(false);
                //设置颜色
                ds.setColor(Color.parseColor("#00B2EE"));
            }

            @Override
            public void onClick(View view) {
                //Do something with URL here.
                String url = urlSpan.getURL();
                Log.d("TAG", "id = " + view.getId());
                Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
                System.out.println(url);
            }

        };
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
    }

    private class IamegGetter implements Html.ImageGetter {

        @Override
        public Drawable getDrawable(final String source) {
            final DrawableWrapper imgDrawable = new DrawableWrapper();

            // 设置默认图
            Drawable dfDrawable = getResources().getDrawable(R.mipmap.ic_launcher);
            Rect dfRect = new Rect(0, 0, tv.getMeasuredWidth(), (int) getResources().getDimension(R.dimen.imhH));
            dfDrawable.setBounds(dfRect);
            imgDrawable.setBounds(dfRect);
            imgDrawable.setDrawable(dfDrawable);

            // 加载图片,
            // 如果是使用ImageLoader加载图片，那么请不要使用单例方式实现的ImageLoader.ImageCache，否则回出现bitmap释放不了，重新进入界面，图片不显示的问题
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            ImageLoader loader = new ImageLoader(queue, new BitmapCache());
            loader.get(source, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    Bitmap bm = response.getBitmap();
                    if (bm != null) {
                        Log.d("TAG", "bitmapH = " + bm.getWidth() + "/" + bm.getHeight());
                        int tvWidth = tv.getMeasuredWidth();

                        Drawable bd = new BitmapDrawable(bm);
                        Rect rect = new Rect(0, 0, tvWidth, tvWidth * bm.getHeight() / bm.getWidth());
//                        Rect rect = new Rect(0, 0, bm.getWidth(), bm.getHeight());
                        bd.setBounds(rect);
                        imgDrawable.setBounds(rect);
                        imgDrawable.setDrawable(bd);
                        Log.d("TAG", "tvH = " + tv.getMeasuredHeight() + ", hashCode = " + tv.hashCode());
//                        int tvH = tv.getHeight() + bm.getHeight();
                        int tvH = tv.getHeight() + rect.bottom;
                        ViewGroup.LayoutParams lp = tv.getLayoutParams();
                        lp.height = tvH;
                        tv.setLayoutParams(lp);
                        tv.setMaxHeight(tvH);  // 更新textview高度
//                        Log.d("TAG", "TVH = " + tvH);
                        tv.postInvalidate();    // 刷新view
                    } else {
//                        Drawable drawable = getResources().getDrawable(Resource.drawable.dgcs_ic_default);
//                        imgDrawable.setDrawable(drawable);
//                        tv.postInvalidate();    // 刷新view
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
//                    Drawable drawable = getResources().getDrawable(Resource.drawable.dgcs_ic_default);
//                    imgDrawable.setDrawable(drawable);
//                    tv.postInvalidate();    // 刷新view
                }
            });


            return imgDrawable;
        }
    }

    private class DrawableWrapper extends BitmapDrawable {
        private Drawable drawable;
        Canvas canvas;

        DrawableWrapper() {
        }

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null)
                drawable.draw(canvas);
            this.canvas = canvas;
        }

        public Drawable getDrawable() {
            return drawable;
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }


    }

    private class HtmlTagHandler implements Html.TagHandler {
        // 自定义标签名称
        private String tagName;

        // 标签开始索引
        private int startIndex = 0;
        // 标签结束索引
        private int endIndex = 0;
        // 存放标签所有属性键值对
        final HashMap<String, String> attributes = new HashMap<>();

        public HtmlTagHandler(String tagName) {
            this.tagName = tagName;
        }

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            // 判断是否是当前需要的tag
            if (tag.equalsIgnoreCase(tagName)) {
                // 解析所有属性值
                parseAttributes(xmlReader);

                if (opening) {
                    startHandleTag(tag, output, xmlReader);
                } else {
                    endEndHandleTag(tag, output, xmlReader);
                }
            }
        }

        public void startHandleTag(String tag, Editable output, XMLReader xmlReader) {
            startIndex = output.length();
        }

        public void endEndHandleTag(String tag, Editable output, XMLReader xmlReader) {
            endIndex = output.length();

            // 获取对应的属性值
            String color = attributes.get("color");
            String size = attributes.get("size");
            size = size.split("px")[0];

            // 设置颜色
            if (!TextUtils.isEmpty(color)) {
                output.setSpan(new ForegroundColorSpan(Color.parseColor(color)), startIndex, endIndex,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            // 设置字体大小
            if (!TextUtils.isEmpty(size)) {
                output.setSpan(new AbsoluteSizeSpan(Integer.parseInt(size)), startIndex, endIndex,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        /**
         * 解析所有属性值
         *
         * @param xmlReader
         */
        private void parseAttributes(final XMLReader xmlReader) {
            try {
                Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
                elementField.setAccessible(true);
                Object element = elementField.get(xmlReader);
                Field attsField = element.getClass().getDeclaredField("theAtts");
                attsField.setAccessible(true);
                Object atts = attsField.get(element);
                Field dataField = atts.getClass().getDeclaredField("data");
                dataField.setAccessible(true);
                String[] data = (String[]) dataField.get(atts);
                Field lengthField = atts.getClass().getDeclaredField("length");
                lengthField.setAccessible(true);
                int len = (Integer) lengthField.get(atts);

                for (int i = 0; i < len; i++) {
                    attributes.put(data[i * 5 + 1], data[i * 5 + 4]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class VpAdapter extends PagerAdapter {
        private List<String> urls;
        private List<VpItemView> items = new ArrayList<>();

        private VpAdapter(List<String> drawables) {
            this.urls = drawables;
        }

        @Override
        public int getCount() {
            return urls == null ? 0 : urls.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view.equals(o);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            VpItemView item = null;
            if (position < items.size()){
                item = items.get(position);
            }
            if (item == null){
                item = new VpItemView(MainActivity.this);
                item.setImageUrl(urls.get(position));
                item.setOnImageClick(new VpItemView.OnImageClick() {
                    @Override
                    public void onClick(SubsamplingScaleImageView imageView) {
                        imageView.resetScaleAndCenter();
                        viewPager.setVisibility(View.GONE);
                    }
                });
            }
            container.addView(item, position);
            return item;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeViewAt(position);
        }
    }

    private static class VpItemView extends RelativeLayout{
        private SubsamplingScaleImageView imageView;
        private OnImageClick onImageClick;

        interface OnImageClick {
            void onClick(SubsamplingScaleImageView imageView);
        }

        public VpItemView(Context context) {
            super(context);
            init();
        }

        public VpItemView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init(){
            LayoutInflater.from(getContext()).inflate(R.layout.layout_img, this, true);
            imageView = findViewById(R.id.img);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onImageClick != null){
                        onImageClick.onClick(imageView);
                    }
                }
            });
        }

        public void setOnImageClick(OnImageClick onImageClick) {
            this.onImageClick = onImageClick;
        }

        public void setImageResource(int res){
            imageView.setImage(ImageSource.resource(res));
        }

        public void setImageUrl(String url){
            RequestQueue mQueue = Volley.newRequestQueue(getContext());
            ImageRequest imageRequest = new ImageRequest(
                    url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            imageView.setImage(ImageSource.bitmap(response));

                        }
                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    // 设置失败图
//                    Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
//                    imgDrawable.setDrawable(drawable);
//                    Log.d("tAG", "加载失败");
//                    tv.postInvalidate();    // 刷新view
                }
            });
            mQueue.add(imageRequest);
        }


    }

}
