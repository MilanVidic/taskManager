package ra241_2015.pnrs1.rtrk.taskmanager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class TaskDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "task";
    private static final String COLUMN_TASK_NAME = "TaskName";
    private static final String COLUMN_TASK_DESCRIPTION = "TaskDescription";
    private static final String COLUMN_IMAGE = "Image";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_TIME = "Time";
    private static final String COLUMN_ALARM = "Alarm";
   // private static final String COLUMN_CHECKED = "Checked";


    TaskDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_TASK_NAME + " TEXT, " +
                COLUMN_TASK_DESCRIPTION + " TEXT, " +
                COLUMN_IMAGE + " INTEGER, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_ALARM + " TEXT);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void insert(Task task) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, task.getmName());
        values.put(COLUMN_TASK_DESCRIPTION, task.getmDescription());
        values.put(COLUMN_IMAGE, task.getmImage());
        values.put(COLUMN_DATE, task.getmDate());
        values.put(COLUMN_TIME, task.getmTime());
        values.put(COLUMN_ALARM, task.getmAlarm());

        db.insert(TABLE_NAME, null, values);
        close();
    }

    Task[] readTasks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }

        Task[] tasks = new Task[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            tasks[i++] = createTask(cursor);
        }

        close();
        return tasks;
    }

    public Task readTask(String taskName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_TASK_NAME + "=?",
                new String[] {taskName}, null, null, null);
        cursor.moveToFirst();
        Task task = createTask(cursor);

        close();
        return task;
    }

    void deleteTask(String taskName) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_TASK_NAME + "=?", new String[] {taskName});
        close();
    }

    private Task createTask(Cursor cursor) {
        String taskName = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
        String taskDescription = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION));
        int image = cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGE));
        String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
        String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
        int alarm = cursor.getInt(cursor.getColumnIndex(COLUMN_ALARM));

        return new Task(taskName, taskDescription, image, date, time, alarm);
    }
}
