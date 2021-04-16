package me.san.contatos

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import me.san.contatos.application.ContatoApplication
import me.san.contatos.bases.BaseActiviy
import me.san.contatos.model.ContatosVO
import me.san.contatos.singleton.ContatoSingleton

class ContatoActivity : BaseActiviy() {

    private var idContato: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)
        val toolbar: Toolbar = findViewById(R.id.toolBar)
        //setupToolbar(toolbar, "Contato", true)
        setupContato()
    }

    private fun setupContato() {
        val progress: ProgressBar = findViewById(R.id.progress)
        val btnExcluirContato: Button = findViewById(R.id.btnExcluirContato)
        idContato = intent.getIntExtra("index", -1)
        if (idContato == -1) {
            btnExcluirContato.visibility = View.GONE
            return
        }
        progress.visibility = View.VISIBLE

        Thread(Runnable {
            Thread.sleep(1500)
            var lista = ContatoApplication.instance.helperDB?.buscarContatos("$idContato", true)
                ?: return@Runnable
            var contato = lista.getOrNull(0) ?: return@Runnable
            val etNome: EditText = findViewById(R.id.etNome)
            val etTelefone: EditText = findViewById(R.id.etTelefone)
            runOnUiThread {
                etNome.setText(contato.nome)
                etTelefone.setText(contato.telefone)
                progress.visibility = View.GONE
            }

        }).start()


    }


    fun onClickExcluirContato(view: View) {
        val progress: ProgressBar = findViewById(R.id.progress)
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            if (idContato > -1) {
                ContatoApplication.instance.helperDB?.deletarContato(idContato)
                runOnUiThread {
                    finish()
                    progress.visibility = View.GONE
                }
            }
        }).start()
    }

    fun onClickSalvarContato(view: View) {
        val progress: ProgressBar = findViewById(R.id.progress)
        progress.visibility = View.VISIBLE
        val etNome: EditText = findViewById(R.id.etNome)
        val etTelefone: EditText = findViewById(R.id.etTelefone)
        val nome = etNome.text.toString()
        val telefone = etTelefone.text.toString()
        val contato = ContatosVO(
            idContato,
            nome,
            telefone
        )
        Thread(Runnable {
            Thread.sleep(1500)
            if (idContato == -1) {
                ContatoApplication.instance.helperDB?.salvarContato(contato)
            } else {
                ContatoApplication.instance.helperDB?.updateContato(contato)
            }
            runOnUiThread {
                finish()
                progress.visibility = View.GONE
            }

        }).start()

    }
}