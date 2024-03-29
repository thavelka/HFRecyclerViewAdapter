# HFRecyclerViewAdapter
[![](https://jitpack.io/v/thavelka/HFRecyclerViewAdapter.svg)](https://jitpack.io/#thavelka/HFRecyclerViewAdapter)


A RecyclerView Adapter with support for dynamically adding an arbitrary number of header and footer views above and below the list content. This allows you to easily add search bars, filters, refresh buttons, loading indicators, etc. that scroll with the list content without fussing with item types and ViewHolders in the adapter.

![In use](/sample/sample.gif)

## Gradle dependency
HFRecyclerViewAdapter is a single file that can can be copied into your project and modified freely, but if you just want to use it as-is, the library is available as a gradle dependency on JitPack.

In your root project build.gradle file, add the JitPack repository to the bottom of your repository list.
```
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```

In your app module build.gradle file, add the HFRecyclerViewAdapter dependency.
```
dependencies {
    implementation 'com.github.thavelka:HFRecyclerViewAdapter:1.0.0'
}
```

## Usage
See the attached sample project for an implementation of HFRecyclerViewAdapter.

When creating your adapter class, extend HFRecyclerViewAdapter instead of RecyclerView.Adapter and implement the required abstract methods `onCreateItemViewHolder`, `onBindItemViewHolder`, and `getCount`. Use these methods to configure and display your regular list items. 

Once the adapter has been created, use `.addHeader(View)` and `.addFooter(View)` on the adapter to add views above and below the list content.

```
adapter = new ExampleAdapter(this, items);
recyclerView.setAdapter(adapter);

// Add some headers
View header1 = getLayoutInflater().inflate(R.layout.header1, recyclerView, false);
View header2 = getLayoutInflater().inflate(R.layout.header2, recyclerView, false);
adapter.addHeaderView(header1);
adapter.addHeaderView(header2);

// Add footer
View footerView = getLayoutInflater().inflate(R.layout.footer_loading, recyclerView, false);
adapter.addFooterView(footerView);
```

HFRecyclerViewAdapter can also automatically manage an empty view. Designate a view with `.setEmptyView(View)` and HFRecyclerView will display the view when the main list content is empty and hide the view when list content is present. This is based on your regular list items, and the presence of headers and footers will not affect the item count. The empty view can be one of the header or footer views, but it does not have to be one. 

```
// Set empty view
View emptyView = findViewById(R.id.text_empty);
adapter.setEmptyView(emptyView);
```

## Caveats
There is no out of the box support for multiple list item types in HFRecyclerView. It is recommended that you use this adapter when you only have one type of regular list item. It is possible to implement multiple item types, but you should take a good look at the source code before overriding `getItemViewType`.
