package com.cursoandroid.organizze.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.model.Movimentacao;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterMovimentacao extends RecyclerView.Adapter<AdapterMovimentacao.MyViewHolder> {

    private List<Movimentacao> listaMovimentacao;
    Context context;

    public AdapterMovimentacao(List<Movimentacao> lista, Context context) {
        this.listaMovimentacao = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movimentacao, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movimentacao movimentacao = listaMovimentacao.get(position);
        holder.txtDescricao.setText(movimentacao.getDescricao());

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String valorFormatado = decimalFormat.format(movimentacao.getValor());
        holder.txtValor.setText(valorFormatado);

        holder.txtTitulo.setText(movimentacao.getCategoria());
        holder.txtValor.setTextColor(context.getResources().getColor(R.color.colorAccentReceita));


        if(movimentacao.getTipo().equals("d")){
            holder.txtValor.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.txtValor.setText("-"+valorFormatado);
        }
    }

    @Override
    public int getItemCount() {
        return listaMovimentacao.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtDescricao;
        TextView txtValor;
        TextView txtTitulo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txtCategoria);
            txtValor = itemView.findViewById(R.id.txtValor);
            txtDescricao = itemView.findViewById(R.id.txtDescricao);
        }
    }
}
