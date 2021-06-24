package com.example.TP1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.TP1.api.DataProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.concurrent.thread
import kotlin.jvm.Throws


class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var mainOK: Button
    private lateinit var edtPseudo: EditText
    private lateinit var edtMdp: EditText
    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var dataProvider: DataProvider
    private val activityScope = CoroutineScope(IO)


    override fun onCreate(savedInstanceState: Bundle?) {
        // A la création de main
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        edtPseudo = findViewById(R.id.editText)
        edtMdp = findViewById(R.id.edtMdp)
        mainOK = findViewById(R.id.main_ok)
        mainOK.setEnabled(false)
        mainOK.setOnClickListener(this)
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        editor = pref.edit()

        dataProvider = DataProvider(this)


    }

    override fun onStart() {
        super.onStart()
        val l: String? = pref.getString("pseudo", "unknown")
        edtPseudo.setText(l)
        if (verifReseau()) {
            mainOK.setEnabled(true)
        }

        // Laisser enabled pour pouvoir passer à la version hors ligne

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intentParam = Intent(this, SettingsActivity::class.java)
        startActivity(intentParam)
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.main_ok -> {
                // Passer le pseudo à l'activité suivante

                editor.putString("pseudo", edtPseudo.text.toString())
                editor.commit()

                activityScope.launch {
                    try {
                        val hash = dataProvider.checkUserFromApi(edtPseudo.text.toString(), edtMdp.text.toString())
                        editor.putString("hash",hash)
                        editor.commit()
                        Log.i("PMR", hash)
                    }catch (e: Exception){
                        Log.e("PMR", e.stackTrace.toString())
                    }

                }


                val bdl = Bundle()
                bdl.putString("pseudo", edtPseudo.text.toString())
                val toChoix = Intent(this@MainActivity,
                        ChoixListActivity::class.java)
                toChoix.putExtras(bdl)
                startActivity(toChoix)
            }
        }
    }


    fun verifReseau(): Boolean {
        // On vérifie si le réseau est disponible,
        // si oui on change le statut du bouton de connexion
        val cnMngr =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cnMngr.activeNetworkInfo
        var sType = "Aucun réseau détecté"
        var bStatut = false
        if (netInfo != null) {
            val netState = netInfo.state
            if (netState.compareTo(NetworkInfo.State.CONNECTED) == 0) {
                bStatut = true
                val netType = netInfo.type
                when (netType) {
                    ConnectivityManager.TYPE_MOBILE -> sType = "Réseau mobile détecté"
                    ConnectivityManager.TYPE_WIFI -> sType = "Réseau wifi détecté"
                }
            }
        }
        Log.i("PMR", sType)
        return bStatut
    }


}