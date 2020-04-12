package com.animesh.android.creatures.app

import android.app.Application
import com.animesh.android.creatures.model.CreatureStore


class CreaturesApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    // Initialize CreatureStore
    CreatureStore.loadCreatures(this)
    CreatureStore.loadFoods(this)
  }
}