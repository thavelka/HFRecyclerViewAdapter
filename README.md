# HFRecyclerViewAdapter

A RecyclerView Adapter with support for dynamically adding an arbitrary number of header and footer views above and below the list content. This allows you to easily add search bars, filters, refresh buttons, loading indicators, etc. without fussing with item types and ViewHolders in the adapter.

![In use](/sample/sample.gif)

### To use
When creating your adapter class, extend HFRecyclerViewAdapter instead of RecyclerView.Adapter and implement the required abstract methods `onCreateItemViewHolder`, `onBindItemViewHolder`, and `getCount`. Use these methods to configure and display your regular list items. 

Once the adapter has been created, use `.addHeader(View)` and `.addFooter(View)` on the adapter to add views above and below the list content.

HFRecyclerViewAdapter can also automatically manage an empty view. Designate a view with `.setEmptyView(View)` and HFRecyclerView will display the view when the main list content is empty and hide the view when list content is present. This is based on your regular list items, and the presence of headers and footers will not affect the item count. The empty may be one of the header or footer views, but it does not have to be one. 

### Caveats
There is no out of the box support for multiple list item types in HFRecyclerView. It is recommended that you use this adapter when you only have one type of regular list item. It is possible to implement multiple item types, but you should take a good look at the source code before overriding `getItemViewType`.
