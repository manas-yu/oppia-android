package org.oppia.android.app.help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.oppia.android.app.fragment.FragmentScope
import org.oppia.android.app.recyclerview.BindableAdapter
import org.oppia.android.databinding.HelpFragmentBinding
import org.oppia.android.databinding.HelpItemBinding
import javax.inject.Inject

/** The presenter for [HelpFragment]. */
@FragmentScope
class HelpFragmentPresenter @Inject constructor(
  private val activity: AppCompatActivity,
  private val fragment: Fragment,
  private val helpListViewModel: HelpListViewModel,
  private val singleTypeBuilderFactory: BindableAdapter.SingleTypeBuilder.Factory
) {
  private lateinit var binding: HelpFragmentBinding

  fun handleCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    isMultipane: Boolean
  ): View? {
    val viewModel = helpListViewModel
    viewModel.isMultipane.set(isMultipane)

    binding = HelpFragmentBinding.inflate(
      inflater,
      container,
      /* attachToRoot = */ false
    )
    binding.helpFragmentRecyclerView.apply {
      layoutManager = LinearLayoutManager(activity.applicationContext)
      adapter = createRecyclerViewAdapter()
    }

    binding.let {
      it.lifecycleOwner = fragment
      it.viewModel = viewModel
    }
    return binding.root
  }

  private fun createRecyclerViewAdapter(): BindableAdapter<HelpItemViewModel> {
    return singleTypeBuilderFactory.create<HelpItemViewModel>()
      .registerViewDataBinderWithSameModelType(
        inflateDataBinding = HelpItemBinding::inflate,
        setViewModel = HelpItemBinding::setViewModel
      ).build()
  }
}
