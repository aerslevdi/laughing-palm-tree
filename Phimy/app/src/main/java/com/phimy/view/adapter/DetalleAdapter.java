package com.phimy.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.phimy.R;
import com.phimy.model.Cast;
import com.phimy.model.MovieDB;
import com.phimy.view.fragment.DetalleFragment;

import java.util.List;

public class DetalleAdapter extends RecyclerView.Adapter<DetalleAdapter.DetalleAdapterViewHolder> {

    private List<Cast> casts;

    public DetalleAdapter(List<Cast> casts) {
        this.casts = casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetalleAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_actor, parent, false);
        DetalleAdapterViewHolder actorViewHolder = new DetalleAdapterViewHolder(view);
        return actorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleAdapterViewHolder holder, int position) {
        Cast cast = casts.get(position);
        holder.cargar(cast);
    }

    public void setCast (List<Cast> casting){
        casts = casting;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    class DetalleAdapterViewHolder extends RecyclerView.ViewHolder {


        private TextView nombreActor;
        private ImageView imagenPerfil;

        public DetalleAdapterViewHolder(final View itemView) {
            super(itemView);
            nombreActor = itemView.findViewById(R.id.actorNombre);
            imagenPerfil = itemView.findViewById(R.id.imageActor);


        }

        public void cargar (Cast cast){
            nombreActor.setText(cast.getName());

            String path = cast.getProfile_path();

            Glide.with(itemView.getContext()).load("http://image.tmdb.org/t/p/w185/"+path).into(imagenPerfil);

        }
    }
}
