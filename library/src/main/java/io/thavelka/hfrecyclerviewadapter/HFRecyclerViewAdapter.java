package io.thavelka.hfrecyclerviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A RecyclerView Adapter with support for adding headers and footers dynamically.
 * <p>
 * To use, extend this class, implement the abstract methods, then call {@link #addHeaderView} or
 * {@link #addFooterView}.
 * <p>
 * The class also supports managing an empty view with
 * {@link #setEmptyView}, which will show or hide the specified View depending on the number of
 * list items.
 */
public abstract class HFRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = -1;
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;

    private LayoutInflater inflater;
    private View emptyView;
    private List<View> headerViews = new ArrayList<>();
    private List<View> footerViews =  new ArrayList<>();

    public HFRecyclerViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Override {@link #onCreateItemViewHolder} instead of this method unless you really know
     * what you're doing.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return onCreateItemViewHolder(parent, inflater);
        } else if (viewType <= TYPE_HEADER) {
            int headerIndex = TYPE_HEADER - viewType;
            return new HeaderViewHolder(headerViews.get(headerIndex));
        } else if (viewType >= TYPE_FOOTER) {
            int footerIndex = viewType - TYPE_FOOTER;
            return new FooterViewHolder(footerViews.get(footerIndex));
        } else {
            return null;
        }
    }

    /**
     * Override {@link #onBindItemViewHolder} instead of this method unless you really know what
     * you're doing.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof HeaderViewHolder) && !(holder instanceof FooterViewHolder)) {
            int adjustedPos = position - headerViews.size();
            onBindItemViewHolder(holder, adjustedPos);
        }
    }

    /**
     * Returns TYPE_HEADER, TYPE_ITEM, or TYPE_FOOTER. Don't override this method unless you
     * really know what you're doing. This class does not support multiple item types.
     */
    @Override
    public int getItemViewType(int position) {
        if (position < headerViews.size()) return TYPE_HEADER - position;
        int endOffset = position - (getItemCount() - footerViews.size());
        return endOffset >= 0 ? TYPE_FOOTER + endOffset : TYPE_ITEM;
    }

    /**
     * Returns regular item count + header and footer counts.
     */
    @Override
    public int getItemCount() {
        return getCount() + headerViews.size() + footerViews.size();
    }

    /**
     * Reloads all items and update empty view. Call this method from inside a change listener to
     * update the list when the data set changes.
     */
    public void refresh() {
        notifyDataSetChanged();
        if (emptyView != null) {
            emptyView.setVisibility(getCount() == 0 ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Sets the empty view to be managed by the adapter. The empty view will be shown when there
     * are no items to display or hidden when items are present. {@link #getCount()} is used to
     * determine the number of items, not {@link #getItemCount()}. Therefore, headers and footers
     * will not affect the count.
     * @param view The View to be managed or null to stop managing a View. This View can be a header
     *             or footer, but it does not have to be one.
     */
    public void setEmptyView(View view) {
        emptyView = view;
        if (emptyView != null) {
            emptyView.setVisibility(getCount() == 0 ? View.VISIBLE : View.GONE);
        }
    }

    public View getEmptyView() {
        return emptyView;
    }

    /**
     * Returns the list of header Views.
     */
    public List<View> getHeaderViews() {
        return headerViews;
    }

    /**
     * Adds a new header View. This View will be displayed above the regular list items but below
     * any existing headers.
     * @param view The View to be added as a header.
     */
    public void addHeaderView(View view) {
        headerViews.add(view);
        notifyItemInserted(headerViews.size() - 1);
    }

    /**
     * Removes a header view.
     * @param view The View to be removed.
     */
    public void removeHeaderView(View view) {
        int headerIndex = headerViews.indexOf(view);
        if (headerIndex >= 0) {
            headerViews.remove(headerIndex);
            notifyItemRemoved(headerIndex);
        }
    }

    /**
     * Returns the list of footer Views.
     */
    public List<View> getFooterViews() {
        return footerViews;
    }

    /**
     * Adds a new footer View. This View will be displayed below the regular list items and any
     * existing footers.
     * @param view The View to be added as a footer.
     */
    public void addFooterView(View view) {
        footerViews.add(view);
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * Removes a footer View.
     * @param view The View to be removed.
     */
    public void removeFooterView(View view) {
        int footerIndex = footerViews.indexOf(view);
        if (footerIndex >= 0) {
            int index = getItemCount() - footerViews.size() + footerIndex;
            footerViews.remove(footerIndex);
            notifyItemRemoved(index);
        }
    }

    /**
     * Adjusts for adapter position offset caused by header views. Use this method to get the list
     * index for the item at the specified adapter position. This is just a convenience method that
     * subtracts header count from the position. Note that the returned position could be below zero
     * or out of range if the provided position was for a header or footer View.
     */
    public int getAdjustedPosition(int position) {
        return position - headerViews.size();
    }

    /**
     * Provides the ViewHolder for rows with item type {@link #TYPE_ITEM}.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param inflater A LayoutInflater for inflating the ViewHolder's itemView.
     * @return A ViewHolder to be used for regular list items.
     */
    public abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, LayoutInflater inflater);

    /**
     * Configures the ViewHolder at the provided position. This will only be called for regular list
     * items, not headers or footers.
     * @param holder The ViewHolder provided by {@link #onCreateItemViewHolder}.
     * @param position The row position, adjusted for any header views. This position can be used to
     *                 retrieve the correct item from the data set. Note that this position will not
     *                 match the adapter position if any headers exist.
     */
    public abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position);

    /**
     * Returns the number of items in the data set.
     */
    public abstract int getCount();

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
