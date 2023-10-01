package com.example.clicker.views.shop

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.clicker.R
import com.example.clicker.exceptions.NoSuchItemException
import com.example.clicker.exceptions.NotEnoughPointsException
import com.example.clicker.formatNumber
import com.example.clicker.model.game.GameRepository
import com.example.clicker.model.game.ShopItemInfo

class ShopListAdapter(private val context: Context, private val repository: GameRepository) :
    RecyclerView.Adapter<ItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ItemViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return repository.boughtItems.size
    }

    fun mapToListAdapter(): MutableList<ShopItemInfo> {
        val shopItemsList = mutableListOf<ShopItemInfo>()
        repository.boughtItems.forEach {
            val shopItemInfo = it.value
            shopItemInfo.itemName = it.key
            shopItemsList.add(shopItemInfo)
        }
        return shopItemsList
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val shopItemList = mapToListAdapter()
        val item = shopItemList[position]
        holder.iconImageView.setImageResource(item.image)
        holder.itemTitleTextView.text = item.itemName.toString().lowercase().replaceFirstChar { firstLetter -> firstLetter.uppercase() }
        holder.itemCountTextView.text = item.boughtTimes.toString()

        holder.itemPriceTextView.text = formatNumber(repository.getCurrentItemPrice(item.itemName))
        holder.iconImageView.setOnClickListener {
            try {
                repository.buyItem(item.itemName)
                holder.itemPriceTextView.text = formatNumber(repository.getCurrentItemPrice(item.itemName))
                holder.itemCountTextView.text = item.boughtTimes.toString()
                val successAnimation = AnimationUtils.loadAnimation(context, R.anim.click)
                holder.iconImageView.startAnimation(successAnimation)
            } catch (e: NotEnoughPointsException) {
                val failAnimation = AnimationUtils.loadAnimation(context, R.anim.fail)
                holder.iconImageView.startAnimation(failAnimation)
            } catch (e: NoSuchItemException) {
                Log.d("NoSuchItemException", "There is an error occurred when customer tried bought item ${e.printStackTrace()}")
            }
        }
    }


}