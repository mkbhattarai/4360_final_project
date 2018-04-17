package com.example.maheshbhattarai.sqlite_database_demo.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.maheshbhattarai.sqlite_database_demo.R
import com.example.maheshbhattarai.sqlite_database_demo.Utill.AppConstant
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.adapter.CustomAdapter
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.example.maheshbhattarai.sqlite_database_demo.model.JobMdal

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CompeletedFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CompeletedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompeletedFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    lateinit var allRecyclerView : RecyclerView

    private var mListener: OnFragmentInteractionListener? = null

    lateinit var mContext : Context

    lateinit var  db : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_all, container, false)
        if (container != null) {
            mContext=container.context
        }
        if (mListener!=null){
            onButtonPressed("Completed Job")
        }
        db= DBHelper(mContext)
        initComponent(rootView)
        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: String) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: String)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AllFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): CompeletedFragment {
            val fragment = CompeletedFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args

            return fragment
        }
    }

    fun  initComponent(view : View){
        allRecyclerView=view.findViewById<RecyclerView>(R.id.recycler_view)
        allRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)
        if (db!=null){
            if (db.selectedJob(Apprefence.getJobCat(mContext), AppConstant.COMPLETED)!=null){
            var  jobM1List : ArrayList<JobMdal> =db.selectedJob(Apprefence.getJobCat(mContext), AppConstant.COMPLETED)
                Log.e("Sidd",jobM1List.toString())
            var customAdapter = CustomAdapter(mContext,jobM1List)
            allRecyclerView.adapter=customAdapter
            customAdapter.notifyDataSetChanged()
        }
    }

//    public fun getJob(status : String , user_cat : String){
////        db.getPost(status,user_cat)
//
//    }
    }

}// Required empty public constructor