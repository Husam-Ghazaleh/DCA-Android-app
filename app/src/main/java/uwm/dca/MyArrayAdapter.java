
package uwm.dca;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class MyArrayAdapter extends ArrayAdapter<ActivityModel> {

    private ArrayList<ActivityModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView description;
        TextView datetime;
        TextView value;
    }

    public MyArrayAdapter(ArrayList<ActivityModel> data, Context context) {
        super(context, R.layout.row_activity, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ActivityModel dataModel = getItem(position);

        //ActivityModel dataModel = dataSet.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        //final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_activity, parent, false);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.description.setText(dataModel.getDescription());
            viewHolder.datetime = (TextView) convertView.findViewById(R.id.datetime);
            viewHolder.value = (TextView) convertView.findViewById(R.id.value);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            //result=convertView;
        }
        viewHolder.description.setText(dataModel.getDescription());
        viewHolder.datetime.setText(dataModel.getDate());
        viewHolder.value.setText(dataModel.getValue());

        // Return the completed view to render on screen
        return convertView;
    }
}