package com.example.TP1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class ChoixListActivity: AppCompatActivity(), View.OnClickListener, OnListClickListener {

    private lateinit var pseudo: String
    private lateinit var choixOK: Button
    private lateinit var newList: EditText

    private lateinit var getJson: String
    private lateinit var gson: Gson

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var profil: ProfilListeToDo
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        newList = findViewById(R.id.new_list)
        choixOK = findViewById(R.id.choix_OK)
        choixOK.setOnClickListener(this)


        val bdl = this.intent.extras
        pseudo = bdl!!.getString("pseudo")!!
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sp.edit()
        getJson = sp.getString(pseudo, "{\"pseudo\": $pseudo, \"listes\": []}").toString()
        gson = Gson()
        profil = gson.fromJson(getJson, ProfilListeToDo::class.java)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = ListAdapter(profil.listes, this)

    }

    override fun onClick(v: View){
        when (v.id) {
            R.id.choix_OK -> {
                // nouvelle entrée dans gson
                profil.listes.add(ListeToDo(newList.text.toString(), mutableListOf()))
                getJson = gson.toJson(profil)

                editor.putString(pseudo, getJson)
                editor.commit()
                recyclerView.adapter = ListAdapter(profil.listes, this)
            }
        }
    }


    override fun onListClicked(list: ListeToDo) {

        val bdl = Bundle()
        bdl.putString("pseudo", pseudo)
        bdl.putString("nom_liste", list.nom)

        // vers la ShowListActivity correspondante
        val toShow = Intent(this@ChoixListActivity, ShowListActivity::class.java)
        toShow.putExtras(bdl)
        startActivity(toShow)
    }
}