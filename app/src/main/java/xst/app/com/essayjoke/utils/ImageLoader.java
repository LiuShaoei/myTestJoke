package xst.app.com.essayjoke.utils;

import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import xst.app.com.baselibrary.commonAdapter.ViewHolder;

/**
 * Created by LiuZhaowei on 2018/12/10 0010.
 */
public class ImageLoader extends ViewHolder.HolderImageLoader {
    public ImageLoader(String path) {
        super(path);
    }
    @Override
    public void loadImage(ImageView imageView, String path) {
       // Glide.with(imageView.getContext()).load(path).into(imageView);

    }
}
