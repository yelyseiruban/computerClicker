package com.example.clicker

import android.content.Context
import com.google.gson.Gson
import java.io.File

class StateAct(private val context: Context) : IStateAct {
    private val FILE_NAME: String = "gameState.json"
    private var fileManager: FileManager = FileManager(context)
    override lateinit var state: GameState
    init {
        initialGameState()
    }

    private fun initialGameState() {
        fun fileDoesNotExist(directoryPath: String, fileName: String): Boolean {
            val directory = File(directoryPath)
            val file = File(directory, fileName)
            return !(file.exists() && file.isFile)
        }
        val directoryPath: String = context.filesDir.toString()
        val fileName = "gameState.json"
        if (fileDoesNotExist(directoryPath, fileName)) {
            this.state = GameState.getInstance()
            this.save()
        } else {
            this.state = GameState.getInstance()
            load()
        }
    }

    override fun save() {
        val stateJSON: String = Gson().toJson(state)
        fileManager.save(FILE_NAME, stateJSON)
    }


    override fun load(): GameState {
        val stateJSON: String? = fileManager.load(FILE_NAME)
        val loadedGameState =Gson().fromJson(stateJSON, GameState::class.java)
        this.update(loadedGameState)
        return state
    }

    override fun update(gameState: GameState) {
        this.state.updateInstance(gameState)
    }

}