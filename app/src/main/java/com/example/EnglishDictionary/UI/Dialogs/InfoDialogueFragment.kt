package com.example.EnglishDictionary.UI.Dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.EnglishDictionary.R
import kotlinx.android.synthetic.main.info_fragment_dialogue.*

class InfoDialogueFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.info_fragment_dialogue, container , false)
        if( (dialog!=null) && (dialog!!.window!=null) ){
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return view
    }

    override fun onStart() {
        super.onStart()

        dismissButton.setOnClickListener {
            dismiss()
        }
    }



}