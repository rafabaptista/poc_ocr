package com.pqc.pococridwall

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.pqc.pococridwall.EventFlow.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        initiateListeners()
    }

    private fun initiateListeners() {
        complete_flow_selector.setOnClickListener { onClickButtonTOOLKIT(COMPLETE_SELECTOR) }
        complete_flow_rg.setOnClickListener { onClickButtonTOOLKIT(COMPLETE_RG) }
        complete_flow_cnh.setOnClickListener { onClickButtonTOOLKIT(COMPLETE_CNH) }
        doc_flow_selector.setOnClickListener { onClickButtonTOOLKIT(DOC_SELECTOR) }
        doc_flow_rg.setOnClickListener { onClickButtonTOOLKIT(RG_ONLY) }
        doc_flow_cnh.setOnClickListener { onClickButtonTOOLKIT(CNH_ONLY) }
        selfie_flow.setOnClickListener { onClickButtonTOOLKIT(FACE) }
    }

    private fun onClickButtonTOOLKIT(event: EventFlow) {
        this.startActivity(Intent(this, OcrActivity::class.java).putExtra("eventFlow", event))
    }
}
