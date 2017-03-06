package com.tianchuang.ihome_b.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;

public class ItemRemoveViewHolder extends RecyclerView.ViewHolder {
    public   TextView price;
    public TextView content;
    public TextView delete;
    public LinearLayout layout;

    public ItemRemoveViewHolder(View itemView) {
        super(itemView);
        content = (TextView) itemView.findViewById(R.id.item_content);
        delete = (TextView) itemView.findViewById(R.id.item_delete);
        layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
        price = ((TextView) itemView.findViewById(R.id.item_price));
    }
}
