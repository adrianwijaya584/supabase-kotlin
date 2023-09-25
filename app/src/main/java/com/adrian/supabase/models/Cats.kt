package com.adrian.supabase.models

import kotlinx.serialization.Serializable

@Serializable
data class Cats(
  val id: Int,
  val name: String,
  val race: String,
  val created_at: String,
  val image: String= "",
)
