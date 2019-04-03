package hollysmart.com.signatureviewdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private CanvasView canvasView;
    private ImageView iv_show;
    private LinearLayout ll_canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        setListener();
        //请求权限
        myPermission();

    }

    private void findView() {
        canvasView = findViewById(R.id.CanvasView);
        iv_show = findViewById(R.id.iv_show);
        ll_canvas = findViewById(R.id.ll_canvas);
    }

    private void setListener() {
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_clear:

                clear();

                break;
            case R.id.btn_save:

                save2File();

                break;
            default:

                break;

        }

    }

    /**
     * 清除手写的签名
     */
    private void clear() {
        canvasView.clear();
    }


    /**
     * 保存为文件
     */

    private void save2File() {

        Bitmap bitmap = canvasView.createBitmap(ll_canvas);

        iv_show.setImageBitmap(bitmap);

        saveBitmap(bitmap);
    }




    /**
     *
     * 将bitmap 保存在文件中；
     * @param bitmap
     */
    private void saveBitmap(Bitmap bitmap) {
        FileOutputStream fos;
        try {
            String  root = Environment.getExternalStorageDirectory()+ "/signatureview/";
            File file = new File(root, "test.png");
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @TargetApi(23)
    private void myPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            CreateDir();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限请求成功的操作
                    CreateDir();
                } else {
                    // 权限请求失败的操作
                    Toast.makeText(MainActivity.this, "请在权限管理中设置存储权限，不然会影响正常使用",Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }


    private void CreateDir() {
        CreateDir(Environment.getExternalStorageDirectory()+ "/signatureview/");
    }

    private File CreateDir(String folder) {
        File dir = new File(folder);
        dir.mkdirs();
        return dir;
    }


}
