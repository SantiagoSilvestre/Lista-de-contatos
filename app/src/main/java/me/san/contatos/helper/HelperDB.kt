package me.san.contatos.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import me.san.contatos.model.ContatosVO

class HelperDB(
    context: Context
) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {

    companion object {
        private val NOME_BANCO = "contato.db"
        private val VERSAO_ATUAL = 1
    }

    val TABLE_NAME = "contato"
    val COLUMNS_ID = "id"
    val COLUMNS_NOME = "nome"
    val COLUMNS_TELEFONE = "telefone"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMNS_ID INTEGER NOT NULL," +
            "$COLUMNS_NOME TEXT NOT NULL," +
            "$COLUMNS_TELEFONE TEXT NOT NULL," +
            "" +
            "PRIMARY KEY ($COLUMNS_ID AUTOINCREMENT)" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }

    fun buscarContatos(buscar: String, isBuscaPorID: Boolean = false): List<ContatosVO> {

        val db = readableDatabase ?: return mutableListOf()
        var lista = mutableListOf<ContatosVO>()
        var sql = "SELECT * FROM $TABLE_NAME  "
        var buscaPercentual: String
        if (isBuscaPorID) {
            sql += "WHERE $COLUMNS_ID = ?"
            buscaPercentual = "$buscar"
        } else {
            sql += "WHERE $COLUMNS_NOME LIKE ?"
            buscaPercentual =  "%$buscar%"
        }
        var curso = db.rawQuery(sql, arrayOf(buscaPercentual))
        if (curso == null) {
            db.close()
            return mutableListOf()
        }
        while (curso.moveToNext()) {
            var contato = ContatosVO(
                curso.getInt(curso.getColumnIndex(COLUMNS_ID)),
                curso.getString(curso.getColumnIndex(COLUMNS_NOME)),
                curso.getString(curso.getColumnIndex(COLUMNS_TELEFONE))
            )
            lista.add(contato)
        }
        db.close()
        return lista
    }

    fun salvarContato(contato: ContatosVO) {
        var db = writableDatabase ?: return
        val sql = "INSERT INTO $TABLE_NAME ($COLUMNS_NOME, $COLUMNS_TELEFONE) VALUES (?, ?)"
        var array = arrayOf(contato.nome, contato.telefone)
        db.execSQL(sql, array)
        db.close()

    }

    fun deletarContato (id: Int) {
        val db = writableDatabase ?: return
        val where  = "id = ?"
        val arg = arrayOf("$id")
        db.delete(TABLE_NAME, where, arg)
        db.close()
    }

    fun updateContato (contato: ContatosVO) {
        val db = writableDatabase ?: return
        val content  = ContentValues()
        content.put(COLUMNS_NOME, contato.nome)
        content.put(COLUMNS_TELEFONE, contato.telefone)
        val where  = "id = ?"
        val arg = arrayOf("${contato.id}")
        db.update(TABLE_NAME, content, where, arg)
        db.close()
    }

}