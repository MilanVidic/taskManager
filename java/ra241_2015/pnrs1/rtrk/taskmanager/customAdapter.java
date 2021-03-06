package ra241_2015.pnrs1.rtrk.taskmanager;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


class customAdapter extends BaseAdapter {

    private Context mContext;
    Task task;

    ArrayList<Task> getmTasks() {
        return mTasks;
    }

    private ArrayList<Task> mTasks;

    customAdapter(Context context) {
        mContext = context;
        mTasks = new ArrayList<>();
    }

    void addTask(Task task) {
        mTasks.add(task);
        notifyDataSetChanged();

    }


    void update(Task[] tasks) {
        mTasks.clear();
        if(tasks != null) {
            Collections.addAll(mTasks, tasks);
        }

        notifyDataSetChanged();
    }


    void editTask(String name,String description, int image, String date, String time, int alarm, int position, int checked) {

        mTasks.get(position).setmName(name);
        mTasks.get(position).setmDescription(description);
        mTasks.get(position).setmAlarm(alarm);
        mTasks.get(position).setmDate(date);
        mTasks.get(position).setmTime(time);
        mTasks.get(position).setmImage(image);
        mTasks.get(position).setmChecked(checked);

        notifyDataSetChanged();

    }


    void removeTask(int i) {
        mTasks.remove(i);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Object getItem(int position) {
        Object rv = null;
        try {
            rv = mTasks.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task_element, null);
            ViewHolder holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.rowImageView);
            holder.text = (TextView) view.findViewById(R.id.rowTextView);
            holder.date = (TextView) view.findViewById(R.id.rowTextViewDate);
            holder.check = (CheckBox) view.findViewById(R.id.checkBox);
            holder.alarm = (ImageView) view.findViewById(R.id.alarm);
            view.setTag(holder);
        }


        Task task = (Task) getItem(position);
        final ViewHolder holder = (ViewHolder) view.getTag();

        holder.image.setImageResource(task.mImage);
        holder.text.setText(task.mName);
        holder.date.setText(task.mDate);
        holder.alarm.setImageResource(task.mAlarm);
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    holder.text.setPaintFlags(holder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    Task task = MainActivity.mTaskDataBase.readTask(String.valueOf(position));
                    Log.d("milan", "Task name = " + task.getmName());
                    task.setmChecked(1);
                    MainActivity.mTaskDataBase.updateTask(task, String.valueOf(position));

                } else {
                    holder.text.setPaintFlags(holder.text.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);


                    Task task = MainActivity.mTaskDataBase.readTask((String.valueOf(position)));
                    Log.d("milan", "Task name = " + task.getmName());
                    task.setmChecked(0);
                    MainActivity.mTaskDataBase.updateTask(task, String.valueOf(position));
                }

            }
        });

        return view;
    }


    private class ViewHolder {
        ImageView image = null;
        TextView text = null;
        TextView date = null;
        CheckBox check = null;
        ImageView alarm = null;
    }

}