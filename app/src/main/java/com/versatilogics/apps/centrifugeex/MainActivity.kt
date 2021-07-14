package com.versatilogics.apps.centrifugeex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.github.centrifugal.centrifuge.*
import org.json.JSONObject
import kotlin.text.Charsets.UTF_8

class MainActivity : AppCompatActivity() {

    private val TAG: String = "Centrifuge"

    private fun getOptions(): Options {
        val opt = Options()
        HashMap<String, String>().let {
            opt.headers = it
        }
        return opt
    }

    private var client: Client =
        Client(CONSTANTS.CENTRIFUGAL_HOST,
            getOptions(),
            object : EventListener() {
                override fun onConnect(client: Client?, event: ConnectEvent?) {
                    Log.i(TAG, "onConnect: %s".format("Connected"))
                }

                override fun onDisconnect(client: Client?, event: DisconnectEvent?) {
                    Log.i(
                        TAG,
                        "onDisconnect: %s, reconnect %s%n".format(
                            event?.reason,
                            event?.reconnect
                        )
                    )
                }
            }
        )

    private val chatListener = object : SubscriptionEventListener() {
        override fun onSubscribeSuccess(sub: Subscription?, event: SubscribeSuccessEvent?) {
            Log.i(TAG, "onSubscribeSuccess: %s".format(sub?.channel))
        }

        override fun onSubscribeError(sub: Subscription?, event: SubscribeErrorEvent?) {
            Log.i(TAG, "onSubscribeError: %s : %s".format(sub?.channel, event?.message))
        }

        override fun onPublish(sub: Subscription?, event: PublishEvent?) {
            event?.let {
                val data = String(it.data, UTF_8)
                val json = JSONObject(data.trim())
                Log.i(
                    TAG,
                    "onPublish: %s : %s : %s".format(sub?.channel, data, json.getString("message"))
                )
                runOnUiThread {
                    findViewById<TextView>(R.id.textView).text = json.getString("message")
                }
            }
        }
    }

    private lateinit var chatSub: Subscription

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initConnection()
    }

    private fun initConnection() {
        client.setToken(CONSTANTS.JWT_TOKEN)
        client.connect()
        try {
            chatSub = client.newSubscription(CONSTANTS.CHANNELS.CHAT, chatListener)
        } catch (e: DuplicateSubscriptionException) {
            e.printStackTrace()
            return
        }
        chatSub.subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        chatSub.unsubscribe()
        client.disconnect()
    }

}