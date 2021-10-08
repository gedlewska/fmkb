package com.example.fmkb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment(), OrderClickInterface {

    private val viewModel: OrderViewModel by activityViewModels()
    lateinit var ordersRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val button: FloatingActionButton = view.findViewById(R.id.refreshBtn)

        ordersRv = view.findViewById(R.id.ordersRv)
        ordersRv.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = OrderAdapter(requireActivity(), this)
        ordersRv.adapter = adapter

        button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            ordersRv.visibility = View.GONE
            button.isEnabled = false
            viewModel.refreshOrders()
        }

        if (viewModel.allOrders.value == null) {
            button.performClick()
        }

        viewModel.allOrders.observe(requireActivity(), { list ->
            list?.let {
                progressBar.visibility = View.GONE
                ordersRv.visibility = View.VISIBLE
                button.isEnabled = true
                adapter.updateList(list)
            }
        })

        viewModel.orderList.observe(requireActivity(), {
            viewModel.insertAllOrders(it)
        })

        viewModel.errorMessage.observe(requireActivity(), {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        return view
    }

    override fun onOrderClick(order: Order) {
        if (requireActivity().findViewById<View>(R.id.containerB) == null) {
            requireActivity().supportFragmentManager.commit {
                replace<OrderDetailsFragment>(R.id.container)
                setReorderingAllowed(true)
                addToBackStack("OrderDetailsFragment")
            }
        }
        viewModel.setUrl(UrlUtils.getUrl(order.description))
    }
}