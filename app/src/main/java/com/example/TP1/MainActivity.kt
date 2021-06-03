package com.example.TP1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var mainOK: Button
    private lateinit var edtPseudo: EditText
    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        // A la création de main
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        edtPseudo = findViewById(R.id.editText)
        mainOK = findViewById(R.id.main_ok)
        mainOK.setOnClickListener(this)
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        editor = pref.edit()
    }

    override fun onStart() {
        super.onStart()
        val l: String? = pref.getString("pseudo", "unknown")
        edtPseudo.setText(l)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent_param = Intent(this, SettingsActivity::class.java)
        startActivity(intent_param)
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.main_ok -> {
                // Passer le pseudo à l'activité suivante

                editor.putString("pseudo", edtPseudo.text.toString())
                editor.commit()


                val bdl = Bundle()
                bdl.putString("pseudo", edtPseudo.text.toString())
                val toChoix = Intent(this@MainActivity,
                        ChoixListActivity::class.java)
                toChoix.putExtras(bdl)
                startActivity(toChoix)
            }
        }
    }


}