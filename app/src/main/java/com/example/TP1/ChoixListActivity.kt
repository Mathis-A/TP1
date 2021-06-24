package com.example.TP1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.TP1.api.DataProvider
import com.example.TP1.api.Repository
import com.example.TP1.api.local.DataSource
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ChoixListActivity: AppCompatActivity(), View.OnClickListener, OnListClickListener {

    private lateinit var pseudo: String
    private lateinit var choixOK: Button
    private lateinit var newList: EditText

    private lateinit var getJson: String
    private lateinit var gson: Gson

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var profil: ProfilListeToDo
    private lateinit var listes: List<ListeToDo>
    private lateinit var recyclerView: RecyclerView
    private val activityScope = CoroutineScope(IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        newList = findViewById(R.id.new_list)
        choixOK = findViewById(R.id.choix_OK)
        choixOK.setOnClickListener(this)
        val repository by lazy { Repository.newInstance(application) }
        val bdl = this.intent.extras
        val hash= bdl?.getString("hash")
        val id = repository.dataSource.getUser(hash)

        activityScope.launch {
            try {
                val hash = sp.getString("hash","").toString()
                try {

                    listes=repository.getList(hash,)
                }


                recyclerView = findViewById(R.id.recycler_view)
                recyclerView.adapter = ListAdapter(listes, this@ChoixListActivity)
            } catch (e:Exception) {
                Log.d("PMR", e.toString())
            }
        }




    }

    override fun onClick(v: View){
        when (v.id) {
            R.id.choix_OK -> {
                //TODO ajouter des listes via api

                // nouvelle entrée dans gson
                //profil.listes.add(ListeToDo(id ? ,newList.text.toString(), mutableListOf()))
                //getJson = gson.toJson(profil)

                //editor.putString(pseudo, getJson)
                //editor.commit()
                //recyclerView.adapter = ListAdapter(profil.listes, this)

            }
        }
    }


    override fun onListClicked(list: ListeToDo) {

        val bdl = Bundle()
        // vers ShowListActivity
        val toShow = Intent(this@ChoixListActivity, ShowListActivity::class.java)
        toShow.putExtra("id",list.id)
        startActivity(toShow)
    }
}