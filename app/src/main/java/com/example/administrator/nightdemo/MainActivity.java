package com.example.administrator.nightdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.nightdemo.setter.ViewGroupSetter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatBtn1;
    private FloatingActionMenu floatingActionMenu;
    Colorful mColorful;
    private RecyclerView mRecyclerView;
    List<String> mNewsList = new ArrayList<String>();
    boolean isNight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_labels_right);
        floatBtn1 = (FloatingActionButton) findViewById(R.id.id_float_btn1);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemViewCacheSize(0);
        //属性动画
//        int w = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        floatingActionMenu.measure(w,h);
//        int measuredHeight = floatingActionMenu.getMeasuredHeight();
//        final ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(floatingActionMenu,"scaleX", 1.0f, 0.1f);
//        final ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(floatingActionMenu,"scaleY", 1.0f, 0.2f);
//        final ObjectAnimator alpha = ObjectAnimator.ofFloat(floatingActionMenu,"alpha", 1.0f, 0.2f);
//        final ObjectAnimator translateY = ObjectAnimator.ofFloat(floatingActionMenu,"translationY", 0f,(float) measuredHeight);
//        final ObjectAnimator translateX= ObjectAnimator.ofFloat(floatingActionMenu,"translationX", 0f,500f);
//        final AnimatorSet set = new AnimatorSet();
//        set.playTogether(scaleOutX,scaleOutY,alpha,translateY,translateX);
//        set.setDuration(1000);
        floatBtn1.setLabelText("切换夜间主题");
        floatBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                floatingActionMenu.setAnimation(set);
//                set.start();
//                floatingActionMenu.setVisibility(View.GONE);
                if(!isNight){
                    mColorful.setTheme(R.style.NightTheme);
                    floatBtn1.setLabelText("切换日间主题");
                }else{
                    mColorful.setTheme(R.style.DayTheme);
                    floatBtn1.setLabelText("切换夜间主题");
                }

                isNight = !isNight;
            }
        });

        // 模拟数据
        mockNews();
        mRecyclerView.setAdapter(new NewsAdapter());

        // 初始化Colorful
        setupColorful();
    }

    private void mockNews() {
        for (int i = 0; i < 20; i++) {
            mNewsList.add("News Title - " + i);
        }
    }


    /**
     * 设置各个视图与颜色属性的关联
     */
    private void setupColorful() {
        ViewGroupSetter recyclerViewSetter = new ViewGroupSetter(mRecyclerView);
        // 绑定ListView的Item View中的news_title视图，在换肤时修改它的text_color属性
        recyclerViewSetter.childViewTextColor(R.id.news_title, R.attr.text_color);

        // 构建Colorful对象来绑定View与属性的对象关系
        mColorful = new Colorful.Builder(this)
                .backgroundDrawable(R.id.root_view, R.attr.root_view_bg)
                // 设置view的背景图片
//                .backgroundColor(R.id.change_btn, R.attr.btn_bg)
                // 设置背景色
                .textColor(R.id.textview, R.attr.text_color)
                .setter(recyclerViewSetter) // 手动设置setter
                .create(); // 设置文本颜色
    }


    public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

        @Override
        public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(MainActivity.this).inflate( R.layout.recycle_item,parent, false);
            return new NewsHolder(inflate);
        }

        @Override
        public void onBindViewHolder(NewsHolder holder, int position) {
            holder.mTextView.setText(mNewsList.get(position));
            holder.setIsRecyclable(false);//设置不复用holder，否则会出现某一个遮挡的子view主题未更换
        }

        @Override
        public int getItemCount() {
            return null == mNewsList ? 0 : mNewsList.size();
        }
    }

    public class NewsHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public NewsHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.news_title);
        }
    }
}
