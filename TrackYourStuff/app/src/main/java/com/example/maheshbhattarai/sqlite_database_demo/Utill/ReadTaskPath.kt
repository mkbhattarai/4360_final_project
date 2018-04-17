package com.example.maheshbhattarai.sqlite_database_demo.Utill

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.androidquery.AQuery
import com.androidquery.callback.AjaxCallback
import com.androidquery.callback.AjaxStatus
import com.google.android.gms.maps.GoogleMap
import org.json.JSONObject

/**
 * Created by esp on 14/2/18.
 */

class ReadTaskPath(internal var mContext: Context,var map : GoogleMap)  {
    internal var callback1: OnReadPath

    init {
        this.callback1 = mContext as OnReadPath
    }

    public fun drawPath(url : String) {
        var query = AQuery(mContext)
            query.ajax(url, JSONObject::class.java, object : AjaxCallback<JSONObject>() {

                override fun callback(url: String?, html: JSONObject?, status: AjaxStatus?) {
                    Log.e("PathDraw",html.toString())
                    if (html != null) {
                        ParserTaskPath(mContext,map).execute(html.toString())
                        callback1.onGetPath(html)
                    }


                }
            })

    }

//
//    override fun doInBackground(vararg url: String): String {
//        var data1 = ""
//        try {
//            var query = AQuery(mContext)
//            query.ajax(url[0], String::class.java, object : AjaxCallback<String>() {
//
//                override fun callback(url: String?, html: String?, status: AjaxStatus?) {
//                    data1= html.toString()
//                }
//            })
//        } catch (e: Exception) {
//            Log.d("Background Task", e.toString())
//        }
//
//        Log.e("here you go", data1)
//
//        return data1
//    }
//
//    override fun onPostExecute(result: String) {
//        super.onPostExecute(result)
//        callback.onGetPath(result)
//    }

}