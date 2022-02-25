package com.kd.truefantasysport

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kd.truefantasysport.R.drawable.blue_rectangle_border
import com.kd.truefantasysport.databinding.FragmentDisplayMnemonicBinding
import com.kd.truefantasysport.viewmodels.NewWalletViewModel
import android.util.TypedValue




/**
 * A simple [Fragment] subclass.
 * Use the [DisplayMnemonicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplayMnemonicFragment : Fragment() {
    private lateinit var binding: FragmentDisplayMnemonicBinding
    private lateinit var viewModel: NewWalletViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentDisplayMnemonicBinding>(inflater,
            R.layout.fragment_display_mnemonic,container,false)
        //Log.i("CreateWalletFormFragmen", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(requireActivity()).get(NewWalletViewModel::class.java)

        createMnemonicTable(viewModel.bip39Wallet.mnemonic)
        binding.btnDisplayMnemonicNext.setOnClickListener {
            it.findNavController().navigate(R.id.action_displayMnemonicFragment_to_confirmMnemonicFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun createMnemonicTable(s:String) {
        val mnemonicArray = s.split(" ")
        val mnemonicArraySize = s.split(" ").size
        val tableRows = arrayOfNulls<TableRow>(mnemonicArraySize/3)
        Log.i("DisplayMnemonicFragment", "createMnemonicTable")
        Log.i("DisplayMnemonicFragment", "$mnemonicArray")
            var i = 0
            while (i < mnemonicArraySize / 3) {
                tableRows[i] = TableRow(context)
                tableRows[i]!!.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )

              //  tableRows[i]!!.setPadding(8, 8, 8, 8)
                Log.i("DisplayMnemonicFragment", "${i*3} : ${(i*3)+1} : ${(i*3)+2}")
                tableRows[i]!!.addView(getNewCustomTextItem("${(i*3)+1} : ${mnemonicArray[(i*3)]}"))
                tableRows[i]!!.addView(getNewCustomTextItem("${(i*3)+2} : ${mnemonicArray[(i*3)+1]}"))
                tableRows[i]!!.addView(getNewCustomTextItem("${(i*3)+3} : ${mnemonicArray[(i*3)+2]}"))
                binding.displayMnemonicTable.addView(tableRows[i])
                i++

            }



    }

    private fun getNewCustomTextItem(s: String) : TextView{
        var textView = TextView(context)
        var layoutParam = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.WRAP_CONTENT, 1.0f
        )
        val dp16 = convertDpToPx(16.0f)
        val dp08 = convertDpToPx(8.0f)
        val dp00 = convertDpToPx(0.0f)
        layoutParam.setMargins(dp16,dp08,dp16,dp08)
        textView.layoutParams =layoutParam
        textView.background = ResourcesCompat.getDrawable(context!!.resources,R.drawable.blue_rectangle_border,context!!.theme)
        textView.setPadding(dp00,dp08,dp00,dp08)
        textView.setTextColor(ResourcesCompat.getColor(context!!.resources,R.color.app_primary_text,context!!.theme))
        textView.gravity = Gravity.CENTER
        textView.text = s
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


}