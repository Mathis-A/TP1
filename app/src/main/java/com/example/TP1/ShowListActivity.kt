package com.example.TP1

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class ShowListActivity: AppCompatActivity(), View.OnClickListener, OnItemClickListener {

    private lateinit var pseudo: String
    private lateinit var showOK: Button
    private lateinit var newItem: EditText

    private lateinit var nom: String

    private lateinit var getJson: String
    private lateinit var gson: Gson

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var liste: ListeToDo
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_item)

        newItem = findViewById(R.id.new_item)
        showOK = findViewById(R.id.item_button)
        showOK.setOnClickListener(this)


        val bdl = this.intent.extras
        pseudo = bdl!!.getString("pseudo")!!
        nom = bdl.getString("nom_liste")!!.replace(" ", "_")

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sp.edit()
        getJson = sp.getString(Pair(pseudo, nom).toString(),
            "{\"nom\": $nom\", \"items\": []}").toString()

        gson = Gson()
        liste = gson.fromJson(getJson, ListeToDo::class.java)

        recyclerView = findViewById(R.id.item_recycler_view)
        recyclerView.adapter = ItemAdapter(liste.items, this)

    }

    override fun onClick(v: View){
        when (v.id) {
            R.id.item_button -> {
                // ajout item
                liste.items.add(ItemToDo(newItem.text.toString()))

                getJson = gson.toJson(liste)
                editor.putString(Pair(pseudo, nom).toString(), getJson)
                editor.commit()

                recyclerView.adapter = ItemAdapter(liste.items, this)
            }
        }
    }

    override fun onItemClicked(v: View, pos: Int) {
        val checkBox = v as CheckBox
        val item = liste.items[pos]
        item.fait = checkBox.isChecked

        getJson = gson.toJson(liste)
        editor.putString(Pair(pseudo, nom).toString(), getJson)
        editor.commit()

    }
}