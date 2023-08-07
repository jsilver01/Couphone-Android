import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuit.couphone.BaseItemAdapter
import com.kuit.couphone.data.StoreInfo
import com.kuit.couphone.databinding.ItemStoreBinding

class StoreAdapter(private val itemList : ArrayList<StoreInfo>) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(itemList: StoreInfo)
    }
    fun setOnItemClickListener(onItemClickListener: StoreAdapter.OnItemClickListener){
        itemClickListener = onItemClickListener
    }
    inner class ViewHolder(val binding : ItemStoreBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(storeInfo: StoreInfo){
            binding.storeNameTv.text = storeInfo.store_name
            binding.storeInfoTv.text = storeInfo.store_couphone_info
            binding.itemWebtoonCl.setOnClickListener{
                itemClickListener.onItemClick(storeInfo)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreAdapter.ViewHolder {
        val binding = ItemStoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int =itemList.size

}