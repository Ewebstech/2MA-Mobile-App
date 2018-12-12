package com.avardonigltd.mobilemedicalaid.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.interfaces.API;
import com.avardonigltd.mobilemedicalaid.interfaces.Listeners;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UpdateUserProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Listeners mListener;
    private Unbinder unbinder;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSIONS = 1234;
    private static final int IMAGE_GALLERY_REQUEST = 1;
    private static final int IMAGE_CAMERA_REQUEST = 2;
    private int choosenAction;
    AlertDialog dialog;
    File photoFile;
    boolean pictureChanged = false;

    String firstName,lastName,fullname,email,phonenumber, imageUrl;


    @BindView(R.id.user_profile_image)
    CircleImageView user_profile_image;
    @BindView(R.id.first_name_et_profile)
    EditText firstNameEt;
    @BindView(R.id.fullname_tv_profile)
    TextView fullname_tv_profile;
    //
    @BindView(R.id.last_name_tv_profile)
    EditText lastName_Et;
    @BindView(R.id.email_tv_profile)
    EditText email_edit_text_box;
    @BindView(R.id.phone_tv_profile)
    EditText phone_edit_text_box;

    public UpdateUserProfileFragment() {
    }

    public static UpdateUserProfileFragment newInstance(String param1, String param2) {
        UpdateUserProfileFragment fragment = new UpdateUserProfileFragment();
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
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        unbinder = ButterKnife.bind(this,view);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         bindViewToPreference();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listeners) {
            mListener = (Listeners) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.edit_user_profile_image)
    public void changePic() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.popup_clicked_image_btn_layout, null);
        TextView name = mView.findViewById(R.id.name);
        name.setText(String.format("%s %s", firstName, lastName));
        ImageView image = mView.findViewById(R.id.profile_image);
        if (photoFile != null) {
            Glide.with(this).load(photoFile)
                    .apply(new RequestOptions().error(R.drawable.boy).placeholder(R.drawable.boy).fitCenter())
                    .into(image);
        } else {
            Glide.with(this).load(API.BASE_URL+imageUrl)
                    .apply(new RequestOptions().error(R.drawable.boy).placeholder(R.drawable.boy).fitCenter())
                    .into(image);
        }

        ImageButton camera = mView.findViewById(R.id.pickCameraImage);
        ImageButton gallery = mView.findViewById(R.id.pickGalleryImage);
        ImageButton accept = mView.findViewById(R.id.acceptImage);
        mBuilder.setView(mView);

        dialog = mBuilder.create();
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoFile = null;
                choosenAction = 0;
                boolean result = checkStoragePermission(getContext());
                if (result) photoCameraIntent();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoFile = null;
                choosenAction = 1;
                boolean result = checkStoragePermission(getContext());
                if (result) photoGalleryIntent();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),photoFile+"",Toast.LENGTH_SHORT).show();
                if (photoFile != null) {
                    Glide.with(getContext()).load(photoFile)
                            .apply(new RequestOptions().error(R.drawable.boy).placeholder(R.drawable.boy).fitCenter())
                            .into(user_profile_image);
                    pictureChanged = true;
                    //checkFields();
                }
                dialog.dismiss();
            }
        });

        dialog.getWindow().getAttributes().windowAnimations = R.style.picture_animation;
        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkStoragePermission(final Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Permission Required");
            alertBuilder.setMessage("Permision to Read/Write to External storage is required");
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_PERMISSIONS);
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_PERMISSIONS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (choosenAction == 0) photoCameraIntent();
                    else photoGalleryIntent();
                } else {
                    dialog.dismiss();
                }
                break;
        }
    }

    private void photoCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_CAMERA_REQUEST);
    }

    private void photoGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_picture)), IMAGE_GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                if (data != null)
                    onSelectFromGalleryResult(data);
            } else if (requestCode == IMAGE_CAMERA_REQUEST) {
                if (data != null)
                    onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        // uri = data.getData();
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        photoFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            photoFile.createNewFile();
            fo = new FileOutputStream(photoFile);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Glide.with(getContext()).load(photoFile).into((ImageView) dialog.findViewById(R.id.profile_image));
        ((ImageButton) dialog.findViewById(R.id.acceptImage)).setVisibility(View.VISIBLE);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                photoFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                photoFile.createNewFile();
                fo = new FileOutputStream(photoFile);
                fo.write(bytes.toByteArray());
                fo.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Glide.with(getContext()).load(photoFile).into((ImageView) dialog.findViewById(R.id.profile_image));
        ((ImageButton) dialog.findViewById(R.id.acceptImage)).setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        unbinder.unbind();
    }

    private void bindViewToPreference() {
        RxSharedPreferences rxSharedPreferences = RxSharedPreferences.create(AppPreference.setUpDefault(getContext()));
        compositeDisposable.addAll(
                rxSharedPreferences.getString(AppPreference.USER_DATA, "")
                        .asObservable()
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                if (TextUtils.isEmpty(s) || TextUtils.equals(s, "null")) {
                                    return;
                                }
                                Gson gson = new GsonBuilder().create();
                                LoginResponse userDataResponse = gson.fromJson(s, LoginResponse.class);

                                firstName = userDataResponse.getData().getFirstname();
                                lastName = userDataResponse.getData().getLastname();
                                fullname = firstName + "\n" + lastName;
                                email = userDataResponse.getData().getEmail();
                                phonenumber = userDataResponse.getData().getPhoneNumber();
                                //imageUrl = userDataResponse.getData().;
                                //userDataResponse.getData().getUser().getDetail().

                                Glide.with(getContext()).load(API.BASE_URL)
                                        .apply(new RequestOptions().error(R.drawable.boy).placeholder(R.drawable.boy).fitCenter()).into(user_profile_image);

                                firstNameEt.setText(firstName);
                                lastName_Et.setText(lastName);
                                fullname_tv_profile.setText(fullname);
                                email_edit_text_box.setText(email);
                                if (TextUtils.isEmpty(phonenumber) || TextUtils.equals(phonenumber, "null")) {
                                    phone_edit_text_box.setText("");
                                } else {
                                    phone_edit_text_box.setText(phonenumber);
                                }
                            }
                        })

        );
    }

}
