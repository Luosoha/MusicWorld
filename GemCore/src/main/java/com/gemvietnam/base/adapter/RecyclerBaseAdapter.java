package com.gemvietnam.base.adapter;

import com.gemvietnam.widget.BaseViewHolder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Adapter with load more
 * Created by neo on 8/19/2016.
 */
public abstract class RecyclerBaseAdapter<T, VH extends BaseViewHolder<T>>
    extends RecyclerView.Adapter<VH> {

  protected Context mContext;
  protected List<T> mItems = new ArrayList<>();

  public RecyclerBaseAdapter(Context context) {
    mContext = context;
  }

  public RecyclerBaseAdapter(Context context, List<T> items) {
    this(context);
    setItems(items);
  }

  /**
   * Get item in list
   */
  public T getItem(int position) {
    return mItems.get(position);
  }

  /**
   * Get items in list
   */
  public List<T> getItems() {
    return mItems;
  }

  @Override
  public int getItemCount() {
    return mItems.size();
  }

  /**
   * Set data for list
   */
  public void setItems(List<T> items) {
    mItems.clear();
    addItems(items);
  }

  /**
   * Add more item
   */
  public void addItem(T item) {
    mItems.add(item);
    notifyDataSetChanged();
  }

  /**
   * Add more item at position
   */
  public void addItem(int position, T item) {
    mItems.add(position, item);
    notifyItemInserted(0);
  }

  /**
   * Add/Load more items
   */
  public void addItems(List<T> items) {
    mItems.addAll(items);
    notifyDataSetChanged();
  }

  /**
   * Remove an item
   */
  public void removeItem(T item) {
    int position = mItems.lastIndexOf(item);
    if (position >= 0) {
      mItems.remove(position);
      notifyItemRemoved(position);
    }
  }

  /**
   * Remove an item at position
   */
  public void removeItem(int position) {
    if (position >= 0) {
      mItems.remove(position);
      notifyItemRemoved(position);
    }
  }

  /**
   * Refresh current items with new ones
   */
  public void refresh(List<T> items) {
    mItems.clear();
    mItems.addAll(items);
    notifyDataSetChanged();
  }

  public void clear() {
    mItems.clear();
    notifyDataSetChanged();
  }

  /**
   * Load more items
   */
  public void loadMore(List<T> items) {
    addItems(items);
  }

  @Override
  public void onBindViewHolder(VH holder, int position) {
    holder.bindView(getItem(position), position);
  }

  /**
   * Inflate view from view resource id
   */
  protected View inflateView(ViewGroup parent, @LayoutRes int viewId) {
    return LayoutInflater.from(mContext).inflate(viewId, parent, false);
  }
}
