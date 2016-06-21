package com.nairbspace.octoandroid.domain.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

@AutoValue
@AutoGson(autoValueClass = AutoValue_Slicer.class)
public abstract class Slicer {
    @Nullable @SerializedName("key") public abstract String key();
    @Nullable @SerializedName("displayName") public abstract String displayName();
    @Nullable @SerializedName("default") public abstract Boolean isDefault();
    @Nullable @SerializedName("profiles") public abstract Map<String, Profile> profiles();

    @AutoValue
    @AutoGson(autoValueClass = AutoValue_Slicer_Profile.class)
    public static abstract class Profile {
        @Nullable @SerializedName("key") public abstract String key();
        @Nullable @SerializedName("displayName") public abstract String displayName();
        @Nullable @SerializedName("default") public abstract Boolean isDefault();
        @Nullable @SerializedName("resource") public abstract String resource();
    }
}
