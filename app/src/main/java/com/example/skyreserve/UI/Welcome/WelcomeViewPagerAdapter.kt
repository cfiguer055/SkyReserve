import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.skyreserve.R

class WelcomeViewPagerAdapter(
    val images: List<Int>
) : RecyclerView.Adapter<WelcomeViewPagerAdapter.WelcomeViewPagerViewHolder>() {

    inner class WelcomeViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.skyReserveWelcomeImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_welcome_view_pager, parent, false)
        return WelcomeViewPagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: WelcomeViewPagerViewHolder, position: Int) {
        val currentImage = images[position]
        holder.imageView.setImageResource(currentImage)
    }
}
