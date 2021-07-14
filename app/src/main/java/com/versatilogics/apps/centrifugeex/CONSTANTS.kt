package com.versatilogics.apps.centrifugeex

object CONSTANTS {
    const val CENTRIFUGAL_HOST =
        "http://10.5.12.22:8086/connection/websocket?format=protobuf"

    //  user_id = 2
    const val JWT_TOKEN =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIn0.xSoqbs-_6Gbilec7o55pjLm1acgrMaT61AyuUFHp7Pg"
    // user_id = 4
    // const val JWT_TOKEN =
    // "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0In0.33YeQHtxTyJRVKgAeUg82tIQmlzytk6gqJwRTc6D5ec"

    object CHANNELS {
        const val CHAT = "chat"
    }
}