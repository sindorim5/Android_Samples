package com.example.carouselrecyclerviewtest

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.carouselrecyclerviewtest.databinding.CustomDialogBinding

class CustomDialog(context: Context, var dateList: ArrayList<String>, var hourList: ArrayList<ArrayList<String>>) :
    Dialog(context) {
    private lateinit var binding: CustomDialogBinding
    private lateinit var dateAdapter: CustomDialogAdapter
    private lateinit var hourAdapter: CustomDialogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dateAdapter = CustomDialogAdapter(dateList)
//        hourAdapter = CustomDialogAdapter(hourList[0])

        binding.okButton.setOnClickListener {
            dismiss()
        }

        binding.dateRecyclerview.apply {
            adapter = dateAdapter
        }
    }

}