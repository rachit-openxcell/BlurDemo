package com.example.rachit.aemjdemo.Fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Response
import com.example.rachit.aemjdemo.Model.StaticData
import com.example.rachit.aemjdemo.R
import com.example.rachit.aemjdemo.Utility.Utility
import com.example.rachit.aemjdemo.Utility.Volley
import com.example.rachit.aemjdemo.Utility.VolleyNetworkRequest
import kotlinx.android.synthetic.main.fragment_about_us.*
import org.json.JSONObject


class AboutUs : Fragment() {

    var mContext:Context? = null
    val TAG:String = "AboutUs"
//    var mListener:OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       /* if (mListener != null) {
            mListener?.onFragmentInteraction("About Us")
        }
*/
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.actionBar?. let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            it.title = Html.fromHtml("<font color='#000000'>About Us</font>", Html.FROM_HTML_MODE_LEGACY)
            } else {
            it.title = Html.fromHtml("<font color='#000000'>About Us</font>")
            }
        }
        getPageDetailWebservice()
    }

    private fun getPageDetailWebservice() {
        if (!Utility.isConnected(mContext)) {
            Utility.showToast(mContext, "No Internet Connection")
            return
        }

        val mBodyParams:HashMap<String, String> = HashMap()
        mBodyParams.put("pageTitle", "aboutUs")

        val requestString = JSONObject(mBodyParams).toString()
        val mVolleyNetworkRequest = VolleyNetworkRequest(
                Utility.NetworkUtility.WS_GET_STATIC_PAGE_DETAIL, mGetPageDetailErrorListener, mGetPageDetailResponseListener, null, requestString)

        Volley.getInstance(mContext).addToRequestQueue(mVolleyNetworkRequest, Utility.NetworkUtility.WS_GET_STATIC_PAGE_DETAIL)
    }

    private val mGetPageDetailResponseListener = object : Response.Listener<String> {
        override fun onResponse(response: String) {

            val staticData = Utility.getObjectFromJsonString(response, StaticData::class.java) as StaticData

            var spanned: Spanned
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                spanned = Html.fromHtml(staticData.data.text, Html.FROM_HTML_MODE_LEGACY)
                about.setText(spanned)
            } else {
                spanned = Html.fromHtml(staticData.data.text)
                about.setText(spanned)
            }
        }
    }

    private val mGetPageDetailErrorListener = Response.ErrorListener { error ->
        Log.d(TAG, "onErrorResponse() called with: error = [$error]")

        // Show Toast
        Utility.showToast(mContext, "Something went wrong.")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

        /*try {
            mListener = context as OnFragmentInteractionListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/

    }

    override fun onDetach() {
        super.onDetach()
//        mListener = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = AboutUs()
    }

}
