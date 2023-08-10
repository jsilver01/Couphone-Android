
import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kuit.couphone.BaseItemAdapter
import com.kuit.couphone.R
import com.kuit.couphone.data.StoreInfo
import com.kuit.couphone.data.StoreResult
import com.kuit.couphone.databinding.ItemStoreBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class StoreAdapter(private val itemList : ArrayList<StoreResult>) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(itemList: StoreResult)
    }
    fun setOnItemClickListener(onItemClickListener: StoreAdapter.OnItemClickListener){
        itemClickListener = onItemClickListener
    }

    inner class ViewHolder(val binding : ItemStoreBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(storeInfo: StoreResult){
            binding.storeNameTv.text = storeInfo.getBrandResponse.name
            binding.storeInfoTv.text = storeInfo.getBrandResponse.rewardDescription
//            binding.categoryImgIv.setImageResource(storeInfo.getBrandResponse.brandImageUrl)
            Glide.with(binding.root).load(storeInfo.getBrandResponse.brandImageUrl).into(binding.categoryImgIv)
//            binding.categoryImgIv.setImageURI(storeInfo.getBrandResponse.brandImageUrl)
            binding.itemWebtoonCl.setOnClickListener{
                itemClickListener.onItemClick(storeInfo)
            }
            if(storeInfo.getBrandResponse.stampCount !=0 ) {
                binding.couphoneCountBackgroundIv.text =
                    storeInfo.getBrandResponse.stampCount.toString() + " / 10"

                var sf = SimpleDateFormat("yyyy-MM-dd")
                var date = sf.parse(storeInfo.getBrandResponse.createdDate)
                var today = Calendar.getInstance()

                var calcuDate = 183 -(today.time.time - date.time) / (60 * 60 * 24 * 1000)

                binding.couphoneDdayBackgroundIv.text = "D-$calcuDate"
            }
            else{
                binding.couphoneCountBackgroundIv.visibility = View.GONE
                binding.couphoneDdayBackgroundIv.visibility = View.GONE
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreAdapter.ViewHolder {
        val binding = ItemStoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: StoreAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int =itemList.size

}
