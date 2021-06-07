package id.ac.umn.team_up.ui.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.controllers.ProjectController;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.User;
import id.ac.umn.team_up.ui.activity.ProfileSettingAboutActivity;
import id.ac.umn.team_up.ui.activity.ProfileSettingActivity;
import id.ac.umn.team_up.ui.activity.ProfileSettingMainActivity;
import id.ac.umn.team_up.ui.activity.ProfileSettingSkillActivity;
import id.ac.umn.team_up.ui.adapter.FlexboxSkillsAdapter;
import id.ac.umn.team_up.ui.adapter.ProfileProjectAdapter;

public class ProfileFragment extends Fragment {
    private ImageView imgProfile;
    private ImageButton btnMenu, btnEditPhoto, btnEditMain, btnEditAbout, btnEditSkill;
    private User currentUser;
    private TextView tvFullname, tvHeadline, tvAbout;

    RecyclerView rvSkills, rvProjectList;
    FlexboxSkillsAdapter adapterSkill;
    ProfileProjectAdapter adapterProject;

    private static final int CHANGE_PROFILE_PICTURE = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("info", "crete view");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        imgProfile = view.findViewById(R.id.imgProfile);
        tvFullname = view.findViewById(R.id.tvFullname);
        tvHeadline = view.findViewById(R.id.tvHeadline);
        tvAbout = view.findViewById(R.id.tvAbout);

        btnMenu = view.findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout
                Intent intent = new Intent(getActivity(), ProfileSettingActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btnEditPhoto = view.findViewById(R.id.btnEditPhoto);
        btnEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnEditMain = view.findViewById(R.id.btnEditMain);
        btnEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileSettingMainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        btnEditAbout = view.findViewById(R.id.btnEditAbout);
        btnEditAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileSettingAboutActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        btnEditSkill = view.findViewById(R.id.btnEditSkill);
        btnEditSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileSettingSkillActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        rvSkills = view.findViewById(R.id.rvSkills);

        rvProjectList = view.findViewById(R.id.rvProject);

        adapterProject = new ProfileProjectAdapter(ProjectController.loadUsersProjectOptions(UserController.getUserId(), true, true), (AppCompatActivity) getActivity() );

        ProjectController.listenToProfileProjectChanges(getContext(), adapterProject, UserController.getUserId());
        rvProjectList.setAdapter(adapterProject);
        rvProjectList.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void openFileChooser(){
        // Intent to insert image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHANGE_PROFILE_PICTURE);
    }

    private void showImageFromInternalStorage(String path) {
        Picasso.get()
                .load(new File(path))
                .resize(120, 120)
                .centerCrop()
                .into(imgProfile, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        imgProfile.setImageDrawable(imageDrawable);
                    }

                    @Override
                    public void onError(Exception e) {
                        imgProfile.setImageResource(R.drawable.img_profile_default);
                    }
                });
    }

    private void refreshUI() {
        Log.d("NOWWW", "A");
        currentUser = UserController.getCurrentUser(getContext());

        if (!currentUser.getPicture().equals("")) {
            if (!currentUser.getLocalPicture().equals("")) {
                showImageFromInternalStorage(currentUser.getLocalPicture());
            } else {
                Log.d("error", "NO");
            }
        }
        tvFullname.setText(currentUser.getFullName());
        String headline = (currentUser.getHeadline().equals("")) ? "Configure your Headline" : currentUser.getHeadline();
        tvHeadline.setText(headline);
        String about = (currentUser.getAbout().equals("")) ? "Describe about yourself here" : currentUser.getAbout();
        tvAbout.setText(about);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        rvSkills.setLayoutManager(layoutManager);
        adapterSkill = new FlexboxSkillsAdapter(getContext(), currentUser.getSkills());
        rvSkills.setAdapter(adapterSkill);
    }


    @Override
    public void onStart() {
        super.onStart();

        Log.d("info", "start");
        refreshUI();

        adapterProject.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterProject.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("info", "resume");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHANGE_PROFILE_PICTURE) {

            if (resultCode == Activity.RESULT_OK) {
                Uri image_uri = data.getData();

                saveImage(image_uri);
            } else {
                Utils.show(getActivity(), "Something went wrong, please try again later.");
            }
        }
    }

    private void saveImage(Uri imageUri) {
        String filename = System.currentTimeMillis() + "." + getFileExtension(imageUri);

        Picasso.get().load(imageUri).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    ContextWrapper cw = new ContextWrapper(getActivity());
                    File directory = cw.getDir("image_profile", Context.MODE_PRIVATE);
                    File file = new File(directory, filename);
                    if (!file.exists()) {
                        Log.d("path", file.toString());
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();

                            showImageFromInternalStorage(file.toString());

                            currentUser.setLocalPicture(file.toString());
                            UserController.updateUser((AppCompatActivity) getActivity(), currentUser, true);

                            UserController.updateProfileImage((AppCompatActivity) getActivity(), currentUser, imageUri, filename);
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch(Exception e){
                    Log.d("err", e.getMessage());
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) { }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });

    }

    private String getFileExtension(Uri uri){
        // Getting file extension
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}