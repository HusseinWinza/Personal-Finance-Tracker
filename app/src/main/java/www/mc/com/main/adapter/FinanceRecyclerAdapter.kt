package www.mc.com.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import www.mc.com.R
import www.mc.com.databinding.ItemRecyclerViewBinding
import www.mc.com.main.model.Finance
import www.mc.com.utils.loadImage

/**
 * Created by SegunFrancis
 */

class FinanceRecyclerAdapter :
    ListAdapter<Finance, FinanceRecyclerAdapter.FinanceViewHolder>(FinanceDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceViewHolder {
        return FinanceViewHolder(
            ItemRecyclerViewBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_view, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: FinanceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FinanceViewHolder(private val binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Finance) = with(binding) {
            titleTextView.text = item.title
            costTextView.text = "$".plus(item.amount.toString())
            subTitleTextView.text = item.details ?: ""
            dateTextView.text = item.dataAdded.toString()
            if (item.category == "Salary" || item.category == "Business" || item.category == "Others") {
                earningImage.loadImage(R.drawable.ic_receive_cash)
            } else {
                earningImage.loadImage(R.drawable.ic_card_payment)
            }
        }
    }

    class FinanceDiffUtil : DiffUtil.ItemCallback<Finance>() {

        override fun areItemsTheSame(oldItem: Finance, newItem: Finance): Boolean {
            return oldItem.dataAdded == newItem.dataAdded
        }


        override fun areContentsTheSame(oldItem: Finance, newItem: Finance): Boolean {
            return oldItem == newItem
        }
    }
}