import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilderhelper.R
import com.example.pcbuilderhelper.api.Product

class ComponentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.component_name)
    val type: TextView = view.findViewById(R.id.component_type)
    val price: TextView = view.findViewById(R.id.component_price)
}

// 1. Créez la classe ComponentAdapter
class ComponentAdapter(private var components: List<Product>) : RecyclerView.Adapter<ComponentAdapter.ComponentViewHolder>() {

    class ComponentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.component_name)
        val type: TextView = view.findViewById(R.id.component_type)
        val price: TextView = view.findViewById(R.id.component_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_component, parent, false)
        return ComponentViewHolder(view)
    }

    override fun getItemCount() = components.size

    fun updateComponents(newComponents: List<Product>) {
        this.components = newComponents
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        val component = components[position]
        holder.name.text = component.label
        holder.type.text = component.sublabel
        holder.price.text = component.offer?.price?.price_final.toString()

        // Ajouter un OnClickListener à la vue racine de chaque élément de la liste
        holder.itemView.setOnClickListener {
            // Ici, vous pouvez ajouter le code à exécuter lorsque l'utilisateur clique sur le composant
            Log.d("ComponentAdapter", "Composant cliqué: ${component.label}")
        }
    }
}