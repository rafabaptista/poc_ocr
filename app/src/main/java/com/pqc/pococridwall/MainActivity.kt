package com.pqc.pococridwall

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//import co.idwall.idwallsdk.IDwallSDK
import co.idwall.toolkit.IDwallToolkit
import co.idwall.toolkit.flow.core.Doc
import co.idwall.toolkit.flow.core.Flow
import kotlinx.android.synthetic.main.activity_main.*
import android.app.Activity
import android.util.Log

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MainActivity : AppCompatActivity() {
    companion object {
        /*private enum class EventOption {
            RG_FRONT, RG_BACK, CNH, GENERIC, CNH_FACE, RG_FRONT_FACE, GENERIC_FACE, ONLY_FACE, SELECTOR,
        }
*/
        private enum class EventFlow {
            COMPLETE_RG, RG_ONLY, COMPLETE_CNH, CNH_ONLY, FACE, COMPLETE_SELECTOR, DOC_SELECTOR
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun startIdWall() {
        IDwallToolkit.getInstance() //Inicia id wall
        IDwallToolkit.getInstance().init(this.application, "<key>")
    }

    override fun onResume() {
        super.onResume()
        initiateListeners()
    }

    private fun initiateListeners() {
        complete_flow_selector.setOnClickListener { onClickButtonTOOLKIT(EventFlow.COMPLETE_SELECTOR) }
        complete_flow_rg.setOnClickListener { onClickButtonTOOLKIT(EventFlow.COMPLETE_RG) }
        complete_flow_cnh.setOnClickListener { onClickButtonTOOLKIT(EventFlow.COMPLETE_CNH) }
        doc_flow_selector.setOnClickListener { onClickButtonTOOLKIT(EventFlow.DOC_SELECTOR) }
        doc_flow_rg.setOnClickListener { onClickButtonTOOLKIT(EventFlow.RG_ONLY) }
        doc_flow_cnh.setOnClickListener { onClickButtonTOOLKIT(EventFlow.CNH_ONLY) }
        selfie_flow.setOnClickListener { onClickButtonTOOLKIT(EventFlow.FACE) }
    }

    //novo método usando o TOLLKIT id wall
    private fun onClickButtonTOOLKIT(event: EventFlow) {
        startIdWall()

        when(event){
            EventFlow.COMPLETE_SELECTOR -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.COMPLETE, Doc.CHOOSE)
            }
            EventFlow.COMPLETE_RG -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.COMPLETE, Doc.RG)
            }
            EventFlow.COMPLETE_CNH -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.COMPLETE, Doc.CNH)
            }
            EventFlow.DOC_SELECTOR -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.DOC, Doc.CHOOSE)
            }
            EventFlow.RG_ONLY -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.DOC, Doc.RG)
            }
            EventFlow.CNH_ONLY -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.DOC, Doc.CNH)
            }
            EventFlow.FACE -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.LIVENESS)
            }
        }
    }

    //método antigo usando SDK id wall
    /*private fun onClickButtonSDK(event: EventOption) {
        IDwallSDK.setAuthKey("cdd5282c767055cf6a6a0929fe33c5fd")

        when(event){
            EventOption.CNH -> {
                IDwallSDK.startFlow(this, IDwallSDK.OP_DOCUMENT_ONLY, IDwallSDK.DOC_CNH)
            }
            EventOption.CNH_FACE -> {
                IDwallSDK.startFlow(this, IDwallSDK.OP_COMPLETE_FLOW, IDwallSDK.DOC_CNH)
            }
            EventOption.RG_BACK -> {
                IDwallSDK.startFlow(this, IDwallSDK.OP_COMPLETE_FLOW, IDwallSDK.DOC_RG_BACK)
            }
            EventOption.RG_FRONT -> {
                IDwallSDK.startFlow(this, IDwallSDK.OP_DOCUMENT_ONLY, IDwallSDK.DOC_RG_FRONT)
            }
            EventOption.RG_FRONT_FACE -> {
                IDwallSDK.startFlow(this, IDwallSDK.OP_COMPLETE_FLOW, IDwallSDK.DOC_RG_FRONT)
            }
            EventOption.GENERIC -> {
                IDwallSDK.startFlow(this, IDwallSDK.OP_DOCUMENT_ONLY, IDwallSDK.DOC_GENERIC)
            }
            EventOption.GENERIC_FACE -> {
                IDwallSDK.startFlow(this, IDwallSDK.OP_COMPLETE_FLOW, IDwallSDK.DOC_GENERIC)
            }
            EventOption.ONLY_FACE -> {
                IDwallSDK.startFlow(this, IDwallSDK.OP_FACE_ONLY, IDwallSDK.CAPTURE_VIEW)
            }
            EventOption.SELECTOR -> {
                IDwallSDK.startFlow(this, IDwallSDK.OP_COMPLETE_FLOW)
            }
        }
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //new
        val token: String
        if (resultCode === Activity.RESULT_OK && requestCode == IDwallToolkit.IDWALL_REQUEST) {
            if (data != null && data.extras != null && data.extras.containsKey(IDwallToolkit.TOKEN)) {
                try {
                    token = data.getStringExtra(IDwallToolkit.TOKEN)

                    Log.d("TokenIDWALL", token)

                    AlertDialog.Builder(this)
                            .setTitle("Protocolo IdWall")
                            .setMessage(token)
                            .setCancelable(false)
                            .setNeutralButton(android.R.string.ok, null).create()
                            .show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                Log.d("NoDataIDWALL", "not found")
            }
        } else {
            Log.d("ErrorIDWALL", "Response Fail")
        }

        //old
        /*if (requestCode == IDwallSDK.requestCode && resultCode === RESULT_OK) {
            if (data != null) {
                try {
                    val token = data.getStringExtra("token")

                    print("Token: $token")

                    AlertDialog.Builder(this)
                            .setTitle("Protocolo IdWall")
                            .setMessage(token)
                            .setCancelable(false)
                            .setNeutralButton(android.R.string.ok, null).create()
                            .show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                IDwallSDK.debug("Data not found")
            }
        } else {
            IDwallSDK.debug("Response failure")
        }*/
    }
}
