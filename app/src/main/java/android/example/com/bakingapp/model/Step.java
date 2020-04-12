package android.example.com.bakingapp.model;

import androidx.annotation.NonNull;

public class Step {

    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;

    public Step(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return mId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "Id: " + mId +
                "\nShortDescription: " + mShortDescription +
                "\nDescription: " + mDescription +
                "\nVideoUrl: " + mVideoUrl +
                "\nThumbnailUrl: " + mThumbnailUrl;
    }
}
