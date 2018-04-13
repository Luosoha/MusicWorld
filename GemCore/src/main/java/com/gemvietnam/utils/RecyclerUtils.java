package com.gemvietnam.utils;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Collection Utils
 * Created by neo on 7/20/2016.
 */
public class RecyclerUtils {

  private RecyclerUtils() {

  }

  public static void setupVerticalRecyclerView(Context context, RecyclerView mRecyclerView) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setClipToPadding(false);
    mRecyclerView.setHasFixedSize(true);
  }

  public static void setupHorizontalRecyclerView(Context context, RecyclerView mRecyclerView) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(context,
        LinearLayoutManager.HORIZONTAL, false);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setClipToPadding(false);
    mRecyclerView.setHasFixedSize(true);
  }

  public static void setupStaggeredVerticalRecyclerView(RecyclerView mRecyclerView, int spanCount) {
    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    mRecyclerView.setClipToPadding(false);
    mRecyclerView.setHasFixedSize(true);
  }

  /**
   * Common Setup for grid recycler view
   */
  public static void setupGridRecyclerView(Context context, RecyclerView mRecyclerView, int spanCount) {
    LinearLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setClipToPadding(false);
    mRecyclerView.setHasFixedSize(true);
  }
}
