package com.jeevansandhu.watson_visual_recognition;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CollectionImage;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectedFaces;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.FindSimilarImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.RecognizedText;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualRecognitionOptions;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button1, button2, button3, button4;
    private TextView text1, text2;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        image = (ImageView) findViewById(R.id.image1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(MainActivity.this)
                        .folderMode(true) // folder mode (false by default)
                        .folderTitle("Folder") // folder selection title
                        .imageTitle("Tap to select") // image selection title
                        .single() // single mode
//                        .multi() // multi mode (default mode)
//                        .limit(10) // max images can be selected (999 by default)
//                        .showCamera(true) // show camera or not (true by default)
//                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
//                        .origin(images) // original selected images, used in multi mode
                        .start(1001); // start image picker activity with request code
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(MainActivity.this)
                        .folderMode(true) // folder mode (false by default)
                        .folderTitle("Folder") // folder selection title
                        .imageTitle("Tap to select") // image selection title
                        .single() // single mode
                        .start(1002); // start image picker activity with request code
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(MainActivity.this)
                        .folderMode(true) // folder mode (false by default)
                        .folderTitle("Folder") // folder selection title
                        .imageTitle("Tap to select") // image selection title
                        .single() // single mode
                        .start(1003); // start image picker activity with request code
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(MainActivity.this)
                        .folderMode(true) // folder mode (false by default)
                        .folderTitle("Folder") // folder selection title
                        .imageTitle("Tap to select") // image selection title
                        .single() // single mode
                        .start(1004); // start image picker activity with request code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            String fileName = images.get(0).getPath();
            text1.setText(fileName);
            classify(fileName);
        } else if (requestCode == 1002 && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            String fileName = images.get(0).getPath();
            text1.setText(fileName);
            detectFaces(fileName);
        } else if (requestCode == 1003 && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            String fileName = images.get(0).getPath();
            text1.setText(fileName);
            detectText(fileName);
        } else if (requestCode == 1004 && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            String fileName = images.get(0).getPath();
            text1.setText(fileName);
            findSimilar(fileName);
        }
    }

    void classify(final String fileName) {
        new AsyncTaskCallback(MainActivity.this, new AsyncTaskCallback.AsyncTaskCallbackInterface() {
            @Override
            public String backGroundCallback() {
                try {
                    VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
                    service.setApiKey("4daf0ac6eec8b07e0ccc1d9c1887b3607c5bc52e");

                    ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                            .images(new File(fileName))
                            .build();

                    VisualClassification result = service.classify(options).execute();

                    return result.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void foregroundCallback(String result) {
                if (result != null) {
                    Log.d("RESULT", result);
                    text2.setText(result);
                } else {
                    Log.d("RESULT", "No Result");
                }
            }
        }).execute();
    }

    void detectFaces(final String fileName) {
        new AsyncTaskCallback(MainActivity.this, new AsyncTaskCallback.AsyncTaskCallbackInterface() {
            @Override
            public String backGroundCallback() {
                try {
                    VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
                    service.setApiKey("4daf0ac6eec8b07e0ccc1d9c1887b3607c5bc52e");

                    VisualRecognitionOptions options = new VisualRecognitionOptions.Builder()
                            .images(new File(fileName))
                            .build();


                    DetectedFaces result = service.detectFaces(options).execute();

                    return result.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void foregroundCallback(String result) {
                if (result != null) {
                    Log.d("RESULT", result);
                    text2.setText(result);

                    File imgFile = new File(fileName);
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    Bitmap bitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
                    Canvas canvas = new Canvas(bitmap);
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(5);
                    paint.setColor(Color.BLUE);
                    canvas.drawBitmap(myBitmap, 0, 0, null);

                    try {
                        JSONObject a = new JSONObject(result);
                        Log.d("ASDF", a.getJSONArray("images").getJSONObject(0).getJSONArray("faces").length() + "");

                        for (int i = 0; i < a.getJSONArray("images").getJSONObject(0).getJSONArray("faces").length(); ++i) {
                            JSONObject asdf = a.getJSONArray("images").getJSONObject(0).getJSONArray("faces").getJSONObject(i).getJSONObject("face_location");
                            canvas.drawRect(asdf.getInt("left"), asdf.getInt("top"), asdf.getInt("left") + asdf.getInt("width"), asdf.getInt("top") + asdf.getInt("height"), paint);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    image.setImageBitmap(bitmap);

                } else {
                    Log.d("RESULT", "No Result");
                }
            }
        }).execute();
    }

    void detectText(final String fileName) {
        new AsyncTaskCallback(MainActivity.this, new AsyncTaskCallback.AsyncTaskCallbackInterface() {
            @Override
            public String backGroundCallback() {
                try {
                    VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
                    service.setApiKey("4daf0ac6eec8b07e0ccc1d9c1887b3607c5bc52e");

                    VisualRecognitionOptions options = new VisualRecognitionOptions.Builder()
                            .images(new File(fileName))
                            .build();

                    RecognizedText result = service.recognizeText(options).execute();

                    return result.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void foregroundCallback(String result) {
                if (result != null) {
                    Log.d("RESULT", result);
                    text2.setText(result);
                } else {
                    Log.d("RESULT", "No Result");
                }
            }
        }).execute();
    }

    void findSimilar(final String fileName) {
        new AsyncTaskCallback(MainActivity.this, new AsyncTaskCallback.AsyncTaskCallbackInterface() {
            @Override
            public String backGroundCallback() {
                try {
                    VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
                    service.setApiKey("4daf0ac6eec8b07e0ccc1d9c1887b3607c5bc52e");

                    /*VisualRecognitionOptions options = new VisualRecognitionOptions.Builder()
                            .images(new File(fileName))
                            .build();
*/
                    FindSimilarImagesOptions options = new FindSimilarImagesOptions.Builder()
                            .image(new File(fileName))
                            .build();

                    List<CollectionImage> result = service.findSimilarImages(options).execute();

                    return result.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void foregroundCallback(String result) {
                if (result != null) {
                    Log.d("RESULT", result);
                    text2.setText(result);
                } else {
                    Log.d("RESULT", "No Result");
                }
            }
        }).execute();
    }
}