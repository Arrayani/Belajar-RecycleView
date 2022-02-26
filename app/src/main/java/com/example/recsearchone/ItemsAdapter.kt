package com.example.recsearchone


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recsearchone.ItemsAdapter.itemAdapterVH
//ini cara nya unik, perlu di pelajari
//fase terakhir masukan lib filterable
class ItemsAdapter
    (var clickListener:ClickListener)
    : RecyclerView.Adapter<itemAdapterVH>(),Filterable {

    var itemsModalList = ArrayList<ItemsModal>()
    var itemsModalListFilter = ArrayList<ItemsModal>()//fase search


    fun setData(itemsModalList:ArrayList<ItemsModal>){
        this.itemsModalList = itemsModalList
        this.itemsModalListFilter = itemsModalList//fase search
        notifyDataSetChanged() //mendengar apakah ada perubahan di data

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemAdapterVH {
        return itemAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.row_items,parent,false))

    }

    override fun onBindViewHolder(holder: itemAdapterVH, position: Int) {
        val itemsModal = itemsModalList[position]
        holder.name.text=itemsModal.name
        holder.desc.text=itemsModal.desc
        holder.image.setImageResource(itemsModal.image)

        //fase dua
        holder.itemView.setOnClickListener{
            clickListener.ClickedItem(itemsModal)
        }
    }

    override fun getItemCount(): Int {
        return itemsModalList.size
    }

    class itemAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.tvName)
        val desc : TextView = itemView.findViewById(R.id.tvDesc)
        val image : ImageView = itemView.findViewById(R.id.imageView)

    }

    //fase dua
    interface ClickListener{
        fun ClickedItem(itemsModal: ItemsModal)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence : CharSequence?): FilterResults {
                val filterResults = FilterResults();
                if (charSequence==null || charSequence.length<0){
                    filterResults.count = itemsModalListFilter.size
                }else
                {
                    var searchChr =charSequence.toString().lowercase()
                    var itemModal = ArrayList<ItemsModal>()

                    //ini cara memfilternya
                    for (item in itemsModalListFilter){
                        if (item.name.contains(searchChr)||item.desc.contains(searchChr)){
                            itemModal.add(item)
                        }
                    }
                    filterResults.count = itemModal.size
                    filterResults.values = itemModal
                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
               itemsModalList =  filterResults!!.values as ArrayList<ItemsModal>
                notifyDataSetChanged()
            }

        }
    }

}