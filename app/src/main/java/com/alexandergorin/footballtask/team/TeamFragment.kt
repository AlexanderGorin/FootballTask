package com.alexandergorin.footballtask.team

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.load
import com.alexandergorin.footballtask.App
import com.alexandergorin.footballtask.ViewBindingFragment
import com.alexandergorin.footballtask.databinding.FragmentTeamBinding
import com.alexandergorin.footballtask.utils.observeNotNull
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class TeamFragment : ViewBindingFragment<FragmentTeamBinding>(FragmentTeamBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var svgImageLoader: ImageLoader

    private val viewModel: TeamViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.load()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorEvent.observeNotNull(viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        }

        viewModel.teamState.observeNotNull(viewLifecycleOwner) {
            when (it) {
                TeamState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.teamImage.isVisible = false
                    binding.teamTitle.isVisible = false
                    binding.recyclerView.isVisible = false
                }
                is TeamState.Loaded -> {
                    binding.progressBar.isVisible = false
                    binding.teamImage.isVisible = true
                    binding.teamTitle.isVisible = true
                    binding.recyclerView.isVisible = true
                    binding.teamTitle.text = it.team.name
                    binding.teamImage.load(it.team.logoURL, svgImageLoader)
                    binding.recyclerView.adapter = MembersListAdapter(it.team.teamMembers)
                }
                is TeamState.Error -> {
                    binding.progressBar.isVisible = false
                    binding.teamImage.isVisible = false
                    binding.teamTitle.isVisible = false
                    binding.recyclerView.isVisible = false
                }
            }
        }
    }

    companion object {
        fun newInstance() = TeamFragment()
    }
}
