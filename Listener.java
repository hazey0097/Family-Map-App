package com.example.client.Listener;

import java.io.Serializable;

public interface Listener extends Serializable {
    void onLoginSuccess();
    void onLoginFailure();
    void onRegisterFailure();
}
