package com.nikola.example.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;

/**
 * Created by Nikola on 06-Sep-18.
 */

public class PhotoDisplayFragment extends DialogFragment {
    private static final String ARG_PHOTO = "photo";
    public static final String EXTRA_PHOTO = "com.nikola.example.criminalintent.photo";

    private String photoPath;
    private ImageView mPhotoView;

    public static PhotoDisplayFragment newInstance(File photoPath) {
        Bundle args = new Bundle();
        args.putString(ARG_PHOTO, photoPath.getPath());

        PhotoDisplayFragment fragment = new PhotoDisplayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        photoPath = getArguments().getString(ARG_PHOTO);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);

        mPhotoView = (ImageView) v.findViewById(R.id.dialog_photo_view);

        mPhotoView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Bitmap bitmap = PictureUtils.getScaledBitmap(photoPath, mPhotoView);

                mPhotoView.setImageBitmap(bitmap);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }
}
