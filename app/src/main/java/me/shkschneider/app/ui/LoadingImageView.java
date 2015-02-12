package me.shkschneider.app.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import me.shkschneider.app.R;
import me.shkschneider.skeleton.helper.ApplicationHelper;

public class LoadingImageView extends ViewSwitcher {

    private static final int VIEW_LOADING = 0;
    private static final int VIEW_IMAGE = 1;

    private View mLoadingView;
    private View mImageView;

    public LoadingImageView(final Context context) {
        this(context, null);
    }

    public LoadingImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("InflateParams")
    private void init() {
        final LayoutInflater layoutInflater = LayoutInflater.from(ApplicationHelper.context());
        mLoadingView = layoutInflater.inflate(R.layout.loadingimageview_loadingview, null, false);
        addView(mLoadingView, VIEW_LOADING);
        mImageView = layoutInflater.inflate(R.layout.loadingimageview_imageview, null, false);
        addView(mImageView, VIEW_IMAGE);
        setDisplayedChild(VIEW_LOADING);
    }

    public void showLoadingView() {
        setDisplayedChild(VIEW_LOADING);
    }

    public void showImageView() {
        setDisplayedChild(VIEW_IMAGE);
    }

    public CircularProgressBar getLoadingView() {
        return (CircularProgressBar) mLoadingView.findViewById(R.id.loadingImageView_loadingView);
    }

    public ImageView getImageView() {
        return (ImageView) mImageView.findViewById(R.id.loadingImageView_imageView);
    }

}
