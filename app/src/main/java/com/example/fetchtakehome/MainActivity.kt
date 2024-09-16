package com.example.fetchtakehome

import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.io.IOException
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    // OkHttpClient instance to make HTTP requests
    private val client = OkHttpClient()

    //ArrayList to hold the data (ListModel) from the JSON
    private var itemArrayList : ArrayList<ListModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        run()
    }

    // Function that will update the recyclerView with sorted data from the JSON
    fun updateView() {
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val itemListAdaptor = itemListAdaptor(itemArrayList)
        recyclerView.adapter = itemListAdaptor
    }

    private fun run() {
        val request = Request.Builder()
            .url("https://fetch-hiring.s3.amazonaws.com/hiring.json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    // Parse the response body into a JSON array
                    val jsonResponse = JSONArray(response.body!!.string())

                    // Loop through each JSON object in the array
                    for (i in 0 until jsonResponse.length()) {
                        val jsonObject = jsonResponse.getJSONObject(i)

                        val id = jsonObject.getInt("id")
                        val listId = jsonObject.getInt("listId")
                        val name = if (!jsonObject.isNull("name")) jsonObject.getString("name") else null

                        // Add items to the ArrayList if they are not null and are not empty
                        if (!name.isNullOrEmpty()) {
                            val tempListModel = ListModel(id, listId, name)
                            itemArrayList.add(tempListModel)
                        }
                    }

                    // Sort the ArrayList by listId, then by name
                    itemArrayList = ArrayList(itemArrayList.sortedWith(compareBy<ListModel> {it.listId}.thenBy { it.name }))

                    // Update the view
                    runOnUiThread{
                        updateView()
                    }
                }
            }
        })
    }
}