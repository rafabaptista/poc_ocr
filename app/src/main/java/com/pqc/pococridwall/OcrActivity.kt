package com.pqc.pococridwall

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.idwall.toolkit.IDwallToolkit
import co.idwall.toolkit.flow.core.Doc
import co.idwall.toolkit.flow.core.Flow
import com.pqc.pococridwall.EventFlow.*

class OcrActivity : AppCompatActivity() {

    private var extraOption: EventFlow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocr)
        extraOption = intent.getSerializableExtra("eventFlow") as EventFlow
    }

    override fun onResume() {
        super.onResume()
        startIdWall(extraOption)
    }

    private fun startIdWall(event: EventFlow?) {
        IDwallToolkit.getInstance() //Inicia id wall
        IDwallToolkit.getInstance().init(application, "<key>")

        when(event){
            COMPLETE_SELECTOR -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.COMPLETE, Doc.CHOOSE)
            }
            COMPLETE_RG -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.COMPLETE, Doc.RG)
            }
            COMPLETE_CNH -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.COMPLETE, Doc.CNH)
            }
            DOC_SELECTOR -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.DOC, Doc.CHOOSE)
            }
            RG_ONLY -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.DOC, Doc.RG)
            }
            CNH_ONLY -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.DOC, Doc.CNH)
            }
            FACE -> {
                IDwallToolkit.getInstance().startFlow(this, Flow.LIVENESS)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //new
        val token: String
        if (resultCode == Activity.RESULT_OK && requestCode == IDwallToolkit.IDWALL_REQUEST) {
            if (data != null && data.extras != null && data.extras.containsKey(IDwallToolkit.TOKEN)) {
                try {
                    token = data.getStringExtra(IDwallToolkit.TOKEN)

                    Log.d("TokenIDWALL", token)

                    AlertDialog.Builder(this)
                            .setTitle("Protocolo IdWall")
                            .setMessage(token)
                            .setCancelable(false)
                            .setNeutralButton(android.R.string.ok
                            ) { dialogInterface: DialogInterface, i: Int ->
                                this.startActivity(Intent(this, MainActivity::class.java))
                            }.create()
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
    }
}
