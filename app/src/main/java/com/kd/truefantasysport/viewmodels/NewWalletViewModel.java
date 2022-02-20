package com.kd.truefantasysport.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.kd.truefantasysport.services.Web3jHelperService;

import org.jetbrains.annotations.NotNull;
import org.web3j.crypto.Bip39Wallet;

public class NewWalletViewModel extends AndroidViewModel {
    private String name , password;
    private Bip39Wallet bip39Wallet;
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Bip39Wallet getBip39Wallet() {
        return bip39Wallet;
    }





    public NewWalletViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("CreateWalletFormFragmen", "NewWalletViewModel destroyed!");
    }
    public boolean createNewWallet(@NotNull String accountName, @NotNull String walletPassword) {
        this.name = accountName;
        this.password = walletPassword;
        String walletPath = getApplication().getFilesDir().getAbsolutePath();
        Log.i("CreateWalletFormFragmen","Wallet Folder"+walletPath);
        return (!Web3jHelperService.createNewWallet(walletPassword,walletPath).isEmpty());
    }

    public boolean createNewBip39Wallet(@NotNull String accountName, @NotNull String walletPassword) {
        this.name = accountName;
        this.password = walletPassword;
        String walletPath = getApplication().getFilesDir().getAbsolutePath();
        Log.i("CreateWalletFormFragmen","Wallet Folder"+walletPath);
        this.bip39Wallet = Web3jHelperService.createNewBip39Wallet(walletPassword,walletPath);

        return (bip39Wallet != null);
    }


}
