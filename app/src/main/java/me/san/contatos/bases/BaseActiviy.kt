package me.san.contatos.bases

import android.os.Build
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import me.san.contatos.R

open class BaseActiviy : AppCompatActivity(){

    /*
    protected fun setupToolbar(toolbar: androidx.appcompat.widget.Toolbar, title: String, navgationBack: Boolean) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.title = title
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(navgationBack)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

     */

}