package xst.app.com.essayjoke;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import xst.app.com.baselibrary.ioc.OnClick;
import xst.app.com.baselibrary.ioc.ViewById;
import xst.app.com.essayjoke.fixbug.FixDexManager;
import xst.app.com.essayjoke.other.MessageService;
import xst.app.com.framelibrary.BaseSkinActivity;
import xst.app.com.framelibrary.DefaultNavigationBar;

public class MainActivity extends BaseSkinActivity {
    /****Hello World!****/
    // @ViewById(R.id.test_tv)
    // private TextView mTestTv;
    @ViewById(R.id.text)
    Button mText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        verifyStoragePermissions(this);
//        findViewById(R.id.test_image).setOnClickListener((view) -> {
//            //Toast.makeText(MainActivity.this,2/1+"But测试",Toast.LENGTH_SHORT).show();
//            //  startActivity(TestActivity.class);
//            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
//                    .setContentView(R.layout.dialog_test)
//                    .setText(R.id.test_first, "接胡搜")
//                    .fullWidth().setOnClickListener(R.id.test_end, (v1) ->
//                            Toast.makeText(MainActivity.this, "test_end", Toast.LENGTH_SHORT).show()
//                    ).setAnimation(R.style.AnimDown)
//                    .show();
//
//            TextView textView = dialog.getView(R.id.test_first);
//            TextView textView1 = dialog.getView(R.id.test_end);
//
//            dialog.setOnClickListener(R.id.test_first, (v) ->
//                    textView1.setText(textView.getText().toString().trim())
//            );

    }

    @OnClick({R.id.to_message, R.id.to_load, R.id.to_recycler_view, R.id.to_custom_view, R.id.to_custom_banner, R.id.to_letter, R.id.to_tag})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.to_recycler_view:
                startActivity(TestRecyclerViewActivity.class);
                break;
            case R.id.to_custom_view:
                startActivity(CustomViewActivity.class);
                break;
            case R.id.to_custom_banner:
                startActivity(BannerTestActivity.class);
                break;
            case R.id.to_letter:
                startActivity(LetterBarActivity.class);
                break;
            case R.id.to_tag:
                startActivity(TagActivity.class);
                break;
            case R.id.to_load:
                startActivity(LoadingViewActivity.class);
                break;
            case R.id.to_message:
                startActivity(MessageActivity.class);
                break;
        }

    }

    @Override
    protected void initData() {
        //  mTestTv.setText("Ioc");
        // fixDexBug();
       /* File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
        boolean b = fixFile.exists();
        if (b) {
            //修复Bug
            try {
                String file = fixFile.getAbsolutePath();
                BaseApplication.mPatchManager.addPatch(file);
                Toast.makeText(MainActivity.this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "修复失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }*/

        //1.启动一个服务.
        //  ObserverManager.getInstance().add(this);
        startService(new Intent(this, MessageService.class));
    }

    /**
     * 自己的修复方式
     */
    private void fixDexBug() {
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        if (fixFile.exists()) {
            //存在就修复
            FixDexManager fixDexManager = new FixDexManager(this);
            try {
                fixDexManager.fixDex(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }


    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("内涵段子").builder();
    }


//    @CheckNet
//    @OnClick({R.id.test_tv, R.id.test_img})
//    private void testTvClick(View view) {
//        if (view.getId() == R.id.test_tv) {
//            Toast.makeText(this, "文字", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "图片", Toast.LENGTH_SHORT).show();
//        }
//    }

}