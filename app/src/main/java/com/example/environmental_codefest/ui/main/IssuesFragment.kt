package com.example.environmental_codefest.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.environmental_codefest.MainActivity
import com.example.environmental_codefest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssuesFragment : Fragment() {
    companion object {
        const val TAG = "IssuesFragment"
        fun newInstance() = IssuesFragment()
    }

    private val viewModel: IssuesViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.issues_fragment, container, false)
        recyclerView = view.findViewById(R.id.issues_recycler_view)
        val itemDecoration = DividerItemDecoration(
            view.context,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(view.context, R.drawable.custom_divider)?.let {
            itemDecoration.setDrawable(
                it
            )
        }
        recyclerView.addItemDecoration(itemDecoration)

        (requireActivity() as MainActivity).supportActionBar?.title =
            resources.getString(R.string.app_name)

        fab = view.findViewById<View>(R.id.fab)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.issues.observe(viewLifecycleOwner) { issues ->
            // todo handle empty list state
            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = IssuesAdapter(issues, IssuesAdapter.OnClickListener { position ->
                (requireActivity() as MainActivity).navigateToIssueDetail(position)
            })
        }

        fab.setOnClickListener {
            (requireActivity() as MainActivity).navigateToIssueCreate()
        }
    }

}