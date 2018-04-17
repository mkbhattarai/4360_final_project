package com.example.maheshbhattarai.sqlite_database_demo.fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

import com.example.maheshbhattarai.sqlite_database_demo.R
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Function
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.example.maheshbhattarai.sqlite_database_demo.model.JobMdal
import com.itextpdf.text.*
import com.itextpdf.text.pdf.*

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPCell
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ReportFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var btn_export: Button? = null
    var daySppiner: Spinner? = null

    var jobStatus: Spinner? = null

    private var mListener: OnFragmentInteractionListener? = null
    var db: DBHelper? = null
    lateinit var mContext: Context
    var doc: Document? = null
    var jobStatusAdapter: ArrayAdapter<String>? = null
    var dayAdapter: ArrayAdapter<String>? = null
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
        var rootView = inflater.inflate(R.layout.fragment_report, container, false)

        if (container != null) {
            mContext = container.context
        }

        db = DBHelper(mContext)
        initCompoent(rootView)

        return rootView
    }

    private fun initCompoent(root: View) {
        btn_export = root.findViewById<Button>(R.id.btn_export)

        jobStatus = root.findViewById<Spinner>(R.id.sppinerstatus)
        daySppiner = root.findViewById<Spinner>(R.id.sppiner)

        jobStatusAdapter = ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item, mContext.resources.getStringArray(R.array.jobStatus))

        jobStatusAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jobStatus?.setAdapter(jobStatusAdapter)
        jobStatusAdapter?.notifyDataSetChanged()


        dayAdapter = ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item, mContext.resources.getStringArray(R.array.day))

        dayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        daySppiner?.setAdapter(dayAdapter)

        dayAdapter?.notifyDataSetChanged()

        btn_export?.setOnClickListener(View.OnClickListener { v: View? ->

            try {
                if (db != null) {
                    createPDF(jobStatus?.selectedItem.toString(), daySppiner?.selectedItem.toString())
                }
            } catch (e: Exception) {
            }
        }
        )
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
         * @return A new instance of fragment ReportFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): ReportFragment {
            val fragment = ReportFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    private fun createPDF(status: String, day: String) {
        Log.e("Asish", status)
        doc = Document()

        try {
            val table = PdfPTable(floatArrayOf(2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f,2f,2f,2f))
            //open the document
            var list: ArrayList<JobMdal>? = null

            if (day == "Weekly") {
                if (status == "Pending") {
                    list = db?.weekly("0", Apprefence.getUserId(mContext))
                } else if (status == "completed") {
                    list = db?.weekly("2", Apprefence.getUserId(mContext))
                }

                if (list!!.isEmpty()) {
                    Toast.makeText(mContext, "Record is not found ", Toast.LENGTH_LONG).show()
                }

                Log.e("PDF week 12", list.toString())

            } else if (day == "Monthly") {
                list = db?.monthly(Apprefence.getUserId(mContext), status)
                Log.e("PDF month 12", list.toString())
                if (status == "Pending") {
                    list = db?.weekly("0", Apprefence.getUserId(mContext))
                } else if (status == "completed") {
                    list = db?.weekly("2", Apprefence.getUserId(mContext))
                }
                if (list!!.isEmpty()) {
                    Toast.makeText(mContext, "Record is not found ", Toast.LENGTH_LONG).show()
                }

            } else if (day == "Yearly") {
                list = db?.yearly(Apprefence.getUserId(mContext), status)
                if (status == "Pending") {
                    list = db?.weekly("0", Apprefence.getUserId(mContext))
                } else if (status == "completed") {
                    list = db?.weekly("2", Apprefence.getUserId(mContext))
                }
                if (list!!.isEmpty()) {
                    Toast.makeText(mContext, "Record is not found ", Toast.LENGTH_LONG).show()
                }
                Log.e("PDF yearly 12", list.toString())
            }

            if (list != null) {
                table.setWidthPercentage(100f)
                table.spacingBefore = 0f
                table.spacingAfter = 0f


//                val cell = PdfPCell(Paragraph("DateRange"))
//
//                cell.colspan = 10
//                cell.horizontalAlignment = Element.ALIGN_CENTER
//                cell.setPadding(5.0f)
//                cell.backgroundColor = BaseColor(140, 221, 8)
//
//                table.addCell(cell)

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.setHeaderRows(1);

                table.addCell("Job Title")
                table.addCell("Customer Name")
                table.addCell("Customer Address")
                table.addCell("Customer Phone No")
                table.addCell("Esitmated Time")
                table.addCell("Spent Time")
                table.addCell("Job Status")
                table.addCell("Hourly Charages")
                table.addCell("Extra Charages")
                table.addCell("Distance Charages")
                table.addCell("Total Ammount")
                table.setHeaderRows(1);


                var cells = table.getRow(0).getCells();
                for (j in 0 until cells.size) {
                    cells[j].backgroundColor = BaseColor.GRAY
                }

                for (text in list) {
                    Log.e("PDF items add 12", text.toString())
                    table.addCell(text.jobTitle)
                    table.addCell(text.customerName)
                    table.addCell(text.customer_address)
                    table.addCell(text.estimated_phoneNo)
                    table.addCell(text.estimated_time)
                    table.addCell(text.job_spent_time)
                    table.addCell(text.jobApproved)
                    table.addCell(text.hourly_charhge+" $")
                    table.addCell(text.job_extra_charge+" $")
                    table.addCell(text.distance_charge+" $")
                    table.addCell(text.total_amount+" $")

                }
            }
            var path = Environment.getExternalStorageDirectory().absolutePath + "/Dir"
            var dir = File(path);
            if (!dir.exists())
                dir.mkdirs()
            var file = File(dir, "newFile.pdf");
            var fOut = FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);
            table.setSpacingBefore(10.0f);       // Space Before table starts, like margin-top in CSS
            table.setSpacingAfter(10.0f);

            doc?.open();


            //Something like in HTML :-)
            val f = Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.RED)
            var para = Paragraph("Report", f)

            para.setAlignment(Element.ALIGN_CENTER);
            doc?.add(para);
            doc?.add(Chunk.NEWLINE);
            doc?.add(Paragraph("Document Generated On - " + Date().toString()));
            doc?.add(Chunk.NEWLINE);   //Something like in HTML :-)
            doc?.add(table);

        } catch (de: DocumentException) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (e: IOException) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc?.close();
        }
        viewPdf("newFile.pdf", "Dir")
    }

    // Method for opening a pdf file
    fun viewPdf(file: String, directory: String) {
        var first = File.separator
        val pdfFile = File(Environment.getExternalStorageDirectory().absolutePath + "/Dir" + "/" + file)
        val path = Uri.fromFile(pdfFile)

        val target = Intent(Intent.ACTION_VIEW)
        target.setDataAndType(path, "application/pdf")
        target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        val intent = Intent.createChooser(target, "Open File")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Instruct the user to install a PDF reader here, or something
        }

    }
}