package galen.nycschools.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import galen.nycschools.R;
import galen.nycschools.datamodels.SchoolGeneralInfo;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.SchoolCardViewHolder> {
    private static final String TAG = "SchoolAdapter";
    private final FragmentManager manager;
    private final SchoolGeneralInfo[] schools;

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
                    school.isRecommended() ? View.VISIBLE : View.INVISIBLE
            );
        }
    }

    public SchoolAdapter(FragmentManager manager, SchoolGeneralInfo[] schools) {
        this.manager = manager;
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
        holder.itemView.setOnClickListener(
                v -> {
                    manager.beginTransaction()
                            .add(R.id.app_body_container, LoadingFragment.class, null)
                            .commit();

//                    model.getSelectedSchool().observe((LifecycleOwner) v, o -> {
//                        manager.beginTransaction()
//                                .remove(Objects.requireNonNull(manager.findFragmentById(R.id.app_body_container)))
//                                .setReorderingAllowed(true)
//                                .addToBackStack(null)
//                                .add(R.id.app_body_container, SchoolDetailsFragment.class, null)
//                                .commit();
//                    });
                }
        );
    }

    @Override
    public int getItemCount() {
        return schools.length;
    }
}
