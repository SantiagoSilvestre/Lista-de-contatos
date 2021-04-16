package me.san.contatos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.san.contatos.R
import me.san.contatos.model.ContatosVO

class ContatoAdapter (
    private val context: Context,
    private val lista: List<ContatosVO>,
    private val onClick: ((Int) -> Unit)
) : RecyclerView.Adapter<ContatoViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contato, parent, false)
        return ContatoViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val contato = lista[position]
        with(holder.itemView) {
            val tvNome :TextView = findViewById(R.id.tvNome)
            val tvTelefone :TextView = findViewById(R.id.tvTelefone)
            val IIItem :LinearLayout = findViewById(R.id.IIItem)
            tvNome.text = contato.nome
            tvTelefone.text = contato.telefone
            IIItem.setOnClickListener {onClick(contato.id)}
        }
    }

}

class ContatoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)