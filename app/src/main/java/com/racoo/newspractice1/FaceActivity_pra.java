package com.racoo.newspractice1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
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

public class FaceActivity_pra extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_face_pra);

        RelativeLayout RelativeLayout_main_pra = findViewById(R.id.RelativeLayout_main_pra);

        Context mContext = this;

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.exampicture_pra);

        FirebaseVisionFaceDetectorOptions highAccuracyOpts =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .build();


        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(highAccuracyOpts);

        Task<List<FirebaseVisionFace>> result =
                detector.detectInImage(image)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<FirebaseVisionFace>>() {
                                    @Override
                                    public void onSuccess(List<FirebaseVisionFace> faces) {
                                        // Task completed successfully
                                        // ...

                                        Point p = new Point(); // 포인트를 위한 p
                                        Display display = getWindowManager().getDefaultDisplay(); // display 객체를 가져와라.
                                        display.getSize(p); //p라는 포인트에 디스플레이 끝 좌표, 즉 디스플레이 최대 좌표 투입.

                                        //p.x p.y 는 디스플레이의 좌표.



                                        for (FirebaseVisionFace face : faces) {

                                            // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                                            // nose available):
                                            FirebaseVisionFaceLandmark rightEye = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE);
                                            FirebaseVisionFaceLandmark leftCheek = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_CHEEK);
                                            FirebaseVisionFaceLandmark rightCheec = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_CHEEK);

                                            Log.d("FACES", faces.toString());

                                            float rex = rightEye.getPosition().getX();
                                            float rey = rightEye.getPosition().getY();

                                            float lcx = leftCheek.getPosition().getX();
                                            float lcy = leftCheek.getPosition().getY();

                                            float rcx = rightCheec.getPosition().getX();
                                            float rcy = rightCheec.getPosition().getY();

                                            // 사진에서 눈 이랑 뺨 좌표 땄음.

                                            // 이미지를 추가하기 위해 장식용 이미지를 이미지 뷰라는 객체로 만들
                                            ImageView imageRE = new ImageView(mContext);
                                            ImageView imageLC = new ImageView(mContext);
                                            ImageView imageRC = new ImageView(mContext);

                                            //그 이미지 객체에 리소스를 달아줌.
                                            //이제 얼굴 좌표랑 올려야 할 파일들이 준비가 됨. 객체로 연결해야 올릴수 있으니깐.
                                            imageRE.setImageResource(R.drawable.mung);
                                            imageLC.setImageResource(R.drawable.left_whiskers);
                                            imageRC.setImageResource(R.drawable.right_whiskers);

                                            imageRE.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                                            imageLC.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                                            imageRC.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);


                                            imageRE.setX(p.x/bitmap.getWidth()*rex+(imageRE.getMeasuredWidth()/2));
                                            imageRE.setY(p.y/bitmap.getHeight()*rey-(imageRE.getMeasuredHeight()/2));

                                            imageLC.setX(p.x/bitmap.getWidth()*lcx+((p.x/bitmap.getWidth())*imageLC.getMeasuredWidth()/2));
                                            imageLC.setY(p.y/bitmap.getHeight()*lcy-((p.y/bitmap.getHeight())*imageLC.getMeasuredHeight()/2));


                                            imageRC.setX(p.x/bitmap.getWidth()*rcx+((p.x/bitmap.getWidth())*imageRC.getMeasuredWidth()/2));
                                            imageRC.setY(p.y/bitmap.getHeight()*rcy-((p.y/bitmap.getHeight())*imageRC.getMeasuredHeight()/2));

                                            //그리고 메인 렐러티브에 이미지를 올림.
                                            RelativeLayout_main_pra.addView(imageRE);
                                            RelativeLayout_main_pra.addView(imageLC);
                                            RelativeLayout_main_pra.addView(imageRC);

                                            //근데 올릴때 정확하게 올릴려면 디스플레이 정보가 필요함 그래서 Display 객체를 선





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
