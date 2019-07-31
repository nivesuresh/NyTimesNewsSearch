package mfp.com.nytimesnewssearch.mvp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mfp.com.nytimesnewssearch.R;
import mfp.com.nytimesnewssearch.models.DocsModel;
import mfp.com.nytimesnewssearch.models.MultimediaModel;

/**
 * Created by nivesuresh on 7/28/19.
 */

public class NewsSearchAdapter extends RecyclerView.Adapter<NewsSearchAdapter.NewsViewHolder> {

    List<DocsModel> docsList;
    Context context;

    public NewsSearchAdapter(Context context, List<DocsModel> resultsList) {
        this.docsList = resultsList;
        this.context = context;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView headline;
        private TextView summary_short;
        private ImageView multimedia;

        NewsViewHolder(View mView) {
            super(mView);
            headline = mView.findViewById(R.id.headline);
            summary_short = mView.findViewById(R.id.summary_short);
            multimedia = mView.findViewById(R.id.multimedia);
        }
    }

    /**
     * getItemViewType and getItemId is essential so we don't have duplicate data
     * in the recyclerview.
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        DocsModel model = docsList.get(position);


        holder.headline.setText(model.getHeadline().getMain());
        holder.summary_short.setText(model.getLead_paragraph());
        if (model.getMultimediaModelList() != null && model.getMultimediaModelList().size() > 0) {
            String url = String.format(context.getResources().getString(R.string.image_url), model.getMultimediaModelList().get(0).getUrl());
            Picasso.get().load(url).into(holder.multimedia);
        } else {
            holder.multimedia.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    @Override
    public int getItemCount() {
        return docsList.size();
    }
}
