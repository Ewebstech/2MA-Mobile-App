package com.avardonigltd.mobilemedicalaid.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.model.Packages;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder>{
    private List<Packages.DataOfPackage> info;
    private OnClickButtonListenerPB listenerPB;

    public interface OnClickButtonListenerPB {
        void onClickToView(Packages.DataOfPackage info, String ClientID);
    }

    public PackageAdapter(OnClickButtonListenerPB listenerPB) {
        this.listenerPB = listenerPB;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_recycler_layout, parent, false);
        return new ViewHolder(view);
    }

    public void swapItem(List<Packages.DataOfPackage> newItem) {
        if (newItem == null) return;
        if (info != null) info.clear();
        info = newItem;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Packages.DataOfPackage populator = info.get(position);
        holder.onBindView(populator);
    }

    @Override
    public int getItemCount() {
        return info == null ? 0 : info.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, price;
        CardView package_card;
        ImageView package_icon;
        public ViewHolder(View itemView) {
            super(itemView);
           title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            package_card = itemView.findViewById(R.id.package_card);
            package_icon  = itemView.findViewById(R.id.package_icon);
        }

        public void onBindView(Packages.DataOfPackage info) {
           title.setText(info.getTitle());
            price.setText(info.getPrice());
            package_card.setOnClickListener(this);
            package_icon.setImageResource(info.getImg());
        }

        @Override
        public void onClick(View v) {
            Packages.DataOfPackage details = info.get(getAdapterPosition());
            String ClientId = "Client_id";
            if (listenerPB != null) {
                listenerPB.onClickToView(details,ClientId);
            }
        }
    }
}
