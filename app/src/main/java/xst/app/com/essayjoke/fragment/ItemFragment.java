package xst.app.com.essayjoke.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xst.app.com.essayjoke.R;

/**
 * Created by LiuZhaowei on 2018/12/12 0012.
 */
public class ItemFragment extends Fragment {


    private TextView mTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.itme_fragment,container,false);
        mTextView = view.findViewById(R.id.text_content);
        Bundle bundle = getArguments();
        mTextView.setText(bundle.getString("title","我是默认的"));
        return view;
    }

    public static ItemFragment newInstance(String item){
        ItemFragment f = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",item);
        f.setArguments(bundle);
        return f;
    }


}
