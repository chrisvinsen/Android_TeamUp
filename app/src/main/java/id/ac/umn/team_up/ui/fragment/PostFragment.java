package id.ac.umn.team_up.ui.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.ProjectController;
import id.ac.umn.team_up.models.Project;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //
    private static final int PICK_IMAGE_REQUEST = 1;
    private int MAX_UPLOAD = 5;

    private EditText project_title;
    private EditText project_description;
    private Button post_button;
    private Button upload_button;
    private ImageView image_view1;
    private ImageView image_view2;
    private ImageView image_view3;
    private ImageView image_view4;
    private ImageView image_view5;
    private ProgressBar progress_bar;

    private List<Uri> image_uri;

    private StorageReference mStorageRef;

    private List<String> upload_url;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_post, container, false);

        project_title = (EditText) view.findViewById(R.id.project_title);
        project_description = (EditText) view.findViewById(R.id.project_description);
        post_button = (Button) view.findViewById(R.id.post_button);
        upload_button = (Button) view.findViewById(R.id.upload_button);
        image_view1 = (ImageView) view.findViewById(R.id.image_view1);
        image_view2 = (ImageView) view.findViewById(R.id.image_view2);
        image_view3 = (ImageView) view.findViewById(R.id.image_view3);
        image_view4 = (ImageView) view.findViewById(R.id.image_view4);
        image_view5 = (ImageView) view.findViewById(R.id.image_view5);
        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        upload_url = new ArrayList<String>();
        image_uri = new ArrayList<Uri>();

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = project_title.getText().toString();
                final String description = project_description.getText().toString();
                boolean isAllFilled = true;

                if (title.isEmpty()) {
                    project_title.setError("Project title must be filled");
                    isAllFilled = false;
                }
                if (description.isEmpty()) {
                    project_description.setError("Project description must be filled");
                    isAllFilled = false;
                }

                if (isAllFilled && post_button.isEnabled()) {
                    post_button.setEnabled(false);
                    Log.d("data", "title: " + project_title.getText().toString());
                    Log.d("data", "description: " + project_description.getText().toString());

                    postProject(title, description);
                } else {
                    Toast.makeText(getActivity(), "Please fill all required data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        return view;
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public String getFileExtension(Uri uri){
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void postProject(String title, String description){
        if(!image_uri.isEmpty()){
            for(int i = 0; i < image_uri.size(); i++) {
                StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(image_uri.get(i)));

                fileReference.putFile(image_uri.get(i))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progress_bar.setProgress(0);
                                    }
                                }, 500);

                                Log.d("upload", "yes1");
                                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        upload_url.add(uri.toString());
                                        if(upload_url.size() == image_uri.size()){
                                            Toast.makeText(getActivity(), "Uploaded Image Successful", Toast.LENGTH_SHORT).show();
                                            ProjectController.postProject((AppCompatActivity) getActivity(), title, description, upload_url);
                                            Log.d("upload", uri.toString());
                                        }
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progress_bar.setProgress((int) progress);
                            }
                        });
            }
        }
        else{
            Log.d("upload", "No image");
            ProjectController.postProject((AppCompatActivity) getActivity(), title, description, upload_url);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            image_uri.add(data.getData());

            if(MAX_UPLOAD == 5){
                Picasso.get().load(data.getData()).into(image_view1);
                upload_button.setText("Insert Image ( 4 Remaining )");
                MAX_UPLOAD--;
            }
            else if(MAX_UPLOAD == 4){
                Picasso.get().load(data.getData()).into(image_view2);
                upload_button.setText("Insert Image ( 3 Remaining )");
                MAX_UPLOAD--;
            }
            else if(MAX_UPLOAD == 3){
                Picasso.get().load(data.getData()).into(image_view3);
                upload_button.setText("Insert Image ( 2 Remaining )");
                MAX_UPLOAD--;
            }
            else if(MAX_UPLOAD == 2){
                Picasso.get().load(data.getData()).into(image_view4);
                upload_button.setText("Insert Image ( 1 Remaining )");
                MAX_UPLOAD--;
            }
            else if(MAX_UPLOAD == 1){
                Picasso.get().load(data.getData()).into(image_view5);
                upload_button.setText("Insert Image ( 0 Remaining )");
                upload_button.setEnabled(false);
                MAX_UPLOAD--;
            }
        }
    }
}