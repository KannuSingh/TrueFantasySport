package com.kd.truefantasysport.services;

import android.util.Log;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Web3jHelperService {


    public static String createNewWallet(String walletPassword, String walletPath) {
        File walletDir  = new File(walletPath);
        String walletFilePath = "";
        try{
            final String walletFilename = WalletUtils.generateLightNewWalletFile(walletPassword,walletDir);
            File walletFile = new File(walletDir, walletFilename);
            if (!walletFile.exists()) throw new IOException("No file created");
            walletFilePath = walletFile.getAbsolutePath();
            Log.i("Web3jHelperService","Wallet File Created at "+walletFilePath);
        }
        catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException | CipherException | IOException e) {
            e.printStackTrace();
        }
        return walletFilePath;
    }

    public static Credentials getCredentials(String walletPassword,File walletDir){
        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(walletPassword, walletDir);
        } catch (CipherException | IOException e) {
            e.printStackTrace();
        }
        return credentials;
    }
}
