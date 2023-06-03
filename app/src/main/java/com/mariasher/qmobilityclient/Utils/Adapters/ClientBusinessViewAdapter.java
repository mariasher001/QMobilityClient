package com.mariasher.qmobilityclient.Utils.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mariasher.qmobilityclient.R;
import com.mariasher.qmobilityclient.clientActivities.BusinessQueuesViewActivity;
import com.mariasher.qmobilityclient.clientActivities.ClientBusinessViewActivity;
import com.mariasher.qmobilityclient.database.BusinessInfo;
import com.mariasher.qmobilityclient.databinding.BusinessDataCardLayoutBinding;

import java.util.List;

public class ClientBusinessViewAdapter extends RecyclerView.Adapter<ClientBusinessViewAdapter.ClientBusinessViewHolder> {

    public static final String BUSINESS_ID = "businessID";
    private List<BusinessInfo> businessInfoList;
    private Context context;
    private ClientBusinessViewActivity clientBusinessViewActivity;

    public ClientBusinessViewAdapter(List<BusinessInfo> businessInfoList, Context context, ClientBusinessViewActivity clientBusinessViewActivity) {
        this.businessInfoList = businessInfoList;
        this.context = context;
        this.clientBusinessViewActivity = clientBusinessViewActivity;
    }

    public class ClientBusinessViewHolder extends RecyclerView.ViewHolder {

        private BusinessDataCardLayoutBinding binding;

        public ClientBusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = BusinessDataCardLayoutBinding.bind(itemView);
        }
    }


    @Override
    public int getItemCount() {
        return businessInfoList.size();
    }

    @NonNull
    @Override
    public ClientBusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_data_card_layout, parent, false);
        return new ClientBusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientBusinessViewHolder holder, int position) {
        holder.binding.businessNameCardTextView.setText(businessInfoList.get(position).getBusinessName());
        holder.binding.businessTypeCardTextView.setText(businessInfoList.get(position).getBusinessType());
        holder.binding.businessAddressCardTextView.setText(businessInfoList.get(position).getBusinessAddress());

        holder.binding.businessDataCardView.setOnClickListener(v -> {
            businessDataCardViewClicked(businessInfoList.get(position).getBusinessID());
        });
    }

    private void businessDataCardViewClicked(String businessID) {
        Intent intent = new Intent(context, BusinessQueuesViewActivity.class);
        intent.putExtra(BUSINESS_ID, businessID);
        context.startActivity(intent);
        clientBusinessViewActivity.finish();
    }

}
