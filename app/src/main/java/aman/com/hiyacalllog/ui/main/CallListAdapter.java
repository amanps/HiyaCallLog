package aman.com.hiyacalllog.ui.main;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import aman.com.hiyacalllog.R;
import aman.com.hiyacalllog.model.CallLogItem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amanpreetsingh on 3/15/18.
 */

public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.ViewHolder> {

    ArrayList<CallLogItem> mCallLogItems;

    public CallListAdapter(ArrayList<CallLogItem> callLogItems) {
        mCallLogItems = callLogItems;
    }

    @Override
    public CallListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CallListAdapter.ViewHolder holder, int position) {
        CallLogItem call = mCallLogItems.get(position);
        holder.mPhoneNumber.setText(call.getPhoneNumber());
        String date = ", " + call.getDate();
        Spannable type = new SpannableString(call.getType());
        int typeColor = call.getTypeColor() == Color.GREEN ? Color.parseColor("#00A86B") : Color.RED;
        type.setSpan(new ForegroundColorSpan(typeColor), 0, type.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.mDateType.setText(type);
        holder.mDateType.append(date);
    }

    @Override
    public int getItemCount() {
        return mCallLogItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.phone_number) TextView mPhoneNumber;
        @BindView(R.id.date_type) TextView mDateType;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
