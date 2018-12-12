package com.phimy.view.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.phimy.R;
import com.phimy.controller.ControllerMovieDB;
import com.phimy.helper.ItemTouchHelperAdapter;
import com.phimy.model.MovieDB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements ItemTouchHelperAdapter, Filterable {
    private List<MovieDB> movieList;
    private Integer resources;
    private Drawable imageFavorito;
    private Drawable imageNoFavorito;
    private ControllerMovieDB controllerMovieDB;
    private Receptor receptor;
    final static public Integer KEY_TAG_FAVORITO = 0;
    final static public Integer KEY_TAG_NOFAVORITO = 1;

    public MovieAdapter(Receptor receptor, List<MovieDB> movieList, Integer resources, Drawable imageFavorito, Drawable imageNoFavorito) {
        this.movieList = movieList;
        this.resources = resources;
        this.imageFavorito=imageFavorito;
        this.imageNoFavorito=imageNoFavorito;
        this.receptor=receptor;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int position) {
        final MovieDB movieDB = movieList.get(position);
        movieViewHolder.load(movieDB);
        movieViewHolder.controlFavoritos(movieDB);

        movieViewHolder.favoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer icon= (Integer) movieViewHolder.favoriteImage.getTag();
                if (icon == KEY_TAG_FAVORITO){
                    Toast.makeText(view.getContext(), "agregar favoritos" , Toast.LENGTH_SHORT).show();
                    movieViewHolder.favoriteImage.setCompoundDrawablesWithIntrinsicBounds(imageFavorito,
                            null, null, null );
                    movieViewHolder.favoriteImage.setTag(KEY_TAG_NOFAVORITO);
                    controllerMovieDB.getInstance().addFavoritos(movieDB);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(view.getContext(), "eliminar favoritos" , Toast.LENGTH_SHORT).show();
                    movieViewHolder.favoriteImage.setCompoundDrawablesWithIntrinsicBounds(imageNoFavorito,
                            null, null, null );
                    movieViewHolder.favoriteImage.setTag(KEY_TAG_FAVORITO);
                    controllerMovieDB.getInstance().removeFavoritos(movieDB);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public Boolean onItemMove(int fromPosition, int toPosition) {
        return null;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView movieImage;
        private Button favoriteImage;
        private TextView artistaName;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.moviePoster);
            artistaName = itemView.findViewById(R.id.artista);
            favoriteImage= itemView.findViewById(R.id.butonFavoritos);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MovieDB movieDB = movieList.get(getAdapterPosition());
                    receptor.recibir(movieDB);
                }
            });
        }

        public void load(MovieDB movie) {
            String path = movie.getPoster_path();
            //TODO sacar la URL de las imagenes a una variable o values
            Glide.with(itemView.getContext()).load("http://image.tmdb.org/t/p/w185/"+movie.getPoster_path()).into(movieImage);
            artistaName.setText(movie.getTitle());
        }

        public void controlFavoritos(MovieDB movieDB){
            //TODO controlar si la peli está o no en favoritos
            if (controllerMovieDB.getInstance().isFavorito(movieDB)) {
                //Asigno a todos los no favoritos
                favoriteImage.setCompoundDrawablesWithIntrinsicBounds(imageFavorito,
                        null, null, null);
                favoriteImage.setTag(KEY_TAG_NOFAVORITO);
            } else{
                favoriteImage.setCompoundDrawablesWithIntrinsicBounds(imageNoFavorito,
                        null, null, null);
                favoriteImage.setTag(KEY_TAG_FAVORITO);
            }
        }
    }

    public List<MovieDB> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieDB> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public interface Receptor{
        void recibir(MovieDB movieDB);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<MovieDB> filterList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filterList.addAll(movieList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (MovieDB movie : movieList){
                        if(movie.getTitle().toLowerCase().trim().contains(filterPattern)) {
                            filterList.add(movie);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filterList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                movieList.clear();
                movieList.addAll((Collection<? extends MovieDB>) results.values);
                notifyDataSetChanged();
            }
        };
    }

}