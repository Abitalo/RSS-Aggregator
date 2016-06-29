package com.abitalo.www.rss_aggregator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pizidea.imagepicker.AndroidImagePicker;

/**
 * Created by Lancelot on 2016/6/22.
 */
public class IconEditActivity extends AppCompatActivity implements AndroidImagePicker.OnImageCropCompleteListener{

    private final int REQ_IMAGE_CROP = 1435;

    private ImageView iconImage;
    private Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_edit);

        iconImage=(ImageView)findViewById(R.id.icon_edit_image);
        editBtn=(Button)findViewById(R.id.icon_edit_btn);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                int requestCode = REQ_IMAGE_CROP;
                AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_SINGLE);
                AndroidImagePicker.getInstance().setShouldShowCamera(true);
                intent.putExtra("isCrop", true);
                intent.setClass(IconEditActivity.this,PickerActivity.class);
                startActivityForResult(intent, requestCode);
            }


        });
    }

    @Override
    public void onImageCropComplete(Bitmap bmp, float ratio) {
        Log.i("abitalo","=====onImageCropComplete (get bitmap="+bmp.toString());
        iconImage.setImageBitmap(bmp);
    }
}
