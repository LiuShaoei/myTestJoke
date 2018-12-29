package xst.app.com.essayjoke;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xst.app.com.baselibrary.ioc.ViewById;
import xst.app.com.essayjoke.city_select.CityBean;
import xst.app.com.essayjoke.city_select.CityListAdapter;
import xst.app.com.essayjoke.city_select.DBManager;
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
    @ViewById(R.id.city_select_list)
    private ListView mCitySelectList;

    private CityListAdapter mCityAdapter;
    private List<CityBean> mAllCities;
    private List<CityBean> mHotCities;
    private DBManager mDBManager;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_letter_bar;
    }

    @Override
    protected void initView() {
        mHotCities = new ArrayList<>();
        mHotCities.add(new CityBean("上海","shanghai"));
        mHotCities.add(new CityBean("北京","shanghai"));
        mHotCities.add(new CityBean("广东","shanghai"));
        mHotCities.add(new CityBean("河南","shanghai"));
        mHotCities.add(new CityBean("信阳","shanghai"));
        mHotCities.add(new CityBean("罗山","shanghai"));
        mHotCities.add(new CityBean("楠杆","shanghai"));
        mHotCities.add(new CityBean("子路","shanghai"));
        mHotCities.add(new CityBean("丰店","shanghai"));
    }

    @Override
    protected void initData() {
        mDBManager = new DBManager(this);
        mDBManager.copyDBFile();
        mAllCities = mDBManager.getAllCities();
        mCityAdapter = new CityListAdapter(this, mAllCities,mHotCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClickListener(String name) {
                //点击城市回调
                Toast.makeText(LetterBarActivity.this, name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLocateClickListener() {

            }
        });
        mCitySelectList.setAdapter(mCityAdapter);

        mLetterBar.setListener((letter, isTouch) -> {
            //得到这个城市在城市列表中的位置
            int position = mCityAdapter.getLetterPosition(letter);

            //这里是控制触摸选择的字母放大提示在屏幕中央
            if (isTouch) {
                mCenterLetter.setVisibility(View.VISIBLE);
                mCenterLetter.setText(letter);
            } else {
                //过700毫秒自动消失,在letterSideBar中做了设置
                mCenterLetter.setVisibility(View.GONE);
            }

            //当位置不等于-1是,表示有城市
            if (position != -1) {
                mLetterBar.setSelectCityColor(letter);
                //然后设置这个位置到列表的最顶端
                mCitySelectList.setSelection(position);
            } else {
                //点击字母没有索引城市
                //mLetterBar.setSelectCityColor(oldLetter);
                mCenterLetter.setText("当前字母未检测到城市");
            }
        });
        mCitySelectList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                String letter = mCityAdapter.getFirstLetter(mAllCities.get(firstVisibleItem).getPinyin());
                mLetterBar.setSelectCityColor(letter);
            }
        });
    }

    @Override
    protected void initTitle() {

    }

}
