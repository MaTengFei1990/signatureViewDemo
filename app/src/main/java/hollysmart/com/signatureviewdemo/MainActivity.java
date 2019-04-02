package hollysmart.com.signatureviewdemo;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {


    private CanvasView canvasView;
    private ImageView iv_show;
    private LinearLayout ll_canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        canvasView = findViewById(R.id.CanvasView);
        iv_show = findViewById(R.id.iv_show);
        ll_canvas = findViewById(R.id.ll_canvas);
       findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               canvasView.clear();

           }
       });
       findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Bitmap bitmap = canvasView.createBitmap(ll_canvas);

               iv_show.setImageBitmap(bitmap);

               saveBitmap(bitmap);

           }
       });


    }


    /**
     * 这个地方需要有6.0 的文件权限；在用的时候要动态申请
     * 将bitmap 保存在文件中；
     * @param bitmap
     */
    private void saveBitmap(Bitmap bitmap) {
        FileOutputStream fos;
        try {
            String  root = Environment.getExternalStorageDirectory()+ "/smart_park/";
            File file = new File(root, "test.png");
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
