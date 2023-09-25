package com.adrian.supabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.adrian.supabase.adapters.CatsAdapter
import com.adrian.supabase.databinding.ActivityMainBinding
import com.adrian.supabase.models.Cats
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.createChannel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  lateinit var client: SupabaseClient
  lateinit var cats: List<Cats>
  lateinit var adapter: CatsAdapter
  val tag= "MainActivity"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    client= createSupabaseClient(
      supabaseKey = getString(R.string.supabase_key),
      supabaseUrl = getString(R.string.supabase_url),
    ) {
      install(Postgrest)
      install(GoTrue)
      install(Realtime)
    }

    binding= ActivityMainBinding.inflate(layoutInflater)
    binding.recylerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


    lifecycleScope.async {
      cats= getCats()

      adapter= CatsAdapter(cats)
      binding.recylerView.adapter= adapter
    }


    lifecycleScope.async {
      val channel = client.realtime.createChannel("channelId") {
        //optional config
      }
      val changeFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public") {
        table = "cats"
      }

//in a new coroutine (or use Flow.onEach().launchIn(scope)):
      changeFlow.collect {
        when(it) {
          is PostgresAction.Delete -> println("Deleted: ${it.oldRecord}")
          is PostgresAction.Insert -> println("Inserted: ${it.record}")
          is PostgresAction.Select -> println("Selected: ${it.record}")
          is PostgresAction.Update -> Log.d(tag,"Updated: ${it.oldRecord} with ${it.record}")
        }
      }

      client.realtime.connect()
      channel.join()
    }

    setContentView(binding.root)
  }


  suspend fun getCats(): List<Cats> {
    return client.postgrest["cats"].select().decodeList<Cats>()
  }
}