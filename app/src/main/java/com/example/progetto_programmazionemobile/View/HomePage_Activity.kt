package com.example.progetto_programmazionemobile.View

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler


class HomePage_Activity : AppCompatActivity(){

    val db_conn = DB_Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_)
        setSupportActionBar(findViewById(R.id.toolbar))





    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_page_toolbar_menu, menu)
        //SEARCH ITEM------------
        val menuItem : MenuItem = menu.findItem(R.id.app_bar_search)
        val searchView : SearchView = MenuItemCompat.getActionView(menuItem) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                //Toast.makeText(applicationContext,"Ricerca in corso",Toast.LENGTH_LONG).show()

                if (query != null) {
                    db_conn.SearchUsers(query,object :DB_Handler.MyCallbackFoundUsers{
                        override fun onCallback(returnValue: ArrayList<Utente>) {
                                //INTENT TO ACTIVITY FOR RESULTS
                            val intent = Intent(this@HomePage_Activity,SearchResult::class.java)
                            intent.putExtra("usersList",returnValue)
                            startActivity(intent)
                        }
                    })
                }


                return false
            }
        })
        //-----------------------
        return true
    }

}
