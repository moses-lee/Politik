package com.wordpress.necessitateapps.politik.Services;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;
import com.wordpress.necessitateapps.politik.R;

import java.util.HashSet;
import java.util.List;

//public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {
//
//    private List<ArticleGetter> articleList;
//
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView artTitle;
//        TextView artTitle2;
//        TextView artSummary;
//        TextView artSource;
//        TextView artBias;
//        Button artButton;
//        ImageView topicImage;
//        ImageView topicImage2;
//
//
//        MyViewHolder(View view) {
//            super(view);
//
//            artTitle = view.findViewById(R.id.title_text);
//            artTitle2 = view.findViewById(R.id.title_text2);
//            artSummary = view.findViewById(R.id.summary_text);
//            artSource = view.findViewById(R.id.text_source);
//            artBias = view.findViewById(R.id.text_bias);
//            artButton = view.findViewById(R.id.button_link);
//            topicImage = view.findViewById(R.id.image_article);
//            topicImage2 = view.findViewById(R.id.image_article2);
//
//        }
//    }
//
//
//    public ArticleAdapter(FragmentActivity activity, List<ArticleGetter> articleList) {
//        this.articleList = articleList;
//
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.article_item, parent, false);
//
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        final ArticleGetter articleGetter = articleList.get(position);
//        holder.artTitle.setText(articleGetter.getTitle());
//        holder.artTitle2.setText(articleGetter.getTitle());
//        holder.artSummary.setText(articleGetter.getSummary());
//        holder.artSource.setText(articleGetter.getSource());
//
//        //gets bias
//        String biasText = "";
//        ArticleBias articleBias = new ArticleBias();
//
//        switch (articleBias.getBias(articleGetter.getSource())) {
//            case 0: biasText = "Left";
//                break;
//            case 1: biasText = "Left-Center";
//                break;
//            case 2: biasText = "Center";
//                break;
//            case 3: biasText = "Right-Center";
//                break;
//            case 4: biasText = "Right";
//                break;
//            case 6: biasText = "No Data";
//                break;
//        }
//
//        holder.artBias.setText(biasText);
//
//        //final Context mContext=holder.topicImage.getContext();
//        Picasso.get().load(articleGetter.getImage()).fit().centerCrop().into(holder.topicImage);
//        Picasso.get().load(articleGetter.getImage()).fit().centerCrop().into(holder.topicImage2);
//
//        final Context mContext = holder.artTitle.getContext();
//        holder.artButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //*** missing 'http://' will cause crashed
//                Uri uri = Uri.parse(articleGetter.getUrl());
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                mContext.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return articleList.size();
//    }
//
////    private void deleteAction(Context mContext, String name, MyViewHolder holder){
////        String filename = "saved";
////        String filename_notes= "saved_notes";
////
////
////        LinkedHashMap hashSaved = null;
////        HashMap hashNotes=null;
////        //read from internal file
////        try {
////            //get hashmap as object
////            FileInputStream inputStream = mContext.openFileInput(filename);
////            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
////            hashSaved = (LinkedHashMap) objectInputStream.readObject();
////            objectInputStream.close();
////
////            //reads from notes file
////            FileInputStream inputStreamNotes = mContext.openFileInput(filename_notes);
////            ObjectInputStream objectInputStreamNotes = new ObjectInputStream(inputStreamNotes);
////            hashNotes = (HashMap) objectInputStreamNotes.readObject();
////            objectInputStreamNotes.close();
////
////        }catch (ClassNotFoundException | IOException e){
////            e.printStackTrace();
////        }
////
////        //removes value from the hashmap
////        if (hashSaved != null) {
////            hashSaved.remove(name);
////        }
////        if (hashNotes != null) {
////            hashNotes.remove(name);
////        }
////
////        //write to internal file, saves hashmap as object
////        try {
////            FileOutputStream outputStream = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
////            ObjectOutputStream objectOutputStream= new ObjectOutputStream(outputStream);
////            objectOutputStream.writeObject(hashSaved);
////            outputStream.close();
////
////            //writes to notes file
////            FileOutputStream outputStreamNotes = mContext.openFileOutput(filename_notes, Context.MODE_PRIVATE);
////            ObjectOutputStream objectOutputStreamNotes= new ObjectOutputStream(outputStreamNotes);
////            objectOutputStreamNotes.writeObject(hashNotes);
////            objectOutputStreamNotes.close();
////
////            //updates the recyclerview
////            //updateRecylerview(holder.getAdapterPosition());
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
////    private void updateRecylerview(int position){
////        //removes item from araylist
////        savedList.remove(position);
////        SavedAdapter mAdapter = new SavedAdapter(savedList);
////        notifyItemRemoved(position);
////        notifyItemRangeChanged(position, savedList.size());
////        mAdapter.notifyDataSetChanged();
////    }
//
//}

@SuppressWarnings({"WeakerAccess", "unused"})
public class ArticleAdapter extends ArrayAdapter<ArticleGetter> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;

    public ArticleAdapter(Context context, List<ArticleGetter> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        final ArticleGetter article = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.article_item, parent, false);

            // binding view parts to view holder
            viewHolder.artTopic = cell.findViewById(R.id.text_topic);
            viewHolder.artTitle = cell.findViewById(R.id.title_text);
            viewHolder.artTitle2 = cell.findViewById(R.id.title_text2);
            viewHolder.artSummary = cell.findViewById(R.id.text_summary);
            viewHolder.artSource = cell.findViewById(R.id.text_source);
            viewHolder.artBias = cell.findViewById(R.id.text_bias);
            viewHolder.artButton = cell.findViewById(R.id.button_link);
            viewHolder. topicImage = cell.findViewById(R.id.image_article);
            viewHolder.topicImage2 = cell.findViewById(R.id.image_article2);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == article)
            return cell;

        // bind data from selected element to view through view holder
        String topic = '#' + article.getTopic().toLowerCase();
        viewHolder.artTopic.setText(topic);
        viewHolder.artTitle.setText(article.getTitle());
        viewHolder.artTitle2.setText(article.getTitle());
        viewHolder.artSummary.setText(article.getSummary());
        viewHolder.artSource.setText(article.getSource());

        //gets bias
        String biasText = "";
        ArticleBias articleBias = new ArticleBias();

        switch (articleBias.getBias(article.getSource())) {
            case 0: biasText = "Left";
                break;
            case 1: biasText = "Left-Center";
                break;
            case 2: biasText = "Center";
                break;
            case 3: biasText = "Right-Center";
                break;
            case 4: biasText = "Right";
                break;
            case 6: biasText = "No Data";
                break;
        }

        viewHolder.artBias.setText(biasText);

        //final Context mContext=holder.topicImage.getContext();
        Picasso.get().load(article.getImage()).fit().centerCrop().into(viewHolder.topicImage);
        Picasso.get().load(article.getImage()).fit().centerCrop().into(viewHolder.topicImage2);

        final Context mContext = viewHolder.artTitle.getContext();
        viewHolder.artButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //*** missing 'http://' will cause crashed
                Uri uri = Uri.parse(article.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });

        sharedPref = mContext.getSharedPreferences("AI", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        return cell;
    }

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    long counter = 0;
    // simple methods for register cell state changes
    public void registerToggle(int position) {
        //AI BOI
//        ArticleGetter article = getItem(position);
//        assert article != null;
//        String topic = article.getTopic();
//        counter = sharedPref.getLong(topic, 0);
//        counter++;
//        editor.putLong(topic, 0);
//        editor.apply();

        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);

    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);

    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView artTopic;
        TextView artTitle;
        TextView artTitle2;
        TextView artSummary;
        TextView artSource;
        TextView artBias;
        Button artButton;
        ImageView topicImage;
        ImageView topicImage2;
    }
}