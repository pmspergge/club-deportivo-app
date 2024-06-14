import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.R

class AdaptadorTablaCliente(
    private val context: Context,
    private val datos: List<DatosTablaClientes>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AdaptadorTablaCliente.ViewHolder>() {

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre_)
        val textViewTipo: TextView = itemView.findViewById(R.id.textViewTipo)
        val textViewEdit: TextView = itemView.findViewById(R.id.textView_edit)
        val textViewElim: TextView = itemView.findViewById(R.id.textView_elim)

        init {
            textViewEdit.setOnClickListener {
                itemClickListener.onEditClick(adapterPosition)
            }
            textViewElim.setOnClickListener {
                itemClickListener.onDeleteClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(context).inflate(R.layout.fila_tabla_cliente, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dato = datos[position]
        holder.textViewNombre.text = dato.nombre
        holder.textViewTipo.text = dato.tipo
        holder.textViewEdit.text = dato.edit
        holder.textViewElim.text = dato.elim
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}

