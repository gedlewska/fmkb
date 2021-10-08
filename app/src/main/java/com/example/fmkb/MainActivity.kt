package com.example.fmkb

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.fmkb.fragment.MainFragment
import com.example.fmkb.fragment.OrderDetailsFragment
import com.example.fmkb.viewModel.OrderViewModel
import com.example.fmkb.viewModel.OrderViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {

            val viewModel = ViewModelProvider(
                this,
                OrderViewModelFactory((application as OrdersApplication).repository)
            )
                .get(OrderViewModel::class.java)

            if (findViewById<View>(R.id.containerB) == null) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<MainFragment>(R.id.container)
                }
            } else {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<MainFragment>(R.id.containerA)
                    add<OrderDetailsFragment>(R.id.containerB)
                }
            }
        }
    }
}