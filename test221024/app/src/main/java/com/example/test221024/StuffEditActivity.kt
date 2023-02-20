package com.example.test221024

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.test221024.databinding.ActivityStuffEditBinding
import com.example.test221024.dto.Stuff

private const val TAG = "StuffEditActivity_싸피"
class StuffEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuffEditBinding
    private lateinit var editStuff: Stuff

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuffEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editStuff = intent.getSerializableExtra("stuff") as Stuff

        binding.etName.setText(editStuff.name)
        binding.etQuantity.setText(editStuff.quantity.toString())

        binding.editSave.setOnClickListener {
            if (!binding.etName.text.isNullOrBlank() || !binding.etQuantity.text.isNullOrBlank()) {
                editStuff.name = binding.etName.text.toString()
                editStuff.quantity = binding.etQuantity.text.toString().toInt()
                val intent = Intent().apply {
                    putExtra("stuff", editStuff)
                }
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.editDelete.setOnClickListener {
            if (editStuff.id != null) {
                val intent = Intent().apply {
                    putExtra("stuff", editStuff)
                }
                setResult(RESULT_DELETE, intent)
            }
            finish()
        }

        binding.editCancel.setOnClickListener {
            finish()
        }
    }
}