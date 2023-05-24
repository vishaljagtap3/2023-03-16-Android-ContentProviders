package com.bitcodetech.contentproviders

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.img)
            .setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.setType("image/*")

                startActivityForResult(
                    intent,
                    1
                )
            }


        //getSystemPreferences()
        getSmses()
        mt("****************************************")
        getCallLog()
        mt("****************************************")

        //getTasks()
        /*addTask(104, "Revise android", 3, false)
        mt("---------------------------------------")
        getTaskById(101)
        mt("---------------------------------------")*/
        /*getUserById(901)
        mt("---------------------------------------")
        getUsers()*/
    }

    private fun getSmses() {

        val contentUri = Uri.parse("content://sms")

        val c = contentResolver.query(
            contentUri,
            null,
            null,
            null,
            null
        )!!

        for(colName in c!!.columnNames) {
            mt(colName)
        }

        c.close()

    }

    private fun getCallLog() {
        val c = contentResolver.query(
            android.provider.CallLog.Calls.CONTENT_URI,
            null,
            null,
            null,
            null
        )!!

        for(colName in c!!.columnNames) {
            mt(colName)
        }

        val colNumberIndex = c.getColumnIndex(android.provider.CallLog.Calls.NUMBER)
        val colNameIndex = c.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME)
        val colTypeIndex = c.getColumnIndex(android.provider.CallLog.Calls.TYPE)
        val colDateIndex = c.getColumnIndex(android.provider.CallLog.Calls.DATE)

        while(c.moveToNext()) {
            mt("${c.getString(colNameIndex)} ${c.getString(colNumberIndex)} ${c.getString(colDateIndex)} ${c.getInt(colTypeIndex)}")
        }

        c.close()

    }

    private fun getSystemPreferences() {

        val contentUri = android.provider.Settings.System.CONTENT_URI
        val c = contentResolver.query(
            contentUri,
            null,
            null,
            null,
            null
        )

        for(colName in c!!.columnNames) {
            mt(colName)
        }

        mt("--------------------------------------------------------")
        while(c.moveToNext()) {
            mt("${c.getString(1)} ---> ${c.getString(2)}")
        }
        mt("--------------------------------------------------------")

        c.close()

    }

    private fun addTask(
        id : Int,
        title : String,
        priority : Int,
        isComplete : Boolean
    ) {
        var contentUri = Uri.parse("content://in.bitcode.contents")
        contentUri = Uri.withAppendedPath(contentUri, "tasks")

        val values = ContentValues()
        values.put("id", "$id")
        values.put("task_title", title)
        values.put("task_priority", priority)
        values.put("is_completed", isComplete)

        val uriToNewTask = contentResolver.insert(
            contentUri,
            values
        )

        mt("new uri: ${uriToNewTask.toString()}")

        val c = contentResolver.query(
            uriToNewTask!!,
            null,
            null,
            null,
            null
        )

        while(c!!.moveToNext()) {
            mt("${c.getInt(0)} ${c.getString(1)} ${c.getInt(2)} ${c.getString(3)}")
        }
        c.close()
    }

    private fun getUserById(id : Int) {
        var contentUri = Uri.parse("content://in.bitcode.contents")
        contentUri = Uri.withAppendedPath(contentUri, "users")
        contentUri = Uri.withAppendedPath(contentUri, "$id")

        val c = contentResolver.query(
            contentUri,
            null,
            null,
            null,
            null
        )

        while(c!!.moveToNext()) {
            mt("${c.getInt(0)} ${c.getString(1)} ${c.getString(2)}")
        }
        c.close()
    }

    private fun getTaskById(id : Int) {
        var contentUri = Uri.parse("content://in.bitcode.contents")
        contentUri = Uri.withAppendedPath(contentUri, "tasks")
        contentUri = Uri.withAppendedPath(contentUri, "$id")

        val c = contentResolver.query(
            contentUri,
            null,
            null,
            null,
            null
        )

        while(c!!.moveToNext()) {
            mt("${c.getInt(0)} ${c.getString(1)} ${c.getInt(2)} ${c.getString(3)}")
        }
        c.close()
    }


    private fun getUsers() {
        var contentUri = Uri.parse("content://in.bitcode.contents")
        contentUri = Uri.withAppendedPath(contentUri, "users")

        val c = contentResolver.query(
            contentUri,
            null,
            null,
            null,
            null
        )

        while(c!!.moveToNext()) {
            mt("${c.getInt(0)} ${c.getString(1)} ${c.getString(2)}")
        }
        c.close()
    }


    private fun getTasks() {
        var contentUri = Uri.parse("content://in.bitcode.contents")
        contentUri = Uri.withAppendedPath(contentUri, "tasks")

        val c = contentResolver.query(
            contentUri,
            null,
            null,
            null,
            null
        )

        while(c!!.moveToNext()) {
            mt("${c.getInt(0)} ${c.getString(1)} ${c.getInt(2)} ${c.getString(3)}")
        }
        c.close()
    }

    private fun mt(text : String) {
        Log.e("tag", text)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null) {
            mt(data.data.toString())
            findViewById<ImageView>(R.id.img).setImageURI(data.data)
        }
    }
}