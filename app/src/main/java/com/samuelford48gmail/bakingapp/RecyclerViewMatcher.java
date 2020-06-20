package com.samuelford48gmail.bakingapp;

import android.content.res.Resources;
import android.util.EventLogTags;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.regex.Matcher;

public class RecyclerViewMatcher {
    private final int recyclerViewId;

    public RecyclerViewMatcher(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    // public Matcher atPosition(final int position) {
    // return atPositionOnView(position, -1);
    //  }

   /* public Matcher atPositionOnView(final int position, final int targetViewId) {

       return new Matcher<View>() {
            Resources resources = null;
            View childView;

            public void describeTo(String description) {
                String idDescription = Integer.toString(recyclerViewId);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(recyclerViewId);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)",
                                new Object[] { Integer.valueOf
                                        (recyclerViewId) });
                    }
                }

                description+=("with id: " + idDescription);
            }


            public boolean matchesSafely(View view) {

                this.resources = view.getResources();

                if (childView == null) {
                    RecyclerView recyclerView =
                            (RecyclerView) view.getRootView().findViewById(recyclerViewId);
                    if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                        childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                    }
                    else {
                        return false;
                    }
                }

                if (targetViewId == -1) {
                    return view == childView;
                } else {
                    View targetView = childView.findViewById(targetViewId);
                    return view == targetView;
                }

            }
        };*/

}