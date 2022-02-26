package com.example.recsearchone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
//ini bikin error jenis searchview ini??
//import android.widget.SearchView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),ItemsAdapter.ClickListener {
    private lateinit var recyclerview: RecyclerView

    val imagesName = arrayOf(
        ItemsModal("image1", "image1 desc", R.drawable.a),
        ItemsModal("image2", "image2 desc", R.drawable.b),
        ItemsModal("image3", "image3 desc", R.drawable.c),
        ItemsModal("image4", "image4 desc", R.drawable.d),
        ItemsModal("image5", "image5 desc", R.drawable.e),
        ItemsModal("image6", "image6 desc", R.drawable.f),
        ItemsModal("image7", "image7 desc", R.drawable.g),
        ItemsModal("image8", "image8 desc", R.drawable.h),
        ItemsModal("image9", "image9 desc", R.drawable.i),
        ItemsModal("image10", "image9 desc", R.drawable.j),
    )
    val itemModalList = ArrayList<ItemsModal>()
    var itemsAdapter: ItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (items in imagesName) {
            itemModalList.add(items)
        }

        itemsAdapter = ItemsAdapter(this)
        itemsAdapter!!.setData(itemModalList)
        recyclerview = findViewById(R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
        recyclerview.adapter = itemsAdapter
    }

    override fun ClickedItem(itemsModal: ItemsModal) {
        Log.e("TAG",itemsModal.name)
        //fase 3
        when(itemsModal.name){
            "image1"->
                startActivity(Intent(this@MainActivity,Item1Activity::class.java))
            "image2"->
                startActivity(Intent(this@MainActivity,Item2Activity::class.java))
            else ->
                Toast.makeText(this@MainActivity,"No Action",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val menuItem = menu!!.findItem(R.id.searchView)
        //awal dibuat error karena salah import jenis searchviewnya,
        val searchView = menuItem.actionView as SearchView
        searchView.maxWidth= Int.MAX_VALUE
        //SearchView.OnQueryTextListener) ---->buat bracket lambdaSearchView.OnQueryTextListener{}) baru muncul implement object membernya
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(filterString: String?): Boolean {
                itemsAdapter!!.filter.filter(filterString)
                return true
            }

            override fun onQueryTextChange(filterString: String?): Boolean {

                itemsAdapter!!.filter.filter(filterString)
                return true
            }

        })
        return true
    }
}