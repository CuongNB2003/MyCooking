package com.devcuong.mycooking.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.devcuong.mycooking.R;
import com.devcuong.mycooking.setup.CustomDialog;
import com.devcuong.mycooking.setup.SearchHistory;

import java.util.List;

public class SearchHistoryAdapter extends ArrayAdapter<String> {
    SearchHistory searchHistory = new SearchHistory(getContext());

    public SearchHistoryAdapter(Context context, List<String> history) {
        super(context, 0, history);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_history, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.tv_history);

        String query = getItem(position);
        Log.d("=================", "getView: "+ query);
        assert query != null;
        if(query.isEmpty()){
            textView.setVisibility(View.GONE);
        }else {
            textView.setText(query);
        }

        // Xử lý sự kiện nhấn lâu
        convertView.setOnLongClickListener(v -> {
            CustomDialog dialog = new CustomDialog(
                    getContext(),
                    "Xóa lịch sử tìm kiếm",
                    "Bạn có muốn xóa '" + query + "' khỏi lịch sử tìm kiếm không?",
                    (dialog1, which) -> {
                        searchHistory.removeFromHistory(query);
                        remove(query);
                        notifyDataSetChanged();
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
            );
            dialog.show();
            return true;
        });

        return convertView;
    }
}
