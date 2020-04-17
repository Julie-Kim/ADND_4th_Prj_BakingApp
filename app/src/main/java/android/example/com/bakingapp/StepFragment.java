package android.example.com.bakingapp;

import android.content.Context;
import android.example.com.bakingapp.databinding.FragmentStepBinding;
import android.example.com.bakingapp.model.Step;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class StepFragment extends Fragment {
    private static final String TAG = StepFragment.class.getSimpleName();

    private FragmentStepBinding mBinding;
    private SimpleExoPlayer mExoPlayer;

    private ArrayList<Step> mStepList;
    private int mStepIndex;

    private OnPrevButtonClickListener mPrevButtonClickListener;

    public interface OnPrevButtonClickListener {
        void onPrevButtonClick();
    }

    private OnSelectedItemChangeListener mSelectedItemChangeListener;

    public interface OnSelectedItemChangeListener {
        void onSelectedItemChanged(int selectedPosition);
    }

    public StepFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mPrevButtonClickListener = (OnPrevButtonClickListener) context;
            mSelectedItemChangeListener = (OnSelectedItemChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnPrevButtonClickListener and OnSelectedItemChangeListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);

        if (getArguments() != null) {
            mStepList = getArguments().getParcelableArrayList(RecipeConstant.KEY_RECIPE_STEPS);
            mStepIndex = getArguments().getInt(RecipeConstant.KEY_RECIPE_STEP_INDEX);

            if (mStepList != null) {
                initStepUi();
            }
        }

        return mBinding.getRoot();
    }

    private void initStepUi() {
        Step step = mStepList.get(mStepIndex);

        mBinding.tvStepShortDescription.setText(step.getShortDescription());
        mBinding.tvStepDescription.setText(step.getDescription());

        String videoUrl = step.getVideoUrl();
        if (TextUtils.isEmpty(videoUrl)) {
            mBinding.playerView.setVisibility(View.GONE);
        } else {
            mBinding.playerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(videoUrl));
        }

        setPrevNextButtonVisibility();

        mBinding.prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausePlayer();

                if (mStepIndex == 0) {
                    mPrevButtonClickListener.onPrevButtonClick();
                } else {
                    mStepIndex--;
                    mSelectedItemChangeListener.onSelectedItemChanged(mStepIndex + 1);
                    initStepUi();
                }
            }
        });

        mBinding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausePlayer();

                mStepIndex++;
                mSelectedItemChangeListener.onSelectedItemChanged(mStepIndex + 1);
                initStepUi();
            }
        });
    }

    private void setPrevNextButtonVisibility() {
        if (mStepIndex == mStepList.size() - 1) {
            mBinding.nextButton.setVisibility(View.GONE);
        } else {
            mBinding.nextButton.setVisibility(View.VISIBLE);
        }
    }

    private void initializePlayer(Uri videoUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mBinding.playerView.setPlayer(mExoPlayer);
        }

        String userAgent = Util.getUserAgent(getContext(), "BakingApp");
        MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        startPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void pausePlayer() {
        if (mExoPlayer == null) {
            return;
        }

        mExoPlayer.setPlayWhenReady(false);
        mExoPlayer.getPlaybackState();
    }

    private void startPlayer() {
        if (mExoPlayer == null) {
            return;
        }

        mExoPlayer.setPlayWhenReady(true);
        mExoPlayer.getPlaybackState();
    }

    private void releasePlayer() {
        if (mExoPlayer == null) {
            return;
        }

        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
