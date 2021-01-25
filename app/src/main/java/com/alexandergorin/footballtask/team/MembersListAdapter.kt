package com.alexandergorin.footballtask.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexandergorin.domain.models.Member
import com.alexandergorin.footballtask.databinding.MemberItemBinding

class MembersListAdapter(
    private val items: List<Member>
) : RecyclerView.Adapter<MembersListAdapter.MainListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val view = MemberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MainListViewHolder(
        private val itemBinding: MemberItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(member: Member) {
            itemBinding.name.text = member.name
            itemBinding.position.text = member.position
            itemBinding.nationality.text = member.nationality
            itemBinding.dateOfBirth.text = member.dateOfBirth.toString()
        }
    }
}
