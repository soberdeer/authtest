package com.telse.authtest.encryption;

import android.view.View;

public interface KeyEncryptable {
    void createNewKeys(String alias);

    void deleteKey(String alias);

    String encryptString(String alias, String string);

    String decryptString(String alias, String string);
}
