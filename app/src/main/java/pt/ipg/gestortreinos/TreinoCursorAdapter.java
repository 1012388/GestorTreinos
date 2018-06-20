package pt.ipg.gestortreinos;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TreinoCursorAdapter extends RecyclerView.Adapter<TreinoCursorAdapter.TreinoViewHolder> {
    private Context context;
    private Cursor cursor = null;
    private View.OnClickListener viewHolderClickListener = null;
    private int lastTreinoClicked = -1;

    public TreinoCursorAdapter(Context context) {//CONSTRUTOR
        this.context = context;
    }

    public void refreshData(Cursor cursor) {
        if (this.cursor != cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public void setViewHolderClickListener(View.OnClickListener viewHolderClickListener) {
        this.viewHolderClickListener = viewHolderClickListener;
    }

    public int getLastTreinoClicked() {
        return lastTreinoClicked;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML

     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */

    @NonNull
    @Override
    public TreinoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_treino, parent, false);

        return new TreinoViewHolder(item);
    }


    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     */
    @Override
    public void onBindViewHolder(@NonNull TreinoViewHolder holder, int position) {
        cursor.moveToPosition(position);

        Treinos treino = DBTableTreino.getCurrentTreinoFromCursor(cursor);
        holder.setTreino(treino);
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (cursor == null) return 0;

        return cursor.getCount();
    }

    public class TreinoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewExercicio;
        private TextView textViewPeso;
        private TextView textViewReps;
        private TextView textViewSeries;
        private int treinoId;

        public TreinoViewHolder(View itemView) {
            super(itemView);

            textViewExercicio = (TextView) itemView.findViewById(R.id.textViewExercicio);
            textViewPeso = (TextView) itemView.findViewById(R.id.textViewPeso);
            textViewReps = (TextView) itemView.findViewById(R.id.textViewReps);
            textViewSeries = (TextView) itemView.findViewById(R.id.textViewSeries);

            itemView.setOnClickListener(this);
        }

        public void setTreino(Treinos treino) {
            textViewExercicio.setText(treino.getExercicio());
            textViewPeso.setText(treino.getPesoUsado());
            textViewReps.setText(treino.getRepeticoes());
            textViewSeries.setText(treino.getSeries());

            treinoId = treino.getTreinoId();
        }

        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position == RecyclerView.NO_POSITION) {
                return;
            }

            if (viewHolderClickListener != null) {
                lastTreinoClicked = treinoId;
                viewHolderClickListener.onClick(v);
            }
        }

    }
}
