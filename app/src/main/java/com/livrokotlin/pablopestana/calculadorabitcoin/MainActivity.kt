package com.livrokotlin.pablopestana.calculadorabitcoin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.bloco_cotacao.*
import kotlinx.android.synthetic.main.bloco_entrada.*
import kotlinx.android.synthetic.main.bloco_saida.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val api_url = "https://www.mercadobitcoin.net/api/BTC/ticker/"
    var cotacaoBitcoin : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buscarCotacao()

        btn_calcular.setOnClickListener {
            calcular()
        }
    }

    fun buscarCotacao(){

        doAsync {
            //Acessa a API e armazena o retorno na veriável
            val resposta = URL(api_url).readText()
            cotacaoBitcoin = JSONObject(resposta).getJSONObject("ticker").getDouble("last")
            val f = NumberFormat.getCurrencyInstance(Locale("pt","br"))
            val cotacaoFormatada = f.format(cotacaoBitcoin)

            uiThread {
                //o que fazer após o retorno
               // alert("$cotacaoBitcoin").show()
                txt_cotacao_bitcoin.setText("$cotacaoFormatada")

            }
        }

    }

    fun calcular(){
        if (txt_valor.text.isEmpty()){
            txt_valor.error = "Preencha um valor!"
            return
        }
        //valor digitado pelo usuario
        val valor_digitado = txt_valor.text.toString().replace(",",".").toDouble()
        val resultado = if (cotacaoBitcoin > 0)  valor_digitado / cotacaoBitcoin else 0.0
        txt_resultado.text = "%.8f".format(resultado)
    }

}
