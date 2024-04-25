package com.devcuong.mycooking.setup;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchHistory {
    private static final String PREFS_NAME = "search_history";
    private static final String KEY_HISTORY = "history";

    private final SharedPreferences prefs;

    public SearchHistory(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void addToHistory(String query) {
        List<String> history = getHistory();
        history.remove(query);
        history.add(0, query);
        saveHistory(history);
    }

    public List<String> getHistory() {
        String historyString = prefs.getString(KEY_HISTORY, "");
        return new ArrayList<>(Arrays.asList(historyString.split(",")));
    }

    private void saveHistory(List<String> history) {
        String historyString = TextUtils.join(",", history);
        prefs.edit().putString(KEY_HISTORY, historyString).apply();
    }

    public void removeFromHistory(String query) {
        List<String> history = getHistory();
        history.remove(query);
        saveHistory(history);
    }
}
