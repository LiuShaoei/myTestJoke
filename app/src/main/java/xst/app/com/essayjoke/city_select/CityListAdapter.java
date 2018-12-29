package xst.app.com.essayjoke.city_select;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import xst.app.com.essayjoke.R;

/**
 * Created by LiuZhaowei on 2018/12/29 0029.
 */
public class CityListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 3;
    private Context mContext;
    private List<CityBean> mCities;
    private List<CityBean> mHotCities;
    private LayoutInflater mInflater;

    private HashMap<String, Integer> mLetterIndex;
    private String[] mSections;
    private String mLocationCity;
    private int mLocationState;
    private OnCityClickListener onCityClickListener;

    public CityListAdapter(Context context, List<CityBean> cityBean, List<CityBean> hotCities) {
        this.mContext = context;
        this.mCities = cityBean;
        this.mHotCities = hotCities;
        this.mInflater = LayoutInflater.from(mContext);
        if (mCities == null) {
            mCities = new ArrayList<>();
        }
        mCities.add(0, new CityBean("定位", "0"));
        mCities.add(1, new CityBean("热门", "1"));
        int size = mCities.size();
        mLetterIndex = new HashMap<>();
        mSections = new String[size];
        for (int index = 0; index < size; index++) {
            //当前城市拼音首字母
            String currentLetter = getFirstLetter(mCities.get(index).getPinyin());
            //上个首字母,如果不存在就设为"
            String previousLetter = index >= 1 ? getFirstLetter(mCities.get(index - 1).getPinyin()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                mLetterIndex.put(currentLetter, index);
                mSections[index] = currentLetter;
            }
        }
    }

    /**
     * 更新定位状态
     *
     * @return
     */
    public void updateLocationState(int state, String city) {
        this.mLocationState = state;
        this.mLocationCity = city;
    }

    /**
     * 获取字母索引的位置
     *
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = mLetterIndex.get(letter);
        return integer == null ? -1 : integer;
    }


    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public CityBean getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                //定位
                convertView = mInflater.inflate(R.layout.ui_select_view_locate_city, parent, false);
                switch (mLocationState) {
                    case LocateState.LOCATING:
                        break;
                    case LocateState.FAILED:
                        break;
                    case LocateState.SUCCESS:
                        break;
                }
                break;
            case 1:
                //热门城市
                convertView = mInflater.inflate(R.layout.ui_select_item_hot_city, parent, false);
                RecyclerView recyclerView = convertView.findViewById(R.id.recycle_view);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                recyclerView.setAdapter(new RecyclerView.Adapter() {
                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        Log.i("love",i+"");
                        return HotCityViewHolder.get(mContext, viewGroup, R.layout.item_tag);
                    }

                    @Override
                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
                        Log.i("love",i+"love");
                        TextView textView = ((HotCityViewHolder)holder).getView(R.id.tag_tv);
                        textView.setText(mHotCities.get(i).getName());
                    }

                    @Override
                    public int getItemCount() {
                        return mHotCities.size();
                    }

                    @Override
                    public int getItemViewType(int position) {
                        if(position == 1){
                            return 1;
                        }
                        if(position == 0){
                            return 0;
                        }
                        return super.getItemViewType(position);
                    }
                });
                break;
            case 2:
                //全部城市
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.ui_select_item_city, parent, false);
                    holder = new CityViewHolder();
                    holder.setLetter(convertView.findViewById(R.id.tv_item_city_letter));
                    holder.setName(convertView.findViewById(R.id.tv_item_city_name));
                    convertView.setTag(holder);
                } else {
                    holder = (CityViewHolder) convertView.getTag();
                }
                if (position >= 1) {
                    final String city = mCities.get(position).getName();
                    holder.getName().setText(city);
                    String currentLetter = getFirstLetter(mCities.get(position).getPinyin());
                    String previousLetter = position >= 1 ? getFirstLetter(mCities.get(position - 1).getPinyin()) : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)) {
                        holder.getLetter().setVisibility(View.VISIBLE);
                        holder.getLetter().setText(currentLetter);
                    } else {
                        holder.getLetter().setVisibility(View.GONE);
                    }
                    holder.getName().setOnClickListener(v -> {
                        if (onCityClickListener != null) {
                            onCityClickListener.onCityClickListener(city);
                        }
                    });
                }
                break;
        }
        return convertView;
    }

    public String getFirstLetter(String pinyin) {
        if (TextUtils.isEmpty(pinyin)) return "定位";
        String c = pinyin.substring(0, 1);
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c).matches()) {
            return c.toUpperCase();
        } else if ("0".equals(c)) {
            return "定位";
        } else if ("1".equals(c)) {
            return "热门";
        }
        return "定位";
    }

    public class LocateState {
        public static final int LOCATING = 0X0011;
        public static final int FAILED = 0X0012;
        public static final int SUCCESS = 0X0013;
    }

    /**
     * 点击回调的监听事件
     *
     * @param listener
     */
    public void setOnCityClickListener(OnCityClickListener listener) {
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener {
        void onCityClickListener(String name);

        void onLocateClickListener();
    }

    private class CityViewHolder {
        public TextView getLetter() {
            return letter;
        }

        public void setLetter(TextView letter) {
            this.letter = letter;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }
        TextView letter;
        TextView name;
    }
}
