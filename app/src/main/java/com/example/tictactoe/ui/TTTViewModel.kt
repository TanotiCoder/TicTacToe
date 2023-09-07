package com.example.tictactoe.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

data class DataModel(var term: String = "", var color: Color? = null)

class TTTViewModel : ViewModel() {

    private val gameArray: MutableState<Array<Array<DataModel>>> = mutableStateOf(
        arrayOf(
            arrayOf(DataModel(), DataModel(), DataModel()),
            arrayOf(DataModel(), DataModel(), DataModel()),
            arrayOf(DataModel(), DataModel(), DataModel()),
        )
    )

    private var _player: MutableState<Int> = mutableStateOf(1)
    private val _error: MutableState<String> = mutableStateOf("")
    val error = mutableStateOf(_error)
    private var _term: MutableState<String> = mutableStateOf("")
    private var _clickable: MutableState<Boolean> = mutableStateOf(true)
    var clickable = mutableStateOf(_clickable)

    private fun addItem(row: Int, column: Int) {
        _term.value = if (_player.value % 2 == 0) "O" else "X"
        gameArray.value[row][column].term = _term.value
        winner(_term.value)
        _player.value += 1
    }

    fun sendValue(row: Int, column: Int): DataModel = gameArray.value[row][column]

    fun checkValue(row: Int, column: Int) {
        if (gameArray.value[row][column].term.isEmpty()) {
            addItem(row, column)
            _error.value = ""
        } else {
            _error.value = "Already Have Value"
        }
    }

    private fun winner(term: String) {
        for (i in 0..2) {
            for (j in 0..2) {
                if ((gameArray.value[0][j].term == term && gameArray.value[1][j].term == term && gameArray.value[2][j].term == term)) {
                    colorFun(0, j, 1, j, 2, j)
                    return
                } else if (gameArray.value[i][0].term == term && gameArray.value[i][1].term == term && gameArray.value[i][2].term == term) {
                    colorFun(i, 0, i, 1, i, 2)
                    return
                } else if (gameArray.value[0][0].term == term && gameArray.value[1][1].term == term && gameArray.value[2][2].term == term) {
                    colorFun(0, 0, 1, 1, 2, 2)
                    return
                } else if (gameArray.value[0][2].term == term && gameArray.value[1][1].term == term && gameArray.value[2][0].term == term) {
                    colorFun(0, 2, 1, 1, 2, 0)
                    return
                }
            }
        }
    }

    private fun colorFun(r1: Int, c1: Int, r2: Int, c2: Int, r3: Int, c3: Int) {
        _clickable.value = false
        gameArray.value[r1][c1].color = Color.Green
        gameArray.value[r2][c2].color = Color.Green
        gameArray.value[r3][c3].color = Color.Green
    }

    fun reset() {
        gameArray.value = arrayOf(
            arrayOf(DataModel(), DataModel(), DataModel()),
            arrayOf(DataModel(), DataModel(), DataModel()),
            arrayOf(DataModel(), DataModel(), DataModel()),
        )
        _player.value = 1
        _error.value = ""
        _clickable.value = true
    }
}