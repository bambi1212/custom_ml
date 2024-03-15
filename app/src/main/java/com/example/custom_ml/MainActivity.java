package com.example.custom_ml;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView outputTextView;
    private TextView failOrNot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Inflate the layout


        outputTextView = findViewById(R.id.textViewOutput);
         failOrNot = findViewById(R.id.onsucces);
        failOrNot.setText("fail");


        LocalModel localModel =
                new LocalModel.Builder()
                        .setAssetFilePath("WasteClassificationModel.tflite")


                        // or .setAbsoluteFilePath(absolute file path to model file)
                        // or .setUri(URI to model file)
                        .build();

        CustomImageLabelerOptions customImageLabelerOptions =
                new CustomImageLabelerOptions.Builder(localModel)
                        .setConfidenceThreshold(0.5f)
                        .setMaxResultCount(5)
                        .build();

        ImageLabeler labeler = ImageLabeling.getClient(customImageLabelerOptions);


        // Get a reference to the drawable resource
        Resources resources = getResources();
        int drawableId = R.drawable.juice_image;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableId);

        // Create an InputImage object from the Bitmap only this can be proccesed
        InputImage imageToProcces = InputImage.fromBitmap(bitmap, 0);

        //check if got image
        ImageView imageViewXml = findViewById(R.id.imageView);
        imageViewXml.setImageBitmap(imageToProcces.getBitmapInternal());
        //end check

        labeler.process(imageToProcces)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> labels) {
                        failOrNot.setText("Success");


                        // Task completed successfully
                        // ...
                        /*
                        for (ImageLabel label : labels) {
                            String text = label.getText();
                            float confidence = label.getConfidence();
                            int index = label.getIndex();
                            outputTextView.setText("text:"+text);}}


                         */
                        if (labels.size() > 0) {
                            StringBuilder builder = new StringBuilder();
                            for (ImageLabel label : labels) {
                                builder.append(label.getText())
                                        .append(" ; ")
                                        //.append(label.getConfidence()) //how ,utch the ai think its true from 0 to 1
                                        .append("\n");

                            }
                            outputTextView.setText(builder.toString());
                        } else {
                            outputTextView.setText("could not classified");

                        }

                    }
                }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // ...
                        //outputTextView.setText("FAILED");
                        e.printStackTrace();
                    }
                });

        /*
        labeler.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> labels) {

                        // [START get_image_label_info]
                        for (ImageLabel label : labels) {
                            String text = label.getText();
                            float confidence = label.getConfidence();
                            int index = label.getIndex();
                            outputTextView.setText("text:"+text  );
                        }
                        // [END get_image_label_info]



                    }
                });




    }
    }


    }

         */
    }
}