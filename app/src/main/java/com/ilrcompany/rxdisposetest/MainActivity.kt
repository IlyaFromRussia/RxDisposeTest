package com.ilrcompany.rxdisposetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val disposables = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val start = findViewById<Button>(R.id.btnStart)
        start.setOnClickListener {
            startWork()
            Toast.makeText(this, "Work started", Toast.LENGTH_SHORT).show()
        }

        val dispose = findViewById<Button>(R.id.btnDispose)
        dispose.setOnClickListener {
            disposables.clear()
            Toast.makeText(this, "Disposables cleared", Toast.LENGTH_SHORT).show()
        }

    }


    private fun startWork() {
        val d = Single.fromCallable{ longWork() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(this, "Work canceled!", Toast.LENGTH_SHORT).show()
            }, {})

        disposables.add(d)
    }

    private fun longWork() {
        for (i in 0..1000000000) {
            if (i % 1000 == 0) Log.d("Ilya, ", "$i")
        }
    }
}