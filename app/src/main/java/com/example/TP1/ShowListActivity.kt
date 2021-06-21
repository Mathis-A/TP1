package com.example.TP1

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.TP1.api.DataProvider
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ShowListActivity: AppCompatActivity(), View.OnClickListener, OnItemClickListener {

    private lateinit var pseudo: String
    private lateinit var showOK: Button
    private lateinit var newItem: EditText

    private lateinit var nom: String
    private lateinit var hash: String

    private lateinit var getJson: String
    private lateinit var gson: Gson

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var liste_items: MutableList<ItemToDo>
    private lateinit var recyclerView: RecyclerView
    private val activityScope = CoroutineScope(IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_item)

        newItem = findViewById(R.id.new_item)
        showOK = findViewById(R.id.item_button)
        showOK.setOnClickListener(this)


        sp = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sp.edit()
        getJson = sp.getString(Pair(pseudo, nom).toString(),
            "{\"nom\": $nom\", \"items\": []}").toString()

        gson = Gson()

        val id=this.intent.getStringExtra("id").toString()

        val dataProvider = DataProvider(this)

        activityScope.launch{
            try {
                hash = sp.getString("hash", "").toString()
                liste_items = dataProvider.getItems(id, hash)
                recyclerView = findViewById(R.id.item_recycler_view)
                recyclerView.adapter = ItemAdapter(liste_items,this@ShowListActivity)
            } catch (e:Exception) {
                Log.d("PMR", e.toString())
            }
        }


    }

    override fun onClick(v: View){
        when (v.id) {
            R.id.item_button -> {
                // ajout item
                liste_items.add(ItemToDo(liste_items.size.toString(),newItem.text.toString()))

                getJson = gson.toJson(liste_items)
                editor.putString(Pair(pseudo, nom).toString(), getJson)
                editor.commit()

                recyclerView.adapter = ItemAdapter(liste_items, this)
            }
        }
    }

    override fun onItemClicked(v: View, pos: Int) {
        val checkBox = v as CheckBox
        val item = liste_items[pos]
        item.fait = checkBox.isChecked

        getJson = gson.toJson(liste_items)
        editor.putString(Pair(pseudo, nom).toString(), getJson)
        editor.commit()

    }
}