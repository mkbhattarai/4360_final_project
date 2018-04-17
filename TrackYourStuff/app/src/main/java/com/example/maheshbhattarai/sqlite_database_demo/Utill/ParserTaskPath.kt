package com.example.maheshbhattarai.sqlite_database_demo.Utill

import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import com.example.maheshbhattarai.sqlite_database_demo.fragment.HomeFragment
import com.google.android.gms.maps.GoogleMap

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions

import org.json.JSONObject

import java.util.ArrayList
import java.util.HashMap

/**
 * Created by esp on 14/2/18.
 */

class ParserTaskPath(internal var mContext: Context, var map: GoogleMap) : AsyncTask<String, Int, List<List<HashMap<String, String>>>>() {
    internal var callback: OnParsePath

    init {
        this.callback = mContext as OnParsePath
    }


    override fun doInBackground(
            vararg jsonData: String): List<List<HashMap<String, String>>>? {

        val jObject: JSONObject
        var routes: List<List<HashMap<String, String>>>? = null
        Log.e("Sidd12", jsonData[0])

        try {
            jObject = JSONObject(jsonData[0])
            Log.e("Sidd12", jObject.toString())
            val parser = PathJSONParser()
            routes = parser.parse(jObject)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return routes
    }

    override fun onPostExecute(routes: List<List<HashMap<String, String>>>) {
        Log.e("Sidd12", routes.toString())

        var points: ArrayList<LatLng>? = null
        var polyLineOptions: PolylineOptions? = null

        try {
            // traversing through routes
            for (i in routes.indices) {
                points = ArrayList()
                polyLineOptions = PolylineOptions()
                val path = routes[i]

                Log.e("PATH", path.toString())

                for (j in path.indices) {
                    val point = path[j]

                    val lat = java.lang.Double.parseDouble(point["lat"])
                    val lng = java.lang.Double.parseDouble(point["lng"])
                    val position = LatLng(lat, lng)
                    Log.e("PATH", "" + position)


                    points.add(position)
                }
                Log.e("PATH12", "" + points.toString())

                polyLineOptions.addAll(points)
                polyLineOptions.width(3f)
                polyLineOptions.color(Color.BLACK)
                map.addPolyline(polyLineOptions)

            }
//            callback.onGetParsePath(polyLineOptions)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
