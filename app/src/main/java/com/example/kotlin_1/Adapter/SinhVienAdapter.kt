package com.example.kotlin_1.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.kotlin_1.Model.SinhVien
import com.example.kotlin_1.R

class SinhVienAdapter(val context : Context, val layoutId : Int, val list : ArrayList<SinhVien>) : BaseAdapter()  {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any {
        return list.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    class ViewHolder (row : View) {
        val tvName : TextView = row.findViewById(R.id.tvName)
        val tvAge : TextView = row.findViewById(R.id.tvAge)
        val btnPlusAge : Button = row.findViewById(R.id.btnPlusAge)
        val btnMinusAge : Button = row.findViewById(R.id.btnMinusAge)
    }

    override fun getView(position: Int, convertView: View?, group: ViewGroup?): View? {
        var view : View?
        var viewHolder : ViewHolder
        if(convertView == null) {
            var inflater : LayoutInflater = LayoutInflater.from(context)
            view = inflater.inflate(layoutId, null)
            viewHolder = ViewHolder(view)
            view.setTag(viewHolder)
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.tvName.text = list.get(position).name
        viewHolder.tvAge.text = list.get(position).age.toString()

        viewHolder.btnPlusAge.setOnClickListener {
            list.get(position).plusAge()
            viewHolder.tvAge.text = list.get(position).age.toString()
        }
        viewHolder.btnMinusAge.setOnClickListener {
            list.get(position).minusAge()
            viewHolder.tvAge.text = list.get(position).age.toString()
        }
        return view
    }
}