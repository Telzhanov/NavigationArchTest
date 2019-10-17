package com.example.navigationarchtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_example.view.*

class ProfileFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onClickListener = object: OnClickListener {
            override fun onClick(pos: Int) {
                val bundle = Bundle()
                bundle.putInt("index", pos)
                view.findNavController().navigate(R.id.to_detail, bundle)
            }

        }
        recycler_view.adapter = ExampleAdapter(20, onClickListener)
        recycler_view.layoutManager = LinearLayoutManager(context)
    }
}

class ExampleAdapter(val size: Int, val listener: OnClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExampleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_example, parent, false))
    }

    override fun getItemCount(): Int  = size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.index_view.text = position.toString()
        holder.itemView.setOnClickListener {
            listener.onClick(position)
        }
    }
}


class ExampleViewHolder(view: View): RecyclerView.ViewHolder(view)

interface OnClickListener {
    fun onClick(pos: Int)
}