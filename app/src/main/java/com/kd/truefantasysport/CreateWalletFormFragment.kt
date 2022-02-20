package com.kd.truefantasysport

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kd.truefantasysport.databinding.FragmentCreateWalletFormBinding
import com.kd.truefantasysport.viewmodels.NewWalletViewModel
import org.web3j.crypto.WalletUtils
import java.io.File

class CreateWalletFormFragment : Fragment() {
    private lateinit var viewModel: NewWalletViewModel
    private lateinit var binding: FragmentCreateWalletFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentCreateWalletFormBinding>(inflater,
            R.layout.fragment_create_wallet_form,container,false)

        Log.i("CreateWalletFormFragmen", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(requireActivity()).get(NewWalletViewModel::class.java)

        binding.createNewWallet.setOnClickListener {
            createNewWallet();
            it.findNavController().navigate(R.id.action_createWalletFormFragment_to_displayMnemonicFragment)
        }
        return binding.root


    }

    private fun createNewWallet() {
        val accountName = binding.inpAccountName.text.toString()
        val walletPassword = binding.inpWalletPassword.text.toString()

        if(viewModel.createNewBip39Wallet(accountName,walletPassword)){
            Toast.makeText(context, "Wallet Created Successfull",Toast.LENGTH_SHORT).show()
            val mnemonic = viewModel.getBip39Wallet().mnemonic
           // Toast.makeText(context, mnemonic,Toast.LENGTH_SHORT).show()

        }
        else{
            Toast.makeText(context, "Wallet Creation Failed",Toast.LENGTH_SHORT).show()
        }
    }


}