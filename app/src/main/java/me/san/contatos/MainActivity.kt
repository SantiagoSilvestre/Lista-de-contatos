package me.san.contatos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.san.contatos.adapter.ContatoAdapter
import me.san.contatos.application.ContatoApplication
import me.san.contatos.bases.BaseActiviy
import me.san.contatos.model.ContatosVO
import me.san.contatos.singleton.ContatoSingleton

class MainActivity : BaseActiviy() {

    private var adapter: ContatoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // val toolbar: Toolbar = findViewById(R.id.toolBar)

        //setupToolbar(toolbar, "Lista de Contatos", false)
        setupRecyclerView ()
        setupOnclicks()

    }

    private fun setupOnclicks() {
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val ivBuscar: ImageView = findViewById(R.id.ivBuscar)
        fab.setOnClickListener { onClickAdd()}
        ivBuscar.setOnClickListener {onClickBuscar()}
    }

    private fun setupRecyclerView () {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd() {
        val intent = Intent(this, ContatoActivity::class.java)
        startActivity(intent)
    }

    private fun onClickBuscar() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val etBuscar: EditText = findViewById(R.id.etBuscar)
        val progress: ProgressBar = findViewById(R.id.progress)
        progress.visibility = View.VISIBLE
        val busca = etBuscar.text.toString()

        Thread(Runnable {
            Thread.sleep(1500)
            var listaFiltrada: List<ContatosVO> = mutableListOf()
            try {
                listaFiltrada = ContatoApplication.instance.helperDB?.buscarContatos(busca) ?: mutableListOf()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            runOnUiThread {
                adapter = ContatoAdapter(this, listaFiltrada) {onClickItemRecyclerView(it)}
                recyclerView.adapter = adapter
                progress.visibility = View.GONE
                Toast.makeText(this, "Buscanco por $busca", Toast.LENGTH_SHORT).show()
            }
        }).start()


    }

    private fun onClickItemRecyclerView (index: Int) {
        val intent = Intent(this, ContatoActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

}