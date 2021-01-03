package galen.nycschools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import galen.nycschools.datamodels.School;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.SchoolCardViewHolder> {

    private School[] schools;

    public static class SchoolCardViewHolder extends RecyclerView.ViewHolder {
        public final View schoolCard;

        public SchoolCardViewHolder(@NonNull View itemView) {
            super(itemView);

            this.schoolCard = itemView;
        }

        public void populateCardFromSchool(School school) {
            ((TextView) schoolCard.findViewById(R.id.schoolName)).setText(school.name);
            ((TextView) schoolCard.findViewById(R.id.schoolAttr)).setText(school.location);
            schoolCard.findViewById(R.id.recommendedView).setVisibility(
                    school.isRecommended() ? View.VISIBLE : View.INVISIBLE
            );
        }
    }

    public SchoolAdapter(School[] schools) {
        this.schools = schools;
    }

    @NonNull
    @Override
    public SchoolAdapter.SchoolCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_card, parent, false);
        return new SchoolCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolAdapter.SchoolCardViewHolder holder, int position) {
        holder.populateCardFromSchool(schools[position]);
    }

    @Override
    public int getItemCount() {
        return schools.length;
    }
}
