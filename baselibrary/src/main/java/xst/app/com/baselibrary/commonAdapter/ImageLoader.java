package xst.app.com.baselibrary.commonAdapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by LiuZhaowei on 2018/12/10 0010.
 */
public class ImageLoader extends ViewHolder.HolderImageLoader {
    public ImageLoader(String path) {
        super(path);
    }
    @Override
    public void loadImage(ImageView imageView, String path) {
        Glide.with(imageView.getContext()).load(path).into(imageView);
    }
}
