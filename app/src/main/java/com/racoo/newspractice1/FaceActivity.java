package com.racoo.newspractice1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

import java.util.List;

public class FaceActivity extends Activity {

    Context mContext = this;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_face);

        final RelativeLayout RelativeLayout_main = findViewById(R.id.RelativeLayout_main);

        FirebaseVisionFaceDetectorOptions options =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .build();

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.exampicture);

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options);

        Task<List<FirebaseVisionFace>> result =
                detector.detectInImage(image)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<FirebaseVisionFace>>() {
                                    @Override
                                    public void onSuccess(List<FirebaseVisionFace> faces) {
                                        // Task completed successfully
                                        // ...

                                        Log.d("Face", faces.toString());

                                        Point p = new Point();
                                        Display display = getWindowManager().getDefaultDisplay();
                                        display.getSize(p);

                                        //얼굴 인식기의 비트맵 사진 안드로이드의 좌표 오차를 따기 위한 방안, 1. 일단 안드로이드 기기의 전체 좌표 구한다.과

                                        //1:10=10:x






                                        for (FirebaseVisionFace face : faces) {
                                            Rect bounds = face.getBoundingBox();

                                            FirebaseVisionFaceLandmark leftEye = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE);
                                            float lex = leftEye.getPosition().getX();
                                            float ley = leftEye.getPosition().getY();

                                            FirebaseVisionFaceLandmark leftcheek = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_CHEEK);
                                            float lcx = leftcheek.getPosition().getX();
                                            float lcy = leftcheek.getPosition().getY();

                                            FirebaseVisionFaceLandmark rightcheek = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_CHEEK);
                                            float rcx = rightcheek.getPosition().getX();
                                            float rcy = rightcheek.getPosition().getY();

                                            ImageView imageLE = new ImageView(mContext);
                                            imageLE.setImageResource(R.drawable.mung);
                                            imageLE.setX(p.x * lex / bitmap.getWidth()-100);
                                            imageLE.setY(p.y * ley / bitmap.getHeight()-100);
                                            imageLE.setLayoutParams(new RelativeLayout.LayoutParams(200,200));

                                            RelativeLayout_main.addView(imageLE);
                                            ImageView imageLC = new ImageView(mContext);
                                            imageLC.setImageResource(R.drawable.left_whiskers);
                                            imageLC.setX(p.x * lcx / bitmap.getWidth()-100);
                                            imageLC.setY(p.y * lcy / bitmap.getWidth()-100);
                                            imageLC.setLayoutParams(new RelativeLayout.LayoutParams(200,200));

                                            RelativeLayout_main.addView(imageLC);
                                            ImageView imageRC = new ImageView(mContext);
                                            imageRC.setImageResource(R.drawable.right_whiskers);
                                            imageRC.setX(p.x * rcx / bitmap.getWidth()-100);
                                            imageRC.setY(p.x * rcy / bitmap.getWidth()-100);
                                            imageRC.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
                                            RelativeLayout_main.addView(imageRC);





                                        }
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });


    }
}
