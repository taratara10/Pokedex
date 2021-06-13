package com.kabos.pokedex.util

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class InfiniteScrollListener(
        private val layoutManager: LinearLayoutManager,
        val func: () -> Unit /*呼び出し元でcallback引数として{}を実装*/) : RecyclerView.OnScrollListener() {

    private var loading = true
    private var visibleThreshold = 5 //閾値
    private var firstVisibleItem = 0 //adapter position
    private var visibleItemCount = 0 //recyclerView
    private var totalItemCount = 0   //layoutManager
    private var previousTotal = 0    //previous update totalItemCount

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollVertically(1)) func()

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount;
            totalItemCount = layoutManager.itemCount;
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

            //Check loading is completed.
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            //Check threshold and get new item
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold /*最後の行から２つ上*/)) {
                // End has been reached
                Log.i("InfiniteScrollListener", "End reached")
                func() //呼び出し元で実装
                loading = true
            }
        }
    }

}
