package com.ipsmeet.uberpath.activity

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityCardDetailsBinding
import com.ipsmeet.uberpath.databinding.DialogCardIsReadyBinding

class CardDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCardDetailsBinding

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cardStyle = intent.getStringExtra("cardStyle").toString()

        when(cardStyle) {
            "cardOne" -> {
                Glide.with(this).load(R.drawable.img_card_style1).into(binding.imgVSelectedCardStyle)
            }
            "cardTwo" -> {
                Glide.with(this).load(R.drawable.img_card_style2).into(binding.imgVSelectedCardStyle)
            }
            "cardThree" -> {
                Glide.with(this).load(R.drawable.img_card_style3).into(binding.imgVSelectedCardStyle)
            }
        }

        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        editor.putString("cardStyle", cardStyle)
        editor.apply()

        //  BACK BUTTON
        binding.btnBack.setOnClickListener {
            finish()
        }

        //  SAVE BUTTON
        binding.btnSave.setOnClickListener {
            readyDialog()
        }
    }

    private fun readyDialog() {
        val dialogBinding = DialogCardIsReadyBinding.inflate(LayoutInflater.from(this))
        val dialog = Dialog(this, R.style.NewDialog)
        dialog.setContentView(dialogBinding.root)
        val layoutParams = dialog.window!!.attributes
        layoutParams.y = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._130sdp)
        dialog.window!!.apply {
            attributes = layoutParams
            attributes.gravity = Gravity.TOP
        }
        dialog.show()

        dialogBinding.imgVImg.elevation = 5F

        //  Corner radius
        val radius = resources.getDimension(com.intuit.sdp.R.dimen._15sdp)

        val bottomBarBackground = dialogBinding.bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomRightCorner(CornerFamily.ROUNDED, radius)
            .build()

        //  BUTTON
        dialogBinding.btnImReady.setOnClickListener {
            dialog.dismiss()

            startActivity(
                Intent(this, HomeActivity::class.java)
            )
            finish()
        }
    }

}