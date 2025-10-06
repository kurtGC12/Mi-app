package com.example.app.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

data class  User(
    val id: Int,
    val nombre : String,
    val correo : String,
    val contrasena : String,

)

class UserDao(context: Context){
    private val dbHelper = DBHelper(context)
    fun insert(user: User): Boolean{
        val db = dbHelper.writableDatabase
        val value = ContentValues().apply {
            put("nombre", user.nombre)
            put("correo", user.correo)
            put("contrasena", user.contrasena)

        }
        val id = db.insert("registros", null, value)
        db.close()
        return id != -1L
    }
    fun update(user: User): Boolean{
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", user.nombre)
            put("correo", user.correo)
            put("contrasena", user.contrasena)

        }
        val rows = db.update("registros", values, "id = ?", arrayOf(user.id.toString()))
        db.close()
        return rows > 0
    }

    fun delete(id: Int): Boolean{
        val db = dbHelper.writableDatabase
        val rows = db.delete("registros", "id = ?", arrayOf(id.toString()))
        db.close()
        return rows > 0
    }
    fun getAll(): List<User>{
        val db = dbHelper.readableDatabase
        val list = mutableListOf<User>()
       val c: Cursor = db.rawQuery("SELECT * FROM registros ORDER BY id DESC", null)
        c.use {
            while (it.moveToNext()) {
                list.add(
                    User(
                        id = it.getInt(0),
                        nombre = it.getString(1),
                        correo = it.getString(2),
                        contrasena = it.getString(3)
                    )
                )
            }
        }
        db.close()
        return list
    }
fun getById(id: Int): User?{
    val db = dbHelper.readableDatabase
    val c = db.rawQuery("SELECT * FROM registros WHERE id = ?", arrayOf(id.toString()))
    var u: User? = null

    c.use{
        if (it.moveToFirst()){
            u = User(
                id = it.getInt(0),
                nombre = it.getString(1),
                correo = it.getString(2),
                contrasena = it.getString(3)
            )
        }
    }
    db.close()
    return u

    }
fun search(q: String): List<User>{
    if (q.isEmpty()) return getAll()
    val db = dbHelper.readableDatabase
    val list = mutableListOf<User>()
    val like ="%$q%"
    val c = db.rawQuery("""
        SELECT * FROM registros 
        WHERE nombre LIKE ?
         OR correo LIKE ?
         OR contrasena LIKE ? 
         ORDER BY id DESC""".trimIndent(),
        arrayOf(like, like, like)

    )
    c.use {
        while (it.moveToNext()){
            list.add(User(
                id = it.getInt(0),
                nombre = it.getString(1),
                correo = it.getString(2),
                contrasena = it.getString(3)
            )
            )
        }
    }
        db.close()
        return list
} }