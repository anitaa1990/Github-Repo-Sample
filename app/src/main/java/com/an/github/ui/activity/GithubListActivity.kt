package com.an.github.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.an.github.AppConstants
import com.an.github.R
import com.an.github.databinding.GithubListActivityBinding
import com.an.github.datasource.state.LoadingState
import com.an.github.ui.adapter.GithubListAdapter
import com.an.github.ui.views.RecyclerItemClickListener
import com.an.github.viewmodel.GithubListViewModel


class GithubListActivity : AppCompatActivity(), RecyclerItemClickListener.OnItemClickListener, AppConstants {

    private lateinit var binding: GithubListActivityBinding

    private lateinit var githubListAdapter: GithubListAdapter
    private lateinit var githubListViewModel: GithubListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModel()
    }


    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_list)
        binding.listRepo.layoutManager = LinearLayoutManager(applicationContext)
        githubListAdapter = GithubListAdapter()
        binding.listRepo.adapter = githubListAdapter
        binding.listRepo.addOnItemTouchListener(RecyclerItemClickListener(applicationContext, binding.listRepo, this))
    }



    private fun initViewModel() {
        githubListViewModel = ViewModelProviders.of(this).get(GithubListViewModel::class.java)
        githubListViewModel.githubLiveData.observe(this, Observer { repositories ->
            githubListAdapter.submitList(repositories)
        })


        githubListViewModel.loadingState.observe(this, Observer { networkState ->
            githubListAdapter.setLoadingState(networkState!!)
        })


        githubListViewModel.refreshState.observe(this, Observer { networkState ->
            if(githubListViewModel.isRefreshing(networkState!!)) {
                showLoader()

            } else if(githubListViewModel.hasFailed(networkState)) {
                showErrorMessage(getString(R.string.text_empty))

            } else hideLoader()
        })
    }



    private fun showLoader() {
        binding.includedLayout.root.visibility = View.VISIBLE
        binding.listRepo.visibility = View.GONE
    }



    private fun hideLoader() {
        binding.includedLayout.root.visibility = View.GONE
        binding.listRepo.visibility = View.VISIBLE
    }



    private fun showErrorMessage(message: String) {
        binding.includedLayout.root.visibility = View.VISIBLE
        binding.listRepo.visibility = View.GONE
        binding.includedLayout.errorMsg.text = message
        binding.includedLayout.progressBar.visibility = View.INVISIBLE
    }



    override fun onItemClick(view: View, position: Int) {
        val intent = Intent(applicationContext, GithubDetailActivity::class.java)
        intent.putExtra(INTENT_DETAIL, githubListAdapter.getItem(position))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                Pair(view.findViewById(R.id.item_title), getString(R.string.transition_name)),
                Pair(view.findViewById(R.id.item_desc) , getString(R.string.transition_desc)),
                Pair(view.findViewById(R.id.item_user_name) , getString(R.string.transition_owner)))

        startActivity(intent, options.toBundle())
    }
}