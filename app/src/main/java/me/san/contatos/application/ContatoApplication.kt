package me.san.contatos.application

import android.app.Application
import me.san.contatos.helper.HelperDB

class ContatoApplication  : Application() {

    var helperDB: HelperDB? = null
        private set

    companion object {
        lateinit var instance: ContatoApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(context = this)
    }
}