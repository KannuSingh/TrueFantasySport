package com.kd.truefantasysport.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;

import com.kd.truefantasysport.services.Web3jHelperService;

import org.jetbrains.annotations.NotNull;
import org.web3j.crypto.Bip39Wallet;

import java.util.Arrays;
import java.util.Random;

public class NewWalletViewModel extends AndroidViewModel {
    private String name , password;
    private Bip39Wallet bip39Wallet;


    private int head = -1;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Bip39Wallet getBip39Wallet() {
        return bip39Wallet;
    }

    private ObservableArrayList<String> inputMnemonic = new ObservableArrayList<String>();






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
    public String[] shuffledMnemonic() {

        String[] shuffledMnemonicArray = bip39Wallet.getMnemonic().split(" ");

        Random rand = new Random();

        for (int i = 0; i < shuffledMnemonicArray.length; i++) {
            int randomIndexToSwap = rand.nextInt(shuffledMnemonicArray.length);
            String temp = shuffledMnemonicArray[randomIndexToSwap];
            shuffledMnemonicArray[randomIndexToSwap] = shuffledMnemonicArray[i];
            shuffledMnemonicArray[i] = temp;
            inputMnemonic.add("");
        }
        return shuffledMnemonicArray;
    }
    //this method is used to check whether inputMnemonic are same as walletMnemonic
    public boolean isInputMnemonicCorrect(){
        return inputMnemonic.equals(Arrays.asList(bip39Wallet.getMnemonic().split(" ")));
    }

    // this method remove text and return false if text was already present in inputMnemonic
    // add text and return true if text as not present in inputMnemonic
    public boolean addTextToInputMnemonic( String text){
        if(inputMnemonic.contains(text)){
            inputMnemonic.remove(text);
            inputMnemonic.add("");
            --head;
            return false;
        }
        inputMnemonic.set(++head, text);
        return true;
    }

    public ObservableArrayList<String> getInputMnemonic() {
        return inputMnemonic;
    }
}
