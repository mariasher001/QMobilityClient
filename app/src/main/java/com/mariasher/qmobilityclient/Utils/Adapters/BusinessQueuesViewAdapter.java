package com.mariasher.qmobilityclient.Utils.Adapters;

import static com.mariasher.qmobilityclient.Utils.Adapters.ClientBusinessViewAdapter.BUSINESS_ID;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mariasher.qmobilityclient.R;
import com.mariasher.qmobilityclient.clientActivities.BusinessQueuesViewActivity;
import com.mariasher.qmobilityclient.clientActivities.ViewQueueDetailsAndEnterActivity;
import com.mariasher.qmobilityclient.database.Queue;
import com.mariasher.qmobilityclient.databinding.QueueDataCardLayoutBinding;

import java.util.List;

public class BusinessQueuesViewAdapter extends RecyclerView.Adapter<BusinessQueuesViewAdapter.BusinessQueuesViewHolder> {

    public static final String QUEUE_ID = "queueId";
    private List<Queue> queues;
    private Context context;
    private String businessID;
    private BusinessQueuesViewActivity businessQueuesViewActivity;

    public BusinessQueuesViewAdapter(List<Queue> queues, String businessID, Context context, BusinessQueuesViewActivity businessQueuesViewActivity) {
        this.queues = queues;
        this.businessID = businessID;
        this.context = context;
        this.businessQueuesViewActivity = businessQueuesViewActivity;
    }

    public class BusinessQueuesViewHolder extends RecyclerView.ViewHolder {
        private QueueDataCardLayoutBinding binding;

        public BusinessQueuesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = QueueDataCardLayoutBinding.bind(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return queues.size();
    }

    @NonNull
    @Override
    public BusinessQueuesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.queue_data_card_layout, parent, false);
        return new BusinessQueuesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessQueuesViewHolder holder, int position) {
        holder.binding.queueNameCardTextView.setText(queues.get(position).getQueueName());
        holder.binding.queueStatusCardTextView.setText(queues.get(position).getQueueStatus());
        holder.binding.numberOfClientsInQueueCardTextView.setText("" + queues.get(position).getClientsInQueue().size());

        holder.binding.queueDataCardView.setOnClickListener(v -> {
            queueDataCardViewClicked(queues.get(position).getQueueId());
        });
    }

    private void queueDataCardViewClicked(String queueId) {
        Intent intent = new Intent(context, ViewQueueDetailsAndEnterActivity.class);
        intent.putExtra(BUSINESS_ID, businessID);
        intent.putExtra(QUEUE_ID, queueId);
        context.startActivity(intent);
        businessQueuesViewActivity.finish();
    }
}
