package ru.cactus.jotme.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.cactus.jotme.R
import ru.cactus.jotme.contract.ContractInterface
import ru.cactus.jotme.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity(), ContractInterface.View {

    private var presenter: MainActivityPresenter? = null
    private lateinit var mSetting: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSetting = getPreferences(Context.MODE_PRIVATE)
        presenter = MainActivityPresenter(this, mSetting)
        initView()
    }

    override fun initView() {
        val notes = presenter?.getAllNotes()

        et_note_title.setText(notes?.get(0)?.title?:"")
        et_note_body.setText(notes?.get(0)?.body?:"")

        iv_back_btn.setOnClickListener {
            presenter?.addNewNote(et_note_title.text.toString(), et_note_body.text.toString())
            showSaveToast()
        }
    }

    override fun updateViewData() {
    }

    override fun showSaveToast() {
        Toast.makeText(applicationContext, "Note was save...", Toast.LENGTH_SHORT).show()
    }


    override fun onBackPressed() {
        presenter?.addNewNote(et_note_title.text.toString(), et_note_body.text.toString())
        showSaveToast()
    }
}