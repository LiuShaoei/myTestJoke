package xst.app.com.essayjoke;

import android.view.View;
import android.widget.TextView;
import xst.app.com.baselibrary.ioc.ViewById;
import xst.app.com.baselibrary.letterSideBar.LetterSideBar;
import xst.app.com.framelibrary.BaseSkinActivity;

/**
 * Created by LiuZhaowei on 2018/12/24 0024.
 */
public class LetterBarActivity extends BaseSkinActivity {
    @ViewById(R.id.current_letter)
    private TextView mCenterLetter;
    @ViewById(R.id.letter_bar)
    private LetterSideBar mLetterBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_letter_bar;
    }

    @Override
    protected void initView() {
        mLetterBar.setListener((text, isTouch) -> {
                    if (isTouch) {
                        mCenterLetter.setVisibility(View.VISIBLE);
                        mCenterLetter.setText(text);
                    }else{
                        mCenterLetter.setVisibility(View.GONE);
                    }
                }
        );
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {

    }
}
