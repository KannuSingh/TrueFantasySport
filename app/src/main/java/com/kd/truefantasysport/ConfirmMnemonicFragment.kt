package com.kd.truefantasysport

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kd.truefantasysport.databinding.FragmentConfirmMnemonicBinding
import com.kd.truefantasysport.viewmodels.NewWalletViewModel
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConfirmMnemonicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmMnemonicFragment : Fragment() {
    private lateinit var binding: FragmentConfirmMnemonicBinding
    private lateinit var viewModel: NewWalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentConfirmMnemonicBinding>(
            inflater,
            R.layout.fragment_confirm_mnemonic, container, false
        )
        Log.i("CreateWalletFormFragmen", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(requireActivity()).get(NewWalletViewModel::class.java)
        val shuffledMnemonic = viewModel.shuffledMnemonic()

        arrangeMnemonicRandomly(shuffledMnemonic)

        //binding.head = viewModel.head
        binding.inputMnemonic = viewModel.inputMnemonic
        binding.btnCheckInputMnemonic.setOnClickListener{
            val result = viewModel.isInputMnemonicCorrect
            if(result){
                Toast.makeText(context, "Correct Mnemonic", Toast.LENGTH_SHORT).show()
                it.findNavController().navigate(R.id.action_confirmMnemonicFragment_to_walletCreationFinishFragment)
            }
            else{
                Toast.makeText(context, "Incorrect Mnemonic",Toast.LENGTH_SHORT).show()
            }

        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun arrangeMnemonicRandomly(shuffledMnemonic: Array<String>?) {
        if (shuffledMnemonic != null) {
            val mnemonicArraySize = shuffledMnemonic.size
            val tableRows = arrayOfNulls<TableRow>(mnemonicArraySize / 3)
            var i = 0
            while (i < mnemonicArraySize / 3) {
                tableRows[i] = TableRow(context)
                tableRows[i]!!.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )

                Log.i("DisplayMnemonicFragment", "${i * 3} : ${(i * 3) + 1} : ${(i * 3) + 2}")
                tableRows[i]!!.addView(getNewCustomTextItem(shuffledMnemonic[(i * 3)]))
                tableRows[i]!!.addView(getNewCustomTextItem(shuffledMnemonic[(i * 3) + 1]))
                tableRows[i]!!.addView(getNewCustomTextItem(shuffledMnemonic[(i * 3) + 2]))
                binding.displayMnemonicRandomTable.addView(tableRows[i])
                i++

            }
        }
    }


    private fun getNewCustomTextItem(s: String): TextView {
        var textView = TextView(context)
        var layoutParam = TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT, 1.0f)
        val dp16 = convertDpToPx(16.0f)
        val dp08 = convertDpToPx(8.0f)
        val dp00 = convertDpToPx(0.0f)
        layoutParam.setMargins(dp16, dp08, dp16, dp08)
        textView.setPadding(dp00, dp08, dp00, dp08)
        textView.layoutParams = layoutParam
        textView.background = ResourcesCompat.getDrawable(
            context!!.resources,
            R.drawable.blue_rectangle_border,
            context!!.theme
        )
        textView.setTextColor(
            ResourcesCompat.getColor(
                context!!.resources,
                R.color.app_primary_text,
                context!!.theme
            )
        )
        textView.gravity = Gravity.CENTER
        textView.text = s
        textView.isClickable = true
        textView.setOnClickListener {
            Log.i("ConfirmMnemonicFragment", "Clicked textview : " + textView.text)
            val text = textView.text as String
            if(viewModel.addTextToInputMnemonic(text)){
                textView.backgroundTintList = white20Transparent()
                textView.backgroundTintMode = PorterDuff.Mode.ADD
            }
            else {
                textView.backgroundTintList = null
            }
        }
        return textView;
    }

    private fun convertDpToPx(value: Float): Int {
        val r: Resources = context!!.getResources()
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            r.displayMetrics
        ).toInt()
    }
    private fun white20Transparent(): ColorStateList{
        return ColorStateList.valueOf(ResourcesCompat.getColor(
            context!!.resources,
            R.color.app_transparent_white,
            context!!.theme
        ))
    }
}