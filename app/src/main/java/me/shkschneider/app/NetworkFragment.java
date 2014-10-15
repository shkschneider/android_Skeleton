package me.shkschneider.app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import me.shkschneider.skeleton.ActivityHelper;
import me.shkschneider.skeleton.GsonParser;
import me.shkschneider.skeleton.MyListView;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.StringHelper;

public class NetworkFragment extends SkeletonFragment {

    private ArrayAdapter<SpannableStringBuilder> mAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_network, container, false);

        final MyListView myListView = (MyListView) view.findViewById(R.id.mylistview);
        mAdapter = new ArrayAdapter<SpannableStringBuilder>(skeletonActivity(), android.R.layout.simple_list_item_1);
        myListView.setAdapter(mAdapter);
        myListView.setCallback(new MyListView.Callback() {
            @Override
            public void overscroll(final int n) {
                if (!skeletonActivity().loading()) {
                    skeletonActivity().charging(n);
                }
            }

            @Override
            public void overscroll() {
                refresh();
            }

            @Override
            public void bottom() {
                ActivityHelper.toast("bottom");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();
    }

    public void refresh() {
        skeletonActivity().loading(true);
        Ion.with(this)
                .load("http://ifconfig.me/all.json")
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<JsonObject> result) {
                        skeletonActivity().loading(false);

                        final int responseCode = result.getHeaders().getResponseCode();
                        final String responseMessage = result.getHeaders().getResponseMessage();
                        if (responseCode >= 400) {
                            ActivityHelper.croutonRed(skeletonActivity(), String.format("%d: %s", responseCode, responseMessage));
                        }
                        else {
                            ActivityHelper.croutonGreen(skeletonActivity(), String.format("%d: %s", responseCode, responseMessage));
                        }
                        final JsonObject response = result.getResult();

                        for (final String key : GsonParser.keys(response)) {
                            final String value = GsonParser.string(response, key);
                            if (! StringHelper.nullOrEmpty(value)) {
                                final String string = String.format("%s: %s", key, value);
                                final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
                                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, string.indexOf(" "), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                                mAdapter.add(spannableStringBuilder);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

}
