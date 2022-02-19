package com.kd.truefantasysport.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.kd.truefantasysport.services.Web3jHelperService;

import org.jetbrains.annotations.NotNull;

public class NewWalletViewModel extends AndroidViewModel {
    private String accountName , accountPassword;


    public NewWalletViewModel(@NonNull Application application) {
        super(application);
    }


    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("CreateWalletFormFragmen", "NewWalletViewModel destroyed!");
    }
    public boolean createNewWallet(@NotNull String accountName, @NotNull String walletPassword) {
        String walletPath = getApplication().getFilesDir().getAbsolutePath();
        Log.i("CreateWalletFormFragmen","Wallet Folder"+walletPath);
        return (!Web3jHelperService.createNewWallet(walletPassword,walletPath).isEmpty());
    }


}
