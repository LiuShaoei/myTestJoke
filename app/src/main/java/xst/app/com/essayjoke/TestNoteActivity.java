package xst.app.com.essayjoke;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import xst.app.com.framelibrary.BaseSkinActivity;

/**
 * Created by LiuZhaowei on 2018/12/6 0006.
 */
public class TestNoteActivity extends BaseSkinActivity {

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {

    }

    //1. 解决scroll 嵌套listView显示不全的问题
    public class ImplantListView extends ListView {

        public ImplantListView(Context context) {
            super(context);
        }

        public ImplantListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ImplantListView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
