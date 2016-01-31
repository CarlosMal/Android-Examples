package com.tonilopezmr.login;

import android.net.Uri;

/**
 * @author Antonio López.
 */
public interface UserProfile {
    String getId();
    String getName();
    String getEmail();
    Uri getImageUri();
}
