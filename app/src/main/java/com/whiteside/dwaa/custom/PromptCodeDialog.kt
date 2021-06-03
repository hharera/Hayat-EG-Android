package com.whiteside.dwaa.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.whiteside.dwaa.R


class PromptCodeDialog : DialogFragment() {

    lateinit var sec1: TextView
    lateinit var sec2: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return layoutInflater.inflate(R.layout.prompt_code_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sec1 = view.findViewById(R.id.sec1)
        sec2 = view.findViewById(R.id.sec2)
    }

    fun updateView(i: Int) {
        sec1.text = "${i / 10}"
        sec2.text = "${i % 10}"
    }
}