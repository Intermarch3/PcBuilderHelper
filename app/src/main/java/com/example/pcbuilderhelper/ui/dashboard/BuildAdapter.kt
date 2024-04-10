// BuildAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilderhelper.R
import com.example.pcbuilderhelper.models.BuildComponent

class BuildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.build_name)
}

class BuildAdapter(private var builds: List<BuildComponent>, private val onAddBuildClicked: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_BUILD = 0
        private const val VIEW_TYPE_ADD_BUILD = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_BUILD) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_build, parent, false)
            // Récupérez la largeur de l'écran
            val displayMetrics = parent.context.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels - 150

            // Définissez la hauteur et la largeur de la vue à la moitié de la largeur de l'écran
            val layoutParams = view.layoutParams
            layoutParams.height = screenWidth / 2
            layoutParams.width = screenWidth / 2
            view.layoutParams = layoutParams
            BuildViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_add_build, parent, false)
            view.setOnClickListener { onAddBuildClicked() }
            object : RecyclerView.ViewHolder(view) {}
        }
    }

    override fun getItemCount() = builds.size + 1 // +1 for the add build button

    override fun getItemViewType(position: Int): Int {
        return if (position < builds.size) VIEW_TYPE_BUILD else VIEW_TYPE_ADD_BUILD
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_BUILD) {
            val build = builds[position]
            (holder as BuildViewHolder).name.text = build.name
        }
    }

    fun updateBuilds(newBuilds: List<BuildComponent>) {
        this.builds = newBuilds
        notifyDataSetChanged()
    }
}