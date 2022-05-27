package com.ivoronline.compose_ktor_store_retrieve

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.launch

//==================================================================
// MAIN ACTIVITY
//==================================================================
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    var token : String = "";

    setContent {
      Column {

        var response1 by remember { mutableStateOf("") }  //For getToken
        var response2 by remember { mutableStateOf("") }  //For sendToken
        val coroutineScope = rememberCoroutineScope()

        //GET TOKEN
        Button(onClick = { coroutineScope.launch { response1 = getToken(); token = response1; } }) {
          Text("Get Token: $response1")
        }

        //SEND TOKEN
        Button(onClick = { coroutineScope.launch { response2 = sendToken(token) } }) {
          Text("Send Token: $response2")
        }

      }
    }
  }
}

//==================================================================
// GET TOKEN
//==================================================================
suspend fun getToken() : String {

  //CONFIGURE CLIENT
  val httpClient = HttpClient(CIO)

  //CAL URL
  //val httpResponse: HttpResponse = httpClient.get("http://192.168.0.102:8080/GetToken")
  val httpResponse: HttpResponse = httpClient.get("http://192.168.0.102:8080/CreateJWT?username=myuser&password=myuserpassword")

  //CLOSE CLIENT
  httpClient.close()

  //RETURN BODY
  return httpResponse.body();

}

//==================================================================
// SEND TOKEN
//==================================================================
suspend fun sendToken(token: String) : String {

  //CONFIGURE CLIENT
  val httpClient = HttpClient(CIO)

  //CAL URL
  //val httpResponse: HttpResponse = httpClient.get("http://192.168.0.102:8080/SendToken") {
  val httpResponse: HttpResponse = httpClient.get("http://192.168.0.102:8080/Hello") {
    header("authorization", "Bearer $token")
  }

  //CLOSE CLIENT
  httpClient.close()

  //RETURN BODY
  return httpResponse.body();

}
