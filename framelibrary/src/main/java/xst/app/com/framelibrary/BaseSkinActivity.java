package xst.app.com.framelibrary;

import xst.app.com.baselibrary.base.BaseActivity;

/**
 * Created by LiuZhaowei on 2018/12/6 0006.
 */
public abstract class BaseSkinActivity extends BaseActivity {
//    private static final String TAG = "tag" ;
//    //后期修改,预留层
//
//    private SkinAppCompatViewInflater mSkinAppCompatViewInflater;
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceStat) {
//        LayoutInflater layoutInflater = LayoutInflater.from(this);
//        LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflater.Factory2() {
//            @Override
//            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//                //拦截View的创建  获取View之后要去解析
//                //1.创建View
//
//                //2.解析属性 src text textColor background 自定义属性
//
//                //3.统一交给SkinManager管理
////                if(name.equals("Button")){
////                    TextView tv = new TextView(BaseSkinActivity.this);
////                    tv.setText("拦截");
////                    return tv;
////                }
//
//                View view  = createView(parent,name,context,attrs);
//               // Log.e(TAG,view+"");
//                // 2.1一个activity的布局肯定对应多个这样的SkinView
//                if(view != null){
//                    List<SkinAttr> skinAttrs = SkinAttrSupport.getSkinAttrs(context ,attrs);
//                    SkinView skinView = new SkinView(view,skinAttrs);
//                    managerSkinView(skinView);
//
//                    //4.统一判断要不要换肤
//                    SkinManager.getInstance().checkChangeSkin(skinView);
//                }
//                return view;
//            }
//
//            @Override
//            public View onCreateView(String name, Context context, AttributeSet attrs) {
//                return null;
//            }
//        });
//        super.onCreate(savedInstanceStat);
//
//    }
//
//    /**
//     *
//     * 统一管理skinView
//     */
//
//    private void managerSkinView(SkinView skinView){
//        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
//        if(skinViews == null){
//            skinViews = new ArrayList<>();
//            SkinManager.getInstance().register(this,skinViews);
//        }
//        skinViews.add(skinView);
//    }
//
//    protected  View createView(View parent, String name, Context context, AttributeSet attrs){
//        final boolean isPre21 = Build.VERSION.SDK_INT < 21;
//        if(mSkinAppCompatViewInflater == null){
//            mSkinAppCompatViewInflater = new SkinAppCompatViewInflater();
//        }
//        final boolean inheritContext = isPre21 && true && shouldInheritContext((ViewParent) parent);
//        return mSkinAppCompatViewInflater.createView(parent,name,context,attrs,inheritContext,isPre21,true,true);
//    }
//
//    protected  boolean shouldInheritContext(ViewParent parent){
//        if(parent == null){
//            return false;
//        }
//        while (true){
//            if(parent == null){
//                return true;
//            }else if(parent == getWindow().getDecorView() ||
//                    !(parent instanceof  View) ||
//                    ViewCompat.isAttachedToWindow((View) parent)){
//                return false;
//            }
//            parent = parent.getParent();
//        }
//    }

}
