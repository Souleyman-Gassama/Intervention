package com.athand.intervention.ui.account.fragments.personal_info.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.athand.intervention.R
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.tools.get_Current_Fragment
import com.athand.intervention.ui.account.fragments.personal_info.adapter.CompanysRecyclerViewAdapter.CompanysViewHolder
import com.google.android.material.textview.MaterialTextView
/**
 * Cree le 14/01/2023 par Gassama Souleyman
 */
class CompanysRecyclerViewAdapter(val companyList: MutableList<MyCompany?>) :
    RecyclerView.Adapter<CompanysViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanysViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_company, parent, false)
        return CompanysViewHolder(view, companyList)
    }

    override fun onBindViewHolder(holder: CompanysViewHolder, position: Int) {
        holder.set_Data_To_View(companyList.get(position))
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    class CompanysViewHolder(view: View, val companyList: MutableList<MyCompany?>)
        : ViewHolder(view), OnClickListener, OnLongClickListener {

        private var nameCompany: MaterialTextView? = null
        var callbackCompanyRecyclerView: CallbackCompanyRecyclerView

        init {
            callbackCompanyRecyclerView =
                get_Current_Fragment( (view.context) as AppCompatActivity, R.id.nav_host_account )
                        as CallbackCompanyRecyclerView
            configuration_View(view)
            configuration_Listener()
        }

        private fun configuration_View(view: View) {
            nameCompany = view.findViewById(R.id.name_item_company)
        }

        fun set_Data_To_View(company: MyCompany?) {
            nameCompany?.text = company?.companyName
        }

        private fun configuration_Listener() {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v) {
            }
        }

        override fun onLongClick(v: View?): Boolean {
            start_PopupMenu(v?.context!!, v, companyList.get(adapterPosition)!!)
            return true
        }


        fun start_PopupMenu(context: Context, itemView: View, company: MyCompany) {
            val popupMenu = PopupMenu(context, itemView)
            popupMenu.menuInflater.inflate(R.menu.setting_update_delete, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                    item -> click_PopupMenu_Contact(item, company)
                true
            }
            popupMenu.show()
        }

        private fun click_PopupMenu_Contact(item: MenuItem, company: MyCompany) {
            when (item.itemId) {
                R.id.update_item_setting_update_delete -> update_Item(company)
                R.id.delete_item_setting_update_delete -> delete_Item(company)
            }
        }

        private fun update_Item(company: MyCompany) {
            callbackCompanyRecyclerView.click_Update_Company(company)
        }

        private fun delete_Item(company: MyCompany) {
            callbackCompanyRecyclerView.click_Delete_Company(company)
        }
    }

    interface CallbackCompanyRecyclerView{
        fun click_Update_Company(company: MyCompany)
        fun click_Delete_Company(company: MyCompany)
    }

}
