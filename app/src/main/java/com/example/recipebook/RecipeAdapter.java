package com.example.recipebook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recipebook.model.Recipe;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ItemClickListener clickListener;
    private List<Recipe> data;
    private Context context;

    public RecipeAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_database_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Recipe> newData) {
        if(data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            data = newData;
        }
    }

    public Recipe getItem(int position) {
        return data.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeName;
        RatingBar ratingBar;

        ViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.dbRecipeName);
            ratingBar = itemView.findViewById(R.id.dbRatingBar);
            itemView.setOnClickListener(this);
        }

        void bind(final Recipe recipe) {
            if(recipe != null) {
                recipeName.setText(recipe.getName());
                ratingBar.setRating(recipe.getRating());
            }
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
