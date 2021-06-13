package com.kabos.pokedex.ui.pokedex

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kabos.pokedex.databinding.AdapterPokedexBinding
import com.kabos.pokedex.databinding.AdapterPokedexBinding.inflate
import com.kabos.pokedex.model.Pokemon

class PokedexAdapter(private val onClick: (Pokemon) -> Unit)
    :    androidx.recyclerview.widget.ListAdapter<Pokemon, PokedexAdapter.PokedexViewHolder>(DiffCallback) {

    class PokedexViewHolder(private val binding: AdapterPokedexBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pokemon, onClick: (Pokemon) -> Unit){
            binding.apply {
                pokemonData = item
                root.setOnClickListener {
                    onClick(item)
                }
                Log.d("tasssssss","${item.type_one?.name}/${item.type_one?.image}/${item.sprite}")
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = inflate(layoutInflater, parent, false)
        return PokedexViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokedexViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}

private object DiffCallback: DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.name == newItem.name
    }
}
