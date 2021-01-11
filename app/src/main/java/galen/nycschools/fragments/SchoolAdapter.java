package galen.nycschools.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import galen.nycschools.NavigationManager;
import galen.nycschools.R;
import galen.nycschools.datamodels.SchoolGeneralInfo;
import galen.nycschools.fancybusinesslogic.SchoolAnalyzer;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.SchoolCardViewHolder> {
    private final SchoolGeneralInfo[] schools;
    private final NavigationManager.ExploreToDetailsCallback callback;
    public static class SchoolCardViewHolder extends RecyclerView.ViewHolder {
        public final View schoolCard;

        public SchoolCardViewHolder(@NonNull View itemView) {
            super(itemView);

            this.schoolCard = itemView;
        }

        public void populateCardFromSchool(SchoolGeneralInfo school) {
            ((TextView) schoolCard.findViewById(R.id.schoolName)).setText(school.name);
            ((TextView) schoolCard.findViewById(R.id.schoolAttr)).setText(school.location);
            schoolCard.findViewById(R.id.recommendedView).setVisibility(
                    SchoolAnalyzer.INSTANCE.isRecommended(school) ? View.VISIBLE : View.INVISIBLE
            );
        }
    }

    public SchoolAdapter(NavigationManager.ExploreToDetailsCallback callback, SchoolGeneralInfo[] schools) {
        this.schools = schools;
        this.callback = callback;
    }

    @NonNull
    @Override
    public SchoolAdapter.SchoolCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: Investigate using fragment or widget for the school card
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_card, parent, false);
        return new SchoolCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolAdapter.SchoolCardViewHolder holder, int position) {
        holder.populateCardFromSchool(schools[position]);
        holder.itemView.setOnClickListener( v -> callback.navigate(position));
    }

    @Override
    public int getItemCount() {
        return schools.length;
    }
}
